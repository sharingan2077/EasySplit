package com.example.easysplit.repository;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.easysplit.model.FriendsImages;
import com.example.easysplit.model.User;
import com.example.easysplit.view.listeners.CompleteListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class AuthAppRepository {
    private static final String TAG = "AuthAppRepository";

    private static AuthAppRepository instance;
    //private Application application;

    //private FirebaseAuth firebaseAuth;

    //private SharedPreferences sharedPreferences;
    private MutableLiveData<FirebaseUser> userLiveData = new MutableLiveData<>();

    // loggedOutLiveData хранит true - текущего пользователя нет, false - пользователь есть
    private MutableLiveData<Boolean> loggedOutLiveData = new MutableLiveData<>();

    private Boolean loggedOutDataSet = true;

    private MutableLiveData<Boolean> isVerified = new MutableLiveData<>();

    FriendsImages friendsImages;

    public MutableLiveData<Boolean> getIsVerified() {
        return isVerified;
    }

    private static Context mContext;

    // Инициализация репозитория
//    public AuthAppRepository(Application application) {
//        this.application = application;
//        this.firebaseAuth = FirebaseAuth.getInstance();
//        this.sharedPreferences = application.getSharedPreferences("ACCOUNT_FILE_KEY", Context.MODE_PRIVATE);
//        this.userLiveData = new MutableLiveData<>();
//        this.loggedOutLiveData = new MutableLiveData<>();
//        this.isVerified = new MutableLiveData<>();
//        if (firebaseAuth.getCurrentUser() != null) {
//            userLiveData.postValue(firebaseAuth.getCurrentUser());
//            loggedOutLiveData.postValue(false);
//        }
//    }
    public static AuthAppRepository getInstance(Context context)
    {
        mContext = context;
        if (instance == null)
        {
            instance = new AuthAppRepository();
        }
        return instance;
    }

    public void addingUserToDataBase(String userName)
    {
        // Генерация токена для пользователя - число от 10000 до 99999
        int randomNum = 10000 + (int)(Math.random() * ((89999 - 10000) + 10000));
        String id = Integer.toString(randomNum);
        friendsImages = new FriendsImages();
        //List<String> imageUrls = friendsImages.getImageUrls();
        List<Integer> imageFriends = friendsImages.getImageFriends();
        int randomImage = ThreadLocalRandom.current().nextInt(0, imageFriends.size());

        // Создание объекта User (с именем при регистрации и созданным токеном)
        User user = new User(userName, id, String.valueOf(randomImage), "0");
        // Добавление в базу данных пользователя
        FirebaseDatabase.getInstance().getReference().child("User")
                .child(FirebaseAuth.getInstance().getUid())
                .setValue(user);
        FirebaseDatabase.getInstance().getReference().child("User")
                .child(FirebaseAuth.getInstance().getUid()).child("sumOwn").removeValue();
        FirebaseDatabase.getInstance().getReference().child("User")
                .child(FirebaseAuth.getInstance().getUid()).child("uid").removeValue();
        FirebaseDatabase.getInstance().getReference().child("User")
                .child(FirebaseAuth.getInstance().getUid()).child("userFriends").setValue("");
        FirebaseDatabase.getInstance().getReference().child("User")
                .child(FirebaseAuth.getInstance().getUid()).child("userGroups").setValue("");

        SharedPreferences sharedPreferences = mContext.getSharedPreferences("ACCOUNT_FILE_KEY", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("UserNameAndId", userName + "#" + id);
        editor.putString("UserEmail", FirebaseAuth.getInstance().getCurrentUser().getEmail());
        editor.putString("UserImage", Integer.toString(randomImage));
        editor.apply();
    }


    // Регистрация пользователя с помощью FireBase
    public void register(String email, String password, String userName, CompleteListener listener) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(mContext.getMainExecutor(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            // Добавление пользователя в базу данных при успешной регистрации
                            addingUserToDataBase(userName);
                            // Добавления в userLiveData зарегистрированного пользователя
                            userLiveData.postValue(FirebaseAuth.getInstance().getCurrentUser());
                            sendVerificationEmail();
                            listener.successful();
                        } else {
                            // Зарегистрировать не удалось
                            Toast.makeText(mContext.getApplicationContext(), "Registration Failure: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    public void sendVerificationEmail()
    {
        FirebaseAuth.getInstance().getCurrentUser().sendEmailVerification()
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

    public void resetPassword(String email, CompleteListener listener) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Email sent.");
                            Toast.makeText(mContext, "Check Your Email", Toast.LENGTH_SHORT).show();
                            listener.successful();
                        } else {
                            Toast.makeText(mContext, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    public void checkIfEmailVerified()
    {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user.isEmailVerified())
        {
            isVerified.setValue(true);
            FirebaseAuth.getInstance().signOut();
        }
        else
        {
            //isVerified.setValue(false);
        }
    }
    public void login(String email, String password, CompleteListener listener) {

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(mContext.getMainExecutor(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Если такой пользователь найден, то добавление в userLiveData этого пользователя
                            refreshSharedPreferences(new CompleteListener() {
                                @Override
                                public void successful() {
                                    userLiveData.postValue(FirebaseAuth.getInstance().getCurrentUser());
                                    listener.successful();
                                }

                                @Override
                                public void unSuccessful() {
                                }
                            });

                        } else {
                            // В другом случае вывод ошибки
                            Toast.makeText(mContext.getApplicationContext(), "Login Failure: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void refreshSharedPreferences(CompleteListener listener)
    {
        FirebaseDatabase.getInstance().getReference()
                .child("User").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        User user = task.getResult().getValue(User.class);
                        SharedPreferences sharedPreferences = mContext.getSharedPreferences("ACCOUNT_FILE_KEY", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("UserNameAndId", user.getUserName()+"#"+user.getId());
                        editor.putString("UserEmail", FirebaseAuth.getInstance().getCurrentUser().getEmail());
                        editor.putString("UserImage", user.getUserImage());
                        editor.apply();
                        listener.successful();

                    }
                });
    }

    public void logOut() {

        // Выход текущего пользователя из FireBase
        FirebaseAuth.getInstance().signOut();
        loggedOutLiveData.setValue(true);
        // Добавление в loggedOutLiveData значения true
        //refreshLoggedOut();
    }

    public void refreshLoggedOut()
    {
        if (FirebaseAuth.getInstance().getCurrentUser() == null)
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

    public MutableLiveData<Boolean> getLoggedOutLiveData(CompleteListener listener) {

        setLoggedOutData(listener);
        loggedOutLiveData.setValue(loggedOutDataSet);
        return loggedOutLiveData;
    }



    private void setLoggedOutData(CompleteListener listener)
    {
        if (FirebaseAuth.getInstance().getCurrentUser() == null)
        {
            loggedOutDataSet = true;
            loggedOutLiveData.setValue(loggedOutDataSet);
            listener.unSuccessful();
            //listener.successful();
        }
        else
        {
            loggedOutDataSet = false;
            loggedOutLiveData.setValue(loggedOutDataSet);
            listener.successful();
            //listener.successful();
        }
    }

}
