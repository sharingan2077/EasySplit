package com.example.easysplit.view.fragments;

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

import com.example.easysplit.databinding.FragmentActivityBinding;
import com.example.easysplit.view.adapters.ActivityRecyclerAdapter;
import com.example.easysplit.view.listeners.CompleteListener;
import com.example.easysplit.view.utils.NavigationUtils;
import com.example.easysplit.R;
import com.example.easysplit.viewModel.ActivityViewModel;
import com.example.easysplit.viewModel.MainActivityViewModel;

public class ActivityFragment extends Fragment {
    private static final String TAG = "ActivityFragment";

    FragmentActivityBinding binding;
    NavController navController;
    MainActivityViewModel mainActivityViewModel;
    ActivityViewModel activityViewModel;
    ActivityRecyclerAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("MyTag", "ActivityFragment open!");
        binding = FragmentActivityBinding.inflate(inflater, container, false);
        navController = Navigation.findNavController(getActivity(), R.id.navHostFragment);
        mainActivityViewModel = new ViewModelProvider(requireActivity()).get(MainActivityViewModel.class);
        activityViewModel = new ViewModelProvider(requireActivity()).get(ActivityViewModel.class);
        activityViewModel.init(new CompleteListener() {
            @Override
            public void successful() {
                adapter.notifyDataSetChanged();
                if (adapter.getItemCount() == 0)
                {
                    hideActivities();
                }
                else
                {
                    showActivities();
                }
            }

            @Override
            public void unSuccessful() {

            }
        });
        initRecyclerView();

        final Observer<Boolean> isGoToExpenseObserver = new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean)
                {
                    Bundle bundle = new Bundle();
                    bundle.putInt("ActionToLastFragment", R.id.action_addExpenseFragment_to_activityFragment);
                    NavigationUtils.navigateSafe(navController, R.id.action_activityFragment_to_addExpenseFragment, bundle);
                }
            }
        };
        mainActivityViewModel.getIsGoToMakeExpense().observe(getViewLifecycleOwner(), isGoToExpenseObserver);

        return binding.getRoot();
    }

    private void initRecyclerView()
    {
        adapter = new ActivityRecyclerAdapter(requireActivity(), activityViewModel.getActivities().getValue());
        binding.recyclerViewActivity.setAdapter(adapter);
        binding.recyclerViewActivity.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void showActivities()
    {
        binding.progressBar.setVisibility(View.GONE);
        binding.textActivity.setVisibility(View.VISIBLE);
        binding.recyclerViewActivity.setVisibility(View.VISIBLE);
        binding.imgNoActivity.setVisibility(View.GONE);
        binding.noActivityText.setVisibility(View.GONE);
    }
    private void hideActivities()
    {
        binding.progressBar.setVisibility(View.GONE);
        binding.textActivity.setVisibility(View.GONE);
        binding.recyclerViewActivity.setVisibility(View.GONE);
        binding.imgNoActivity.setVisibility(View.VISIBLE);
        binding.noActivityText.setVisibility(View.VISIBLE);
    }

}