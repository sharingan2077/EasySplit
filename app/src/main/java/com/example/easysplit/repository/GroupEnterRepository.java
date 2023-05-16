package com.example.easysplit.repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.easysplit.model.Group;
import com.example.easysplit.view.listeners.CompleteListener;
import com.example.easysplit.view.listeners.CompleteListenerInt;
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
import java.util.List;
import java.util.Map;

public class GroupEnterRepository {

    private static GroupEnterRepository instance;

    private static final String TAG = "GroupEnterRepository";
    private MutableLiveData<Integer> countOfGroupMembers = new MutableLiveData<>();

    private List<String> groupUsers;

    public static GroupEnterRepository getInstance()
    {
        if (instance == null)
        {
            instance = new GroupEnterRepository();
        }
        return instance;
    }


    public void getCountOfGroupMembers(String groupId, CompleteListenerInt listenerInt)
    {
        FirebaseDatabase.getInstance().getReference()
                .child("Group")
                .child(groupId)
                .child("countMember").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        Long i = (Long)task.getResult().getValue();
                        countOfGroupMembers.setValue(i.intValue());
                        listenerInt.successful(i.intValue());
                        Log.d(TAG, Integer.toString(countOfGroupMembers.getValue()));
                    }
                });
    }
//    public MutableLiveData<Integer> getCountOfGroupMembers()
//    {
//
//
//        return countOfGroupMembers;
//    }
    public void leaveGroup(String id)
    {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.child("Group").child(id).child("countMember")
                .get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        Long count_member = (Long)task.getResult().getValue();
                        if (count_member == 1)
                        {
                            reference.child("Group").child(id).removeValue();
                        }
                        else
                        {
                            reference.child("Group").child(id).child("countMember").setValue(count_member - 1);
                        }
                    }
                });
        reference.child("User").child(FirebaseAuth.getInstance().getUid()).child("userGroups").child(id).removeValue();
    }

    public void deleteGroup(String id)
    {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.child("Group").child(id).removeValue();
        Query query = reference.child("User");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String UID = snapshot.getKey();
                    if (snapshot.child("userGroups").hasChild(id)) {
                        reference.child("User").child(UID).child("userGroups").child(id).removeValue();
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setGroupUsers(String id, CompleteListener listener)
    {
        groupUsers = new ArrayList<>();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference.child("Group").child(id).child("groupUsers");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    groupUsers.add(snapshot.getValue().toString());
                }
                listener.successful();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}
