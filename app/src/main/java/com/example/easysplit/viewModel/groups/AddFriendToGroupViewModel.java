package com.example.easysplit.viewModel.groups;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.easysplit.model.User;
import com.example.easysplit.repository.UserRepository;
import com.example.easysplit.view.listeners.CompleteListener;

import java.util.List;

public class AddFriendToGroupViewModel extends ViewModel {

    private MutableLiveData<List<User>> users;
    private UserRepository mRepo;

    public void init(CompleteListener listener)
    {

        if (users != null)
        {
            users = mRepo.getUsers(listener);
            return;
        }
        mRepo = UserRepository.getInstance();
        users = mRepo.getUsers(listener);
    }

    public void addNewValue(final User user)
    {

    }

    public void addFriendToGroup(String groupId, String userId, UserRepository.AddFriendToGroupListener listener)
    {
        mRepo.addFriendToGroup(groupId, userId, listener);
    }

    public LiveData<List<User>> getUsers()
    {
        return users;
    }
}
