package com.example.easysplit.viewModel.authentication;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.easysplit.repository.AuthAppRepository;
import com.example.easysplit.view.listeners.CompleteListener;

public class VerificationUserViewModel extends ViewModel {

    private AuthAppRepository mRepo;
    //private Application application;


    public void init(Context mContext)
    {

//        if (loggedOutLiveData != null)
//        {
//            loggedOutLiveData = mRepo.getLoggedOutLiveData(listener);
//            return;
//        }
        mRepo = mRepo.getInstance(mContext);
        //loggedOutLiveData = mRepo.getLoggedOutLiveData(listener);
    }

    public void sendVerificationEmail(String email, CompleteListener listener)
    {
        mRepo.resetPassword(email, listener);
    }
    private MutableLiveData<Boolean> isVerified;
//    public VerificationUserViewModel(@NonNull Application application) {
//        authAppRepository = new AuthAppRepository(application);
//    }

    public LiveData<Boolean> getIsVerified() {
        return isVerified;
    }

}
