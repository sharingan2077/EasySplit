package com.example.easysplit.view.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.easysplit.view.utils.NavigationUtils;
import com.example.easysplit.R;
import com.example.easysplit.databinding.FriendsEmptyFragmentBinding;
import com.example.easysplit.viewModel.AddExpenseViewModel;
import com.example.easysplit.viewModel.MainActivityViewModel;


public class FriendsEmptyFragment extends Fragment {
    private static final String TAG = "FriendsEmptyFragment";
    MainActivityViewModel mainActivityViewModel;
    FriendsEmptyFragmentBinding binding;

    AddExpenseViewModel addExpenseViewModel;

    NavController navController;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        binding = FriendsEmptyFragmentBinding.inflate(inflater, container, false);
        addExpenseViewModel = new ViewModelProvider(requireActivity()).get(AddExpenseViewModel.class);
        addExpenseViewModel.setLastFragmentAction(R.id.action_addExpenseFragment_to_friendsEmptyFragment);
        navController = Navigation.findNavController(getActivity(), R.id.navHostFragment);
        mainActivityViewModel = new ViewModelProvider(requireActivity()).get(MainActivityViewModel.class);
        mainActivityViewModel.showBottomNavigationBar();

        final Observer<Boolean> isGoToExpenseObserver = aBoolean -> {
            if (aBoolean)
            {
                Log.d(TAG, "Go to expenseFragment");
                NavigationUtils.navigateSafe(navController, R.id.action_friendsEmptyFragment_to_addExpenseFragment, null);
            }
        };
        mainActivityViewModel.getIsGoToMakeExpense().observe(getViewLifecycleOwner(), isGoToExpenseObserver);

//        final Observer<Integer> itemSelectedObserver = itemId -> {
//            switch (itemId)
//            {
//                case R.id.groups:
//                    NavigationUtils.navigateSafe(navController, R.id.action_friendsEmptyFragment_to_groupsEmptyFragment, null);
//                    break;
//                case R.id.activities:
//                    NavigationUtils.navigateSafe(navController, R.id.action_friendsEmptyFragment_to_activityFragment, null);
//                    break;
//                case R.id.profile:
//                    NavigationUtils.navigateSafe(navController, R.id.action_friendsEmptyFragment_to_profileFragment, null);
//                    break;
//            }
//        };
//        mainActivityViewModel.getBottomNavigationItem().observe(getViewLifecycleOwner(), itemSelectedObserver);


        return binding.getRoot();
    }
}