package com.example.easysplit.repository.activity;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.easysplit.model.Activity;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ActivityRepository {

    private static ActivityRepository instance;
    private ArrayList<Activity> dataSet = new ArrayList<>();
    MutableLiveData<List<Activity>> dataActivity = new MutableLiveData<>();
    private static final String TAG = "ActivityRepository";

    private List<String> userGroups;
    private List<String> groupsExpenses;

    private Boolean expenseExist = false;

    public static ActivityRepository getInstance()
    {
        if (instance == null)
        {
            instance = new ActivityRepository();
        }
        return instance;
    }

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

    private void setGroupsExpenses(CompleteListener listener)
    {
        setUserGroups(new CompleteListener() {
            @Override
            public void successful() {
                groupsExpenses = new ArrayList<>();
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
                Query query = reference.child("Group");
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren())
                        {
                            if (userGroups.contains(snapshot.getKey()))
                            {
                                for (DataSnapshot snapshot1 : snapshot.child("groupExpenses").getChildren())
                                {
                                    groupsExpenses.add(snapshot1.getKey());
                                }
                            }
                        }
                        listener.successful();
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            @Override
            public void unSuccessful() {

            }
        });
    }

    private void findNameOfUserById(String id, CompleteListener2 listener)
    {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.child("User").child(id).child("userName").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful())
                {
                    listener.successful(task.getResult().getValue().toString());
                }
            }
        });
    }

    public void findNameOfGroupById(String id, CompleteListener2 listener)
    {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.child("Group").child(id).child("groupName").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful())
                {
                    listener.successful(task.getResult().getValue().toString());
                }
            }
        });
    }

    public MutableLiveData<List<Activity>> getActivities(CompleteListener listener)
    {
        setActivities(listener);
        dataActivity.setValue(dataSet);
        return dataActivity;
    }


    private void setActivities(CompleteListener listener)
    {
        setGroupsExpenses(new CompleteListener() {
            @Override
            public void successful() {
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
                Query query = reference.child("Expense");
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        dataSet.clear();
                        for (DataSnapshot snapshot : dataSnapshot.getChildren())
                        {
                            if (groupsExpenses.contains(snapshot.getKey()))
                            {
                                expenseExist = true;
                               Activity activity = new Activity();
                               activity.setNameOfExpense(snapshot.child("expenseName").getValue().toString());
                               String date = snapshot.child("expenseDate").getValue().toString();

                               String day = date.substring(0, 2) + "." + date.substring(2, 4);
                               int day2 = Integer.valueOf(date.substring(0,2));
                               String nowDay = new SimpleDateFormat("ddMM").format(Calendar.getInstance().getTime());
                               String nowDay2 = nowDay.substring(0, 2) + "." + nowDay.substring(2, 4);
                               int nowDay22 = Integer.valueOf(nowDay2.substring(0,2));
                               if (nowDay2.equals(day))
                               {
                                   day = "Сегодня";
                               }
                               else if (date.substring(2,4).equals(nowDay.substring(2,4)) && nowDay22 - day2 == 1)
                               {
                                   day = "Вчера";
                               }
                               String newDate = day + ", " + date.substring(5, 7) + ":" + date.substring(7);
                               activity.setDate(newDate);
                               findNameOfUserById(FirebaseAuth.getInstance().getUid(), new CompleteListener2() {
                                    @Override
                                    public void successful(String data) {
                                        activity.setUserName(data);
                                        findNameOfGroupById(snapshot.child("expenseGroup").getValue().toString(), new CompleteListener2() {
                                            @Override
                                            public void successful(String data2) {
                                                activity.setNameOfGroup(data2);
                                                dataSet.add(activity);
                                                listener.successful();
                                                dataActivity.postValue(dataSet);
                                            }
                                        });
                                    }
                                });
                            }
                        }
                        Log.d(TAG, "listener successful after");
                        if (!expenseExist)
                        {
                            listener.successful();
                        }

                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
            @Override
            public void unSuccessful() {

            }
        });
    }

}
