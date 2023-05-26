package com.example.easysplit.repository.authentication;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.easysplit.model.FriendsImages;
import com.example.easysplit.view.listeners.CompleteListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class AccountRepository {
    private static AccountRepository instance;
    private static final String TAG = "AccountRepository";

    private SharedPreferences sharedPreferences;

    private MutableLiveData<String> userEmail = new MutableLiveData<>();
    private MutableLiveData<String> userNameAndId = new MutableLiveData<>();

    private MutableLiveData<String> userImage = new MutableLiveData<>();

    private int userImageNumber;

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

    public MutableLiveData<String> getUserImage() {
        sharedPreferences = mContext.getSharedPreferences("ACCOUNT_FILE_KEY", Context.MODE_PRIVATE);
        userImage.setValue(sharedPreferences.getString("UserImage", "-1"));
        Log.d(TAG, sharedPreferences.getString("UserImage", "-1"));
        return userImage;
    }

    public MutableLiveData<String> getUserEmail()
    {
        sharedPreferences = mContext.getSharedPreferences("ACCOUNT_FILE_KEY", Context.MODE_PRIVATE);
        userEmail.setValue(sharedPreferences.getString("UserEmail", "user@gmail.com"));
        return userEmail;
    }
    public MutableLiveData<String> getUserNameAndId()
    {
        sharedPreferences = mContext.getSharedPreferences("ACCOUNT_FILE_KEY", Context.MODE_PRIVATE);
        userNameAndId.setValue(sharedPreferences.getString("UserNameAndId", "User#45012"));
        return userNameAndId;
    }

    public void changeUserImage(CompleteListener listener)
    {
        FriendsImages friendsImages = new FriendsImages();
        List<Integer> imageFriends = friendsImages.getImageFriends();

        FirebaseDatabase.getInstance().getReference().child("User").child(FirebaseAuth.getInstance().getUid())
                        .child("userImage").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        userImageNumber = Integer.valueOf(task.getResult().getValue().toString());
                        int randomImage = ThreadLocalRandom.current().nextInt(0, imageFriends.size());
                        Log.d(TAG, "userImageNumber - " + Integer.toString(userImageNumber) + " randomImage - " + Integer.toString(randomImage));
                        while (randomImage == userImageNumber)
                        {
                            randomImage = ThreadLocalRandom.current().nextInt(0, imageFriends.size());
                        }
                        FirebaseDatabase.getInstance().getReference().child("User").child(FirebaseAuth.getInstance().getUid())
                                .child("userImage").setValue(Integer.toString(randomImage)).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                    }
                                });
                        SharedPreferences sharedPreferences = mContext.getSharedPreferences("ACCOUNT_FILE_KEY", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        Log.d(TAG, "putting new userImage - " + Integer.toString(randomImage));
                        editor.putString("UserImage", Integer.toString(randomImage)).apply();
                        listener.successful();
                    }
                });
    }
    public void changeNickNameUser(String name, String id, CompleteListener listener)
    {
        FirebaseDatabase.getInstance().getReference().child("User").child(FirebaseAuth.getInstance().getUid())
                .child("userName").setValue(name).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        SharedPreferences sharedPreferences = mContext.getSharedPreferences("ACCOUNT_FILE_KEY", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("UserNameAndId", name + id).apply();
                        listener.successful();
                    }
                });
    }
}
