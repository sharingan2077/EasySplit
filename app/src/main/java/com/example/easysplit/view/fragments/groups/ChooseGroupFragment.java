package com.example.easysplit.view.fragments.groups;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.easysplit.R;
import com.example.easysplit.databinding.FragmentChooseGroupBinding;
import com.example.easysplit.utils.NavigationUtils;
import com.example.easysplit.view.adapters.GroupsRecyclerAdapter;
import com.example.easysplit.viewModel.GroupsViewModel;

public class ChooseGroupFragment extends Fragment {
    private static final String TAG = "ChooseGroupFragment";

    FragmentChooseGroupBinding binding;

    private GroupsRecyclerAdapter adapter;
    NavController navController;
    GroupsViewModel groupsViewModel;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentChooseGroupBinding.inflate(inflater, container, false);
        groupsViewModel = new ViewModelProvider(requireActivity()).get(GroupsViewModel.class);
        navController = Navigation.findNavController(getActivity(), R.id.navHostFragment);


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