package com.example.easysplit.viewModel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainActivityViewModel extends ViewModel {
    private static final String TAG = "MainActivityViewModel";
    private final MutableLiveData<Boolean> isShowBottomBar = new MutableLiveData<>();

    private final MutableLiveData<Integer> bottomNavigationItem = new MutableLiveData<>();

    public void init()
    {
        isShowBottomBar.setValue(false);
    }

    public void showBottomNavigationBar()
    {
        Log.d(TAG, "Setting true");
        if (isShowBottomBar.getValue() == false)
        {
            isShowBottomBar.setValue(true);
        }
    }
    public void setBottomNavigationItem(int itemId)
    {
        bottomNavigationItem.setValue(itemId);
        Log.d("MyTag", "Setting itemId Successful");
    }
    public void hideBottomNavigationBar()
    {
        isShowBottomBar.setValue(false);
    }
    public LiveData<Boolean> getIsShowBottomBar()
    {
        return isShowBottomBar;
    }

    public LiveData<Integer> getBottomNavigationItem()
    {
        return bottomNavigationItem;
    }

}
