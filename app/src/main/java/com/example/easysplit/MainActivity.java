package com.example.easysplit;

import static androidx.navigation.ui.NavigationUI.setupActionBarWithNavController;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.easysplit.databinding.ActivityMainBinding;
import com.example.easysplit.model.FriendsImages;
import com.example.easysplit.viewModel.MainActivityViewModel;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    private static final String TAG = "MainActivity";

    private MainActivityViewModel mainActivityViewModel;

    NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setBackgroundToBottomNavigation();
        binding.bottomNavigationBar.setItemIconTintList(null);


        mainActivityViewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
        mainActivityViewModel.init(this);
        mainActivityViewModel.initFab();


        final Observer<Boolean> booleanObserver = aBoolean -> {
            if (aBoolean) showBottomNavigationBar();
            else hideBottomNavigationBar();
        };
        mainActivityViewModel.getIsShowBottomBar().observe(this, booleanObserver);


        final Observer<String> observerUserImage = new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Log.d(TAG, "observerUserImage " + s);
                FriendsImages friendsImages = new FriendsImages();
                int drawableId = friendsImages.getImageFriends().get(Integer.valueOf(s));
                Menu navView = binding.bottomNavigationBar.getMenu();
                navView.findItem(R.id.profileFragment).setIcon(drawableId);
            }
        };
        mainActivityViewModel.getUserImage().observe(this, observerUserImage);


        binding.bottomNavigationBar.setOnItemSelectedListener(item -> {
            mainActivityViewModel.setBottomNavigationItem(item.getItemId());
            return true;
        });

        navController = Navigation.findNavController(this, R.id.navHostFragment);
        NavigationUI.setupWithNavController(binding.bottomNavigationBar, navController);
        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller,
                                             @NonNull NavDestination destination, @Nullable Bundle arguments) {
                int id = destination.getId();
                if(id == R.id.groupsFragment || id == R.id.friendsFragment
                || id == R.id.activityFragment || id == R.id.profileFragment) {
                    showBottomNavigationBar();

                }
//                else if (id == R.id.groupEnterFragment)
//                {
//                    showBottomNavigationBar();
//                }
                else
                {
                    hideBottomNavigationBar();
                }
            }
        });
        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivityViewModel.setIsGoToMakeExpense();
            }
        });
        setTransparentStatusBar();
    }
    private void setBackgroundToBottomNavigation()
    {
        binding.bottomNavigationBar.setBackground(null);
    }

    private void showBottomNavigationBar()
    {
        binding.fab.setVisibility(View.GONE);
        binding.bottomAppBar.setVisibility(View.GONE);
        binding.bottomAppBar.setVisibility(View.VISIBLE);
        //binding.fab.setLayoutDirection(R.id.bottomAppBar);
        binding.fab.setVisibility(View.VISIBLE);
    }

    private void hideBottomNavigationBar()
    {
        binding.fab.setVisibility(View.GONE);
        binding.bottomAppBar.setVisibility(View.GONE);
    }

    public void setTransparentStatusBar()
    {
        if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true);
        }
        if (Build.VERSION.SDK_INT >= 19) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }

    public static void setWindowFlag(Activity activity, final int bits, boolean on) {
        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

}