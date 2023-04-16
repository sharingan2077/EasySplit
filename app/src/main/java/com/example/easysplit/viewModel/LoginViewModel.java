package com.example.easysplit.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LoginViewModel  extends ViewModel {
    private final MutableLiveData<String> loginText = new MutableLiveData<String>();
    private final MutableLiveData<String> passwordText = new MutableLiveData<String>();

    public LiveData<String> getLoginText() {
        return loginText;
    }
    public LiveData<String> getPasswordText() {
        return passwordText;
    }
}
