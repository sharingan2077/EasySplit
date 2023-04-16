package com.example.easysplit.view.fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.easysplit.R;
import com.example.easysplit.databinding.FriendsEmptyFragmentBinding;
import com.example.easysplit.viewModel.MainActivityViewModel;


public class FriendsEmptyFragment extends Fragment {
    MainActivityViewModel mainActivityViewModel;
    FriendsEmptyFragmentBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        binding = FriendsEmptyFragmentBinding.inflate(inflater, container, false);
        mainActivityViewModel = new ViewModelProvider(requireActivity()).get(MainActivityViewModel.class);
        mainActivityViewModel.showBottomNavigationBar();
        final Observer<Integer> itemSelectedObserver = new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable final Integer itemId) {
                switch (itemId)
                {
                    case R.id.groups:
                        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_friendsEmptyFragment_to_groupsEmptyFragment);
                }
            }
        };
        mainActivityViewModel.getBottomNavigationItem().observe(requireActivity(), itemSelectedObserver);


        return binding.getRoot();
    }
}