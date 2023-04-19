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
import com.example.easysplit.view.utils.NavigationUtils;
import com.example.easysplit.view.adapters.GroupsRecyclerAdapter;
import com.example.easysplit.viewModel.AddExpenseViewModel;
import com.example.easysplit.viewModel.GroupsViewModel;
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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: groups");
        binding = FragmentGroupsBinding.inflate(inflater, container, false);
        groupsViewModel = new ViewModelProvider(requireActivity()).get(GroupsViewModel.class);
        navController = Navigation.findNavController(getActivity(), R.id.navHostFragment);
        initRecyclerView();
        mainActivityViewModel = new ViewModelProvider(requireActivity()).get(MainActivityViewModel.class);
        mainActivityViewModel.showBottomNavigationBar();
        addExpenseViewModel = new ViewModelProvider(requireActivity()).get(AddExpenseViewModel.class);
        addExpenseViewModel.setLastFragmentAction(R.id.action_addExpenseFragment_to_groupsFragment);
        binding.createGroup.setOnClickListener(v ->
                NavigationUtils.navigateSafe(navController, R.id.action_groupsFragment_to_groupCreateFragment, null)
        );

        final Observer<List<Group>> observerNewGroup = groups -> adapter.notifyDataSetChanged();

        final Observer<Boolean> isGoToExpenseObserver = aBoolean -> {
            if (aBoolean) NavigationUtils.navigateSafe(navController, R.id.action_groupsEmptyFragment_to_addExpenseFragment, null);
        };

//        final Observer<Integer> itemSelectedObserver = itemId -> {
//            Log.d("MyTaggg", "Observer in GroupEmpty see changes");
//            switch (itemId)
//            {
//                case R.id.friends:
//                    NavigationUtils.navigateSafe(navController, R.id.action_groupsEmptyFragment_to_friendsEmptyFragment, null);
//                    break;
//                case R.id.activities:
//                    NavigationUtils.navigateSafe(navController, R.id.action_groupsEmptyFragment_to_activityFragment, null);
//                    break;
//                case R.id.profile:
//                    NavigationUtils.navigateSafe(navController, R.id.action_groupsEmptyFragment_to_profileFragment, null);
//                    break;
//            }
//        };
//        mainActivityViewModel.getBottomNavigationItem().observe(getViewLifecycleOwner(), itemSelectedObserver);

        mainActivityViewModel.getIsGoToMakeExpense().observe(getViewLifecycleOwner(), isGoToExpenseObserver);
        groupsViewModel.getGroups().observe(requireActivity(), observerNewGroup);

        return binding.getRoot();
    }

    private void initRecyclerView()
    {
        adapter = new GroupsRecyclerAdapter(getActivity(), groupsViewModel.getGroups().getValue(), () -> {
            Log.d(TAG, "You click on ");
            NavigationUtils.navigateSafe(navController, R.id.action_groupsFragment_to_groupEnterNoOneFragment, null);
        });
        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

}