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

import java.util.ArrayList;
import java.util.List;

public class GroupsRecyclerAdapter extends RecyclerView.Adapter<GroupsRecyclerAdapter.ViewHolder> {




    public interface onGroupClickListener{
        void onClick(String groupId);
    }
    private static final String TAG = "RecyclerViewAdapter";
    private List<Group> groups =  new ArrayList<>();
    onGroupClickListener listener;

    private Context mContext;

    public GroupsRecyclerAdapter(Context mContext, List<Group> groups) {
        this.groups = groups;
        this.mContext = mContext;
    }

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
        holder.imageView.setImageResource(R.drawable.group_item_1);
        holder.groupName.setText(groups.get(position).getGroupName());
        int count = groups.get(position).getCountMember();
        holder.countMember.setText(Integer.toString(count));
        holder.parentLayout.setOnClickListener(v -> {
            listener.onClick(groups.get(position).getId());
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
