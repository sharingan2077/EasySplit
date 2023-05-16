package com.example.easysplit.view.fragments.groups;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.easysplit.R;
import com.example.easysplit.databinding.FragmentGroupsBinding;
import com.example.easysplit.view.adapters.GroupsRecyclerAdapter;
import com.example.easysplit.view.listeners.CompleteListener;
import com.example.easysplit.view.utils.NavigationUtils;
import com.example.easysplit.viewModel.groups.GroupsViewModel;
import com.example.easysplit.viewModel.MainActivityViewModel;

public class GroupsFragment extends Fragment{

    private static final String TAG = "GroupsFragment";

    MainActivityViewModel mainActivityViewModel;
    FragmentGroupsBinding binding;
    private GroupsRecyclerAdapter adapter;
    NavController navController;
    GroupsViewModel groupsViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: groups");

        binding = FragmentGroupsBinding.inflate(inflater, container, false);
        navController = Navigation.findNavController(requireActivity(), R.id.navHostFragment);
//        progressing();
        groupsViewModel = new ViewModelProvider(requireActivity()).get(GroupsViewModel.class);
        groupsViewModel.init(new CompleteListener() {
            @Override
            public void successful() {
                adapter.notifyDataSetChanged();
                mainActivityViewModel.setCountOfGroups(adapter.getItemCount());

                Log.d(TAG, String.valueOf(adapter.getItemCount()));
                if (adapter.getItemCount() == 0)
                {
                    hideGroups();
                }
                else
                {
                    showGroups();
                }
            }
            @Override
            public void unSuccessful() {

            }
        });
        initRecyclerView();

        binding.createGroup.setOnClickListener(v ->
                NavigationUtils.navigateSafe(navController, R.id.action_groupsFragment_to_groupCreateFragment, null)
        );

        mainActivityViewModel = new ViewModelProvider(requireActivity()).get(MainActivityViewModel.class);
        final Observer<Boolean> isGoToExpenseObserver = aBoolean -> {
            Bundle bundle = new Bundle();
            bundle.putInt("ActionToLastFragment", R.id.action_addExpenseFragment_to_groupsFragment);
            if (aBoolean)
            {
                NavigationUtils.navigateSafe(navController, R.id.action_groupsFragment_to_addExpenseFragment, bundle);
            }
        };
        mainActivityViewModel.getIsGoToMakeExpense().observe(getViewLifecycleOwner(), isGoToExpenseObserver);
        mainActivityViewModel.showBottomNavigationBar();

//        final Observer<Boolean> isProgressingObserver = new Observer<Boolean>() {
//            @Override
//            public void onChanged(Boolean aBoolean) {
//                Log.d(TAG, "isProgressingObserver " + aBoolean.toString());
//                if (aBoolean)
//                {
//                    isProgressing = false;
//                }
//            }
//        };
//        groupsViewModel.getDataLoaded().observe(getViewLifecycleOwner(), isProgressingObserver);
//        final Observer<List<Group>> observerNewGroups = new Observer<List<Group>>() {
//            @Override
//            public void onChanged(List<Group> groups) {
//                Log.d(TAG, "ObserverNewGroups " + groups.size());
//                Log.d("Adpa", Integer.toString(adapter.getItemCount()));
//                if (groups.size() > adapter.getItemCount())
//                {
//                    Log.d(TAG, "MOre");
//                    adapter.notifyDataSetChanged();
//
//                }
//                adapter.notifyDataSetChanged();
//                if (isProgressing)
//                {
//                    progressing();
//                }
//                else if (groups.size() == 0 )
//                {
//                    hideGroups();
//                }
//                else if (groups.size() != 0)
//                {
//                    showGroups();
//                }
//            }
//        };
//        groupsViewModel.getGroups().observe(requireActivity(), observerNewGroups);

        return binding.getRoot();
    }

    private void initRecyclerView()
    {
        adapter = new GroupsRecyclerAdapter(getActivity(), groupsViewModel.getGroups().getValue(), new GroupsRecyclerAdapter.onGroupClickListener() {
            @Override
            public void onClick(String groupId, String nameOfGroup, int countGroupMembers) {
                Bundle bundle = new Bundle();
                bundle.putString("groupId", groupId);
                bundle.putString("nameOfGroup", nameOfGroup);
                bundle.putInt("countGroupMembers", countGroupMembers);
                NavigationUtils.navigateSafe(navController, R.id.action_groupsFragment_to_groupEnterFragment, bundle);
            }
        });

        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void progressing()
    {
        binding.progressBar.setVisibility(View.VISIBLE);
        binding.imgOfGroups.setVisibility(View.GONE);
        binding.txtEmptyGroups.setVisibility(View.GONE);
        binding.groups.setVisibility(View.GONE);
        binding.recyclerView.setVisibility(View.GONE);
        binding.createGroup.setVisibility(View.GONE);
    }

    private void showGroups()
    {
        binding.imgOfGroups.setVisibility(View.GONE);
        binding.txtEmptyGroups.setVisibility(View.GONE);
        binding.groups.setVisibility(View.VISIBLE);
        binding.recyclerView.setVisibility(View.VISIBLE);
        binding.progressBar.setVisibility(View.GONE);
        binding.createGroup.setVisibility(View.VISIBLE);
    }
    private void hideGroups()
    {
        binding.imgOfGroups.setVisibility(View.VISIBLE);
        binding.txtEmptyGroups.setVisibility(View.VISIBLE);
        binding.groups.setVisibility(View.GONE);
        binding.recyclerView.setVisibility(View.GONE);
        binding.progressBar.setVisibility(View.GONE);
        binding.createGroup.setVisibility(View.VISIBLE);
    }

}