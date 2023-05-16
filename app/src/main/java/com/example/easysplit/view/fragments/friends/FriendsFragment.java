package com.example.easysplit.view.fragments.friends;

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
import com.example.easysplit.databinding.FragmentFriendsBinding;
import com.example.easysplit.view.adapters.FriendsRecyclerAdapter;
import com.example.easysplit.view.listeners.CompleteListener;
import com.example.easysplit.view.utils.NavigationUtils;
import com.example.easysplit.viewModel.MainActivityViewModel;
import com.example.easysplit.viewModel.friends.FriendsViewModel;

public class FriendsFragment extends Fragment {

    FragmentFriendsBinding binding;
    NavController navController;

    MainActivityViewModel mainActivityViewModel;

    FriendsViewModel friendsViewModel;

    private FriendsRecyclerAdapter adapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentFriendsBinding.inflate(inflater, container, false);
        navController = Navigation.findNavController(requireActivity(), R.id.navHostFragment);
        showProgress();
        friendsViewModel = new ViewModelProvider(requireActivity()).get(FriendsViewModel.class);
        friendsViewModel.init(new CompleteListener() {
            @Override
            public void successful() {
                adapter.notifyDataSetChanged();
                if (adapter.getItemCount() == 0)
                {
                    hideFriends();
                }
                else
                {
                    showFriends();
                }
            }
            @Override
            public void unSuccessful() {

            }
        });


        initRecyclerView();

//        final Observer<List<User>> observerNewFriends = new Observer<List<User>>() {
//            @Override
//            public void onChanged(List<User> users) {
//                adapter.notifyDataSetChanged();
//                if (adapter.getItemCount() == 0)
//                {
//                    hideFriends();
//                }
//                else
//                {
//                    showFriends();
//                }
//
//            }
//        };
//        friendsViewModel.getUsers().observe(requireActivity(), observerNewFriends);


        mainActivityViewModel = new ViewModelProvider(requireActivity()).get(MainActivityViewModel.class);
        final Observer<Boolean> isGoToExpenseObserver = aBoolean -> {
            if (aBoolean)
            {
                Bundle bundle = new Bundle();
                bundle.putInt("ActionToLastFragment", R.id.action_addExpenseFragment_to_friendsFragment);
                NavigationUtils.navigateSafe(navController, R.id.action_friendsFragment_to_addExpenseFragment, bundle);
            }
        };
        mainActivityViewModel.getIsGoToMakeExpense().observe(getViewLifecycleOwner(), isGoToExpenseObserver);

        binding.addFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavigationUtils.navigateSafe(navController, R.id.action_friendsFragment_to_addFriendFragment, null);
            }
        });



        return binding.getRoot();
    }

    private void initRecyclerView()
    {
        adapter = new FriendsRecyclerAdapter(requireActivity(), friendsViewModel.getUsers().getValue());
        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void showProgress()
    {
        binding.progressBar.setVisibility(View.VISIBLE);
        binding.imgOfFriends.setVisibility(View.GONE);
        binding.txtEmptyFriends.setVisibility(View.GONE);
        binding.friends.setVisibility(View.GONE);
        //binding.totalSum.setVisibility(View.GONE);
        //binding.totalSumValue.setVisibility(View.GONE);
        binding.recyclerView.setVisibility(View.GONE);
        binding.addFriend.setVisibility(View.GONE);

    }

    private void showFriends()
    {
        binding.progressBar.setVisibility(View.GONE);
        binding.imgOfFriends.setVisibility(View.GONE);
        binding.txtEmptyFriends.setVisibility(View.GONE);
        binding.friends.setVisibility(View.VISIBLE);
        //binding.totalSum.setVisibility(View.VISIBLE);
        //binding.totalSumValue.setVisibility(View.VISIBLE);
        binding.recyclerView.setVisibility(View.VISIBLE);
        binding.addFriend.setVisibility(View.VISIBLE);
    }
    private void hideFriends()
    {
        binding.progressBar.setVisibility(View.GONE);
        binding.imgOfFriends.setVisibility(View.VISIBLE);
        binding.txtEmptyFriends.setVisibility(View.VISIBLE);
        binding.friends.setVisibility(View.GONE);
        //binding.totalSum.setVisibility(View.GONE);
        //binding.totalSumValue.setVisibility(View.GONE);
        binding.recyclerView.setVisibility(View.GONE);
        binding.addFriend.setVisibility(View.VISIBLE);

    }

}