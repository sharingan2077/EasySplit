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
import com.example.easysplit.databinding.FragmentActivityBinding;
import com.example.easysplit.viewModel.AddExpenseViewModel;
import com.example.easysplit.viewModel.MainActivityViewModel;

public class ActivityFragment extends Fragment {
    private static final String TAG = "ActivityFragment";
    FragmentActivityBinding binding;
    MainActivityViewModel mainActivityViewModel;
    NavController navController;

    AddExpenseViewModel addExpenseViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("MyTag", "ActivityFragment open!");
        binding = FragmentActivityBinding.inflate(inflater, container, false);
        mainActivityViewModel = new ViewModelProvider(requireActivity()).get(MainActivityViewModel.class);
        navController = Navigation.findNavController(getActivity(), R.id.navHostFragment);
        mainActivityViewModel.showBottomNavigationBar();
        addExpenseViewModel = new ViewModelProvider(requireActivity()).get(AddExpenseViewModel.class);
        addExpenseViewModel.setLastFragmentAction(R.id.action_addExpenseFragment_to_activityFragment);
//        final Observer<Integer> itemSelectedObserver = itemId -> {
//            Log.d(TAG, "Activity");
//            switch (itemId)
//            {
//                case R.id.groups:
//                    NavigationUtils.navigateSafe(navController, R.id.action_activityFragment_to_groupsEmptyFragment, null);
//                    break;
//                case R.id.friends:
//                    NavigationUtils.navigateSafe(navController, R.id.action_activityFragment_to_friendsEmptyFragment, null);
//                    break;
//                case R.id.profile:
//                    NavigationUtils.navigateSafe(navController, R.id.action_activityFragment_to_profileFragment, null);
//                    break;
//            }
//        };
//        mainActivityViewModel.getBottomNavigationItem().observe(requireActivity(), itemSelectedObserver);
        final Observer<Boolean> isGoToExpenseObserver = new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean)
                {
                    NavigationUtils.navigateSafe(navController, R.id.action_activityFragment_to_addExpenseFragment, null);
                }
            }
        };
        mainActivityViewModel.getIsGoToMakeExpense().observe(getViewLifecycleOwner(), isGoToExpenseObserver);

        return binding.getRoot();
    }

}