package com.example.easysplit.view.fragments.expense;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.easysplit.R;
import com.example.easysplit.databinding.FragmentWhoPaidBinding;
import com.example.easysplit.model.DebtInGroup;
import com.example.easysplit.model.User;
import com.example.easysplit.view.adapters.DebtInGroupAdapter;
import com.example.easysplit.view.adapters.ExpenseInGroupRecyclerAdapter;
import com.example.easysplit.view.adapters.UsersRecyclerAdapter;
import com.example.easysplit.view.utils.NavigationUtils;
import com.example.easysplit.viewModel.WhoPaidViewModel;

import java.util.List;

public class WhoPaidFragment extends Fragment {

    FragmentWhoPaidBinding binding;
    NavController navController;
    private UsersRecyclerAdapter adapter;

    WhoPaidViewModel whoPaidViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentWhoPaidBinding.inflate(inflater, container, false);
        navController = Navigation.findNavController(requireActivity(), R.id.navHostFragment);
        whoPaidViewModel = new ViewModelProvider(requireActivity()).get(WhoPaidViewModel.class);
        whoPaidViewModel.init();
        initRecyclerView();
        binding.toolbar.textToolbar.setText("Кто платит?");

        final Observer<List<User>> observerNewUsers = new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> user) {
                adapter.notifyDataSetChanged();
            }
        };
        whoPaidViewModel.getUsers().observe(requireActivity(), observerNewUsers);
        binding.toolbar.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavigationUtils.navigateSafe(navController, R.id.action_whoPaidFragment_to_addExpenseFragment, null);
            }
        });
        return binding.getRoot();
    }

    private void initRecyclerView()
    {
        adapter = new UsersRecyclerAdapter(requireActivity(), whoPaidViewModel.getUsers().getValue(), userId -> {
            NavigationUtils.navigateSafe(navController, R.id.action_whoPaidFragment_to_addExpenseFragment, null);
        });
        binding.recyclerViewUsers.setAdapter(adapter);
        binding.recyclerViewUsers.setLayoutManager(new LinearLayoutManager(getActivity()));
    }
}