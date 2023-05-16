package com.example.easysplit.view.fragments.expense;

import android.content.ClipData;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.easysplit.R;
import com.example.easysplit.databinding.FragmentAddExpenseBinding;
import com.example.easysplit.model.Expense;
import com.example.easysplit.view.listeners.CompleteListener2;
import com.example.easysplit.view.listeners.CompleteListenerListString;
import com.example.easysplit.view.utils.NavigationUtils;
import com.example.easysplit.viewModel.AddExpenseViewModel;
import com.example.easysplit.viewModel.MainActivityViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class AddExpenseFragment extends Fragment {
    private static final String TAG = "AddExpenseFragment";
    FragmentAddExpenseBinding binding;
    MainActivityViewModel mainActivityViewModel;
    AddExpenseViewModel addExpenseViewModel;
    NavController navController;

    private int actionToLastFragment;

    private String groupId;
    private String userId;

    private ArrayList<String> usersId;
    private long[] usersSum;

    private String expenseId;


    private String expenseName;
    private String expenseSumString;
    private Long expenseSum;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAddExpenseBinding.inflate(inflater, container, false);
        navController = Navigation.findNavController(requireActivity(), R.id.navHostFragment);

        addExpenseViewModel = new ViewModelProvider(requireActivity()).get(AddExpenseViewModel.class);
        addExpenseViewModel.init();


        actionToLastFragment = getArguments().getInt("ActionToLastFragment", 1);
        groupId = getArguments().getString("groupId", "0");
        userId = getArguments().getString("userId", "0");
        expenseSumString = getArguments().getString("expenseSum", "0");
        expenseName = getArguments().getString("expenseName", "0");
        if (!expenseSumString.equals("0"))
        {
            binding.sum.setText(expenseSumString);
        }
        if (!expenseName.equals("0"))
        {
            binding.description.setText(expenseName);
        }

        usersId = getArguments().getStringArrayList("usersId");
        usersSum = getArguments().getLongArray("usersSum");

        if (usersId == null) Log.d(TAG, "null1");
        if (usersSum == null) Log.d(TAG, "null2");

        expenseId = getArguments().getString("expenseId", "0");

        if (expenseId.equals("0"))
        {
            expenseId = UUID.randomUUID().toString();
            addExpenseViewModel.addExpense(expenseId);
        }

        if (groupId == "0")
        {
            addExpenseViewModel.getFirstGroupId();
        }
        else
        {
            addExpenseViewModel.setGroupId(groupId);
        }
        if (userId == "0")
        {
            addExpenseViewModel.getFirstUserId();
        }
        else
        {
            addExpenseViewModel.setUserId(userId);
        }

        final Observer<String> idOfGroupObserver = new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Log.d(TAG, "idOfGroupObserver - " + s);
                groupId = s;
                if (s != null)
                {
                    addExpenseViewModel.findNameOfGroupById(s, new CompleteListener2() {
                        @Override
                        public void successful(String data) {
                            binding.group.setText(data);
                        }
                    });
                }
            }
        };
        addExpenseViewModel.getIdOfGroup().observe(getViewLifecycleOwner(), idOfGroupObserver);
        final Observer<String> idOfUserObserver = new Observer<String>() {
            @Override
            public void onChanged(String s) {
                userId = s;
                Log.d(TAG, "on Changed id of User - " + s);
                if (s != null)
                {
                    addExpenseViewModel.findNameOfUserById(s, new CompleteListener2() {
                        @Override
                        public void successful(String data) {
                            binding.whoPaid.setText("  " + data + "  ");
                        }
                    });
                }
            }
        };
        addExpenseViewModel.getIdOfUser().observe(getViewLifecycleOwner(), idOfUserObserver);

        mainActivityViewModel = new ViewModelProvider(requireActivity()).get(MainActivityViewModel.class);
        binding.toolbar.back.setOnClickListener(v -> {
            addExpenseViewModel.deleteExpense(expenseId);
            mainActivityViewModel.setIsNotToMakeExpense();
            NavigationUtils.navigateSafe(navController, actionToLastFragment, null);
        });

        binding.whoPaid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("ActionToLastFragment", actionToLastFragment);
                bundle.putString("groupId", groupId);
                bundle.putString("expenseId", expenseId);
                bundle.putString("userId", userId);
                bundle.putString("expenseName", binding.description.getText().toString());
                bundle.putString("expenseSum", binding.sum.getText().toString());
                NavigationUtils.navigateSafe(navController, R.id.action_addExpenseFragment_to_whoPaidFragment, bundle);

            }
        });
        binding.group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("ActionToLastFragment", actionToLastFragment);
                bundle.putString("expenseId", expenseId);
                bundle.putString("groupId", groupId);
                bundle.putString("expenseName", binding.description.getText().toString());
                bundle.putString("expenseSum", binding.sum.getText().toString());
                bundle.putString("userId", userId);
                NavigationUtils.navigateSafe(navController, R.id.action_addExpenseFragment_to_chooseGroupFragment, bundle);
            }
        });

        binding.howSplit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("ActionToLastFragment", actionToLastFragment);
                bundle.putString("groupId", groupId);
                bundle.putString("expenseId", expenseId);
                bundle.putString("expenseName", binding.description.getText().toString());
                bundle.putString("expenseSum", binding.sum.getText().toString());
                bundle.putString("userId", userId);

                if (usersId == null)
                {
                    addExpenseViewModel.setUsersId(groupId, new CompleteListenerListString() {
                        @Override
                        public void successful(ArrayList<String> usersId) {
                            bundle.putStringArrayList("usersId", usersId);
                            NavigationUtils.navigateSafe(navController, R.id.action_addExpenseFragment_to_splitEquallyFragment, bundle);
                        }
                    });
                }
                else
                {
                    bundle.putStringArrayList("usersId", usersId);
                    NavigationUtils.navigateSafe(navController, R.id.action_addExpenseFragment_to_splitEquallyFragment, bundle);
                }
            }
        });

        binding.toolbar.done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.description.getText().toString().equals(""))
                {
                    Toast.makeText(requireContext(), "Заполните описание!", Toast.LENGTH_SHORT).show();
                }
                else if (binding.sum.getText().toString().equals(""))
                {
                    Toast.makeText(requireContext(), "Введите сумму расхода!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    expenseName = binding.description.getText().toString();
                    String expenseDate = new SimpleDateFormat("ddMM_HHmm").format(Calendar.getInstance().getTime());
                    expenseSum = Long.parseLong(binding.sum.getText().toString());

                    if (usersId == null)
                    {
                        addExpenseViewModel.setUsersId(groupId, new CompleteListenerListString() {
                            @Override
                            public void successful(ArrayList<String> usersId) {
                                Long waste = expenseSum / usersId.size();
                                HashMap<String,Long>  usersWaste = new HashMap<String, Long>();
                                for (String i : usersId) usersWaste.put(i, waste);
                                Expense expense = new Expense(expenseName, expenseDate, expenseSum, groupId, userId, usersWaste);
                                addExpenseViewModel.addExpense(expense, expenseId);
                            }
                        });
                    }
                    else
                    {
                        Long waste = expenseSum / usersId.size();
                        HashMap<String,Long>  usersWaste = new HashMap<String, Long>();
                        for (String i : usersId) usersWaste.put(i, waste);
                        Expense expense = new Expense(expenseName, expenseDate, expenseSum, groupId, userId, usersWaste);
                        addExpenseViewModel.addExpense(expense, expenseId);
                    }

                    Toast.makeText(requireContext(), "Расход сохранен!", Toast.LENGTH_SHORT).show();
                    mainActivityViewModel.setIsNotToMakeExpense();
                    NavigationUtils.navigateSafe(navController, actionToLastFragment, null);
                }
            }
        });


        return binding.getRoot();



    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.sum.setTransformationMethod(null);
        if (binding.sum.getText().toString().length() > 10)
        {
            Toast.makeText(requireActivity(), "Сумма не может превышать 10 символов!", Toast.LENGTH_SHORT).show();
        }
    }
}