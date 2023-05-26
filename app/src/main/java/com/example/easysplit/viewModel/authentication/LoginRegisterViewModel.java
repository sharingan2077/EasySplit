package com.example.easysplit.viewModel.authentication;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.easysplit.repository.authentication.AuthAppRepository;
import com.example.easysplit.view.listeners.CompleteListener;

public class LoginRegisterViewModel extends ViewModel {
    private AuthAppRepository mRepo;

    // хранит текущего пользователя

    // loggedOutLiveData хранит true - текущего пользователя нет, false - пользователь есть
    private MutableLiveData<Boolean> loggedOutLiveData;
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


    public void register(String email, String password, String userName, CompleteListener listener) {
        mRepo.register(email, password, userName, listener);
    }

    public void logOut() {
        mRepo.logOut();
    }


    public LiveData<Boolean> getLoggedOutLiveData() {
        return loggedOutLiveData;
    }

}
