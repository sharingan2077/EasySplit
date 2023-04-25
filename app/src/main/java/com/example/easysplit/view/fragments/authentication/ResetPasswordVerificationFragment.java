package com.example.easysplit.view.fragments.authentication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.easysplit.R;
import com.example.easysplit.databinding.FragmentResetPasswordVerificationBinding;
import com.example.easysplit.view.utils.NavigationUtils;

public class ResetPasswordVerificationFragment extends Fragment {
    FragmentResetPasswordVerificationBinding binding;
    NavController navController;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentResetPasswordVerificationBinding.inflate(inflater, container, false);
        navController = Navigation.findNavController(requireActivity(), R.id.navHostFragment);

        binding.toolbar.textToolbar.setText("Забыли пароль");
        binding.toolbar.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavigationUtils.navigateSafe(navController, R.id.action_resetPasswordVerificationFragment_to_loginFragment, null);
            }
        });


        return  binding.getRoot();
    }
}