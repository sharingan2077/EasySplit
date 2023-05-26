package com.example.easysplit.view.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easysplit.R;
import com.example.easysplit.model.Group;
import com.example.easysplit.model.GroupsImages;

import java.util.ArrayList;
import java.util.List;

public class GroupsRecyclerAdapter extends RecyclerView.Adapter<GroupsRecyclerAdapter.ViewHolder> {




    public interface onGroupClickListener{
        void onClick(String groupId, String nameOfGroup, int countGroupMembers, int imageDrawable);
    }

    private static final String TAG = "GroupsRecyclerAdapter";
    private List<Group> groups =  new ArrayList<>();
    onGroupClickListener listener;

    private Context mContext;

    public GroupsRecyclerAdapter(Context mContext, List<Group> groups, onGroupClickListener listener) {
        this.groups = groups;
        this.mContext = mContext;
        this.listener=listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.resycler_view_item_groups, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "Uuu");

        String groupType = groups.get(position).getGroupType();
        int resourceId;
        GroupsImages groupsImages = new GroupsImages();
        switch (groupType)
        {
            case "home":
                resourceId = groupsImages.getImageGroupsHome().get(Integer.valueOf(groups.get(position).getGroupImage()));
                holder.imageView.setTag(resourceId);
                holder.imageView.setImageResource(resourceId);
                break;
            case "family":
                resourceId = groupsImages.getImageGroupsFamily().get(Integer.valueOf(groups.get(position).getGroupImage()));
                holder.imageView.setTag(resourceId);
                holder.imageView.setImageResource(resourceId);
                break;
            case "trip":
                resourceId = groupsImages.getImageGroupsTrip().get(Integer.valueOf(groups.get(position).getGroupImage()));
                holder.imageView.setTag(resourceId);
                holder.imageView.setImageResource(resourceId);
                break;
            case "work":
                resourceId = groupsImages.getImageGroupWork().get(Integer.valueOf(groups.get(position).getGroupImage()));
                holder.imageView.setTag(resourceId);
                holder.imageView.setImageResource(resourceId);
                break;

            case "party":
                resourceId = groupsImages.getImageGroupParty().get(Integer.valueOf(groups.get(position).getGroupImage()));
                holder.imageView.setTag(resourceId);
                holder.imageView.setImageResource(resourceId);
                break;
            case "love":
                resourceId = groupsImages.getImageGroupLove().get(Integer.valueOf(groups.get(position).getGroupImage()));
                holder.imageView.setTag(resourceId);
                holder.imageView.setImageResource(resourceId);
                break;
            case "other":
                resourceId = groupsImages.getImageGroupOther().get(Integer.valueOf(groups.get(position).getGroupImage()));
                Log.d(TAG, Integer.toString(resourceId));
                holder.imageView.setTag(resourceId);
                holder.imageView.setImageResource(resourceId);
                break;
        }


        holder.groupName.setText(groups.get(position).getGroupName());
        int count = groups.get(position).getCountMember();
        holder.countMember.setText(Integer.toString(count));



        holder.parentLayout.setOnClickListener(v -> {
            Log.d(TAG, String.valueOf((int)holder.imageView.getTag()));
            listener.onClick(groups.get(position).getId(), groups.get(position).getGroupName(), count, (int)holder.imageView.getTag());
        });

    }

    @Override
    public int getItemCount() {
        if (groups == null) return 0;
        return groups.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView imageView;
        TextView groupName;
        TextView countMember;
        ConstraintLayout parentLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageOfGroup);
            groupName = itemView.findViewById(R.id.nameOfGroup);
            countMember = itemView.findViewById(R.id.numberOfPeople);
            parentLayout = itemView.findViewById(R.id.parent);
        }

    }
}
