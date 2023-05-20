package com.example.easysplit.repository;

import android.graphics.Paint;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.easysplit.model.Expense;
import com.example.easysplit.model.User;
import com.example.easysplit.view.listeners.AddFriendToUserListener;
import com.example.easysplit.view.listeners.CompleteListener;
import com.example.easysplit.view.listeners.CompleteListenerBoolean;
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
import java.util.HashMap;
import java.util.List;

public class UserRepository {

    private static UserRepository instance;
    private ArrayList<User> dataSet = new ArrayList<>();

    private static final String TAG = "UserRepository";
    MutableLiveData<List<User>> data = new MutableLiveData<>();

    private List<String> userFriends;
    private HashMap<String, Long> allFriendsExpenses;

    private Boolean friendExist = false;

    public static UserRepository getInstance()
    {
        if (instance == null)
        {
            instance = new UserRepository();
        }
        return instance;
    }
    public MutableLiveData<List<User>> getUsers(CompleteListener listener)
    {
        setUsers(listener);
        data.setValue(dataSet);
        return data;
    }


    //Заполнение массива userFriends друзьями текущего пользователя
    private void setUserFriends(CompleteListener listener)
    {
        userFriends = new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference.child("User").child(FirebaseAuth.getInstance().getUid()).child("userFriends");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    userFriends.add(snapshot.getValue().toString());
                }
                listener.successful();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

//    private void setUserJoinedGroups(ArrayList<String> userFriends, CompleteListener listener)
//    {
//        userJoinedGroups = new ArrayList<>();
//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
//        Query query = reference.child("Group");
//        query.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for (DataSnapshot snapshot : dataSnapshot.getChildren())
//                {
//                    if (snapshot.child("groupUsers").hasChild(FirebaseAuth.getInstance().getUid()))
//                    {
//                        for (String id : userFriends)
//                        {
//                            if (snapshot.child("groupUsers").hasChild(id))
//                            {
//                                userJoinedGroups.add(snapshot.getKey());
//                                break;
//                            }
//                        }
//                    }
//                }
//                listener.successful();
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }
    private void setAllFriendsExpenses(List<String> userFriends, CompleteListener listener)
    {
        allFriendsExpenses = new HashMap<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference.child("Expense");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Expense expense = snapshot.getValue(Expense.class);
                    HashMap<String, Long> usersWaste = expense.getUsersWaste();
                    String expenseOwner = expense.getExpenseOwner();
                    String UID = FirebaseAuth.getInstance().getUid();
                    if (expenseOwner.equals(UID))
                    {
                        for (String name: usersWaste.keySet()) {
                            if (userFriends.contains(name))
                            {
                                if (allFriendsExpenses.get(name) == null)
                                {
                                    allFriendsExpenses.put(name, usersWaste.get(name));
                                }
                                else
                                {
                                    allFriendsExpenses.put(name, allFriendsExpenses.get(name) + usersWaste.get(name));
                                }
                            }
                        }

                    }
                    else if (userFriends.contains(expenseOwner))
                    {
                        if (usersWaste.get(UID) != null)
                        {
                            if (allFriendsExpenses.get(expenseOwner) == null)
                            {
                                allFriendsExpenses.put(expenseOwner, usersWaste.get(UID) * (-1));
                            }
                            else
                            {
                                allFriendsExpenses.put(expenseOwner, allFriendsExpenses.get(expenseOwner) - usersWaste.get(UID));
                            }
                        }
                    }
                }
                for (String name : allFriendsExpenses.keySet())
                {
                    if (allFriendsExpenses.get(name) == 0)
                    {
                        allFriendsExpenses.remove(name);
                    }
                }
                listener.successful();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }





    //Загрузка друзей для текущего пользователя
    private void setUsers(CompleteListener listener)
    {

        setUserFriends(new CompleteListener() {


            @Override
            public void successful() {

                setAllFriendsExpenses(userFriends, new CompleteListener() {
                    @Override
                    public void successful()
                    {
                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
                        Query query = reference.child("User");
                        ValueEventListener postListener = new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                dataSet.clear();
                                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                                {
                                    if (userFriends.contains(snapshot.getKey()))
                                    {


                                        User user = new User(snapshot.child("userName").getValue().toString(), snapshot.child("id").getValue().toString(), snapshot.child("userImage").getValue().toString(), snapshot.getKey());
                                        if (allFriendsExpenses.get(snapshot.getKey()) != null)
                                        {
                                            if (allFriendsExpenses.get(snapshot.getKey()) > 0)
                                            {
                                                user.setYouOwn(true);
                                                user.setSumOwn(allFriendsExpenses.get(snapshot.getKey()));
                                            }
                                            else
                                            {
                                                user.setYouOwn(false);
                                                user.setSumOwn(allFriendsExpenses.get(snapshot.getKey()) * (-1));
                                            }
                                        }
                                        dataSet.add(user);
                                    }
                                }
                                listener.successful();
                                data.postValue(dataSet);
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError)
                            {
                            }
                        };
                        query.addValueEventListener(postListener);
                    }

                    @Override
                    public void unSuccessful() {

                    }
                });
            }
            @Override
            public void unSuccessful() {

            }
        });
    }

    private void userAlreadyHasFriend(String UID, CompleteListenerBoolean listener)
    {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference.child("User").child(FirebaseAuth.getInstance().getUid()).child("userFriends");

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChild(UID))
                {
                    listener.successful(false);
                }
                else
                {
                    listener.successful(true);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    public void addFriend(String userName, String id, AddFriendToUserListener listener)
    {


        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference.child("User");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    if (snapshot.child("id").getValue().equals(id) && snapshot.child("userName").getValue().equals(userName))
                    {
                        friendExist = true;
                        if (snapshot.getKey().equals(FirebaseAuth.getInstance().getUid().toString()))
                        {
                            listener.userIsYou();
                        }
                        else
                        {
                            addFriendToUser(snapshot.getKey(), listener);
                        }
                        break;
                    }
                }
                if (!friendExist)
                {
                    listener.userNotFound();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void addFriendToUser(String UID, AddFriendToUserListener listener)
    {
        userAlreadyHasFriend(UID, new CompleteListenerBoolean() {
            @Override
            public void successful(Boolean data) {
                if (data)
                {
                    FirebaseDatabase.getInstance().getReference()
                            .child("User")
                            .child(FirebaseAuth.getInstance().getUid())
                            .child("userFriends")
                            .updateChildren(Collections.singletonMap(UID,UID)).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (!task.isSuccessful())
                                    {
                                        Log.d("User", "Не удалось добавить пользователя");
                                    }
                                }
                            });
                    FirebaseDatabase.getInstance().getReference()
                            .child("User")
                            .child(UID)
                            .child("userFriends")
                            .updateChildren(Collections.singletonMap(FirebaseAuth.getInstance().getUid(), FirebaseAuth.getInstance().getUid()))
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (!task.isSuccessful())
                                    {
                                        Log.d("User", "Не удалось добавить текущего пользователя к новому");
                                    }
                                }
                            });
                    listener.successful();
                }
                else
                {
                    listener.userAlreadyExist();
                }
            }
        });
    }

    public interface AddFriendToGroupListener
    {
        void successful();
        void unSuccessful();
    }

    private void userAlreadyExistInGroup(String groupId, String userId, CompleteListenerBoolean listenerBoolean)
    {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference.child("Group").child(groupId).child("groupUsers");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(userId))
                {
                    listenerBoolean.successful(true);
                }
                else
                {
                    listenerBoolean.successful(false);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d(TAG, "onCancelled");

            }
        });
    }

    public void addFriendToGroup(String groupId, String userId, AddFriendToGroupListener listener)
    {
        userAlreadyExistInGroup(groupId, userId, new CompleteListenerBoolean() {
            @Override
            public void successful(Boolean data) {
                if (!data)
                {
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference()
                            .child("User")
                            .child(userId)
                            .child("userGroups");
                    databaseReference.updateChildren(Collections.singletonMap(groupId, groupId)).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful())
                            {
                                Log.d(TAG, "Successful");
                                increaseGroupMemberCount(groupId);
                                FirebaseDatabase.getInstance().getReference().child("Group").child(groupId)
                                        .child("groupUsers").updateChildren(Collections.singletonMap(userId, userId))
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                listener.successful();
                                            }
                                        });
                            }
                            else
                            {
                                Log.d(TAG, "unSuccessful");
                                listener.unSuccessful();
                            }
                        }
                    });
                }
                else
                {
                    listener.unSuccessful();
                }
            }
        });

    }
    public void increaseGroupMemberCount(String groupId)
    {
        int countGroupMembersOld, countGroupMembersNew;
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
                .child("Group")
                .child(groupId)
                .child("countMember");
        reference.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful())
                {
                    Long count = (Long)task.getResult().getValue();
                    reference.setValue(count.intValue() + 1);
                }
            }
        });
    }

    public MutableLiveData<List<User>> getUsers()
    {
        setUsers();
        data.setValue(dataSet);
        return data;
    }

    private void setUsers()
    {

        setUserFriends(new CompleteListener() {
            @Override
            public void successful() {
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
                Query query = reference.child("User");
                ValueEventListener postListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        dataSet.clear();
                        for (DataSnapshot snapshot : dataSnapshot.getChildren())
                        {
                            if (userFriends.contains(snapshot.getKey()))
                            {
                                User user = snapshot.getValue(User.class);
                                //User user = new User(snapshot.child("userName").getValue().toString(), snapshot.child("id").getValue().toString(), snapshot.getKey());
                                dataSet.add(user);
                            }
                        }
                        data.postValue(dataSet);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                    }
                };
                query.addValueEventListener(postListener);
            }

            @Override
            public void unSuccessful() {

            }
        });
    }

    // Вычисление долгов друзей






}
