package com.example.easysplit.viewModel.authentication;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.easysplit.repository.AuthAppRepository;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class LoginRegisterViewModel extends AndroidViewModel {
    private AuthAppRepository authAppRepository;
    private MutableLiveData<FirebaseUser> userLiveData;

    private MutableLiveData<Boolean> loggedOutLiveData;

    public LoginRegisterViewModel(@NonNull Application application) {
        super(application);
        authAppRepository = new AuthAppRepository(application);
        userLiveData = authAppRepository.getUserLiveData();
        loggedOutLiveData = authAppRepository.getLoggedOutLiveData();
    }

    public void login(String email, String password) {

        authAppRepository.login(email, password);
    }

    public void refreshLoggedOutLiveData()
    {
        Log.d("Naruto", "Refreshing");
        authAppRepository.refreshLoggedOut();
        loggedOutLiveData = authAppRepository.getLoggedOutLiveData();
    }

    public void register(String email, String password) {
        authAppRepository.register(email, password);
    }

    public void sendVerificationEmail()
    {
        authAppRepository.sendVerificationEmail();
    }

    public void logOut() {
        authAppRepository.logOut();
    }

    public MutableLiveData<Boolean> getLoggedOutLiveData() {
        return loggedOutLiveData;
    }

    public MutableLiveData<FirebaseUser> getUserLiveData() {
        return userLiveData;
    }


}
