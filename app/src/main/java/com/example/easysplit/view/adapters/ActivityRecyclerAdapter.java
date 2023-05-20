package com.example.easysplit.view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easysplit.R;
import com.example.easysplit.model.Activity;

import java.util.ArrayList;
import java.util.List;

public class ActivityRecyclerAdapter extends RecyclerView.Adapter<ActivityRecyclerAdapter.ViewHolder> {
    private List<Activity> activities =  new ArrayList<>();
    private Context mContext;
    public ActivityRecyclerAdapter(Context mContext, List<Activity> activities) {
        this.activities = activities;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ActivityRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item_activity, parent, false);
        ActivityRecyclerAdapter.ViewHolder holder = new ActivityRecyclerAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ActivityRecyclerAdapter.ViewHolder holder, int position) {

        //holder.imgOfGroup.setImageResource(R.drawable.activity_1);
        holder.userName.setText(activities.get(position).getUserName());
        holder.nameOfExpense.setText(activities.get(position).getNameOfExpense());
        holder.nameOfGroup.setText(activities.get(position).getNameOfGroup());
        holder.date.setText(activities.get(position).getDate());
    }

    @Override
    public int getItemCount() {
        return activities.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgOfGroup;
        TextView userName;
        TextView nameOfExpense;
        TextView nameOfGroup;
        TextView date;
        LinearLayout parentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgOfGroup = itemView.findViewById(R.id.imgOfGroup);
            userName = itemView.findViewById(R.id.userName);
            nameOfExpense = itemView.findViewById(R.id.nameOfExpense);
            nameOfGroup = itemView.findViewById(R.id.nameOfGroup);
            date = itemView.findViewById(R.id.date);
            parentLayout = itemView.findViewById(R.id.parent);
        }
    }
}
