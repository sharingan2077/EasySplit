package com.example.easysplit.view.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.easysplit.R;
import com.example.easysplit.databinding.FragmentAddExpenseBinding;
import com.example.easysplit.view.utils.NavigationUtils;
import com.example.easysplit.viewModel.AddExpenseViewModel;
import com.example.easysplit.viewModel.MainActivityViewModel;

public class AddExpenseFragment extends Fragment {
    private static final String TAG = "AddExpenseFragment";
    FragmentAddExpenseBinding binding;
    MainActivityViewModel mainActivityViewModel;
    AddExpenseViewModel addExpenseViewModel;
    NavController navController;

    private int lastFragmentActionId;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAddExpenseBinding.inflate(inflater, container, false);
        navController = Navigation.findNavController(requireActivity(), R.id.navHostFragment);
        mainActivityViewModel = new ViewModelProvider(requireActivity()).get(MainActivityViewModel.class);
        addExpenseViewModel = new ViewModelProvider(requireActivity()).get(AddExpenseViewModel.class);
        binding.toolbar.back.setOnClickListener(v -> {
            Log.d(TAG, "Click on back " + lastFragmentActionId);
            mainActivityViewModel.setIsNotToMakeExpense();
            NavigationUtils.navigateSafe(navController, lastFragmentActionId, null);
        });

        final Observer<Integer> lastFragmentAction = integer -> lastFragmentActionId = integer;
        addExpenseViewModel.getLastFragment().observe(requireActivity(), lastFragmentAction);

        binding.whoPaid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavigationUtils.navigateSafe(navController, R.id.action_addExpenseFragment_to_whoPaidFragment, null);

            }
        });
        binding.group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavigationUtils.navigateSafe(navController, R.id.action_addExpenseFragment_to_chooseGroupFragment, null);
            }
        });

        binding.howSplit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavigationUtils.navigateSafe(navController, R.id.action_addExpenseFragment_to_splitEquallyFragment, null);
            }
        });

        return binding.getRoot();



    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.sum.setTransformationMethod(null);
        if (binding.sum.getText().toString().length() > 10)
        {
            Toast.makeText(requireActivity(), "Сумма не может превышать 10 символов!", Toast.LENGTH_SHORT).show();
        }
    }
}