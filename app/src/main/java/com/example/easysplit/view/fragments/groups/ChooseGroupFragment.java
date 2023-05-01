package com.example.easysplit.view.fragments.groups;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.easysplit.R;
import com.example.easysplit.databinding.FragmentChooseGroupBinding;
import com.example.easysplit.model.Group;
import com.example.easysplit.view.utils.NavigationUtils;
import com.example.easysplit.view.adapters.GroupsRecyclerAdapter;
import com.example.easysplit.viewModel.groups.GroupsViewModel;

import java.util.List;

public class ChooseGroupFragment extends Fragment {
    private static final String TAG = "ChooseGroupFragment";

    FragmentChooseGroupBinding binding;

    private GroupsRecyclerAdapter adapter;
    NavController navController;
    GroupsViewModel groupsViewModel;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentChooseGroupBinding.inflate(inflater, container, false);
        groupsViewModel = new ViewModelProvider(requireActivity()).get(GroupsViewModel.class);
        navController = Navigation.findNavController(requireActivity(), R.id.navHostFragment);
        groupsViewModel.init();
        initRecyclerView();
        binding.toolbar.textToolbar.setText("Выберите группу");
        final Observer<List<Group>> observerNewGroup = new Observer<List<Group>>() {
            @Override
            public void onChanged(List<Group> groups) {
                adapter.notifyDataSetChanged();
            }
        };
        groupsViewModel.getGroups().observe(requireActivity(), observerNewGroup);

        binding.toolbar.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavigationUtils.navigateSafe(navController, R.id.action_chooseGroupFragment_to_addExpenseFragment, null);
            }
        });


        return binding.getRoot();
    }

    private void initRecyclerView()
    {
        adapter = new GroupsRecyclerAdapter(getActivity(), groupsViewModel.getGroups().getValue(), groupId -> {
            Log.d(TAG, "You click on ");
            NavigationUtils.navigateSafe(navController, R.id.action_chooseGroupFragment_to_addExpenseFragment, null);
        });
        binding.recyclerViewGroup.setAdapter(adapter);
        binding.recyclerViewGroup.setLayoutManager(new LinearLayoutManager(getActivity()));
    }
}