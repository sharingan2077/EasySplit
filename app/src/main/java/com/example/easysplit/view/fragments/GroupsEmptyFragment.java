package com.example.easysplit.view.fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.easysplit.R;
import com.example.easysplit.databinding.GroupsEmptyFragmentBinding;
import com.example.easysplit.viewModel.MainActivityViewModel;

public class GroupsEmptyFragment extends Fragment {
    GroupsEmptyFragmentBinding binding;
    MainActivityViewModel mainActivityViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = GroupsEmptyFragmentBinding.inflate(inflater, container, false);
        mainActivityViewModel = new ViewModelProvider(requireActivity()).get(MainActivityViewModel.class);
        mainActivityViewModel.showBottomNavigationBar();
        final Observer<Integer> itemSelectedObserver = new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable final Integer itemId) {
                switch (itemId)
                {
                    case R.id.friends:
                        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_groupsEmptyFragment_to_friendsEmptyFragment);
                }
            }
        };
        mainActivityViewModel.getBottomNavigationItem().observe(requireActivity(), itemSelectedObserver);

        return binding.getRoot();
    }
}