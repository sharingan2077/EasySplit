package com.example.easysplit.viewModel.authentication;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.easysplit.repository.AuthAppRepository;

public class VerificationUserViewModel extends ViewModel {

    private AuthAppRepository authAppRepository;
    private Application application;

    private MutableLiveData<Boolean> isVerified;
    public VerificationUserViewModel(@NonNull Application application) {
        authAppRepository = new AuthAppRepository(application);
    }

    public LiveData<Boolean> getIsVerified() {
        return isVerified;
    }

}
