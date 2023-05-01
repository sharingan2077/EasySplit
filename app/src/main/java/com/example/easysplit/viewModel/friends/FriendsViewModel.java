package com.example.easysplit.viewModel.friends;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.easysplit.model.User;
import com.example.easysplit.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

public class FriendsViewModel extends ViewModel {
    private MutableLiveData<List<User>> users;
    private UserRepository mRepo;

    public void init()
    {

        if (users != null)
        {
            return;
        }
        mRepo = UserRepository.getInstance();
        users = mRepo.getUsers();
    }

    public void addNewValue(String userName, String id)
    {
        mRepo.addFriend(userName, id);
    }

    public LiveData<List<User>> getUsers()
    {
        return users;
    }
}
