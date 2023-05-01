package com.example.easysplit.viewModel;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.easysplit.repository.AccountRepository;
import com.example.easysplit.repository.AuthAppRepository;
import com.example.easysplit.repository.GroupRepository;

public class ProfileViewModel extends ViewModel {

    private AccountRepository mRepo;

    MutableLiveData<String> userEmail = new MutableLiveData<>();

    MutableLiveData<String> userNameAndId = new MutableLiveData<>();
    MutableLiveData<Boolean> loadAccount = new MutableLiveData<>();


    public void init(Context mContext)
    {
        loadAccount.setValue(false);
        Log.d("LoadingAcc", loadAccount.getValue().toString());
        mRepo = AccountRepository.getInstance(mContext);
        userEmail = mRepo.getUserEmail();
        userNameAndId = mRepo.getUserNameAndId();
        Log.d("UserNameAndId", userNameAndId.getValue());
        loadAccount.setValue(true);
        Log.d("LoadingAcc", loadAccount.getValue().toString());
    }

    public LiveData<String> getUserNameAndId() {
        return userNameAndId;
    }

    public LiveData<String> getUserEmail() {
        return userEmail;
    }
}
