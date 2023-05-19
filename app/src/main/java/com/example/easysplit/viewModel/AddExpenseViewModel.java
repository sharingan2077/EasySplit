package com.example.easysplit.viewModel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.easysplit.model.Expense;
import com.example.easysplit.repository.AddExpenseRepository;
import com.example.easysplit.repository.DebtInGroupRepository;
import com.example.easysplit.repository.ExpenseInGroupRepository;
import com.example.easysplit.repository.GroupEnterRepository;
import com.example.easysplit.view.listeners.CheckUsersIdListener;
import com.example.easysplit.view.listeners.CompleteListener;
import com.example.easysplit.view.listeners.CompleteListener2;
import com.example.easysplit.view.listeners.CompleteListenerInt;
import com.example.easysplit.view.listeners.CompleteListenerListString;

import java.util.ArrayList;

public class AddExpenseViewModel extends ViewModel {

    private static final String TAG = "AddExpenseViewModel";
    MutableLiveData<Integer> lastFragmentAction = new MutableLiveData<>();
    MutableLiveData<String> nameOfGroup = new MutableLiveData<>();
    MutableLiveData<String> idOfGroup;

    MutableLiveData<String> idOfUser = new MutableLiveData<>();

    MutableLiveData<String> nameOfUser = new MutableLiveData<>();

    MutableLiveData<String> usersId = new MutableLiveData<>();

    public MutableLiveData<String> getUsersId() {
        return usersId;
    }

    public void setUsersId(String groupId, CompleteListenerListString listenerListString)
    {
        mRepo.setUsersId(groupId, listenerListString);
    }

    private AddExpenseRepository mRepo;

    public void init()
    {

        if (idOfGroup != null)
        {
            return;
        }
        mRepo = AddExpenseRepository.getInstance();
    }

    public void findNameOfGroupById(String id, CompleteListener2 listener)
    {
        mRepo.findNameOfGroupById(id, listener);
    }
    public void findCountOfGroupMemberById(String id, CompleteListenerInt listener)
    {
        mRepo.findCountOfGroupMemberById(id, listener);
    }
    public void setGroupId(String id)
    {
        idOfGroup.setValue(id);
    }

    public void getFirstGroupId()
    {
        idOfGroup = mRepo.getGroupId();
    }

    public void findNameOfUserById(String id, CompleteListener2 listener)
    {
        mRepo.findNameOfUserById(id, listener);
    }
    public void getFirstUserId()
    {
        idOfUser.postValue(mRepo.getUserId());
    }
    public void setUserId(String id)
    {
        idOfUser.setValue(id);
    }





    public void addExpense(String id)
    {
        mRepo.addExpense(id);
    }
    public void addExpense(final Expense expense, String id)
    {
        mRepo.addExpense(expense, id);
    }
    public void deleteExpense(String id)
    {
        mRepo.deleteExpense(id);
    }

    public void setLastFragmentAction(Integer id)
    {
        Log.d(TAG, "Setting Last Fragment " + id.toString() + " to ExpenseFragment");
        lastFragmentAction.setValue(id);
    }

    public LiveData<Integer> getLastFragment()
    {
        return lastFragmentAction;
    }

    public LiveData<String> getIdOfGroup() {
        return idOfGroup;
    }
    public LiveData<String> getNameOfGroup() {
        return nameOfGroup;
    }
    public LiveData<String> getIdOfUser() {
        return idOfUser;
    }
    public LiveData<String> getNameOfUser() {
        return nameOfUser;
    }



}
