package com.example.easysplit.viewModel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainActivityViewModel extends ViewModel {
    private static final String TAG = "MainActivityViewModel";
    private MutableLiveData<Boolean> isShowBottomBar = new MutableLiveData<>();

    private MutableLiveData<Integer> bottomNavigationItem = new MutableLiveData<>();

    public void showBottomNavigationBar()
    {
        Log.d(TAG, "Setting true");
        isShowBottomBar.setValue(true);
    }
    public void setBottomNavigationItem(int itemId)
    {
        bottomNavigationItem.setValue(itemId);
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
