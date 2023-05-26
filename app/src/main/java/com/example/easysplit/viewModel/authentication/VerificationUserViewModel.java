package com.example.easysplit.viewModel.authentication;

import android.content.Context;

import androidx.lifecycle.ViewModel;

import com.example.easysplit.repository.authentication.AuthAppRepository;
import com.example.easysplit.view.listeners.CompleteListener;

public class VerificationUserViewModel extends ViewModel {

    private AuthAppRepository mRepo;
    public void init(Context mContext)
    {

        mRepo = AuthAppRepository.getInstance(mContext);
    }

    public void sendVerificationEmail(String email, CompleteListener listener)
    {
        mRepo.resetPassword(email, listener);
    }

}
