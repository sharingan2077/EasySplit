package com.example.easysplit.view.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.easysplit.utils.NavigationUtils;
import com.example.easysplit.R;
import com.example.easysplit.databinding.GroupsEmptyFragmentBinding;
import com.example.easysplit.viewModel.MainActivityViewModel;

public class GroupsEmptyFragment extends Fragment {
    private static final String TAG = "GroupsEmptyFragment";
    GroupsEmptyFragmentBinding binding;
    MainActivityViewModel mainActivityViewModel;
    NavController navController;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("MyTag", "GroupsEmptyFragment open!");
        binding = GroupsEmptyFragmentBinding.inflate(inflater, container, false);
        navController = Navigation.findNavController(getActivity(), R.id.navHostFragment);
        mainActivityViewModel = new ViewModelProvider(requireActivity()).get(MainActivityViewModel.class);
        mainActivityViewModel.showBottomNavigationBar();
//        AppCompatActivity activity = (AppCompatActivity)getActivity();
//        activity.setSupportActionBar(binding.toolbar);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.createGroup.setOnClickListener(v -> {
            NavigationUtils.navigateSafe(navController, R.id.action_groupsEmptyFragment_to_groupCreateFragment, null);
        });
        final Observer<Integer> itemSelectedObserver = itemId -> {
            Log.d("MyTaggg", "Observer in GroupEmpty see changes");
            switch (itemId)
            {
                case R.id.friends:
                    NavigationUtils.navigateSafe(navController, R.id.action_groupsEmptyFragment_to_friendsEmptyFragment, null);
                    break;
                case R.id.activities:
                    NavigationUtils.navigateSafe(navController, R.id.action_groupsEmptyFragment_to_activityFragment, null);
                    break;
                case R.id.profile:
                    NavigationUtils.navigateSafe(navController, R.id.action_groupsEmptyFragment_to_profileFragment, null);
                    break;
            }
        };
        mainActivityViewModel.getBottomNavigationItem().observe(getViewLifecycleOwner(), itemSelectedObserver);
    }

    @Override
    public void onPause() {
        Log.d("MyTag", "onPause GroupsEmpty");
        super.onPause();
    }

    @Override
    public void onStop() {
        Log.d("MyTag", "onStop GroupsEmpty");
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        Log.d("MyTag", "onDestroyView GroupsEmpty");
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        Log.d("MyTag", "onDestroy GroupsEmpty");
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        Log.d("MyTag", "onDetach GroupsEmpty");
        super.onDetach();
    }
}