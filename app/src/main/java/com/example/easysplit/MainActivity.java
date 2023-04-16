package com.example.easysplit;

import static androidx.navigation.ui.NavigationUI.setupActionBarWithNavController;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.easysplit.R;
import com.example.easysplit.databinding.ActivityMainBinding;
import com.example.easysplit.viewModel.MainActivityViewModel;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    private static final String TAG = "MainActivity";

    private MainActivityViewModel mainActivityViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setBackgroundToBottomNavigation();

        mainActivityViewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
        final Observer<Boolean> booleanObserver = new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable final Boolean aBoolean) {
                if (aBoolean)
                {
                    Log.d(TAG, "Showing");
                    showBottomNavigationBar();
                }
                else
                {
                    hideBottomNavigationBar();
                }
            }
        };
        mainActivityViewModel.getIsShowBottomBar().observe(this, booleanObserver);


        binding.bottomNavigationBar.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                mainActivityViewModel.setBottomNavigationItem(item.getItemId());
                return true;
            }
        });
    }
    private void setBackgroundToBottomNavigation()
    {
        binding.bottomNavigationBar.setBackground(null);
    }

    private void showBottomNavigationBar()
    {
        binding.bottomAppBar.setVisibility(View.VISIBLE);
        binding.fab.setVisibility(View.VISIBLE);
    }

    private void hideBottomNavigationBar()
    {
        binding.bottomAppBar.setVisibility(View.GONE);
        binding.fab.setVisibility(View.GONE);
    }
    public void init()
    {

    }


}