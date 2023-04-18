package com.example.easysplit.view.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.easysplit.utils.NavigationUtils;
import com.example.easysplit.R;
import com.example.easysplit.databinding.FragmentProfileBinding;
import com.example.easysplit.viewModel.AddExpenseViewModel;
import com.example.easysplit.viewModel.MainActivityViewModel;

public class ProfileFragment extends Fragment {
    private static final String TAG = "ProfileFragment";

    MainActivityViewModel mainActivityViewModel;
    FragmentProfileBinding binding;

    AddExpenseViewModel addExpenseViewModel;

    NavController navController;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("MyTag", "ProfileFragment open!");
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        navController = Navigation.findNavController(getActivity(), R.id.navHostFragment);
        mainActivityViewModel = new ViewModelProvider(requireActivity()).get(MainActivityViewModel.class);
        mainActivityViewModel.showBottomNavigationBar();
        addExpenseViewModel = new ViewModelProvider(requireActivity()).get(AddExpenseViewModel.class);
        addExpenseViewModel.setLastFragmentAction(R.id.action_addExpenseFragment_to_profileFragment);
        final Observer<Integer> itemSelectedObserver = itemId -> {
            switch (itemId)
            {
                case R.id.groups:
                    NavigationUtils.navigateSafe(navController, R.id.action_profileFragment_to_groupsEmptyFragment, null);
                    break;
                case R.id.friends:
                    NavigationUtils.navigateSafe(navController, R.id.action_profileFragment_to_friendsEmptyFragment, null);
                    break;
                case R.id.activities:
                    NavigationUtils.navigateSafe(navController, R.id.action_profileFragment_to_activityFragment, null);
                    break;

            }
        };
        mainActivityViewModel.getBottomNavigationItem().observe(requireActivity(), itemSelectedObserver);
        final Observer<Boolean> isGoToExpenseObserver = aBoolean -> {
            if (aBoolean) NavigationUtils.navigateSafe(navController, R.id.action_profileFragment_to_addExpenseFragment, null);
        };
        mainActivityViewModel.getIsGoToMakeExpense().observe(getViewLifecycleOwner(), isGoToExpenseObserver);

        binding.leaveAccount.setOnClickListener(v -> NavigationUtils.navigateSafe(navController, R.id.action_profileFragment_to_loginFragment, null));

        return binding.getRoot();
    }
}