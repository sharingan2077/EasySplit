package com.example.easysplit.model;

import com.example.easysplit.R;

import java.util.ArrayList;
import java.util.List;

public class FriendsImages {

    List<String> imageUrls = new ArrayList<>();

    List<Integer> imageFriends = new ArrayList<>();

    public void setImageFriends(List<Integer> imageFriends) {
        this.imageFriends = imageFriends;
    }

    public List<Integer> getImageFriends() {
        return imageFriends;
    }

    public FriendsImages()
    {
        imageUrls.add("gs://easysplit-434e6.appspot.com/users_icons/icons8-circled-user-female-skin-type-1-and-2-50.svg");
        imageUrls.add("gs://easysplit-434e6.appspot.com/users_icons/icons8-circled-user-female-skin-type-3-50.svg");
        imageUrls.add("gs://easysplit-434e6.appspot.com/users_icons/icons8-circled-user-female-skin-type-4-50.svg");
        imageUrls.add("gs://easysplit-434e6.appspot.com/users_icons/icons8-circled-user-male-skin-type-1-and-2-50.svg");
        imageUrls.add("gs://easysplit-434e6.appspot.com/users_icons/icons8-circled-user-male-skin-type-3-50.svg");
        imageUrls.add("gs://easysplit-434e6.appspot.com/users_icons/icons8-circled-user-male-skin-type-4-50.svg");

        imageFriends.add(R.drawable.ic_user_1);
        imageFriends.add(R.drawable.ic_user_3);
        imageFriends.add(R.drawable.ic_user_2);
        imageFriends.add(R.drawable.ic_user_4);
        imageFriends.add(R.drawable.ic_user_5);
        imageFriends.add(R.drawable.ic_user_6);
        imageFriends.add(R.drawable.ic_user_7);
        imageFriends.add(R.drawable.ic_user_8);
        imageFriends.add(R.drawable.ic_user_9);
        imageFriends.add(R.drawable.ic_user_10);
        imageFriends.add(R.drawable.ic_user_11);
        imageFriends.add(R.drawable.ic_user_12);
        //imageFriends.add(R.drawable.ic_user_14);
        //imageFriends.add(R.drawable.ic_user_15);
        //imageFriends.add(R.drawable.ic_user_17);
        imageFriends.add(R.drawable.ic_user_18);
        //imageFriends.add(R.drawable.ic_user_19);
        imageFriends.add(R.drawable.ic_user_20);
        imageFriends.add(R.drawable.ic_user_21);
        //imageFriends.add(R.drawable.ic_user_22);
        //imageFriends.add(R.drawable.ic_user_23);
        imageFriends.add(R.drawable.ic_user_24);
        //imageFriends.add(R.drawable.ic_user_25);
        imageFriends.add(R.drawable.ic_user_26);
        imageFriends.add(R.drawable.ic_user_27);
        imageFriends.add(R.drawable.ic_user_28);
        imageFriends.add(R.drawable.ic_user_29);

    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }
}
