package com.example.easysplit.repository;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.easysplit.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.HashMap;

public class AuthAppRepository {
    private static final String TAG = "AuthAppRepository";
    private Application application;

    private FirebaseAuth firebaseAuth;

    private SharedPreferences sharedPreferences;
    private MutableLiveData<FirebaseUser> userLiveData;

    // loggedOutLiveData хранит true - текущего пользователя нет, false - пользователь есть
    private MutableLiveData<Boolean> loggedOutLiveData;

    private MutableLiveData<Boolean> isVerified;

    public MutableLiveData<Boolean> getIsVerified() {
        return isVerified;
    }

    // Инициализация репозитория
    public AuthAppRepository(Application application) {
        this.application = application;
        this.firebaseAuth = FirebaseAuth.getInstance();
        this.sharedPreferences = application.getSharedPreferences("ACCOUNT_FILE_KEY", Context.MODE_PRIVATE);
        this.userLiveData = new MutableLiveData<>();
        this.loggedOutLiveData = new MutableLiveData<>();
        this.isVerified = new MutableLiveData<>();
        if (firebaseAuth.getCurrentUser() != null) {
            userLiveData.postValue(firebaseAuth.getCurrentUser());
            loggedOutLiveData.postValue(false);
        }
    }

    public void addingUserToDataBase(String userName)
    {
        // Генерация токена для пользователя - число от 10000 до 99999
        int randomNum = 10000 + (int)(Math.random() * ((89999 - 10000) + 10000));
        String id = Integer.toString(randomNum);
        // Создание объекта User (с именем при регистрации и созданным токеном)
        User user = new User(userName, id);
        // Добавление в базу данных пользователя
        FirebaseDatabase.getInstance().getReference().child("User")
                .child(FirebaseAuth.getInstance().getUid())
                .setValue(user);
        FirebaseDatabase.getInstance().getReference().child("User")
                .child(FirebaseAuth.getInstance().getUid()).child("userFriends").setValue("");
        FirebaseDatabase.getInstance().getReference().child("User")
                .child(FirebaseAuth.getInstance().getUid()).child("userGroups").setValue("");

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("ACCOUNT_FILE_KEY", userName + "#" + id);
        editor.apply();
    }


    // Регистрация пользователя с помощью FireBase
    public void register(String email, String password, String userName) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(application.getMainExecutor(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            // Добавление пользователя в базу данных при успешной регистрации
                            addingUserToDataBase(userName);
                            // Добавления в userLiveData зарегистрированного пользователя
                            userLiveData.postValue(firebaseAuth.getCurrentUser());
                            sendVerificationEmail();
                        } else {
                            // Зарегистрировать не удалось
                            Toast.makeText(application.getApplicationContext(), "Registration Failure: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
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
    public void checkIfEmailVerified()
    {
        FirebaseUser user = firebaseAuth.getCurrentUser();

        if (user.isEmailVerified())
        {
            isVerified.setValue(true);
            firebaseAuth.signOut();
        }
        else
        {
            //isVerified.setValue(false);
        }
    }
    public void login(String email, String password) {

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(application.getMainExecutor(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Если такой пользователь найден, то добавление в userLiveData этого пользователя
                            userLiveData.postValue(firebaseAuth.getCurrentUser());
                        } else {
                            // В другом случае вывод ошибки
                            Toast.makeText(application.getApplicationContext(), "Login Failure: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void logOut() {

        // Выход текущего пользователя из FireBase
        firebaseAuth.signOut();

        // Добавление в loggedOutLiveData значения true
        refreshLoggedOut();
    }

    public void refreshLoggedOut()
    {
        if (firebaseAuth.getCurrentUser() == null)
        {
            loggedOutLiveData.setValue(true);
        }
        else
        {
            loggedOutLiveData.setValue(false);
        }
    }

    public MutableLiveData<FirebaseUser> getUserLiveData() {
        return userLiveData;
    }

    public MutableLiveData<Boolean> getLoggedOutLiveData() {
        return loggedOutLiveData;
    }

}
