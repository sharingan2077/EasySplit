package com.example.easysplit.viewModel.authentication;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.easysplit.repository.ActivityRepository;
import com.example.easysplit.repository.AuthAppRepository;
import com.example.easysplit.view.listeners.CompleteListener;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class LoginRegisterViewModel extends ViewModel {
    private AuthAppRepository mRepo;

    // хранит текущего пользователя
    private MutableLiveData<FirebaseUser> userLiveData;

    // loggedOutLiveData хранит true - текущего пользователя нет, false - пользователь есть
    private MutableLiveData<Boolean> loggedOutLiveData;

//    public LoginRegisterViewModel(@NonNull Application application) {
//        super(application);
//        authAppRepository = new AuthAppRepository(application);
//        userLiveData = authAppRepository.getUserLiveData();
//        loggedOutLiveData = authAppRepository.getLoggedOutLiveData();
//    }
    public void init(Context context, CompleteListener listener)
    {

        if (loggedOutLiveData != null)
        {
            loggedOutLiveData = mRepo.getLoggedOutLiveData(listener);
            return;
        }
        mRepo = AuthAppRepository.getInstance(context);
        loggedOutLiveData = mRepo.getLoggedOutLiveData(listener);
    }


    public void login(String email, String password, CompleteListener listener) {

        mRepo.login(email, password, listener);
    }



//    public void refreshLoggedOutLiveData()
//    {
//        mRepo.refreshLoggedOut();
//        loggedOutLiveData = mRepo.getLoggedOutLiveData();
//    }

    public void register(String email, String password, String userName, CompleteListener listener) {
        mRepo.register(email, password, userName, listener);
    }

    public void sendVerificationEmail()
    {
        mRepo.sendVerificationEmail();
    }

    public void logOut() {
        mRepo.logOut();
    }


    public LiveData<Boolean> getLoggedOutLiveData() {
        return loggedOutLiveData;
    }

    public MutableLiveData<FirebaseUser> getUserLiveData() {
        return userLiveData;
    }


}
