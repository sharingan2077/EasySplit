package com.example.easysplit.viewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.easysplit.model.User;
import com.example.easysplit.repository.UserRepository;
import com.example.easysplit.repository.WhoPaidRepository;
import com.example.easysplit.view.listeners.CheckUsersIdListener;
import com.example.easysplit.view.listeners.CompleteListener;

import java.util.ArrayList;
import java.util.List;

public class WhoPaidViewModel extends ViewModel {

    private MutableLiveData<List<User>> users;
    private WhoPaidRepository mRepo;

    public void init(String id, CompleteListener listener)
    {

        if (users != null)
        {
            users = mRepo.getUsers(id, listener);
            return;
        }
        mRepo = WhoPaidRepository.getInstance();
        users = mRepo.getUsers(id, listener);
    }

    public void checkUsersId(ArrayList<String> usersId, CheckUsersIdListener listener)
    {
        mRepo.checkUsersId(usersId, listener);
    }

    public LiveData<List<User>> getUsers()
    {
        return users;
    }

}
