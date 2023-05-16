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
import com.example.easysplit.view.listeners.CompleteListenerInt;

import java.util.ArrayList;
import java.util.List;

public class GroupEnterViewModel extends ViewModel {

    private MutableLiveData<List<ExpenseInGroup>> expensesInGroups;
    private MutableLiveData<List<DebtInGroup>> debtsInGroup;
    private ExpenseInGroupRepository mRepo1;
    private DebtInGroupRepository mRepo2;

    private GroupEnterRepository mRepo;

    public void setCountOfGroupMembers(Integer integer) {
        countOfGroupMembers.setValue(integer);
    }

    public MutableLiveData<Integer> countOfGroupMembers = new MutableLiveData<>();


    public void init(String groupId)
    {

        if (expensesInGroups != null)
        {
            //mRepo.getCountOfGroupMembers(groupId);
            expensesInGroups = mRepo1.getExpensesInGroup();
            debtsInGroup = mRepo2.getDebtsInGroup();
            return;
        }
        mRepo = GroupEnterRepository.getInstance();
        ///mRepo.getCountOfGroupMembers(groupId);
        mRepo1 = ExpenseInGroupRepository.getInstance();
        expensesInGroups = mRepo1.getExpensesInGroup();
        mRepo2 = DebtInGroupRepository.getInstance();
        debtsInGroup = mRepo2.getDebtsInGroup();
    }

    public void leaveGroup(String id)
    {
        mRepo.leaveGroup(id);
    }

    public void deleteGroup(String id)
    {
        mRepo.deleteGroup(id);
    }
//    public void setCountOfGroupMembers(String groupId)
//    {
//        mRepo.getCountOfGroupMembers(groupId, new GroupRepository.GetCountOfGroupMembersListener() {
//            @Override
//            public void onSuccessful(MutableLiveData<Integer> groupId) {
//                countOfGroupMembers = groupId;
//            }
//        });
//    }
    public void gettingCountOfGroupMembers(String groupId, CompleteListenerInt listenerInt)
    {
        mRepo.getCountOfGroupMembers(groupId, listenerInt);
    }

    public LiveData<Integer> getCountOfGroupMembers() {
        return countOfGroupMembers;
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
