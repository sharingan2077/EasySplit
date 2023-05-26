package com.example.easysplit.view.fragments.groups;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.easysplit.databinding.FragmentGroupCreateBinding;
import com.example.easysplit.model.Group;
import com.example.easysplit.model.GroupsImages;
import com.example.easysplit.view.utils.NavigationUtils;
import com.example.easysplit.R;
import com.example.easysplit.viewModel.groups.GroupsViewModel;
import com.example.easysplit.viewModel.mainActivity.MainActivityViewModel;

import java.util.concurrent.ThreadLocalRandom;

public class GroupCreateFragment extends Fragment {

    FragmentGroupCreateBinding binding;

    private final String colorAqua = "#08FFC8";
    private final String colorGrey = "#8b8687";

    private int groupItemId = 0;
    NavController navController;
    MainActivityViewModel mainActivityViewModel;
    GroupsViewModel groupsViewModel;

    String groupType;

    GroupsImages groupsImages;

    private int imageHomeIndex = -1;
    private int imageFamilyIndex = -1;
    private int imageTripIndex = -1;
    private int imageWorkIndex = -1;
    private int imagePartyIndex = -1;
    private int imageLoveIndex = -1;
    private int imageOtherIndex = -1;

    private int imageIndex;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentGroupCreateBinding.inflate(inflater, container, false);
        navController = Navigation.findNavController(requireActivity(), R.id.navHostFragment);
        mainActivityViewModel = new ViewModelProvider(requireActivity()).get(MainActivityViewModel.class);
        groupsViewModel = new ViewModelProvider(requireActivity()).get(GroupsViewModel.class);
        groupsImages = new GroupsImages();

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
                groupsViewModel.addNewValue(new Group(binding.nameOfGroup.getText().toString(), 1, groupType, Integer.toString(imageIndex)));
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        binding.home.setOnClickListener(v -> {
            int randomImage = ThreadLocalRandom.current().nextInt(0, groupsImages.getImageGroupsHome().size());
            while (randomImage == imageHomeIndex) randomImage = ThreadLocalRandom.current().nextInt(0, groupsImages.getImageGroupsHome().size());
            imageHomeIndex = randomImage;
            groupType = "home";
            binding.imageGroupCreate.setImageResource(groupsImages.getImageGroupsHome().get(imageHomeIndex));
            imageIndex = imageHomeIndex;


            clearAllViews();
            binding.home.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.style_click_type_group));
            binding.home1.setColorFilter(Color.parseColor(colorAqua));
            binding.home2.setTextColor(Color.parseColor(colorAqua));
            groupItemId = 1;

        });
        binding.trip.setOnClickListener(v -> {
            int randomImage = ThreadLocalRandom.current().nextInt(0, groupsImages.getImageGroupsTrip().size());
            while (randomImage == imageTripIndex) randomImage = ThreadLocalRandom.current().nextInt(0, groupsImages.getImageGroupsTrip().size());
            imageTripIndex = randomImage;
            groupType = "home";
            binding.imageGroupCreate.setImageResource(groupsImages.getImageGroupsTrip().get(imageTripIndex));
            imageIndex = imageTripIndex;


            clearAllViews();
            binding.trip.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.style_click_type_group));
            binding.trip1.setColorFilter(Color.parseColor(colorAqua));
            binding.trip2.setTextColor(Color.parseColor(colorAqua));
            groupItemId = 2;
            groupType = "trip";
        });
        binding.party.setOnClickListener(v -> {
            int randomImage = ThreadLocalRandom.current().nextInt(0, groupsImages.getImageGroupParty().size());
            while (randomImage == imagePartyIndex) randomImage = ThreadLocalRandom.current().nextInt(0, groupsImages.getImageGroupParty().size());
            imagePartyIndex = randomImage;
            groupType = "party";
            binding.imageGroupCreate.setImageResource(groupsImages.getImageGroupParty().get(imagePartyIndex));
            imageIndex = imagePartyIndex;


            clearAllViews();
            binding.party.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.style_click_type_group));
            binding.party1.setColorFilter(Color.parseColor(colorAqua));
            binding.party2.setTextColor(Color.parseColor(colorAqua));
            groupItemId = 3;
            groupType = "party";
        });
        binding.other.setOnClickListener(v -> {
            int randomImage = ThreadLocalRandom.current().nextInt(0, groupsImages.getImageGroupOther().size());
            while (randomImage == imageOtherIndex) randomImage = ThreadLocalRandom.current().nextInt(0, groupsImages.getImageGroupOther().size());
            imageOtherIndex = randomImage;
            groupType = "other";
            binding.imageGroupCreate.setImageResource(groupsImages.getImageGroupOther().get(imageOtherIndex));
            imageIndex = imageOtherIndex;

            clearAllViews();
            binding.other.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.style_click_type_group));
            binding.other1.setColorFilter(Color.parseColor(colorAqua));
            binding.other2.setTextColor(Color.parseColor(colorAqua));
            groupItemId = 4;
            groupType = "other";
        });
        binding.family.setOnClickListener(v -> {
            int randomImage = ThreadLocalRandom.current().nextInt(0, groupsImages.getImageGroupsFamily().size());
            while (randomImage == imageFamilyIndex) randomImage = ThreadLocalRandom.current().nextInt(0, groupsImages.getImageGroupsFamily().size());
            imageFamilyIndex = randomImage;
            groupType = "family";
            binding.imageGroupCreate.setImageResource(groupsImages.getImageGroupsFamily().get(imageFamilyIndex));
            imageIndex = imageFamilyIndex;

            clearAllViews();
            binding.family.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.style_click_type_group));
            binding.family1.setColorFilter(Color.parseColor(colorAqua));
            binding.family2.setTextColor(Color.parseColor(colorAqua));
            groupItemId = 5;
            groupType = "family";
        });
        binding.work.setOnClickListener(v -> {
            int randomImage = ThreadLocalRandom.current().nextInt(0, groupsImages.getImageGroupWork().size());
            while (randomImage == imageWorkIndex) randomImage = ThreadLocalRandom.current().nextInt(0, groupsImages.getImageGroupWork().size());
            imageWorkIndex = randomImage;
            groupType = "work";
            binding.imageGroupCreate.setImageResource(groupsImages.getImageGroupWork().get(imageWorkIndex));
            imageIndex = imageWorkIndex;

            clearAllViews();
            binding.work.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.style_click_type_group));
            binding.work1.setColorFilter(Color.parseColor(colorAqua));
            binding.work2.setTextColor(Color.parseColor(colorAqua));
            groupItemId = 6;
            groupType = "work";
        });
        binding.love.setOnClickListener(v -> {
            int randomImage = ThreadLocalRandom.current().nextInt(0, groupsImages.getImageGroupLove().size());
            while (randomImage == imageLoveIndex) randomImage = ThreadLocalRandom.current().nextInt(0, groupsImages.getImageGroupLove().size());
            imageLoveIndex = randomImage;
            groupType = "love";
            binding.imageGroupCreate.setImageResource(groupsImages.getImageGroupLove().get(imageLoveIndex));
            imageIndex = imageLoveIndex;
            clearAllViews();
            binding.love.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.style_click_type_group));
            binding.love1.setColorFilter(Color.parseColor(colorAqua));
            binding.love2.setTextColor(Color.parseColor(colorAqua));
            groupItemId = 7;
            groupType = "love";
        });
    }

    private void clearAllViews()
    {
        switch (groupItemId)
        {
            case 1:
                binding.home.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.style_choose_group_type));
                binding.home1.setColorFilter(Color.parseColor(colorGrey));
                binding.home2.setTextColor(Color.parseColor(colorGrey));
                break;
            case 2:
                binding.trip.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.style_choose_group_type));
                binding.trip1.setColorFilter(Color.parseColor(colorGrey));
                binding.trip2.setTextColor(Color.parseColor(colorGrey));
                break;
            case 3:
                binding.party.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.style_choose_group_type));
                binding.party1.setColorFilter(Color.parseColor(colorGrey));
                binding.party2.setTextColor(Color.parseColor(colorGrey));
                break;
            case 4:
                binding.other.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.style_choose_group_type));
                binding.other1.setColorFilter(Color.parseColor(colorGrey));
                binding.other2.setTextColor(Color.parseColor(colorGrey));
                break;
            case 5:
                binding.family.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.style_choose_group_type));
                binding.family1.setColorFilter(Color.parseColor(colorGrey));
                binding.family2.setTextColor(Color.parseColor(colorGrey));
                break;
            case 6:
                binding.work.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.style_choose_group_type));
                binding.work1.setColorFilter(Color.parseColor(colorGrey));
                binding.work2.setTextColor(Color.parseColor(colorGrey));;
                break;
            case 7:
                binding.love.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.style_choose_group_type));
                binding.love1.setColorFilter(Color.parseColor(colorGrey));
                binding.love2.setTextColor(Color.parseColor(colorGrey));
                break;

        }
    }
}