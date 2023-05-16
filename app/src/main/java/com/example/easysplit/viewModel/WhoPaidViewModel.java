package com.example.easysplit.viewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.easysplit.model.User;
import com.example.easysplit.repository.UserRepository;
import com.example.easysplit.repository.WhoPaidRepository;

import java.util.ArrayList;
import java.util.List;

public class WhoPaidViewModel extends ViewModel {

    private MutableLiveData<List<User>> users;
    private WhoPaidRepository mRepo;

    public void init(String id)
    {

        if (users != null)
        {
            users = mRepo.getUsers(id);
            return;
        }
        mRepo = WhoPaidRepository.getInstance();
        users = mRepo.getUsers(id);
    }

    public LiveData<List<User>> getUsers()
    {
        return users;
    }

}
