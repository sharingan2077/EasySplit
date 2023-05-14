package com.example.easysplit.repository;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.easysplit.model.Group;
import com.example.easysplit.view.listeners.DataLoadFirstListener;
import com.example.easysplit.view.listeners.DataLoadListener;
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
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class GroupRepository {


    private static GroupRepository instance;
    private static final String TAG = "GroupRepository";
    private ArrayList<Group> dataSet = new ArrayList<>();
    MutableLiveData<List<Group>> data = new MutableLiveData<>();

    private MutableLiveData<Boolean> dataLoaded = new MutableLiveData<>();

    private static DataLoadFirstListener dataLoadFirstListener;

    private List<String> userGroups;

    private static Boolean complete = false;

    public static GroupRepository getInstance(DataLoadFirstListener listener)
    {
        if (instance == null)
        {
            instance = new GroupRepository();
        }
        dataLoadFirstListener = listener;
        return instance;
    }
    public MutableLiveData<List<Group>> getGroups()
    {
        if (dataSet.size() == 0)
        {
            Log.d(TAG, "Size of dataset - " + dataSet.size());
            setGroups();
            data.setValue(dataSet);
        }
        Log.d(TAG, "Return data");
        return data;
    }
    public MutableLiveData<Boolean> getDataLoaded()
    {
        return dataLoaded;
    }

    //Заполнение массива userGroups группами текущего пользователя
    private void setUserGroups(final DataLoadListener listener)
    {
        userGroups = new ArrayList<>();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference.child("User").child(FirebaseAuth.getInstance().getUid()).child("userGroups");
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Log.d(TAG, "onDataChange in setUserGroups");
                    for (DataSnapshot snapshot : dataSnapshot.getChildren())
                    {
                        userGroups.add(snapshot.getValue().toString());
                    }
                    listener.dataLoaded();
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
    }

    private void setGroups() {
        dataLoaded.setValue(false);
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference.child("Group");
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                setUserGroups(new DataLoadListener() {
                    @Override
                    public void dataLoaded() {
                        Log.d(TAG, "onDataChange in setGroups");
                        dataSet.clear();
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            if (userGroups.contains(snapshot.getKey())) {
                                Long i = (Long) snapshot.child("countMember").getValue();
                                Group group = new Group(snapshot.child("groupName").getValue().toString(), i.intValue(), snapshot.getKey());
                                dataSet.add(group);
                            }
                        }Log.d(TAG, "size of dataset - " + dataSet.size());
                        data.postValue(dataSet);
                        dataLoaded.setValue(true);
                        dataLoadFirstListener.dataLoaded(true);
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        query.addValueEventListener(postListener);
    }

    public void addGroup(final Group group)
    {
        String id = UUID.randomUUID().toString();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.child("Group").child(id).setValue(group);
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

    public interface GetCountOfGroupMembersListener
    {
        void onSuccessful(MutableLiveData<Integer> groupId);
    }


}
