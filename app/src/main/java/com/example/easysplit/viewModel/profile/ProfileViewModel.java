package com.example.easysplit.viewModel.profile;

import android.content.Context;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.easysplit.repository.authentication.AccountRepository;
import com.example.easysplit.view.listeners.CompleteListener;

public class ProfileViewModel extends ViewModel {

    private AccountRepository mRepo;

    MutableLiveData<String> userEmail = new MutableLiveData<>();

    MutableLiveData<String> userNameAndId = new MutableLiveData<>();

    MutableLiveData<String> userImage = new MutableLiveData<>();


    public void init(Context mContext)
    {
        mRepo = AccountRepository.getInstance(mContext);
        userEmail = mRepo.getUserEmail();
        userNameAndId = mRepo.getUserNameAndId();
        userImage = mRepo.getUserImage();
    }

    public void changeImageUser(CompleteListener listener)
    {
        mRepo.changeUserImage(listener);
    }
    public void changeNickNameUser(String name, String id, CompleteListener listener)
    {
        mRepo.changeNickNameUser(name, id, listener);
    }

    public void refreshUserNameAndId()
    {
        userNameAndId = mRepo.getUserNameAndId();
    }


    public void refreshUserImage()
    {
        userImage = mRepo.getUserImage();
    }

    public LiveData<String> getUserImage() {
        return userImage;
    }

    public LiveData<String> getUserNameAndId() {
        return userNameAndId;
    }

    public LiveData<String> getUserEmail() {
        return userEmail;
    }
}
