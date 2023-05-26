package com.example.easysplit.viewModel.mainActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainActivityViewModel extends ViewModel {
    private static final String TAG = "MainActivityViewModel";
    private final MutableLiveData<Boolean> isShowBottomBar = new MutableLiveData<>();

    private final MutableLiveData<Integer> bottomNavigationItem = new MutableLiveData<>();

    private final MutableLiveData<Boolean> isGoToMakeExpense = new MutableLiveData<>();

    private final MutableLiveData<String> userImage = new MutableLiveData<>();

    private Context mContext;

    private int countOfGroups = 0;

    public LiveData<String> getUserImage() {
        return userImage;
    }

    public void init(Context context)
    {
        isShowBottomBar.setValue(false);
        mContext = context;
        setUserImage();

    }

    public void setUserImage()
    {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("ACCOUNT_FILE_KEY", Context.MODE_PRIVATE);
        userImage.setValue(sharedPreferences.getString("UserImage", "0"));
    }
    public void initFab()
    {
        isGoToMakeExpense.setValue(false);
    }

    public void setCountOfGroups(int value)
    {
        countOfGroups = value;
    }

    public void showBottomNavigationBar()
    {
        Log.d(TAG, "Setting true");
        if (isShowBottomBar.getValue() == false)
        {
            isShowBottomBar.setValue(true);
        }
    }

    public void setIsGoToMakeExpense()
    {
        Log.d(TAG, Integer.toString(countOfGroups));
        if (countOfGroups != 0)
        {
            isGoToMakeExpense.setValue(true);
        }
        else
        {
            Toast.makeText(mContext, "У вас пока нет групп!", Toast.LENGTH_SHORT).show();
        }
    }
    public void setIsNotToMakeExpense()
    {
        isGoToMakeExpense.setValue(false);
    }
    public void setBottomNavigationItem(int itemId)
    {
        bottomNavigationItem.setValue(itemId);
        Log.d("MyTag", "Setting itemId Successful");
    }
    public LiveData<Boolean> getIsShowBottomBar()
    {
        return isShowBottomBar;
    }

    public LiveData<Boolean> getIsGoToMakeExpense()
    {
        return isGoToMakeExpense;
    }

}
