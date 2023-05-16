package com.example.easysplit.view.fragments.expense;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.easysplit.R;
import com.example.easysplit.databinding.FragmentSplitEquallyBinding;
import com.example.easysplit.view.adapters.UsersSplitEquallyAdapter;
import com.example.easysplit.view.utils.NavigationUtils;
import com.example.easysplit.viewModel.SplitEquallyViewModel;
import com.example.easysplit.viewModel.WhoPaidViewModel;

import java.util.ArrayList;
import java.util.List;

public class SplitEquallyFragment extends Fragment {

    private static final String TAG = "SplitEquallyFragment";

    FragmentSplitEquallyBinding binding;

    NavController navController;

    UsersSplitEquallyAdapter adapter;

    SplitEquallyViewModel splitEquallyViewModel;
    WhoPaidViewModel whoPaidViewModel;

    private int actionToLastFragment;
    private String groupId;
    private String expenseId;

    private String userId;

    private String expenseName;
    private String expenseSumString;




    private ArrayList<String> usersId;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSplitEquallyBinding.inflate(inflater, container, false);
        navController = Navigation.findNavController(requireActivity(), R.id.navHostFragment);
        actionToLastFragment = getArguments().getInt("ActionToLastFragment", 1);
        groupId = getArguments().getString("groupId", "0");
        expenseId = getArguments().getString("expenseId", "0");
        usersId = getArguments().getStringArrayList("usersId");
        expenseSumString = getArguments().getString("expenseSum", "0");
        expenseName = getArguments().getString("expenseName", "0");
        userId = getArguments().getString("userId", "0");
        //Log.d(TAG, "usersId " + Integer.toString(usersId.size()));
//        splitEquallyViewModel = new ViewModelProvider(requireActivity()).get(SplitEquallyViewModel.class);
//        splitEquallyViewModel.init();
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
                NavigationUtils.navigateSafe(navController, R.id.action_splitEquallyFragment_to_addExpenseFragment, bundle);
            }
        });
        binding.toolbar.done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("ActionToLastFragment", actionToLastFragment);
                bundle.putString("expenseId", expenseId);
                bundle.putString("groupId", groupId);
                bundle.putStringArrayList("usersId", usersId);
                bundle.putString("expenseName", expenseName);
                bundle.putString("expenseSum", expenseSumString);
                bundle.putString("userId", userId);
                NavigationUtils.navigateSafe(navController, R.id.action_splitEquallyFragment_to_addExpenseFragment, bundle);
            }
        });
        binding.splitUnequally.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("ActionToLastFragment", actionToLastFragment);
                bundle.putString("groupId", groupId);
                bundle.putString("expenseId", expenseId);
                bundle.putString("expenseName", expenseName);
                bundle.putString("expenseSum", expenseSumString);
                bundle.putString("userId", userId);
                NavigationUtils.navigateSafe(navController, R.id.action_splitEquallyFragment_to_splitUnequallyFragment, bundle);
            }
        });


        return binding.getRoot();
    }

    private void initRecyclerView()
    {
        adapter = new UsersSplitEquallyAdapter(requireActivity(), whoPaidViewModel.getUsers().getValue(), new UsersSplitEquallyAdapter.onUserClickListener() {
            @Override
            public void onClickAdd(String userId) {
                usersId.add(userId);
                Log.d(TAG, "onClickAdd - " + Integer.toString(usersId.size()));
            }
            @Override
            public void onClickRemove(String userId) {
                usersId.remove(userId);
                Log.d(TAG, "onClickRemove - " + Integer.toString(usersId.size()));
            }
        });

        binding.recyclerViewSplitEqually.setAdapter(adapter);
        binding.recyclerViewSplitEqually.setLayoutManager(new LinearLayoutManager(getActivity()));
    }
}