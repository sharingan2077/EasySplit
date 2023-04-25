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
import com.example.easysplit.model.Group;
import com.example.easysplit.view.listeners.DataLoadListener;
import com.example.easysplit.view.utils.NavigationUtils;
import com.example.easysplit.view.adapters.GroupsRecyclerAdapter;
import com.example.easysplit.viewModel.AddExpenseViewModel;
import com.example.easysplit.viewModel.groups.GroupsViewModel;
import com.example.easysplit.viewModel.MainActivityViewModel;

import java.util.List;

public class GroupsFragment extends Fragment{

    private static final String TAG = "GroupsFragment";

    MainActivityViewModel mainActivityViewModel;
    AddExpenseViewModel addExpenseViewModel;
    FragmentGroupsBinding binding;
    private GroupsRecyclerAdapter adapter;
    NavController navController;
    GroupsViewModel groupsViewModel;

    private Boolean isProgressing = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: groups");
        binding = FragmentGroupsBinding.inflate(inflater, container, false);
        navController = Navigation.findNavController(requireActivity(), R.id.navHostFragment);
        groupsViewModel = new ViewModelProvider(requireActivity()).get(GroupsViewModel.class);
        groupsViewModel.init();
        initRecyclerView();
        mainActivityViewModel = new ViewModelProvider(requireActivity()).get(MainActivityViewModel.class);
        mainActivityViewModel.showBottomNavigationBar();
        addExpenseViewModel = new ViewModelProvider(requireActivity()).get(AddExpenseViewModel.class);
        addExpenseViewModel.setLastFragmentAction(R.id.action_addExpenseFragment_to_groupsFragment);


        binding.createGroup.setOnClickListener(v ->
                NavigationUtils.navigateSafe(navController, R.id.action_groupsFragment_to_groupCreateFragment, null)
        );
        final Observer<Boolean> isGoToExpenseObserver = aBoolean -> {
            if (aBoolean) NavigationUtils.navigateSafe(navController, R.id.action_groupsFragment_to_addExpenseFragment, null);
        };
        mainActivityViewModel.getIsGoToMakeExpense().observe(getViewLifecycleOwner(), isGoToExpenseObserver);

        final Observer<Boolean> isProgressingObserver = new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean)
                {
                    isProgressing = false;
                    Log.d("Prog", "is not progressing");;
                }
            }
        };
        groupsViewModel.getDataLoaded().observe(requireActivity(), isProgressingObserver);
        final Observer<List<Group>> observerNewGroups = new Observer<List<Group>>() {
            @Override
            public void onChanged(List<Group> groups) {
                adapter.notifyDataSetChanged();
                if (isProgressing)
                {
                    progressing();
                }
                else if (adapter.getItemCount() == 0)
                {
                    Log.d("Prog", "hide groups");
                    hideGroups();
                }
                else if (adapter.getItemCount() != 0)
                {
                    Log.d("Prog", "Show groups");
                    showGroups();
                }
            }
        };
        groupsViewModel.getGroups().observe(getViewLifecycleOwner(), observerNewGroups);

        return binding.getRoot();
    }

    private void initRecyclerView()
    {
        adapter = new GroupsRecyclerAdapter(getActivity(), groupsViewModel.getGroups().getValue(), () -> {
            Log.d(TAG, "You click on ");
            NavigationUtils.navigateSafe(navController, R.id.action_groupsFragment_to_groupEnterFragment, null);
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