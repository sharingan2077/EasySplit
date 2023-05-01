package com.example.easysplit.viewModel.groups;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.easysplit.model.DebtInGroup;
import com.example.easysplit.model.ExpenseInGroup;
import com.example.easysplit.repository.DebtInGroupRepository;
import com.example.easysplit.repository.ExpenseInGroupRepository;
import com.example.easysplit.repository.GroupEnterRepository;
import com.example.easysplit.repository.GroupRepository;

import java.util.ArrayList;
import java.util.List;

public class GroupEnterViewModel extends ViewModel {

    private MutableLiveData<List<ExpenseInGroup>> expensesInGroups;
    private MutableLiveData<List<DebtInGroup>> debtsInGroup;
    private ExpenseInGroupRepository mRepo1;
    private DebtInGroupRepository mRepo2;

    private GroupEnterRepository mRepo;

    public MutableLiveData<Integer> countOfGroupMembers = new MutableLiveData<>();


    public LiveData<Integer> getCountOfGroupMembers() {
        return countOfGroupMembers;
    }

    public void init()
    {

        if (expensesInGroups != null)
        {
            return;
        }
        mRepo = GroupEnterRepository.getInstance();
        countOfGroupMembers = mRepo.getCountOfGroupMembers();
        mRepo1 = ExpenseInGroupRepository.getInstance();
        expensesInGroups = mRepo1.getExpensesInGroup();
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

    public void setCountOfGroupMembers(String groupId)
    {
        mRepo.getCountOfGroupMembers(groupId, new GroupRepository.GetCountOfGroupMembersListener() {
            @Override
            public void onSuccessful(MutableLiveData<Integer> groupId) {
                countOfGroupMembers = groupId;
            }
        });
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
