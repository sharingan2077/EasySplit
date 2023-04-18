package com.example.easysplit.view.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.easysplit.R;
import com.example.easysplit.databinding.FragmentAddExpenseBinding;
import com.example.easysplit.utils.NavigationUtils;
import com.example.easysplit.viewModel.AddExpenseViewModel;
import com.example.easysplit.viewModel.MainActivityViewModel;

public class AddExpenseFragment extends Fragment {
    private static final String TAG = "AddExpenseFragment";
    FragmentAddExpenseBinding binding;
    MainActivityViewModel mainActivityViewModel;
    AddExpenseViewModel addExpenseViewModel;
    NavController navController;

    private int lastFragmentActionId;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAddExpenseBinding.inflate(inflater, container, false);
        navController = Navigation.findNavController(requireActivity(), R.id.navHostFragment);
        mainActivityViewModel = new ViewModelProvider(requireActivity()).get(MainActivityViewModel.class);
        addExpenseViewModel = new ViewModelProvider(requireActivity()).get(AddExpenseViewModel.class);
        mainActivityViewModel.hideBottomNavigationBar();
        binding.toolbar.back.setOnClickListener(v -> {
            Log.d(TAG, "Click on back " + lastFragmentActionId);
            mainActivityViewModel.setIsNotToMakeExpense();
            NavigationUtils.navigateSafe(navController, lastFragmentActionId, null);
        });

        final Observer<Integer> lastFragmentAction = integer -> lastFragmentActionId = integer;
        addExpenseViewModel.getLastFragment().observe(requireActivity(), lastFragmentAction);



        return binding.getRoot();
    }
}