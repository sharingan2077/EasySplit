package com.example.easysplit.viewModel.authentication;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.easysplit.repository.AuthAppRepository;
import com.example.easysplit.view.listeners.CompleteListener;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class LoginRegisterViewModel extends AndroidViewModel {
    private AuthAppRepository authAppRepository;

    // хранит текущего пользователя
    private MutableLiveData<FirebaseUser> userLiveData;

    // loggedOutLiveData хранит true - текущего пользователя нет, false - пользователь есть
    private MutableLiveData<Boolean> loggedOutLiveData;

    public LoginRegisterViewModel(@NonNull Application application) {
        super(application);
        authAppRepository = new AuthAppRepository(application);
        userLiveData = authAppRepository.getUserLiveData();
        loggedOutLiveData = authAppRepository.getLoggedOutLiveData();
    }


    public void login(String email, String password, CompleteListener listener) {

        authAppRepository.login(email, password, listener);
    }



    public void refreshLoggedOutLiveData()
    {
        authAppRepository.refreshLoggedOut();
        loggedOutLiveData = authAppRepository.getLoggedOutLiveData();
    }

    public void register(String email, String password, String userName) {
        authAppRepository.register(email, password, userName);
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
