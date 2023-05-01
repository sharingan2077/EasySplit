package com.example.easysplit.view.fragments.authentication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.easysplit.R;
import com.example.easysplit.databinding.FragmentVerificationUserBinding;
import com.example.easysplit.view.utils.NavigationUtils;
import com.example.easysplit.viewModel.authentication.LoginRegisterViewModel;

public class VerificationUserFragment extends Fragment {
    FragmentVerificationUserBinding binding;
    NavController navController;

    LoginRegisterViewModel loginRegisterViewModel;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentVerificationUserBinding.inflate(inflater, container, false);
        navController = Navigation.findNavController(requireActivity(), R.id.navHostFragment);
        loginRegisterViewModel = new ViewModelProvider(requireActivity()).get(LoginRegisterViewModel.class);
        binding.toolbar.textToolbar.setText("Верификация");
        binding.toolbar.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavigationUtils.navigateSafe(navController, R.id.action_verificationUserFragment_to_registrationFragment, null);
            }
        });



        return binding.getRoot();
    }
}