package com.example.easysplit.view.fragments.expense;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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
import com.example.easysplit.view.listeners.CompleteListenerInt;
import com.example.easysplit.view.listeners.CompleteListenerListString;
import com.example.easysplit.view.utils.NavigationUtils;
import com.example.easysplit.viewModel.expense.AddExpenseViewModel;
import com.example.easysplit.viewModel.mainActivity.MainActivityViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
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

    private int countMemberOfFirstGroup;
    private String nameOfGroup;
    private String nameOfUser;




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
        Log.d(TAG, "groupId - " + groupId);
        nameOfGroup = getArguments().getString("nameOfGroup", "*2_39/");
        Log.d(TAG, "nameOfGroup - " + nameOfGroup);
        nameOfUser = getArguments().getString("nameOfUser", "*2_39/");
        Log.d(TAG, "nameOfUser - " + nameOfUser);
        countMemberOfFirstGroup = getArguments().getInt("countMemberOfFirstGroup", -1);

        if (!expenseSumString.equals("0"))
        {
            binding.sum.setText(expenseSumString);
        }
        if (!expenseName.equals("0"))
        {
            binding.description.setText(expenseName);
        }
        if (!nameOfGroup.equals("*2_39/"))
        {
            binding.group.setText(nameOfGroup);
        }
        if (!nameOfUser.equals("*2_39/"))
        {
            binding.whoPaid.setText("  " + nameOfUser + "  ");
        }

        usersId = getArguments().getStringArrayList("usersId");
        usersSum = getArguments().getLongArray("usersSum");

        if (usersId == null)
        {
            Log.d(TAG, "null1");
        }

        if (usersSum == null)
        {
            Log.d(TAG, "null2");
        }
        else
        {
            binding.howSplit.setText("  По частям  ");
        }


        expenseId = getArguments().getString("expenseId", "0");

        if (expenseId.equals("0"))
        {
            expenseId = UUID.randomUUID().toString();
            addExpenseViewModel.addExpense(expenseId);
        }

        if (groupId.equals("0"))
        {
            Log.d("Releasing", "gettingFirstGroupId");
            addExpenseViewModel.getFirstGroupId(data -> {
                groupId = data;
                Log.d(TAG, "idOfGroupObserver - " + groupId);
                addExpenseViewModel.findNameOfGroupById(groupId, data1 -> {
                    Log.d(TAG, "nameOfGroup in Observer - " + data1);
                    nameOfGroup = data1;
                    binding.group.setText(data1);
                });
                addExpenseViewModel.findCountOfGroupMemberById(groupId, data12 -> countMemberOfFirstGroup = data12);
            });
        }
        if (userId.equals("0"))
        {
            addExpenseViewModel.getFirstUserId(data -> {
                userId = data;
                Log.d(TAG, "on Changed id of User - " + userId);
                addExpenseViewModel.findNameOfUserById(userId, data13 -> {
                    Log.d(TAG, "nameOfUser in Observer - " + data13);
                    nameOfUser = data13;
                    binding.whoPaid.setText("  " + data13 + "  ");
                });
            });
        }
        mainActivityViewModel = new ViewModelProvider(requireActivity()).get(MainActivityViewModel.class);
        binding.toolbar.back.setOnClickListener(v -> {
            addExpenseViewModel.deleteExpense(expenseId);
            mainActivityViewModel.setIsNotToMakeExpense();
            mainActivityViewModel.showBottomNavigationBar();
            NavigationUtils.navigateSafe(navController, actionToLastFragment, null);
        });

        binding.whoPaid.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putInt("ActionToLastFragment", actionToLastFragment);
            bundle.putString("groupId", groupId);
            bundle.putString("expenseId", expenseId);
            bundle.putString("userId", userId);
            bundle.putString("expenseName", binding.description.getText().toString());
            bundle.putString("expenseSum", binding.sum.getText().toString());
            bundle.putStringArrayList("usersId", usersId);
            bundle.putLongArray("usersSum", usersSum);

            bundle.putString("nameOfUser", nameOfUser);
            bundle.putString("nameOfGroup", nameOfGroup);
            bundle.putInt("countMemberOfFirstGroup", countMemberOfFirstGroup);

            NavigationUtils.navigateSafe(navController, R.id.action_addExpenseFragment_to_whoPaidFragment, bundle);

        });
        binding.group.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putInt("ActionToLastFragment", actionToLastFragment);
            bundle.putString("expenseId", expenseId);
            bundle.putString("groupId", groupId);
            bundle.putString("expenseName", binding.description.getText().toString());
            bundle.putString("expenseSum", binding.sum.getText().toString());
            bundle.putString("userId", userId);
            bundle.putStringArrayList("usersId", usersId);
            bundle.putLongArray("usersSum", usersSum);

            bundle.putString("nameOfUser", nameOfUser);
            bundle.putString("nameOfGroup", nameOfGroup);
            bundle.putInt("countMemberOfFirstGroup", countMemberOfFirstGroup);

            NavigationUtils.navigateSafe(navController, R.id.action_addExpenseFragment_to_chooseGroupFragment, bundle);
        });

        binding.howSplit.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putInt("ActionToLastFragment", actionToLastFragment);
            bundle.putString("groupId", groupId);
            bundle.putString("expenseId", expenseId);
            bundle.putString("expenseName", binding.description.getText().toString());
            String expenseSum = binding.sum.getText().toString();
            if (expenseSum.equals("")) expenseSum = "0";
            bundle.putString("expenseSum", expenseSum);
            bundle.putString("userId", userId);

            bundle.putString("nameOfUser", nameOfUser);
            bundle.putString("nameOfGroup", nameOfGroup);
            bundle.putInt("countMemberOfFirstGroup", countMemberOfFirstGroup);


            if (usersId == null)
            {
                addExpenseViewModel.setUsersId(groupId, usersId -> {
                    bundle.putStringArrayList("usersId", usersId);
                    bundle.putLongArray("usersSum", usersSum);
                    if (binding.sum.getText().toString().length() > 0 && binding.sum.getText().toString().charAt(0) == '0')
                    {
                        Toast.makeText(requireContext(), "Сумма расхода не может начинаться с 0!", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        NavigationUtils.navigateSafe(navController, R.id.action_addExpenseFragment_to_splitEquallyFragment, bundle);
                    }
                });
            }
            else
            {
                if (binding.sum.getText().toString().length() > 0 && binding.sum.getText().toString().charAt(0) == '0')
                {
                    Toast.makeText(requireContext(), "Сумма расхода не может начинаться с 0!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    bundle.putLongArray("usersSum", usersSum);
                    bundle.putStringArrayList("usersId", usersId);
                    NavigationUtils.navigateSafe(navController, R.id.action_addExpenseFragment_to_splitEquallyFragment, bundle);
                }
            }
        });

        binding.toolbar.done.setOnClickListener(v -> {
            long totalSum = 0;
            if (usersSum != null)
            {
                for (long i : usersSum) totalSum+=i;
            }
            if (binding.description.getText().toString().equals(""))
            {
                Toast.makeText(requireContext(), "Заполните описание!", Toast.LENGTH_SHORT).show();
            }
            else if (countMemberOfFirstGroup < 2 && countMemberOfFirstGroup != -1)
            {
                Toast.makeText(requireContext(), "В группе должно быть как минимум 2 участника!", Toast.LENGTH_SHORT).show();
            }
            else if (binding.sum.getText().toString().equals("") || binding.sum.getText().toString().equals("0"))
            {
                Toast.makeText(requireContext(), "Введите сумму расхода!", Toast.LENGTH_SHORT).show();
            }
            else if (binding.sum.getText().toString().charAt(0) == '0')
            {
                Toast.makeText(requireContext(), "Сумма расхода не может начинаться с 0!", Toast.LENGTH_SHORT).show();
            }
            else if (!Long.toString(totalSum).equals(binding.sum.getText().toString()) && usersSum != null)
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
                builder.setTitle("Невозможно сохранить расход");
                builder.setMessage("Сумма, поделенная между участниками, не равна всей сумме");
                builder.setPositiveButton("Ок", (dialog, which) -> {
                    return;
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
            else
            {
                expenseName = binding.description.getText().toString();
                String expenseDate = new SimpleDateFormat("ddMM_HHmm").format(Calendar.getInstance().getTime());
                expenseSum = Long.parseLong(binding.sum.getText().toString());

                if (usersId == null)
                {
                    addExpenseViewModel.setUsersId(groupId, usersId -> {
                        Long waste = expenseSum / usersId.size();
                        HashMap<String,Long>  usersWaste = new HashMap<String, Long>();
                        for (String i : usersId) usersWaste.put(i, waste);
                        Expense expense = new Expense(expenseName, expenseDate, expenseSum, groupId, userId, usersWaste);
                        addExpenseViewModel.addExpense(expense, expenseId);
                    });
                }
                else
                {
                    HashMap<String,Long>  usersWaste = new HashMap<String, Long>();
                    if (usersSum == null)
                    {
                        Long waste = expenseSum / usersId.size();
                        for (String i : usersId) usersWaste.put(i, waste);
                    }
                    else
                    {
                        for (int i = 0; i < usersId.size(); i++)
                        {
                            usersWaste.put(usersId.get(i), usersSum[i]);
                        }
                    }
                    Expense expense = new Expense(expenseName, expenseDate, expenseSum, groupId, userId, usersWaste);
                    addExpenseViewModel.addExpense(expense, expenseId);
                }

                Toast.makeText(requireContext(), "Расход сохранен!", Toast.LENGTH_SHORT).show();
                mainActivityViewModel.setIsNotToMakeExpense();
                NavigationUtils.navigateSafe(navController, actionToLastFragment, null);
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