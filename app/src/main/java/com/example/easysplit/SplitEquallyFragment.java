package com.example.easysplit;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.easysplit.databinding.FragmentSplitEquallyBinding;

public class SplitEquallyFragment extends Fragment {

    FragmentSplitEquallyBinding binding;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSplitEquallyBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }
}