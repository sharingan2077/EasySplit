package com.example.easysplit.view.fragments.authentication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.easysplit.R;
import com.example.easysplit.databinding.FragmentRegistrationBinding;
import com.example.easysplit.view.utils.NavigationUtils;
import com.example.easysplit.viewModel.authentication.LoginRegisterViewModel;

public class RegistrationFragment extends Fragment {
    FragmentRegistrationBinding binding;

    LoginRegisterViewModel loginRegisterViewModel;
    NavController navController;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentRegistrationBinding.inflate(inflater, container, false);
        navController = Navigation.findNavController(requireActivity(), R.id.navHostFragment);
        loginRegisterViewModel = new ViewModelProvider(requireActivity()).get(LoginRegisterViewModel.class);
        binding.btnRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = binding.name.getText().toString();
                String email = binding.email.getText().toString();
                String password = binding.password.getText().toString();
                String confirmPassword = binding.confirmPassword.getText().toString();

                if (name.equals("") || email.equals("") || password.equals("") || confirmPassword.equals(""))
                {
                    Toast.makeText(requireActivity(), "Заполните все поля!", Toast.LENGTH_SHORT).show();
                }

//                else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
//                {
//
//                }
                else if (!password.equals(confirmPassword))
                {
                    Toast.makeText(requireActivity(), "Пароли не совпадают!", Toast.LENGTH_SHORT).show();
                }
                else  {
                    loginRegisterViewModel.register(email, password, name);
                    loginRegisterViewModel.refreshLoggedOutLiveData();
                }
            }
        });

        final Observer<Boolean> successRegObserver = new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (!aBoolean)
                {
                    NavigationUtils.navigateSafe(navController, R.id.action_registrationFragment_to_loginFragment, null);
                }
            }
        };
        loginRegisterViewModel.getLoggedOutLiveData().observe(getViewLifecycleOwner(), successRegObserver);

        binding.login.setOnClickListener(v -> NavigationUtils.navigateSafe(navController, R.id.action_registrationFragment_to_loginFragment, null));

        return binding.getRoot();
    }
}