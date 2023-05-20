package com.example.easysplit.model;

import com.example.easysplit.R;

import java.util.ArrayList;
import java.util.List;

public class GroupsImages {

    List<Integer> imageGroupsHome = new ArrayList<>();
    List<Integer> imageGroupsFamily = new ArrayList<>();
    List<Integer> imageGroupsTrip = new ArrayList<>();
    List<Integer> imageGroupWork = new ArrayList<>();
    List<Integer> imageGroupParty = new ArrayList<>();
    List<Integer> imageGroupLove = new ArrayList<>();
    List<Integer> imageGroupOther = new ArrayList<>();

    public List<Integer> getImageGroupsHome() {
        return imageGroupsHome;
    }

    public List<Integer> getImageGroupsFamily() {
        return imageGroupsFamily;
    }

    public List<Integer> getImageGroupsTrip() {
        return imageGroupsTrip;
    }

    public List<Integer> getImageGroupWork() {
        return imageGroupWork;
    }

    public List<Integer> getImageGroupParty() {
        return imageGroupParty;
    }

    public List<Integer> getImageGroupLove() {
        return imageGroupLove;
    }

    public List<Integer> getImageGroupOther() {
        return imageGroupOther;
    }

    public GroupsImages() {
        imageGroupsHome.add(R.drawable.ic_home_1);
        imageGroupsHome.add(R.drawable.ic_home_2);
        imageGroupsHome.add(R.drawable.ic_home_3);
        imageGroupsHome.add(R.drawable.ic_home_4);

        imageGroupsFamily.add(R.drawable.ic_family_1);
        imageGroupsFamily.add(R.drawable.ic_family_2);
        imageGroupsFamily.add(R.drawable.ic_family_3);

        imageGroupsTrip.add(R.drawable.ic_trip_1);
        imageGroupsTrip.add(R.drawable.ic_trip_2);

        imageGroupWork.add(R.drawable.ic_work_1);
        imageGroupWork.add(R.drawable.ic_work_2);
        imageGroupWork.add(R.drawable.ic_work_3);

        imageGroupParty.add(R.drawable.ic_party_1);
        imageGroupParty.add(R.drawable.ic_party_2);
        imageGroupParty.add(R.drawable.ic_party_3);

        imageGroupLove.add(R.drawable.ic_love_1);
        imageGroupLove.add(R.drawable.ic_love_2);
        imageGroupLove.add(R.drawable.ic_love_3);
        imageGroupLove.add(R.drawable.ic_love_4);


        imageGroupOther.add(R.drawable.ic_other_1);
        imageGroupOther.add(R.drawable.ic_group_1);
        imageGroupOther.add(R.drawable.ic_group_2);

    }

}
