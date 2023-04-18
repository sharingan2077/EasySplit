package com.example.easysplit.view.fragments.authentication;

import android.os.Binder;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.easysplit.R;
import com.example.easysplit.databinding.FragmentResetPasswordBinding;

public class ResetPasswordFragment extends Fragment {
    FragmentResetPasswordBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentResetPasswordBinding.inflate(inflater, container, false);

        return  binding.getRoot();
    }
}