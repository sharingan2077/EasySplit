package com.example.easysplit.view.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.easysplit.databinding.FragmentGroupsBinding;
import com.example.easysplit.model.Group;
import com.example.easysplit.view.adapters.GroupsRecyclerAdapter;
import com.example.easysplit.viewModel.GroupsViewModel;
import com.example.easysplit.viewModel.MainActivityViewModel;

import java.util.List;

public class GroupsFragment extends Fragment {

    private static final String TAG = "GroupsFragment";

    MainActivityViewModel mainActivityViewModel;
    FragmentGroupsBinding binding;
    private GroupsRecyclerAdapter adapter;
    GroupsViewModel groupsViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentGroupsBinding.inflate(inflater, container, false);
        groupsViewModel = new ViewModelProvider(requireActivity()).get(GroupsViewModel.class);
        groupsViewModel.init();
        mainActivityViewModel = new ViewModelProvider(requireActivity()).get(MainActivityViewModel.class);
        mainActivityViewModel.showBottomNavigationBar();
        binding.createGroup.setOnClickListener(v -> groupsViewModel.addNewValue(new Group("Example", 2)));

        final Observer<List<Group>> observerNewGroup = groups -> adapter.notifyDataSetChanged();
        groupsViewModel.getGroups().observe(getViewLifecycleOwner(), observerNewGroup);

        initRecyclerView();
        return binding.getRoot();
    }

    private void initRecyclerView()
    {
        adapter = new GroupsRecyclerAdapter(getActivity(), groupsViewModel.getGroups().getValue());
        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }
}