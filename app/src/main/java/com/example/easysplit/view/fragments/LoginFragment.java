package com.example.easysplit.view.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.easysplit.R;
import com.example.easysplit.databinding.LoginFragmentBinding;
import com.example.easysplit.viewModel.LoginViewModel;


public class LoginFragment extends Fragment {
    LoginFragmentBinding binding;
    LoginViewModel loginViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = LoginFragmentBinding.inflate(inflater, container, false);
        binding.btnEnter.setOnClickListener(v -> {
            if (binding.loginText.getText().toString().equals("") || binding.passwordText.getText().toString().equals("")) {
                Toast.makeText(getActivity(), "Введите все поля!", Toast.LENGTH_SHORT).show();
            } else {
                Navigation.findNavController(binding.getRoot()).navigate(R.id.action_loginFragment_to_groupsEmptyFragment);
            }
        });
        binding.createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(binding.getRoot()).navigate(R.id.action_loginFragment_to_registrationFragment);
            }
        });


        return binding.getRoot();

    }
}