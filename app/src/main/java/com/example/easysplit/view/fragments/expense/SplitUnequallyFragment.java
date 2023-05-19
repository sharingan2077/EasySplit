package com.example.easysplit.view.fragments.expense;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import com.example.easysplit.databinding.FragmentSplitUnequallyBinding;
import com.example.easysplit.view.adapters.UsersSplitUnequallyAdapter;
import com.example.easysplit.view.listeners.CompleteListener;
import com.example.easysplit.view.utils.NavigationUtils;
import com.example.easysplit.viewModel.WhoPaidViewModel;

import java.util.ArrayList;
import java.util.HashMap;

public class SplitUnequallyFragment extends Fragment {

    private static final String TAG = "SplitUnequallyFragment";
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

    private ArrayList<String> usersId;
    private ArrayList<Long> usersSumArrayList = new ArrayList<>();
    private long[] usersSum;

    private HashMap<String, Long> map = new HashMap<>();

    private String totalSumString = "0";

    private String nameOfGroup;
    private String nameOfUser;

    private int countMemberOfFirstGroup;

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
        binding.sumRemained.setText("₽0 из ₽" + expenseSumString);

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

                NavigationUtils.navigateSafe(navController, R.id.action_splitUnequallyFragment_to_addExpenseFragment, bundle);
            }
        });
        binding.toolbar.done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!totalSumString.equals(expenseSumString))
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
                    builder.setTitle("Уууупс!");
                    builder.setMessage("Сумма введенных расходов (₽" + totalSumString + ")" + " не равна исходной " +
                            "сумме расхода (₽" + expenseSumString + ").");
                    builder.setPositiveButton("Ок", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            return;
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
                else
                {
                    Bundle bundle = new Bundle();
                    bundle.putInt("ActionToLastFragment", actionToLastFragment);
                    bundle.putString("expenseId", expenseId);
                    bundle.putString("groupId", groupId);
                    bundle.putString("expenseName", expenseName);
                    bundle.putString("expenseSum", expenseSumString);
                    bundle.putString("userId", userId);

                    bundle.putString("nameOfUser", nameOfUser);
                    bundle.putString("nameOfGroup", nameOfGroup);
                    bundle.putInt("countMemberOfFirstGroup", countMemberOfFirstGroup);

                    //bundle.putString("totalSum", totalSumString);
                    usersId.clear();
                    for (String name: map.keySet()) {
                        String key = name.toString();
                        String value = map.get(name).toString();
                        usersId.add(key);
                        usersSumArrayList.add(map.get(name));
                        //Log.d(TAG, "Print hashmap " + key + "  " + value);
                    }
                    usersSum = new long[usersSumArrayList.size()];
                    for (int i = 0; i < usersSumArrayList.size(); i++)
                    {
                        usersSum[i] = usersSumArrayList.get(i);
                    }
                    bundle.putStringArrayList("usersId", usersId);
                    bundle.putLongArray("usersSum", usersSum);
                    NavigationUtils.navigateSafe(navController, R.id.action_splitUnequallyFragment_to_addExpenseFragment, bundle);
                }
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

                bundle.putStringArrayList("usersId", usersId);
                bundle.putLongArray("usersSum", usersSum);

                bundle.putString("nameOfUser", nameOfUser);
                bundle.putString("nameOfGroup", nameOfGroup);
                bundle.putInt("countMemberOfFirstGroup", countMemberOfFirstGroup);

                Log.d(TAG, "size of usersId - " + Integer.toString(usersId.size()));
                NavigationUtils.navigateSafe(navController, R.id.action_splitUnequallyFragment_to_splitEquallyFragment, bundle);
            }
        });

        return binding.getRoot();
    }

    private void initRecyclerView()
    {
        adapter = new UsersSplitUnequallyAdapter(requireActivity(), whoPaidViewModel.getUsers().getValue(), usersId, usersSum, new UsersSplitUnequallyAdapter.onSumClickListener() {
            @Override
            public void onChangeSum(String userId, String sum) {
                if (!sum.equals(""))
                {
                    long s = Long.parseLong(sum);
                    map.put(userId, s);
                }
                else
                {
                    map.remove(userId);
                }
                long totalSum = 0;
                for (String name: map.keySet()) {
                    totalSum += map.get(name);
                }
                totalSumString = Long.toString(totalSum);
                binding.sumRemained.setText("₽" + Long.toString(totalSum) + " из ₽" + expenseSumString);
            }
        });
        binding.recyclerViewSplitUnequally.setAdapter(adapter);
        binding.recyclerViewSplitUnequally.setLayoutManager(new LinearLayoutManager(getActivity()));
    }
}