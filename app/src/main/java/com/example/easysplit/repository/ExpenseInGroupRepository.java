package com.example.easysplit.repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.easysplit.model.Expense;
import com.example.easysplit.model.ExpenseInGroup;
import com.example.easysplit.model.Group;
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

public class ExpenseInGroupRepository {

    private static ExpenseInGroupRepository instance;
    private ArrayList<ExpenseInGroup> dataSet = new ArrayList<>();
    MutableLiveData<List<ExpenseInGroup>> dataExpense = new MutableLiveData<>();

    private static final String TAG = "ExpenseInGroupRepositor";


    private List<String> groupExpensesId;

    private List<Expense> expensesInGroup;


    public static ExpenseInGroupRepository getInstance()
    {
        if (instance == null)
        {
            instance = new ExpenseInGroupRepository();
        }
        return instance;
    }
    public MutableLiveData<List<ExpenseInGroup>> getExpensesInGroup(String groupId, CompleteListener listener)
    {
        setExpensesInGroup(groupId, listener);
        dataExpense.setValue(dataSet);
        return dataExpense;
    }



//    private void setGroupExpensesId(String groupId, CompleteListener listener)
//    {
//        groupExpensesId = new ArrayList<>();
//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
//        Query query = reference.child("Group").child(groupId).child("groupExpenses");;
//        ValueEventListener postListener = new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                    groupExpensesId.add(snapshot.getKey());
//                }
//                listener.successful();
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        };
//        query.addValueEventListener(postListener);
//    }


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

    private void setExpensesInGroup(String groupId, CompleteListener listener)
    {
        setExpensesInArray(groupId, new CompleteListener() {
            @Override
            public void successful() {
                dataSet.clear();
                for (Expense expense : expensesInGroup)
                {
                    String owner = expense.getExpenseOwner();
                    findNameOfUserById(owner, new CompleteListener2() {
                        @Override
                        public void successful(String data) {
                            Boolean yourExpense;
                            if (data.equals("Ты")) yourExpense = true;
                            else yourExpense = false;
                            String expenseName = expense.getExpenseName();
                            String expenseDate = expense.getExpenseDate().substring(0, 2) + "." + expense.getExpenseDate().substring(2, 4);
                            Long expenseSum = expense.getExpenseSum();
                            HashMap<String, Long> usersWaste = expense.getUsersWaste();
                            long ownSum = 0;
                            String UID = FirebaseAuth.getInstance().getUid();
                            if (yourExpense)
                            {
                                for (String name: usersWaste.keySet()) {
                                    String key = name.toString();
                                    if (!key.equals(UID))
                                    {
                                        ownSum += usersWaste.get(key);
                                    }
                                }
                            }
                            else
                            {
                                if (usersWaste.get(UID) != null)
                                {
                                    ownSum = usersWaste.get(UID);
                                }
                            }
                            ExpenseInGroup expenseInGroup = new ExpenseInGroup(expenseName, expenseDate, data, expenseSum, yourExpense, ownSum);
                            dataSet.add(expenseInGroup);
                            dataExpense.setValue(dataSet);
                            listener.successful();
                            Log.d(TAG, "Size - " + Integer.toString(dataSet.size()));
                        }
                    });
                }
//                dataExpense.setValue(dataSet);
//                listener.successful();
            }

            @Override
            public void unSuccessful() {

            }
        });
    }

    private void findNameOfUserById(String id, CompleteListener2 listener)
    {
        if (FirebaseAuth.getInstance().getUid().equals(id))
        {
            listener.successful("Ты");
        }
        else
        {
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
            reference.child("User").child(id).child("userName").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if (task.isSuccessful())
                    {
                        listener.successful(task.getResult().getValue().toString());
                    }
                    else
                    {
                    }
                }
            });
        }
    }





//        dataSet.clear();
//        dataSet.add(new ExpenseInGroup("Potato", "20.09", "Misha", 1000));
//        dataSet.add(new ExpenseInGroup("Banana", "21.09", "You", 2000));

}
