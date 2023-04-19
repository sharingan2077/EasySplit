package com.example.easysplit.viewModel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AddExpenseViewModel extends ViewModel {

    private static final String TAG = "AddExpenseViewModel";

    MutableLiveData<Integer> lastFragmentAction = new MutableLiveData<>();

    public void setLastFragmentAction(Integer id)
    {
        Log.d(TAG, "Setting Last Fragment " + id.toString() + " to ExpenseFragment");
        lastFragmentAction.setValue(id);
    }

    public LiveData<Integer> getLastFragment()
    {
        return lastFragmentAction;
    }



}
