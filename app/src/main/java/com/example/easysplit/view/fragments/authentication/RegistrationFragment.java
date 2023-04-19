package com.example.easysplit.view.fragments.authentication;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.easysplit.R;
import com.example.easysplit.databinding.FragmentRegistrationBinding;
import com.example.easysplit.view.utils.NavigationUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegistrationFragment extends Fragment {
    FragmentRegistrationBinding binding;
    NavController navController;

    private FirebaseAuth mAuth;

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
                String name = binding.name.getText().toString();
                String email = binding.email.getText().toString();
                String password = binding.password.getText().toString();
                String confirmPassword = binding.confirmPassword.getText().toString();

                if (name.equals("") || email.equals("") || password.equals("") || confirmPassword.equals(""))
                {
                    Toast.makeText(requireActivity(), "Заполните все поля!", Toast.LENGTH_SHORT).show();
                }

                else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
                {

                }
                else if (!binding.password.getText().toString().equals(binding.confirmPassword.getText().toString()))
                {
                    Toast.makeText(requireActivity(), "Пароли не совпадают!", Toast.LENGTH_SHORT).show();
                }
                else if (true) {
                    mAuth.createUserWithEmailAndPassword(binding.email.getText().toString(), binding.password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful())
                            {
                                NavigationUtils.navigateSafe(navController, R.id.action_registrationFragment_to_verificationUserFragment, null);
                            }
                            else{
                                Toast.makeText(requireActivity(), "Неправильно введены данные", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        binding.login.setOnClickListener(v -> NavigationUtils.navigateSafe(navController, R.id.action_registrationFragment_to_loginFragment, null));

        return binding.getRoot();
    }
}