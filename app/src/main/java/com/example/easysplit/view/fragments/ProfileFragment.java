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

import com.example.easysplit.view.utils.NavigationUtils;
import com.example.easysplit.R;
import com.example.easysplit.databinding.FragmentProfileBinding;
import com.example.easysplit.viewModel.AddExpenseViewModel;
import com.example.easysplit.viewModel.MainActivityViewModel;
import com.example.easysplit.viewModel.authentication.LoggedInViewModel;
import com.example.easysplit.viewModel.authentication.LoginRegisterViewModel;

public class ProfileFragment extends Fragment {
    private static final String TAG = "ProfileFragment";

    MainActivityViewModel mainActivityViewModel;
    FragmentProfileBinding binding;

    AddExpenseViewModel addExpenseViewModel;
    LoginRegisterViewModel loginRegisterViewModel;

    NavController navController;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("MyTag", "ProfileFragment open!");
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        navController = Navigation.findNavController(requireActivity(), R.id.navHostFragment);
        mainActivityViewModel = new ViewModelProvider(requireActivity()).get(MainActivityViewModel.class);
        addExpenseViewModel = new ViewModelProvider(requireActivity()).get(AddExpenseViewModel.class);
        addExpenseViewModel.setLastFragmentAction(R.id.action_addExpenseFragment_to_profileFragment);
        loginRegisterViewModel = new ViewModelProvider(requireActivity()).get(LoginRegisterViewModel.class);
        final Observer<Boolean> isGoToExpenseObserver = aBoolean -> {
            if (aBoolean) NavigationUtils.navigateSafe(navController, R.id.action_profileFragment_to_addExpenseFragment, null);
        };
        mainActivityViewModel.getIsGoToMakeExpense().observe(getViewLifecycleOwner(), isGoToExpenseObserver);
        binding.leaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavigationUtils.navigateSafe(navController, R.id.action_profileFragment_to_loginFragment, null);
                loginRegisterViewModel.logOut();
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onStop() {
        Log.d(TAG, "Stopping Profile Fragment");
        super.onStop();
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "Stopping Profile Fragment");
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        Log.d(TAG, "Stopping Profile Fragment");
        super.onDetach();
    }
}