package com.example.easysplit.view.fragments.authentication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.easysplit.R;
import com.example.easysplit.databinding.FragmentRegistrationBinding;
import com.example.easysplit.view.listeners.CompleteListener;
import com.example.easysplit.view.utils.NavigationUtils;
import com.example.easysplit.viewModel.authentication.LoginRegisterViewModel;

public class RegistrationFragment extends Fragment {
    FragmentRegistrationBinding binding;

    LoginRegisterViewModel loginRegisterViewModel;
    NavController navController;

    String successfulLogin = "a";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentRegistrationBinding.inflate(inflater, container, false);
        navController = Navigation.findNavController(requireActivity(), R.id.navHostFragment);
        loginRegisterViewModel = new ViewModelProvider(requireActivity()).get(LoginRegisterViewModel.class);

        successfulLogin = getArguments().getString("successLogin", "a");
        binding.btnRegistration.setOnClickListener(v -> {
            String name = binding.name.getText().toString();
            String email = binding.email.getText().toString();
            String password = binding.password.getText().toString();
            String confirmPassword = binding.confirmPassword.getText().toString();

            if (name.equals("") || email.equals("") || password.equals("") || confirmPassword.equals(""))
            {
                Toast.makeText(requireActivity(), "Заполните все поля!", Toast.LENGTH_SHORT).show();
            }
            else if (!password.equals(confirmPassword))
            {
                Toast.makeText(requireActivity(), "Пароли не совпадают!", Toast.LENGTH_SHORT).show();
            }
            else if (name.length() > 10)
            {
                Toast.makeText(requireContext(), "Длина никнейма не должна превышать 10 символов", Toast.LENGTH_SHORT).show();
            }

            else  {
                loginRegisterViewModel.register(email, password, name, new CompleteListener() {
                    @Override
                    public void successful() {
                        Bundle bundle = new Bundle();
                        bundle.putString("successLogin", "true");
                        NavigationUtils.navigateSafe(navController, R.id.action_registrationFragment_to_loginFragment, bundle);
                    }
                    @Override
                    public void unSuccessful() {

                    }
                });
            }
        });

        binding.login.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("successLogin", successfulLogin);
            NavigationUtils.navigateSafe(navController, R.id.action_registrationFragment_to_loginFragment, bundle);
        });

        return binding.getRoot();
    }
}