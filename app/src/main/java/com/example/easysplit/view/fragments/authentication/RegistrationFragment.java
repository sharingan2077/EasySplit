package com.example.easysplit.view.fragments.authentication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.easysplit.R;
import com.example.easysplit.databinding.FragmentRegistrationBinding;
import com.example.easysplit.utils.NavigationUtils;

public class RegistrationFragment extends Fragment {
    FragmentRegistrationBinding binding;
    NavController navController;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentRegistrationBinding.inflate(inflater, container, false);
        navController = Navigation.findNavController(requireActivity(), R.id.navHostFragment);
        binding.btnRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.name.getText().toString().equals("") ||
                binding.email.getText().toString().equals("") ||
                binding.password.getText().toString().equals("") ||
                binding.confirmPassword.getText().toString().equals(""))
                {
                    Toast.makeText(requireActivity(), "Заполните все поля!", Toast.LENGTH_SHORT).show();
                }
                else if (!binding.password.getText().toString().equals(binding.confirmPassword.getText().toString()))
                {
                    Toast.makeText(requireActivity(), "Пароли не совпадают!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    NavigationUtils.navigateSafe(navController, R.id.action_registrationFragment_to_loginFragment, null);
                }
            }
        });
        return binding.getRoot();
    }
}