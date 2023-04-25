package com.example.easysplit.repository;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AuthAppRepository {
    private static final String TAG = "AuthAppRepository";
    private Application application;

    private FirebaseAuth firebaseAuth;
    private MutableLiveData<FirebaseUser> userLiveData;
    private MutableLiveData<Boolean> loggedOutLiveData;
    public AuthAppRepository(Application application) {
        this.application = application;
        this.firebaseAuth = FirebaseAuth.getInstance();
        this.userLiveData = new MutableLiveData<>();
        this.loggedOutLiveData = new MutableLiveData<>();
        if (firebaseAuth.getCurrentUser() != null) {
            Log.d("LoginFragment", "userNotNull");
            userLiveData.postValue(firebaseAuth.getCurrentUser());
            loggedOutLiveData.postValue(false);
        }
    }

    public void register(String email, String password) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(application.getMainExecutor(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            userLiveData.postValue(firebaseAuth.getCurrentUser());
                            sendVerificationEmail();
                        } else {
                            //Toast.makeText(application.getApplicationContext(), "Registration Failure: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }



    public void login(String email, String password) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(application.getMainExecutor(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            userLiveData.postValue(firebaseAuth.getCurrentUser());
                        } else {
                            Log.d("Auth", "Error");

                            //Toast.makeText(application.getApplicationContext(), "Login Failure: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }



    public void logOut() {
        firebaseAuth.signOut();
        refreshLoggedOut();
    }

    public void refreshLoggedOut()
    {
        if (firebaseAuth.getCurrentUser() == null)
        {
            Log.d("Naruto", "Current user is null");
            loggedOutLiveData.setValue(true);
        }
        else
        {
            Log.d("Naruto", "Current user is not null");
            loggedOutLiveData.setValue(false);
        }
    }

    public void sendVerificationEmail()
    {
        firebaseAuth.getCurrentUser().sendEmailVerification()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            // email sent
                            // after email is sent just logout the user and finish this activity
                        }
                        else
                        {
                            // email not sent, so display message and restart the activity or do whatever you wish to do
                        }
                    }
                });
    }


    public MutableLiveData<FirebaseUser> getUserLiveData() {
        return userLiveData;
    }

    public MutableLiveData<Boolean> getLoggedOutLiveData() {
        return loggedOutLiveData;
    }

}
