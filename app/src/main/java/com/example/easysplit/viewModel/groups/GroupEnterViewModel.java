package com.example.easysplit.viewModel.groups;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.easysplit.model.DebtInGroup;
import com.example.easysplit.model.ExpenseInGroup;
import com.example.easysplit.repository.DebtInGroupRepository;
import com.example.easysplit.repository.ExpenseInGroupRepository;

import java.util.ArrayList;
import java.util.List;

public class GroupEnterViewModel extends ViewModel {

    private MutableLiveData<List<ExpenseInGroup>> expensesInGroups;
    private MutableLiveData<List<DebtInGroup>> debtsInGroup;
    private ExpenseInGroupRepository mRepo;
    private DebtInGroupRepository mRepo2;

    public void init()
    {

        if (expensesInGroups != null)
        {
            return;
        }
        mRepo = ExpenseInGroupRepository.getInstance();
        expensesInGroups = mRepo.getExpensesInGroup();
        mRepo2 = DebtInGroupRepository.getInstance();
        debtsInGroup = mRepo2.getDebtsInGroup();
    }

    public void addNewValue(final ExpenseInGroup expenseInGroup)
    {
        List<ExpenseInGroup> currentExpensesInGroup;
        if (expensesInGroups != null)
        {
            currentExpensesInGroup = expensesInGroups.getValue();
        }
        else
        {
            expensesInGroups = new MutableLiveData<>();
            currentExpensesInGroup = new ArrayList<>();
        }
        currentExpensesInGroup.add(expenseInGroup);
        expensesInGroups.setValue(currentExpensesInGroup);
    }

    public LiveData<List<ExpenseInGroup>> getExpensesInGroups()
    {
        return expensesInGroups;
    }

    public LiveData<List<DebtInGroup>> getDebtsInGroup()
    {
        return debtsInGroup;
    }

}
