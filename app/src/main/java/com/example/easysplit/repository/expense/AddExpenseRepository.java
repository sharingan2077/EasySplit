package com.example.easysplit.repository.expense;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.easysplit.model.Expense;
import com.example.easysplit.view.listeners.CompleteListener2;
import com.example.easysplit.view.listeners.CompleteListenerInt;
import com.example.easysplit.view.listeners.CompleteListenerListString;
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

public class AddExpenseRepository {

    private static final String TAG = "AddExpenseRepository";
    private static AddExpenseRepository instance;

    private String dataSet;


    private MutableLiveData<String> nameOfGroup = new MutableLiveData<>();
    private String dataNameOfGroup;
    MutableLiveData<String> data = new MutableLiveData<>();
    private MutableLiveData<String> nameOfUser = new MutableLiveData<>();

    private  String dataNameOfUser;


    public static AddExpenseRepository getInstance()
    {
        if (instance == null)
        {
            instance = new AddExpenseRepository();
        }
        return instance;
    }

    public void setUsersId(String groupId, CompleteListenerListString listenerListString)
    {
        ArrayList<String> usersId = new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference.child("Group").child(groupId).child("groupUsers");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    usersId.add(snapshot.getKey());
                }
                listenerListString.successful(usersId);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d(TAG, "onCancelled");

            }
        });
    }

    public void getFirstGroupId(CompleteListener2 listener)
    {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference.child("User").child(FirebaseAuth.getInstance().getUid()).child("userGroups");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    Log.d("Releasing", "setting FirstGroupId - " + snapshot.getKey());
                    dataSet = snapshot.getKey();
                    data.setValue(dataSet);
                    listener.successful(dataSet);
                    Log.d(TAG, dataSet);
                    break;
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d(TAG, "onCancelled");

            }
        });
    }
    public MutableLiveData<String> getGroupId(CompleteListener2 listener)
    {
        getFirstGroupId(listener);
        data.setValue(dataSet);
        return data;
    }
    public void findNameOfGroupById(String id, CompleteListener2 listener)
    {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.child("Group").child(id).child("groupName").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful())
                {
                    Log.d(TAG, "Successful");
                    dataNameOfGroup = task.getResult().getValue().toString();
                    nameOfGroup.setValue(dataNameOfGroup);
                    listener.successful(dataNameOfGroup);
                }
                else
                {
                    Log.d(TAG, "onSuccessful");
                }
            }
        });
    }
    public void findCountOfGroupMemberById(String id, CompleteListenerInt listener)
    {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.child("Group").child(id).child("countMember").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful())
                {
                    Log.d(TAG, "Successful");
                    listener.successful(Integer.valueOf(task.getResult().getValue().toString()));
                }
                else
                {
                    Log.d(TAG, "onSuccessful");
                }
            }
        });
    }
    public void getUserId(CompleteListener2 listener)
    {
        listener.successful(FirebaseAuth.getInstance().getUid());
    }

    public void findNameOfUserById(String id, CompleteListener2 listener)
    {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.child("User").child(id).child("userName").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful())
                {
                    Log.d(TAG, "Successful");
                    dataNameOfUser = task.getResult().getValue().toString();
                    nameOfUser.setValue(dataNameOfUser);
                    listener.successful(dataNameOfUser);
                }
                else
                {
                    Log.d(TAG, "onSuccessful");
                }
            }
        });
    }
    public void addExpense(String id)
    {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.child("Expense").child(id).setValue(id);
    }
    public void addExpense(final Expense expense, String id)
    {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.child("Expense").child(id).setValue(expense);
        reference.child("Group").child(expense.getExpenseGroup()).child("groupExpenses").updateChildren(Collections.singletonMap(id, id));
    }


    public void deleteExpense(String id)
    {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.child("Expense").child(id).removeValue();
    }

}
