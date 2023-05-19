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
import com.example.easysplit.model.FriendsImages;
import com.example.easysplit.model.User;

import java.util.ArrayList;
import java.util.List;

public class UsersRecyclerAdapter extends RecyclerView.Adapter<UsersRecyclerAdapter.ViewHolder>
{

    public interface onUserClickListener{
        void onClick(String userId, String userName);
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

        FriendsImages friendsImages = new FriendsImages();
        int image = friendsImages.getImageFriends().get(Integer.valueOf(users.get(position).getUserImage()));
        holder.imageView.setImageResource(image);

        holder.userName.setText(users.get(position).getUserName());
        holder.parentLayout.setOnClickListener(v -> {
            listener.onClick(users.get(position).getUID(), users.get(position).getUserName());
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
