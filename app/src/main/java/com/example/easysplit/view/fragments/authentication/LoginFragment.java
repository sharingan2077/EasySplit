package com.example.easysplit.view.fragments.authentication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.easysplit.R;
import com.example.easysplit.databinding.LoginFragmentBinding;
import com.example.easysplit.view.listeners.CompleteListener;
import com.example.easysplit.viewModel.mainActivity.MainActivityViewModel;
import com.example.easysplit.viewModel.authentication.LoginRegisterViewModel;

public class LoginFragment extends Fragment {
    LoginFragmentBinding binding;
    LoginRegisterViewModel loginRegisterViewModel;

    MainActivityViewModel mainActivityViewModel;


    String successfulLogin = "a";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = LoginFragmentBinding.inflate(inflater, container, false);
        loginRegisterViewModel = new ViewModelProvider(requireActivity()).get(LoginRegisterViewModel.class);
        mainActivityViewModel = new ViewModelProvider(requireActivity()).get(MainActivityViewModel.class);

        if (getArguments() != null)
        {
            successfulLogin = getArguments().getString("successLogin", "a");
        }
        loginRegisterViewModel.init(requireContext(), new CompleteListener() {
            @Override
            public void successful()
            {

            }
            @Override
            public void unSuccessful() {

            }
        });
        binding.createAccount.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("successLogin", successfulLogin);
            Navigation.findNavController(binding.getRoot()).navigate(R.id.action_loginFragment_to_registrationFragment, bundle);
        });

        binding.btnEnter.setOnClickListener(v -> {
            String email = binding.loginText.getText().toString().replace(" ", "");
            String password = binding.passwordText.getText().toString();
            loginRegisterViewModel.login(email, password, new CompleteListener() {
                @Override
                public void successful() {
                    Navigation.findNavController(binding.getRoot()).navigate(R.id.action_loginFragment_to_groupsFragment);
                    mainActivityViewModel.setUserImage();

                }

                @Override
                public void unSuccessful() {

                }
            });
        });

        final Observer<Boolean> loggedOutObserver = aBoolean -> {
            if (aBoolean != null) {}
            if (aBoolean != null && !aBoolean && !successfulLogin.equals("true"))
            {
                Navigation.findNavController(binding.getRoot()).navigate(R.id.action_loginFragment_to_groupsFragment);
            }
        };
        loginRegisterViewModel.getLoggedOutLiveData().observe(getViewLifecycleOwner(), loggedOutObserver);
        binding.forgotPassword.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("successLogin", successfulLogin);
            Navigation.findNavController(binding.getRoot()).navigate(R.id.action_loginFragment_to_resetPasswordVerificationFragment, bundle);
        });
        return binding.getRoot();
    }

}