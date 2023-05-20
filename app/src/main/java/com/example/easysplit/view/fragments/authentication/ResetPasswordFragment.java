package com.example.easysplit.view.fragments.authentication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.easysplit.R;
import com.example.easysplit.databinding.FragmentResetPasswordBinding;
import com.example.easysplit.view.utils.NavigationUtils;

public class ResetPasswordFragment extends Fragment {
    FragmentResetPasswordBinding binding;
    NavController navController;

    String successfulLogin;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentResetPasswordBinding.inflate(inflater, container, false);
        navController = Navigation.findNavController(requireActivity(), R.id.navHostFragment);
        successfulLogin = getArguments().getString("successLogin", "a");
        binding.toolbar.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("successLogin", successfulLogin);
                NavigationUtils.navigateSafe(navController, R.id.action_resetPasswordFragment_to_loginFragment, bundle);
            }
        });
        binding.toolbar.textToolbar.setText("Сброс пароля");
//        binding.btnSendCode.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                NavigationUtils.navigateSafe(navController, R.id.action_resetPasswordFragment_to_resetPasswordVerificationFragment, null);
//            }
//        });
        return binding.getRoot();
    }
}