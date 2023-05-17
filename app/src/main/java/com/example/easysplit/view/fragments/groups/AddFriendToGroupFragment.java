package com.example.easysplit.view.fragments.groups;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.easysplit.R;
import com.example.easysplit.databinding.FragmentAddFriendToGroupBinding;
import com.example.easysplit.repository.UserRepository;
import com.example.easysplit.view.adapters.UsersRecyclerAdapter;
import com.example.easysplit.view.listeners.CompleteListener;
import com.example.easysplit.view.utils.NavigationUtils;
import com.example.easysplit.viewModel.groups.AddFriendToGroupViewModel;

public class AddFriendToGroupFragment extends Fragment {

    NavController navController;
    FragmentAddFriendToGroupBinding binding;

    AddFriendToGroupViewModel addFriendToGroupViewModel;

    private String groupId;
    private String nameOfGroup;
    private int countGroupMembers;

    UsersRecyclerAdapter adapter;

    Bundle bundle;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAddFriendToGroupBinding.inflate(inflater, container, false);
        navController = Navigation.findNavController(requireActivity(), R.id.navHostFragment);
        addFriendToGroupViewModel = new ViewModelProvider(requireActivity()).get(AddFriendToGroupViewModel.class);
        addFriendToGroupViewModel.init(new CompleteListener() {
            @Override
            public void successful() {
                adapter.notifyDataSetChanged();

            }
            @Override
            public void unSuccessful() {

            }
        });
        groupId = getArguments().getString("groupId");
        nameOfGroup = getArguments().getString("nameOfGroup", "0");
        countGroupMembers = getArguments().getInt("countGroupMembers", 0);

        initRecyclerView();
        binding.toolbar.textToolbar.setText("Добавить в группу");
        binding.toolbar.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundle = new Bundle();
                bundle.putString("groupId", groupId);
                bundle.putString("nameOfGroup", nameOfGroup);
                bundle.putInt("countGroupMembers", countGroupMembers);
                NavigationUtils.navigateSafe(navController, R.id.action_addFriendToGroupFragment_to_groupEnterFragment, bundle);
            }
        });

        return binding.getRoot();
    }

    private void initRecyclerView()
    {
//        adapter = new UsersRecyclerAdapter(requireActivity(), addFriendToGroupViewModel.getUsers().getValue(), () -> {
//
//
//            NavigationUtils.navigateSafe(navController, R.id.action_whoPaidFragment_to_addExpenseFragment, null);
//        });

        adapter = new UsersRecyclerAdapter(requireActivity(), addFriendToGroupViewModel.getUsers().getValue(), userId -> {
            addFriendToGroupViewModel.addFriendToGroup(groupId, userId, new UserRepository.AddFriendToGroupListener() {
                @Override
                public void successful() {
                    bundle = new Bundle();
                    bundle.putString("groupId", groupId);
                    bundle.putString("nameOfGroup", nameOfGroup);
                    bundle.putInt("countGroupMembers", countGroupMembers);
                    NavigationUtils.navigateSafe(navController, R.id.action_addFriendToGroupFragment_to_groupEnterFragment, bundle);
                }
                @Override
                public void unSuccessful() {
                    Toast.makeText(requireActivity(), "Этот друг уже добавлен!", Toast.LENGTH_SHORT).show();
                }
            });
        });
        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }
}