package com.example.easysplit.view.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easysplit.R;
import com.example.easysplit.model.Group;
import com.example.easysplit.model.User;

import java.util.ArrayList;
import java.util.List;

public class UsersRecyclerAdapter extends RecyclerView.Adapter<UsersRecyclerAdapter.ViewHolder>
{

    public interface onUserClickListener{
        void onClick();
    }
    private List<User> users =  new ArrayList<>();
    UsersRecyclerAdapter.onUserClickListener listener;

    private Context mContext;
    public UsersRecyclerAdapter(Context mContext, List<User> users) {
        this.users = users;
        this.mContext = mContext;
    }
    public UsersRecyclerAdapter(Context mContext, List<User> users, UsersRecyclerAdapter.onUserClickListener listener) {
        this.users = users;
        this.mContext = mContext;
        this.listener=listener;
    }

    @NonNull
    @Override
    public UsersRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item_users, parent, false);
        UsersRecyclerAdapter.ViewHolder holder = new UsersRecyclerAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull UsersRecyclerAdapter.ViewHolder holder, int position) {

        holder.imageView.setImageResource(R.drawable.ic_user_54);
        holder.userName.setText(users.get(position).getUserName());
        holder.parentLayout.setOnClickListener(v -> {
            listener.onClick();
        });

    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView imageView;
        TextView userName;
        LinearLayout parentLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageOfUser);
            userName = itemView.findViewById(R.id.nameOfUser);
            parentLayout = itemView.findViewById(R.id.parent);
        }

    }
}
