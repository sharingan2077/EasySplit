package com.example.easysplit.view.fragments.groups;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.easysplit.databinding.FragmentGroupEnterBinding;
import com.example.easysplit.viewModel.MainActivityViewModel;

public class GroupEnterFragment extends Fragment {
    FragmentGroupEnterBinding binding;
    MainActivityViewModel mainActivityViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentGroupEnterBinding.inflate(inflater, container, false);
        mainActivityViewModel = new ViewModelProvider(requireActivity()).get(MainActivityViewModel.class);
        mainActivityViewModel.hideBottomNavigationBar();

        return binding.getRoot();
    }
}