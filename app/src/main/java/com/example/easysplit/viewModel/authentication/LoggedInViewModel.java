package com.example.easysplit.viewModel.authentication;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.easysplit.repository.AuthAppRepository;
import com.google.firebase.auth.FirebaseUser;

public class LoggedInViewModel extends ViewModel {
    private AuthAppRepository authAppRepository;
    private MutableLiveData<FirebaseUser> userLiveData;
    private MutableLiveData<Boolean> loggedOutLiveData;

//    public LoggedInViewModel(@NonNull Application application) {
//        super(application);
//
//        authAppRepository = new AuthAppRepository(application);
//        userLiveData = authAppRepository.getUserLiveData();
//        loggedOutLiveData = authAppRepository.getLoggedOutLiveData();
//    }

    public void logOut() {
        authAppRepository.logOut();
    }


    public MutableLiveData<FirebaseUser> getUserLiveData() {
        return userLiveData;
    }

    public MutableLiveData<Boolean> getLoggedOutLiveData() {
        return loggedOutLiveData;
    }

}
