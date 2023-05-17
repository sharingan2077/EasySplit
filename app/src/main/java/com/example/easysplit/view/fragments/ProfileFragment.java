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

import com.example.easysplit.databinding.FragmentProfileBinding;
import com.example.easysplit.view.utils.NavigationUtils;
import com.example.easysplit.R;
import com.example.easysplit.viewModel.MainActivityViewModel;
import com.example.easysplit.viewModel.ProfileViewModel;
import com.example.easysplit.viewModel.authentication.LoginRegisterViewModel;

public class ProfileFragment extends Fragment {
    private static final String TAG = "ProfileFragment";

    MainActivityViewModel mainActivityViewModel;
    FragmentProfileBinding binding;
    LoginRegisterViewModel loginRegisterViewModel;

    ProfileViewModel profileViewModel;

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
        loginRegisterViewModel = new ViewModelProvider(requireActivity()).get(LoginRegisterViewModel.class);
        profileViewModel = new ViewModelProvider(requireActivity()).get(ProfileViewModel.class);
        profileViewModel.init(getContext());

        final Observer<Boolean> isGoToExpenseObserver = aBoolean -> {
            Bundle bundle = new Bundle();
            bundle.putInt("ActionToLastFragment", R.id.action_addExpenseFragment_to_profileFragment);
            if (aBoolean) NavigationUtils.navigateSafe(navController, R.id.action_profileFragment_to_addExpenseFragment, bundle);
        };
        mainActivityViewModel.getIsGoToMakeExpense().observe(getViewLifecycleOwner(), isGoToExpenseObserver);
        binding.leaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                NavigationUtils.navigateSafe(navController, R.id.action_profileFragment_to_loginFragment, null);
                loginRegisterViewModel.logOut();
            }
        });

        final Observer<String> observerUserEmail = new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Log.d(TAG, "email is Changed! " + s);
                binding.userEmail.setText(s);
            }
        };
        profileViewModel.getUserEmail().observe(getViewLifecycleOwner(), observerUserEmail);

        final Observer<String> observerUserNameAndId = new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Log.d(TAG, "userNameAndId is Changed! " + s);
                binding.userNameAndId.setText(s);
            }
        };
        profileViewModel.getUserNameAndId().observe(getViewLifecycleOwner(), observerUserNameAndId);

        return binding.getRoot();
    }

}