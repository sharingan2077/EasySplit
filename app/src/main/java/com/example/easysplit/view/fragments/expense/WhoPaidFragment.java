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
import com.example.easysplit.databinding.FragmentWhoPaidBinding;
import com.example.easysplit.view.adapters.UsersRecyclerAdapter;
import com.example.easysplit.view.listeners.CompleteListener;
import com.example.easysplit.view.utils.NavigationUtils;
import com.example.easysplit.viewModel.expense.WhoPaidViewModel;

import java.util.ArrayList;

public class WhoPaidFragment extends Fragment {

    private static final String TAG = "WhoPaidFragment";

    FragmentWhoPaidBinding binding;
    NavController navController;
    private UsersRecyclerAdapter adapter;

    WhoPaidViewModel whoPaidViewModel;

    private int actionToLastFragment;
    private String groupId;

    private String userId;
    private String expenseId;
    private String expenseName;
    private String expenseSumString;

    private ArrayList<String> usersId;
    private long[] usersSum;

    private String nameOfGroup;
    private String nameOfUser;

    private int countMemberOfFirstGroup;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentWhoPaidBinding.inflate(inflater, container, false);
        navController = Navigation.findNavController(requireActivity(), R.id.navHostFragment);
        whoPaidViewModel = new ViewModelProvider(requireActivity()).get(WhoPaidViewModel.class);

        actionToLastFragment = getArguments().getInt("ActionToLastFragment", 1);
        groupId = getArguments().getString("groupId", "0");
        expenseId = getArguments().getString("expenseId", "0");
        userId = getArguments().getString("userId", "0");
        expenseSumString = getArguments().getString("expenseSum", "0");
        expenseName = getArguments().getString("expenseName", "0");

        usersId = getArguments().getStringArrayList("usersId");
        usersSum = getArguments().getLongArray("usersSum");

        nameOfGroup = getArguments().getString("nameOfGroup", "*2_39/");
        nameOfUser = getArguments().getString("nameOfUser", "*2_39/");
        countMemberOfFirstGroup = getArguments().getInt("countMemberOfFirstGroup", -1);

        whoPaidViewModel.init(groupId, new CompleteListener() {
            @Override
            public void successful() {
                adapter.notifyDataSetChanged();
            }

            @Override
            public void unSuccessful() {

            }
        });
        initRecyclerView();
        binding.toolbar.textToolbar.setText("Кто платит?");
        binding.toolbar.back.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putInt("ActionToLastFragment", actionToLastFragment);
            bundle.putString("groupId", groupId);
            bundle.putString("expenseId", expenseId);
            bundle.putString("userId", userId);
            bundle.putString("expenseName", expenseName);
            bundle.putString("expenseSum", expenseSumString);

            bundle.putStringArrayList("usersId", usersId);
            bundle.putLongArray("usersSum", usersSum);

            bundle.putString("nameOfUser", nameOfUser);
            bundle.putString("nameOfGroup", nameOfGroup);
            bundle.putInt("countMemberOfFirstGroup", countMemberOfFirstGroup);

            NavigationUtils.navigateSafe(navController, R.id.action_whoPaidFragment_to_addExpenseFragment, bundle);
        });
        return binding.getRoot();
    }

    private void initRecyclerView()
    {
        adapter = new UsersRecyclerAdapter(requireActivity(), whoPaidViewModel.getUsers().getValue(), (userId, userName) -> {
            Bundle bundle = new Bundle();
            bundle.putInt("ActionToLastFragment", actionToLastFragment);
            bundle.putString("groupId", groupId);

            bundle.putString("userId", userId);

            bundle.putString("nameOfUser", userName);
            bundle.putString("nameOfGroup", nameOfGroup);

            bundle.putString("expenseId", expenseId);
            bundle.putString("expenseName", expenseName);
            bundle.putString("expenseSum", expenseSumString);

            bundle.putStringArrayList("usersId", usersId);
            bundle.putLongArray("usersSum", usersSum);
            bundle.putInt("countMemberOfFirstGroup", countMemberOfFirstGroup);

            NavigationUtils.navigateSafe(navController, R.id.action_whoPaidFragment_to_addExpenseFragment, bundle);
        });
        binding.recyclerViewUsers.setAdapter(adapter);
        binding.recyclerViewUsers.setLayoutManager(new LinearLayoutManager(getActivity()));
    }
}