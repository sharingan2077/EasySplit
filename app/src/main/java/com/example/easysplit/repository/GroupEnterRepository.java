package com.example.easysplit.repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;

public class GroupEnterRepository {

    private static GroupEnterRepository instance;

    private static final String TAG = "GroupEnterRepository";
    private MutableLiveData<Integer> countOfGroupMembers = new MutableLiveData<>();

    public static GroupEnterRepository getInstance()
    {
        if (instance == null)
        {
            instance = new GroupEnterRepository();
        }
        return instance;
    }

    public MutableLiveData<Integer> getCountOfGroupMembers() {
        return countOfGroupMembers;
    }

    public void getCountOfGroupMembers(String groupId, GroupRepository.GetCountOfGroupMembersListener listener)
    {
        FirebaseDatabase.getInstance().getReference()
                .child("Group")
                .child(groupId)
                .child("countMember").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        Long i = (Long)task.getResult().getValue();
                        countOfGroupMembers.postValue(i.intValue());
                        listener.onSuccessful(countOfGroupMembers);
                    }
                });
    }
}
