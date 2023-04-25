package com.example.easysplit.repository;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.easysplit.model.Group;
import com.example.easysplit.view.listeners.DataLoadListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GroupRepository {


    private static GroupRepository instance;
    private static final String TAG = "GroupRepository";
    private ArrayList<Group> dataSet = new ArrayList<>();
    MutableLiveData<List<Group>> data = new MutableLiveData<>();

    private MutableLiveData<Boolean> dataLoaded = new MutableLiveData<>();


    private String GROUP_KEY = "Group";


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
        if (dataSet.size() == 0)
        {
            setGroups();
        }
        data.setValue(dataSet);
        return data;
    }
    public MutableLiveData<Boolean> getDataLoaded()
    {
        return dataLoaded;
    }

    private void setGroups()
    {
        dataLoaded.setValue(false);
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference.child("Group");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    dataSet.add(snapshot.getValue(Group.class));
                }
                data.postValue(dataSet);
                dataLoaded.setValue(true);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
        Log.d("Prog", "Data loaded - " + dataLoaded.getValue().toString());
    }

    public void addGroup(final Group group)
    {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.child("Group").setValue(group);
    }


}
