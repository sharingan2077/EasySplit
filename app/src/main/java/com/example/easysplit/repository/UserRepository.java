package com.example.easysplit.repository;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.easysplit.model.Group;
import com.example.easysplit.model.User;
import com.example.easysplit.view.listeners.CompleteListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class UserRepository {

    private static UserRepository instance;
    private ArrayList<User> dataSet = new ArrayList<>();

    private static final String TAG = "UserRepository";
    MutableLiveData<List<User>> data = new MutableLiveData<>();

    private List<String> userFriends;

    public static UserRepository getInstance()
    {
        if (instance == null)
        {
            instance = new UserRepository();
        }
        return instance;
    }
    public MutableLiveData<List<User>> getUsers(CompleteListener listener)
    {
        setUsers(listener);
        data.setValue(dataSet);
        Log.d("NewUser", "size == 0");
        return data;
    }


    //Заполнение массива userFriends друзьями текущего пользователя
    private void setUserFriends(CompleteListener listener)
    {
        userFriends = new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference.child("User").child(FirebaseAuth.getInstance().getUid()).child("userFriends");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    Log.d("UserNew", "settingUserFriendUID - " + snapshot.getValue().toString());
                    userFriends.add(snapshot.getValue().toString());
                }
                listener.successful();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    //Загрузка друзей для текущего пользователя
    private void setUsers(CompleteListener listener)
    {

        setUserFriends(new CompleteListener() {
            @Override
            public void successful() {
                Log.d("UserNew", "Starting to filling");

                DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
                Query query = reference.child("User");
                ValueEventListener postListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        dataSet.clear();
                        for (DataSnapshot snapshot : dataSnapshot.getChildren())
                        {
                            if (userFriends.contains(snapshot.getKey()))
                            {
                                User user = new User(snapshot.child("userName").getValue().toString(), snapshot.child("id").getValue().toString(), snapshot.getKey());
                                Log.d("User", "snapshot.getKey() " + snapshot.getKey());
                                dataSet.add(user);
                            }
                        }
                        listener.successful();
                        data.postValue(dataSet);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                    }
                };
                query.addValueEventListener(postListener);
            }

            @Override
            public void unSuccessful() {

            }
        });
    }

    public void addFriend(String userName, String id, CompleteListener listener)
    {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference.child("User");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    if (snapshot.child("id").getValue().equals(id) && snapshot.child("userName").getValue().equals(userName))
                    {
                        addFriendToUser(snapshot.getKey(), listener);
                        break;
                    }
                    else
                    {
                        Log.d("User", "Пользователя с id - " + id +
                                " именем - " + userName + " не найдено");
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void addFriendToUser(String UID, CompleteListener listener)
    {
        Log.d("User", "Adding new Friends to " + FirebaseAuth.getInstance().getUid());
        FirebaseDatabase.getInstance().getReference()
                .child("User")
                .child(FirebaseAuth.getInstance().getUid())
                .child("userFriends")
                .updateChildren(Collections.singletonMap(UID,UID)).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (!task.isSuccessful())
                        {
                            Log.d("User", "Не удалось добавить пользователя");
                        }
                    }
                });
        FirebaseDatabase.getInstance().getReference()
                .child("User")
                .child(UID)
                .child("userFriends")
                .updateChildren(Collections.singletonMap(FirebaseAuth.getInstance().getUid(), FirebaseAuth.getInstance().getUid()))
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (!task.isSuccessful())
                        {
                            Log.d("User", "Не удалось добавить текущего пользователя к новому");
                        }
                    }
                });
        listener.successful();
    }

    public interface AddFriendToGroupListener
    {
        void successful();
        void unSuccessful();
    }

    public void addFriendToGroup(String groupId, String userId, AddFriendToGroupListener listener)
    {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference()
                .child("User")
                .child(userId)
                .child("userGroups");
//        int countUserGroupsOld = databaseReference.
        databaseReference.updateChildren(Collections.singletonMap(groupId, groupId)).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful())
                        {
                            Log.d(TAG, "Successful");
                            listener.successful();
                            increaseGroupMemberCount(groupId);
                        }
                        else
                        {
                            Log.d(TAG, "unSuccessful");
                            listener.unSuccessful();
                        }
                    }
                });
    }
    public void increaseGroupMemberCount(String groupId)
    {
        int countGroupMembersOld, countGroupMembersNew;
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
                .child("Group")
                .child(groupId)
                .child("countMember");
        reference.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful())
                {
                    Long count = (Long)task.getResult().getValue();
                    reference.setValue(count.intValue() + 1);
                }
            }
        });
    }

    public MutableLiveData<List<User>> getUsers()
    {
        setUsers();
        data.setValue(dataSet);
        Log.d("NewUser", "size == 0");
        return data;
    }

    private void setUsers()
    {

        setUserFriends(new CompleteListener() {
            @Override
            public void successful() {
                Log.d("UserNew", "Starting to filling");

                DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
                Query query = reference.child("User");
                ValueEventListener postListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        dataSet.clear();
                        for (DataSnapshot snapshot : dataSnapshot.getChildren())
                        {
                            if (userFriends.contains(snapshot.getKey()))
                            {
                                User user = new User(snapshot.child("userName").getValue().toString(), snapshot.child("id").getValue().toString(), snapshot.getKey());
                                Log.d("User", "snapshot.getKey() " + snapshot.getKey());
                                dataSet.add(user);
                            }
                        }
                        data.postValue(dataSet);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                    }
                };
                query.addValueEventListener(postListener);
            }

            @Override
            public void unSuccessful() {

            }
        });
    }
}
