package com.example.easysplit.repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.easysplit.model.DebtInGroup;
import com.example.easysplit.model.Expense;
import com.example.easysplit.view.listeners.CompleteListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DebtInGroupRepository {

    private static DebtInGroupRepository instance;
    private ArrayList<DebtInGroup> dataSet = new ArrayList<>();

    MutableLiveData<List<DebtInGroup>> dataDebt = new MutableLiveData<>();

    private List<Expense> expensesInGroup;

    private static final String TAG = "DebtInGroupRepository";

    public static DebtInGroupRepository getInstance()
    {
        if (instance == null)
        {
            instance = new DebtInGroupRepository();
        }
        return instance;
    }
    public MutableLiveData<List<DebtInGroup>> getDebtsInGroup(String groupId, CompleteListener listener)
    {
        setDebtsInGroup(groupId, listener);
        dataDebt.setValue(dataSet);
        return dataDebt;
    }

    private void setExpensesInArray(String groupId, CompleteListener listener)
    {
        expensesInGroup = new ArrayList<>();
        Log.d(TAG, "setExpensesInArray");
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference.child("Expense");

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if (snapshot.child("expenseGroup").getValue().toString().equals(groupId)) {
                        expensesInGroup.add(snapshot.getValue(Expense.class));
                    }
                }
                listener.successful();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setDebtsInGroup(String groupId, CompleteListener listener)
    {
        setExpensesInArray(groupId, new CompleteListener() {
            @Override
            public void successful() {
                dataSet.clear();
                for (Expense expense : expensesInGroup)
                {

                }
            }

            @Override
            public void unSuccessful() {

            }
        });


//        dataSet.clear();
//        dataSet.add(new DebtInGroup("Misha", 2000));
//        dataSet.add(new DebtInGroup("You", 3000));
//        dataSet.add(new DebtInGroup("Misha", 4000));
//        dataSet.add(new DebtInGroup("Misha", 5000));
    }

}
