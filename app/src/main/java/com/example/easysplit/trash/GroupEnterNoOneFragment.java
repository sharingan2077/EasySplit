package com.example.easysplit.trash;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.easysplit.R;
import com.example.easysplit.databinding.FragmentGroupEnterNoOneBinding;
import com.example.easysplit.view.utils.NavigationUtils;
import com.example.easysplit.viewModel.MainActivityViewModel;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class GroupEnterNoOneFragment extends Fragment {
    FragmentGroupEnterNoOneBinding binding;
    MainActivityViewModel mainActivityViewModel;

    NavController navController;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentGroupEnterNoOneBinding.inflate(inflater, container, false);
        navController = Navigation.findNavController(getActivity(), R.id.navHostFragment);
        mainActivityViewModel = new ViewModelProvider(requireActivity()).get(MainActivityViewModel.class);
        mainActivityViewModel.hideBottomNavigationBar();
        binding.toolbar.back.setOnClickListener(v -> {
            //NavigationUtils.navigateSafe(navController, R.id.action_groupEnterNoOneFragment_to_groupsFragment, null);
        });
        binding.toolbar.menu.setOnClickListener(v -> {
            showPopup(binding.toolbar.menu);
        });
        return binding.getRoot();
    }

    public void showPopup(View v) {
        PopupMenu popup = new PopupMenu(requireActivity(), v);
        popup.inflate(R.menu.toopbar_menu);
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.nav_delete)
                {
                    return true;
                }
                return false;
            }
        });
        setForceShowIcon(popup);
        popup.show();
    }
    public static void setForceShowIcon(PopupMenu popupMenu) {
        try {
            Field[] fields = popupMenu.getClass().getDeclaredFields();
            for (Field field : fields) {
                if ("mPopup".equals(field.getName())) {
                    field.setAccessible(true);
                    Object menuPopupHelper = field.get(popupMenu);
                    Class<?> classPopupHelper = Class.forName(menuPopupHelper
                            .getClass().getName());
                    Method setForceIcons = classPopupHelper.getMethod(
                            "setForceShowIcon", boolean.class);
                    setForceIcons.invoke(menuPopupHelper, true);
                    break;
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
