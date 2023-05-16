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
import com.example.easysplit.view.adapters.GroupsRecyclerAdapter;
import com.example.easysplit.view.listeners.CompleteListener;
import com.example.easysplit.view.utils.NavigationUtils;
import com.example.easysplit.viewModel.groups.GroupsViewModel;

import java.util.List;

public class ChooseGroupFragment extends Fragment {
    private static final String TAG = "ChooseGroupFragment";

    FragmentChooseGroupBinding binding;

    private GroupsRecyclerAdapter adapter;
    NavController navController;
    GroupsViewModel groupsViewModel;

    private int actionToLastFragment;
    private String expenseId;
    private String groupId;
    private String expenseName;
    private String expenseSumString;
    private String userId;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentChooseGroupBinding.inflate(inflater, container, false);
        groupsViewModel = new ViewModelProvider(requireActivity()).get(GroupsViewModel.class);
        navController = Navigation.findNavController(requireActivity(), R.id.navHostFragment);
        actionToLastFragment = getArguments().getInt("ActionToLastFragment", 1);
        expenseId = getArguments().getString("expenseId", "0");
        groupId = getArguments().getString("groupId", "0");
        expenseSumString = getArguments().getString("expenseSum", "0");
        expenseName = getArguments().getString("expenseName", "0");
        userId = getArguments().getString("userId", "0");
        groupsViewModel.init(new CompleteListener() {
            @Override
            public void successful() {

            }

            @Override
            public void unSuccessful() {

            }
        });
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
                Bundle bundle = new Bundle();
                bundle.putInt("ActionToLastFragment", actionToLastFragment);
                bundle.putString("expenseId", expenseId);
                bundle.putString("groupId", groupId);
                bundle.putString("expenseName", expenseName);
                bundle.putString("expenseSum", expenseSumString);
                bundle.putString("userId", userId);
                NavigationUtils.navigateSafe(navController, R.id.action_chooseGroupFragment_to_addExpenseFragment, bundle);
            }
        });


        return binding.getRoot();
    }

    private void initRecyclerView()
    {
        adapter = new GroupsRecyclerAdapter(getActivity(), groupsViewModel.getGroups().getValue(), new GroupsRecyclerAdapter.onGroupClickListener() {
            @Override
            public void onClick(String groupId, String nameOfGroup, int countGroupMembers) {
                Bundle bundle = new Bundle();
                bundle.putInt("ActionToLastFragment", actionToLastFragment);
                bundle.putString("groupId", groupId);
                bundle.putString("expenseId", expenseId);
                bundle.putString("expenseName", expenseName);
                bundle.putString("expenseSum", expenseSumString);
                bundle.putString("userId", userId);
                NavigationUtils.navigateSafe(navController, R.id.action_chooseGroupFragment_to_addExpenseFragment, bundle);
            }
        });
        binding.recyclerViewGroup.setAdapter(adapter);
        binding.recyclerViewGroup.setLayoutManager(new LinearLayoutManager(getActivity()));
    }
}