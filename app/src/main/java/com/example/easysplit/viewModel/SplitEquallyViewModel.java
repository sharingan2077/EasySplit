package com.example.easysplit.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.easysplit.model.User;
import com.example.easysplit.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

public class SplitEquallyViewModel extends ViewModel {
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

    public void addNewValue(final User user)
    {
        List<User> currentUsers;
        if (users != null)
        {
            currentUsers = users.getValue();
        }
        else
        {
            users = new MutableLiveData<>();
            currentUsers = new ArrayList<>();
        }
        currentUsers.add(user);
        users.setValue(currentUsers);
    }

    public LiveData<List<User>> getUsers()
    {
        return users;
    }
}
