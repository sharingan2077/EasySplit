package com.example.easysplit.view.fragments.expense;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.easysplit.R;
import com.example.easysplit.databinding.FragmentSplitUnequallyBinding;
import com.example.easysplit.view.adapters.UsersSplitEquallyAdapter;
import com.example.easysplit.view.adapters.UsersSplitUnequallyAdapter;
import com.example.easysplit.view.utils.NavigationUtils;
import com.example.easysplit.viewModel.WhoPaidViewModel;

public class SplitUnequallyFragment extends Fragment {
    FragmentSplitUnequallyBinding binding;
    WhoPaidViewModel whoPaidViewModel;
    NavController navController;

    UsersSplitUnequallyAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSplitUnequallyBinding.inflate(inflater, container, false);
        navController = Navigation.findNavController(requireActivity(), R.id.navHostFragment);

        whoPaidViewModel = new ViewModelProvider(requireActivity()).get(WhoPaidViewModel.class);
        whoPaidViewModel.init();
        initRecyclerView();
        binding.toolbar.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavigationUtils.navigateSafe(navController, R.id.action_splitUnequallyFragment_to_addExpenseFragment, null);
            }
        });
        binding.toolbar.done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavigationUtils.navigateSafe(navController, R.id.action_splitUnequallyFragment_to_addExpenseFragment, null);
            }
        });
        binding.splitEqually.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavigationUtils.navigateSafe(navController, R.id.action_splitUnequallyFragment_to_splitEquallyFragment, null);
            }
        });

        return binding.getRoot();
    }

    private void initRecyclerView()
    {
        adapter = new UsersSplitUnequallyAdapter(requireActivity(), whoPaidViewModel.getUsers().getValue());
        binding.recyclerViewSplitUnequally.setAdapter(adapter);
        binding.recyclerViewSplitUnequally.setLayoutManager(new LinearLayoutManager(getActivity()));
    }
}