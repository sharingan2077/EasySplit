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
import com.example.easysplit.view.adapters.UsersSplitUnequallyAdapter;
import com.example.easysplit.view.utils.NavigationUtils;
import com.example.easysplit.viewModel.WhoPaidViewModel;

public class SplitUnequallyFragment extends Fragment {
    FragmentSplitUnequallyBinding binding;
    WhoPaidViewModel whoPaidViewModel;
    NavController navController;

    UsersSplitUnequallyAdapter adapter;

    private int actionToLastFragment;
    private String groupId;
    private String expenseId;
    private String expenseName;
    private String expenseSumString;
    private String userId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSplitUnequallyBinding.inflate(inflater, container, false);
        navController = Navigation.findNavController(requireActivity(), R.id.navHostFragment);
        actionToLastFragment = getArguments().getInt("ActionToLastFragment", 1);
        groupId = getArguments().getString("groupId", "0");
        expenseId = getArguments().getString("expenseId", "0");
        expenseSumString = getArguments().getString("expenseSum", "0");
        expenseName = getArguments().getString("expenseName", "0");
        userId = getArguments().getString("userId", "0");

        whoPaidViewModel = new ViewModelProvider(requireActivity()).get(WhoPaidViewModel.class);
        whoPaidViewModel.init(groupId);
        initRecyclerView();
        binding.toolbar.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("ActionToLastFragment", actionToLastFragment);
                bundle.putString("expenseId", expenseId);
                bundle.putString("groupId", groupId);
                bundle.putString("expenseName", expenseName);
                bundle.putString("expenseSum", expenseSumString);
                bundle.putString("userId", userId);

                NavigationUtils.navigateSafe(navController, R.id.action_splitUnequallyFragment_to_addExpenseFragment, bundle);
            }
        });
        binding.toolbar.done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("ActionToLastFragment", actionToLastFragment);
                bundle.putString("expenseId", expenseId);
                bundle.putString("groupId", groupId);
                bundle.putString("expenseName", expenseName);
                bundle.putString("expenseSum", expenseSumString);
                bundle.putString("userId", userId);
                NavigationUtils.navigateSafe(navController, R.id.action_splitUnequallyFragment_to_addExpenseFragment, bundle);
            }
        });
        binding.splitEqually.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("ActionToLastFragment", actionToLastFragment);
                bundle.putString("groupId", groupId);
                bundle.putString("expenseId", expenseId);
                bundle.putString("expenseName", expenseName);
                bundle.putString("expenseSum", expenseSumString);
                bundle.putString("userId", userId);
                NavigationUtils.navigateSafe(navController, R.id.action_splitUnequallyFragment_to_splitEquallyFragment, bundle);
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