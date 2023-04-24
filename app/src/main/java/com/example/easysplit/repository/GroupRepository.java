package com.example.easysplit.repository;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.easysplit.model.Group;
import com.example.easysplit.view.listeners.DataLoadListener;
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
    private ArrayList<Group> dataSet = new ArrayList<>();


    private String GROUP_KEY = "Group";

    static Context mContext;
    static DataLoadListener dataLoadListener;

    public static GroupRepository getInstance(Context context)
    {
        mContext = context;
        if (instance == null)
        {
            instance = new GroupRepository();
        }
        dataLoadListener = (DataLoadListener) mContext;
        return instance;
    }
    public MutableLiveData<List<Group>> getGroups()
    {
        if (dataSet.size() == -1)
        {
            setGroups();
        }
        MutableLiveData<List<Group>> data = new MutableLiveData<>();
        data.setValue(dataSet);
        return data;
    }

    private void setGroups()
    {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference.child("Group");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    dataSet.add(snapshot.getValue(Group.class));
                }
                dataLoadListener.onGroupLoaded();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


//        dataSet.add(new Group("Example", 2, UUID.randomUUID().toString()));
//        dataSet.add(new Group("Example2", 3, UUID.randomUUID().toString()));
    }


}
