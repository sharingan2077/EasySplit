package com.example.easysplit.repository;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.easysplit.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.List;

public class AccountRepository {
    private static AccountRepository instance;
    private static final String TAG = "AccountRepository";

    private SharedPreferences sharedPreferences;

    private MutableLiveData<String> userEmail = new MutableLiveData<>();
    private MutableLiveData<String> userNameAndId = new MutableLiveData<>();

    private String userName;
    private String userId;

    private Context mContext;

    public AccountRepository(Context mContext) {
        this.mContext = mContext;
    }

    public static AccountRepository getInstance(Context mContext)
    {
        if (instance == null)
        {
            instance = new AccountRepository(mContext);
        }
        return instance;
    }

    public MutableLiveData<String> getUserEmail()
    {
        userEmail.setValue(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        return userEmail;
    }
    public MutableLiveData<String> getUserNameAndId()
    {
        sharedPreferences = mContext.getSharedPreferences("ACCOUNT_FILE_KEY", Context.MODE_PRIVATE);
        userNameAndId.setValue(sharedPreferences.getString("ACCOUNT_FILE_KEY", "maks#00000"));
        Log.d("UserNameAndId", userNameAndId.getValue());
//        FirebaseDatabase.getInstance().getReference().child("User")
//                .child(FirebaseAuth.getInstance().getUid())
//                .child("userName").get()
//                .addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<DataSnapshot> task) {
//                        if (task.isSuccessful())
//                        {
//                            Log.d("UserName", userName = task.getResult().getValue().toString());
//                            userName = task.getResult().getValue().toString();
//                        }
//                        else
//                        {
//                            Log.d(TAG, "Task to find userName unSuccess");
//                        }
//
//                    }
//                });
//        FirebaseDatabase.getInstance().getReference().child("User")
//                .child(FirebaseAuth.getInstance().getUid())
//                .child("id").get()
//                .addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<DataSnapshot> task) {
//                        if (task.isSuccessful())
//                        {
//                            userId = task.getResult().getValue().toString();
//                        }
//                        else
//                        {
//                            Log.d(TAG, "Task to find userId unSuccess");
//                        }
//
//                    }
//                });
//
//        userNameAndId.setValue(userName + "#" + userId);
        return userNameAndId;
    }
}
