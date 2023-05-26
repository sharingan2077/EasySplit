package com.example.easysplit.repository.groups;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.easysplit.model.DebtInGroup;
import com.example.easysplit.model.Expense;
import com.example.easysplit.view.listeners.CompleteListener;
import com.example.easysplit.view.listeners.CompleteListener2;
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
import java.util.HashMap;
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
    public MutableLiveData<List<DebtInGroup>> getDebtsInGroup(String groupId, CompleteListener2 listener)
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

    private void setDebtsInGroup(String groupId, CompleteListener2 listener)
    {
        setExpensesInArray(groupId, new CompleteListener() {
            @Override
            public void successful() {
                dataSet.clear();
                HashMap<String, Long> map = new HashMap<>();
                for (Expense expense : expensesInGroup)
                {
                    String expenseOwner = expense.getExpenseOwner();
                    String UID = FirebaseAuth.getInstance().getUid();
                    HashMap<String, Long> usersWaste = expense.getUsersWaste();
                    if (expenseOwner.equals(UID))
                    {
                        for (String name: usersWaste.keySet()) {
                            if (!name.equals(UID))
                            {
                                if (map.get(name) == null)
                                {
                                    map.put(name, usersWaste.get(name));
                                }
                                else
                                {
                                    map.put(name, map.get(name) + usersWaste.get(name));
                                }
                            }
                        }
                    }
                    else
                    {
                        if (usersWaste.get(UID) != null)
                        {
                            if (map.get(expenseOwner) == null)
                            {
                                map.put(expenseOwner, usersWaste.get(UID) * (-1));
                            }
                            else
                            {
                                map.put(expenseOwner, map.get(expenseOwner) - usersWaste.get(UID));
                            }
                        }
                    }
                }
                for (String name : map.keySet())
                {
                    if (map.get(name) == 0)
                    {
                        map.remove(name);
                    }
                }
                for (String name : map.keySet())
                {
                    findNameOfUserById(name, new CompleteListener2() {
                        @Override
                        public void successful(String data) {
                            if (map.get(name) > 0)
                            {
                                dataSet.add(new DebtInGroup(data, map.get(name), true));
                            }
                            else if (map.get(name) < 0)
                            {
                                dataSet.add(new DebtInGroup(data, map.get(name) * (-1), false));
                            }
                            listener.successful("debt");
                            dataDebt.setValue(dataSet);

                        }
                    });
                }
            }
            @Override
            public void unSuccessful() {

            }
        });
    }


    private void findNameOfUserById(String id, CompleteListener2 listener) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.child("User").child(id).child("userName").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    listener.successful(task.getResult().getValue().toString());
                } else {
                }
            }
        });
    }



}
