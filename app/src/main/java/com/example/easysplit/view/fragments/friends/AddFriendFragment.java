package com.example.easysplit.view.fragments.friends;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.easysplit.R;
import com.example.easysplit.databinding.FragmentAddFriendBinding;
import com.example.easysplit.view.listeners.AddFriendToUserListener;
import com.example.easysplit.view.utils.NavigationUtils;
import com.example.easysplit.viewModel.friends.FriendsViewModel;

public class AddFriendFragment extends Fragment {

    FragmentAddFriendBinding binding;
    NavController navController;

    FriendsViewModel friendsViewModel;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAddFriendBinding.inflate(inflater, container, false);
        navController = Navigation.findNavController(requireActivity(), R.id.navHostFragment);
        friendsViewModel = new ViewModelProvider(requireActivity()).get(FriendsViewModel.class);

        binding.toolbar.textToolbar.setText("Добавить друзей");
        binding.addFriendId.setTransformationMethod(null);

        binding.toolbar.back.setOnClickListener(v -> NavigationUtils.navigateSafe(navController, R.id.action_addFriendFragment_to_friendsFragment, null));



        binding.addFriendComplete.setOnClickListener(v -> {
            String userName = binding.addFriendName.getText().toString();
            String id = binding.addFriendId.getText().toString();
            if (userName.length() == 0)
            {
                Toast.makeText(requireActivity(), "Заполните поле Имя!", Toast.LENGTH_SHORT).show();
            }
            else if (id.length() != 5)
            {
                Toast.makeText(requireActivity(), "Поле id должно иметь 5 цифр", Toast.LENGTH_SHORT).show();
            }
            else
            {
                friendsViewModel.addNewValue(userName, id, new AddFriendToUserListener() {
                    @Override
                    public void userNotFound() {
                        Toast.makeText(requireContext(), "Пользователя с таким именем и id не найдено!", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void userIsYou() {
                        Toast.makeText(requireContext(), "Вы не можете добавить себя!", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void userAlreadyExist() {
                        Toast.makeText(requireContext(), "Этот друг уже добавлен!", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void successful() {
                        NavigationUtils.navigateSafe(navController, R.id.action_addFriendFragment_to_friendsFragment, null);
                        Toast.makeText(requireContext(), "Друг успешно добавлен!", Toast.LENGTH_SHORT).show();
                    }
                });
            }

        });

        return binding.getRoot();
    }
}