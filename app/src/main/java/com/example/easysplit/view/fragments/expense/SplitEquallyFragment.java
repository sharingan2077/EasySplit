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
import android.widget.Toast;

import com.example.easysplit.R;
import com.example.easysplit.databinding.FragmentSplitEquallyBinding;
import com.example.easysplit.view.adapters.UsersSplitEquallyAdapter;
import com.example.easysplit.view.listeners.CheckUsersIdListener;
import com.example.easysplit.view.listeners.CompleteListener;
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
    private ArrayList<String> usersIdCash;
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
        binding = FragmentSplitEquallyBinding.inflate(inflater, container, false);
        navController = Navigation.findNavController(requireActivity(), R.id.navHostFragment);
        actionToLastFragment = getArguments().getInt("ActionToLastFragment", 1);
        groupId = getArguments().getString("groupId", "0");
        expenseId = getArguments().getString("expenseId", "0");
        expenseSumString = getArguments().getString("expenseSum", "0");
        expenseName = getArguments().getString("expenseName", "0");
        userId = getArguments().getString("userId", "0");

        usersId = getArguments().getStringArrayList("usersId");
        usersSum = getArguments().getLongArray("usersSum");

        nameOfGroup = getArguments().getString("nameOfGroup", "*2_39/");
        nameOfUser = getArguments().getString("nameOfUser", "*2_39/");
        countMemberOfFirstGroup = getArguments().getInt("countMemberOfFirstGroup", -1);

        whoPaidViewModel = new ViewModelProvider(requireActivity()).get(WhoPaidViewModel.class);
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
        binding.toolbar.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                usersIdCash = getArguments().getStringArrayList("usersId");
                Log.d(TAG, "onClicl - " + Integer.toString(usersIdCash.size()));
                bundle.putInt("ActionToLastFragment", actionToLastFragment);
                bundle.putString("expenseId", expenseId);
                bundle.putString("groupId", groupId);
                bundle.putString("expenseName", expenseName);
                bundle.putString("expenseSum", expenseSumString);
                bundle.putString("userId", userId);
                bundle.putStringArrayList("usersId", usersIdCash);
                bundle.putLongArray("usersSum", usersSum);

                bundle.putString("nameOfUser", nameOfUser);
                bundle.putString("nameOfGroup", nameOfGroup);
                bundle.putInt("countMemberOfFirstGroup", countMemberOfFirstGroup);

                //Log.d(TAG, "put" + Integer.toString(usersIdCash.size()));
                NavigationUtils.navigateSafe(navController, R.id.action_splitEquallyFragment_to_addExpenseFragment, bundle);
            }
        });
        binding.toolbar.done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                whoPaidViewModel.checkUsersId(usersId, userId, new CheckUsersIdListener() {
                    @Override
                    public void successful() {
                        Bundle bundle = new Bundle();
                        bundle.putInt("ActionToLastFragment", actionToLastFragment);
                        bundle.putString("expenseId", expenseId);
                        bundle.putString("groupId", groupId);
                        bundle.putString("expenseName", expenseName);
                        bundle.putString("expenseSum", expenseSumString);
                        bundle.putString("userId", userId);
                        bundle.putStringArrayList("usersId", usersId);

                        bundle.putString("nameOfUser", nameOfUser);
                        bundle.putString("nameOfGroup", nameOfGroup);
                        bundle.putInt("countMemberOfFirstGroup", countMemberOfFirstGroup);


                        NavigationUtils.navigateSafe(navController, R.id.action_splitEquallyFragment_to_addExpenseFragment, bundle);
                    }
                    @Override
                    public void noUsersId() {
                        Toast.makeText(requireContext(), "Вы должны выбрать хотя бы 1 человека, участвующего в расходе", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onlyOwnUserId() {
                        Toast.makeText(requireContext(), "Расход не может быть поделен между одним и тем же участником!", Toast.LENGTH_SHORT).show();
                    }
                });
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

                bundle.putStringArrayList("usersId", usersId);
                bundle.putLongArray("usersSum", usersSum);

                bundle.putString("nameOfUser", nameOfUser);
                bundle.putString("nameOfGroup", nameOfGroup);
                bundle.putInt("countMemberOfFirstGroup", countMemberOfFirstGroup);

                NavigationUtils.navigateSafe(navController, R.id.action_splitEquallyFragment_to_splitUnequallyFragment, bundle);
            }
        });


        return binding.getRoot();
    }

    private void initRecyclerView()
    {
        adapter = new UsersSplitEquallyAdapter(requireActivity(), whoPaidViewModel.getUsers().getValue(), usersId, new UsersSplitEquallyAdapter.onUserClickListener() {
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