package com.example.easysplit.repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.easysplit.model.Group;
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
import java.util.List;
import java.util.UUID;

public class GroupRepository {


    private static GroupRepository instance;
    private static final String TAG = "GroupRepository";
    private ArrayList<Group> dataSet = new ArrayList<>();
    MutableLiveData<List<Group>> data = new MutableLiveData<>();
    private List<String> userGroups;


    public static GroupRepository getInstance()
    {
        if (instance == null)
        {
            instance = new GroupRepository();
        }
        return instance;
    }
    public MutableLiveData<List<Group>> getGroups()
    {
        setGroups();
        data.setValue(dataSet);
        return data;
    }

    //Заполнение массива userGroups группами текущего пользователя
    private void setUserGroups(CompleteListener listener)
    {
        userGroups = new ArrayList<>();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference.child("User").child(FirebaseAuth.getInstance().getUid()).child("userGroups");
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren())
                    {
                        userGroups.add(snapshot.getValue().toString());
                    }
                    listener.successful();
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
    }

    private void setGroups() {
        setUserGroups(new CompleteListener() {
            @Override
            public void successful() {
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
                Query query = reference.child("Group");
                ValueEventListener postListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        dataSet.clear();
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            if (userGroups.contains(snapshot.getKey())) {
                                Long i = (Long) snapshot.child("countMember").getValue();
                                Group group = new Group(snapshot.child("groupName").getValue().toString(), i.intValue(), snapshot.getKey());
                                dataSet.add(group);
                            }
                        }
                        data.postValue(dataSet);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                };
                query.addValueEventListener(postListener);
            }
            @Override
            public void unSuccessful() {
            }
        });
    }

    public void addGroup(final Group group)
    {
        String id = UUID.randomUUID().toString();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.child("Group").child(id).setValue(group);
        reference.child("Group").child(id).child("groupUsers").updateChildren(Collections.singletonMap(FirebaseAuth.getInstance().getUid(), FirebaseAuth.getInstance().getUid())).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Log.d(TAG, "add friend to groupUsers");
            }
        });
        reference.child("User").child(FirebaseAuth.getInstance().getUid()).child("userGroups").updateChildren(Collections.singletonMap(id, id)).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Log.d("GroupToUser", "Complete");
            }
        });
    }

    public void deleteGroup(String id)
    {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.child("Group").child(id).removeValue();

        reference.child("User").child(FirebaseAuth.getInstance().getUid()).child("userGroups").child(id).removeValue();
    }

    public MutableLiveData<List<Group>> getGroups(CompleteListener listener)
    {
        setGroups(listener);
        data.setValue(dataSet);
        return data;
    }
    private void setGroups(CompleteListener listener) {
        setUserGroups(new CompleteListener() {
            @Override
            public void successful() {
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
                Query query = reference.child("Group");
                ValueEventListener postListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        dataSet.clear();
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            if (userGroups.contains(snapshot.getKey())) {
                                Long i = (Long) snapshot.child("countMember").getValue();
                                Group group = new Group(snapshot.child("groupName").getValue().toString(), i.intValue(), snapshot.getKey());
                                dataSet.add(group);
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
            @Override
            public void unSuccessful() {
            }
        });
    }

    private void clearExpensesInDataBase()
    {

    }



}
