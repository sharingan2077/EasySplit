package com.example.easysplit.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.easysplit.model.User;
import com.example.easysplit.view.listeners.CheckUsersIdListener;
import com.example.easysplit.view.listeners.CompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class WhoPaidRepository {

    private static WhoPaidRepository instance;
    private ArrayList<User> dataSet = new ArrayList<>();
    private static final String TAG = "WhoPaidRepository";
    MutableLiveData<List<User>> data = new MutableLiveData<>();
    public static WhoPaidRepository getInstance()
    {
        if (instance == null)
        {
            instance = new WhoPaidRepository();
        }
        return instance;
    }

    public MutableLiveData<List<User>> getUsers(String id, CompleteListener listener)
    {
        setUsers(id, listener);
        data.setValue(dataSet);
        return data;
    }

    private void setUsers(String id, CompleteListener listener)
    {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference.child("User");
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dataSet.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    if (snapshot.child("userGroups").hasChild(id))
                    {
                        User user = new User(snapshot.child("userName").getValue().toString(), snapshot.child("id").getValue().toString(), snapshot.child("userImage").getValue().toString(), snapshot.getKey());
                        dataSet.add(user);
                    }
                }
                listener.successful();
                data.postValue(dataSet);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        query.addValueEventListener(postListener);
    }

    public void checkUsersId(ArrayList<String> usersId, String userId, CheckUsersIdListener listener)
    {
        if (usersId.size() == 1 && usersId.contains(userId))
        {
            listener.onlyOwnUserId();
        }
        else if (usersId.size() == 0)
        {
            listener.noUsersId();
        }
        else
        {
            listener.successful();
        }
    }

}
