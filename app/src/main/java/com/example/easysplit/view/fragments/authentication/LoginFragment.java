package com.example.easysplit.view.fragments.authentication;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.easysplit.R;
import com.example.easysplit.databinding.LoginFragmentBinding;
import com.example.easysplit.viewModel.MainActivityViewModel;
import com.example.easysplit.viewModel.authentication.LoginRegisterViewModel;

public class LoginFragment extends Fragment {
    private static final String TAG = "LoginFragment";
    LoginFragmentBinding binding;
    LoginRegisterViewModel loginRegisterViewModel;

    MainActivityViewModel mainActivityViewModel;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = LoginFragmentBinding.inflate(inflater, container, false);
        loginRegisterViewModel = new ViewModelProvider(requireActivity()).get(LoginRegisterViewModel.class);
        mainActivityViewModel = new ViewModelProvider(requireActivity()).get(MainActivityViewModel.class);
        loginRegisterViewModel.refreshLoggedOutLiveData();

        //Navigation.findNavController(binding.getRoot()).navigate(R.id.action_loginFragment_to_groupsFragment);
        binding.createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(binding.getRoot()).navigate(R.id.action_loginFragment_to_registrationFragment);
            }
        });

        binding.btnEnter.setOnClickListener(v -> {
            String email = binding.loginText.getText().toString().replace(" ", "");
            String password = binding.passwordText.getText().toString();
            loginRegisterViewModel.login(email, password);
            loginRegisterViewModel.refreshLoggedOutLiveData();
            mainActivityViewModel.setUserImage();
            //Navigation.findNavController(binding.getRoot()).navigate(R.id.action_loginFragment_to_groupsFragment);

//            {
//                Navigation.findNavController(binding.getRoot()).navigate(R.id.action_loginFragment_to_groupsEmptyFragment);
//            }
//            if (login.equals("") || password.equals("")) {
//                Toast.makeText(getActivity(), "Введите все поля!", Toast.LENGTH_SHORT).show();
//            }
//            else if (!Patterns.EMAIL_ADDRESS.matcher(login).matches())
//            {
//                Toast.makeText(getActivity(), "Неправильно введена почта", Toast.LENGTH_SHORT).show();
//            }
//            else {
//
//            }
        });

        final Observer<Boolean> loggedOutObserver = new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                Log.d(TAG, aBoolean.toString());
                if (!aBoolean)
                {
                    Navigation.findNavController(binding.getRoot()).navigate(R.id.action_loginFragment_to_groupsFragment);
                }
            }
        };
        loginRegisterViewModel.getLoggedOutLiveData().observe(getViewLifecycleOwner(), loggedOutObserver);

        binding.forgotPassword.setOnClickListener(v -> Navigation.findNavController(binding.getRoot()).navigate(R.id.action_loginFragment_to_resetPasswordVerificationFragment));


        return binding.getRoot();
    }

}