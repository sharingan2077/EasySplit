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
import com.example.easysplit.databinding.FragmentSplitEquallyBinding;
import com.example.easysplit.view.adapters.UsersRecyclerAdapter;
import com.example.easysplit.view.adapters.UsersSplitEquallyAdapter;
import com.example.easysplit.view.utils.NavigationUtils;
import com.example.easysplit.viewModel.SplitEquallyViewModel;
import com.example.easysplit.viewModel.WhoPaidViewModel;

public class SplitEquallyFragment extends Fragment {

    FragmentSplitEquallyBinding binding;

    NavController navController;

    UsersSplitEquallyAdapter adapter;

    SplitEquallyViewModel splitEquallyViewModel;
    WhoPaidViewModel whoPaidViewModel;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSplitEquallyBinding.inflate(inflater, container, false);
        navController = Navigation.findNavController(requireActivity(), R.id.navHostFragment);
//        splitEquallyViewModel = new ViewModelProvider(requireActivity()).get(SplitEquallyViewModel.class);
//        splitEquallyViewModel.init();
        whoPaidViewModel = new ViewModelProvider(requireActivity()).get(WhoPaidViewModel.class);
        whoPaidViewModel.init();
        initRecyclerView();
        binding.toolbar.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavigationUtils.navigateSafe(navController, R.id.action_splitEquallyFragment_to_addExpenseFragment, null);
            }
        });
        binding.toolbar.done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavigationUtils.navigateSafe(navController, R.id.action_splitEquallyFragment_to_addExpenseFragment, null);
            }
        });
        binding.splitUnequally.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavigationUtils.navigateSafe(navController, R.id.action_splitEquallyFragment_to_splitUnequallyFragment, null);
            }
        });


        return binding.getRoot();
    }

    private void initRecyclerView()
    {
        adapter = new UsersSplitEquallyAdapter(requireActivity(), whoPaidViewModel.getUsers().getValue());
        binding.recyclerViewSplitEqually.setAdapter(adapter);
        binding.recyclerViewSplitEqually.setLayoutManager(new LinearLayoutManager(getActivity()));
    }
}