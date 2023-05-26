package com.example.easysplit.viewModel.groups;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.easysplit.model.DebtInGroup;
import com.example.easysplit.model.ExpenseInGroup;
import com.example.easysplit.repository.groups.DebtInGroupRepository;
import com.example.easysplit.repository.groups.ExpenseInGroupRepository;
import com.example.easysplit.repository.groups.GroupEnterRepository;
import com.example.easysplit.view.listeners.CompleteListener2;

import java.util.List;

public class GroupEnterViewModel extends ViewModel {

    private MutableLiveData<List<ExpenseInGroup>> expensesInGroups;
    private MutableLiveData<List<DebtInGroup>> debtsInGroup;
    private ExpenseInGroupRepository mRepo1;
    private DebtInGroupRepository mRepo2;

    private GroupEnterRepository mRepo;


    public void init(String groupId, CompleteListener2 listener)
    {

        if (expensesInGroups != null)
        {
            expensesInGroups = mRepo1.getExpensesInGroup(groupId, listener);
            debtsInGroup = mRepo2.getDebtsInGroup(groupId, listener);
            return;
        }
        mRepo = GroupEnterRepository.getInstance();
        mRepo1 = ExpenseInGroupRepository.getInstance();
        expensesInGroups = mRepo1.getExpensesInGroup(groupId, listener);
        mRepo2 = DebtInGroupRepository.getInstance();
        debtsInGroup = mRepo2.getDebtsInGroup(groupId, listener);
    }

    public void leaveGroup(String id)
    {
        mRepo.leaveGroup(id);
    }

    public void deleteGroup(String id)
    {
        mRepo.deleteGroup(id);
    }

    public void changeNameOfGroup(String name, String groupId)
    {
        mRepo.changeNameOfGroup(name, groupId);
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
