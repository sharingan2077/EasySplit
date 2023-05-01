package com.example.easysplit.view.fragments.groups;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.easysplit.model.Group;
import com.example.easysplit.view.utils.NavigationUtils;
import com.example.easysplit.R;
import com.example.easysplit.databinding.FragmentGroupCreateBinding;
import com.example.easysplit.viewModel.groups.GroupsViewModel;
import com.example.easysplit.viewModel.MainActivityViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.UUID;

public class GroupCreateFragment extends Fragment {

    FragmentGroupCreateBinding binding;

    private final String colorAqua = "#08FFC8";
    private final String colorGrey = "#8b8687";

    private int groupItemId = 0;
    NavController navController;
    MainActivityViewModel mainActivityViewModel;
    GroupsViewModel groupsViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentGroupCreateBinding.inflate(inflater, container, false);
        navController = Navigation.findNavController(getActivity(), R.id.navHostFragment);
        mainActivityViewModel = new ViewModelProvider(requireActivity()).get(MainActivityViewModel.class);
        groupsViewModel = new ViewModelProvider(requireActivity()).get(GroupsViewModel.class);
        binding.toolbar.back.setOnClickListener(v -> {
            NavigationUtils.navigateSafe(navController, R.id.action_groupCreateFragment_to_groupsFragment, null);
        });
        binding.addGroup.setOnClickListener(v -> {
            if (binding.nameOfGroup.getText().toString().equals(""))
            {
                Toast.makeText(getActivity(), "Введите название группы!", Toast.LENGTH_SHORT).show();
            }
            else if (groupItemId == 0)
            {
                Toast.makeText(getActivity(), "Выберите назначение группы!", Toast.LENGTH_SHORT).show();
            }
            else
            {
                NavigationUtils.navigateSafe(navController, R.id.action_groupCreateFragment_to_groupsFragment, null);
                groupsViewModel.addNewValue(new Group(binding.nameOfGroup.getText().toString(), 1));
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.home.setOnClickListener(v -> {
            clearAllViews();
            binding.home.setBackground(getResources().getDrawable(R.drawable.style_click_type_group));
            binding.home1.setColorFilter(Color.parseColor(colorAqua));
            binding.home2.setTextColor(Color.parseColor(colorAqua));
            groupItemId = 1;

        });
        binding.trip.setOnClickListener(v -> {
            clearAllViews();
            binding.trip.setBackground(getResources().getDrawable(R.drawable.style_click_type_group));
            binding.trip1.setColorFilter(Color.parseColor(colorAqua));
            binding.trip2.setTextColor(Color.parseColor(colorAqua));
            groupItemId = 2;
        });
        binding.party.setOnClickListener(v -> {
            clearAllViews();
            binding.party.setBackground(getResources().getDrawable(R.drawable.style_click_type_group));
            binding.party1.setColorFilter(Color.parseColor(colorAqua));
            binding.party2.setTextColor(Color.parseColor(colorAqua));
            groupItemId = 3;
        });
        binding.other.setOnClickListener(v -> {
            clearAllViews();
            binding.other.setBackground(getResources().getDrawable(R.drawable.style_click_type_group));
            binding.other1.setColorFilter(Color.parseColor(colorAqua));
            binding.other2.setTextColor(Color.parseColor(colorAqua));
            groupItemId = 4;
        });
        binding.family.setOnClickListener(v -> {
            clearAllViews();
            binding.family.setBackground(getResources().getDrawable(R.drawable.style_click_type_group));
            binding.family1.setColorFilter(Color.parseColor(colorAqua));
            binding.family2.setTextColor(Color.parseColor(colorAqua));
            groupItemId = 5;
        });
        binding.work.setOnClickListener(v -> {
            clearAllViews();
            binding.work.setBackground(getResources().getDrawable(R.drawable.style_click_type_group));
            binding.work1.setColorFilter(Color.parseColor(colorAqua));
            binding.work2.setTextColor(Color.parseColor(colorAqua));
            groupItemId = 6;
        });
        binding.love.setOnClickListener(v -> {
            clearAllViews();
            binding.love.setBackground(getResources().getDrawable(R.drawable.style_click_type_group));
            binding.love1.setColorFilter(Color.parseColor(colorAqua));
            binding.love2.setTextColor(Color.parseColor(colorAqua));
            groupItemId = 7;
        });
    }

    private void clearAllViews()
    {
        switch (groupItemId)
        {
            case 1:
                binding.home.setBackground(getResources().getDrawable(R.drawable.style_choose_group_type));
                binding.home1.setColorFilter(Color.parseColor(colorGrey));
                binding.home2.setTextColor(Color.parseColor(colorGrey));
                break;
            case 2:
                binding.trip.setBackground(getResources().getDrawable(R.drawable.style_choose_group_type));
                binding.trip1.setColorFilter(Color.parseColor(colorGrey));
                binding.trip2.setTextColor(Color.parseColor(colorGrey));
                break;
            case 3:
                binding.party.setBackground(getResources().getDrawable(R.drawable.style_choose_group_type));
                binding.party1.setColorFilter(Color.parseColor(colorGrey));
                binding.party2.setTextColor(Color.parseColor(colorGrey));
                break;
            case 4:
                binding.other.setBackground(getResources().getDrawable(R.drawable.style_choose_group_type));
                binding.other1.setColorFilter(Color.parseColor(colorGrey));
                binding.other2.setTextColor(Color.parseColor(colorGrey));
                break;
            case 5:
                binding.family.setBackground(getResources().getDrawable(R.drawable.style_choose_group_type));
                binding.family1.setColorFilter(Color.parseColor(colorGrey));
                binding.family2.setTextColor(Color.parseColor(colorGrey));
                break;
            case 6:
                binding.work.setBackground(getResources().getDrawable(R.drawable.style_choose_group_type));
                binding.work1.setColorFilter(Color.parseColor(colorGrey));
                binding.work2.setTextColor(Color.parseColor(colorGrey));;
                break;
            case 7:
                binding.love.setBackground(getResources().getDrawable(R.drawable.style_choose_group_type));
                binding.love1.setColorFilter(Color.parseColor(colorGrey));
                binding.love2.setTextColor(Color.parseColor(colorGrey));
                break;

        }
    }
}