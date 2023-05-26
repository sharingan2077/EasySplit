package com.example.easysplit.view.fragments.authentication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.easysplit.R;
import com.example.easysplit.databinding.FragmentResetPasswordVerificationBinding;
import com.example.easysplit.view.listeners.CompleteListener;
import com.example.easysplit.view.utils.NavigationUtils;
import com.example.easysplit.viewModel.authentication.VerificationUserViewModel;

public class ResetPasswordVerificationFragment extends Fragment {
    FragmentResetPasswordVerificationBinding binding;
    NavController navController;
    String successfulLogin;

    VerificationUserViewModel verificationUserViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentResetPasswordVerificationBinding.inflate(inflater, container, false);
        navController = Navigation.findNavController(requireActivity(), R.id.navHostFragment);
        verificationUserViewModel = new ViewModelProvider(requireActivity()).get(VerificationUserViewModel.class);
        verificationUserViewModel.init(requireContext());
        successfulLogin = getArguments().getString("successLogin", "a");

        binding.toolbar.textToolbar.setText("Забыли пароль");
        binding.toolbar.back.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("successLogin", successfulLogin);
            NavigationUtils.navigateSafe(navController, R.id.action_resetPasswordVerificationFragment_to_loginFragment, bundle);
        });

        binding.btnSendCode.setOnClickListener(v -> {
            String email = binding.email.getText().toString();
            verificationUserViewModel.sendVerificationEmail(email, new CompleteListener() {
                @Override
                public void successful() {
                    AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
                    builder.setTitle("Сброс пароля");
                    builder.setMessage("На указанную вами почту " + email + " пришла ссылка для сброса пароля");
                    builder.setPositiveButton("Ок", (dialog, which) -> {
                        Bundle bundle = new Bundle();
                        bundle.putString("successLogin", successfulLogin);
                        NavigationUtils.navigateSafe(navController, R.id.action_resetPasswordVerificationFragment_to_loginFragment, bundle);
                        return;
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }

                @Override
                public void unSuccessful() {

                }
            });
        });


        return  binding.getRoot();
    }
}