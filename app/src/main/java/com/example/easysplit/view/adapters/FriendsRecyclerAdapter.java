package com.example.easysplit.view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easysplit.R;
import com.example.easysplit.model.User;

import java.util.ArrayList;
import java.util.List;

public class FriendsRecyclerAdapter extends RecyclerView.Adapter<FriendsRecyclerAdapter.ViewHolder> {
    public interface onUserClickListener{
        void onClick();
    }
    private List<User> users =  new ArrayList<>();
    FriendsRecyclerAdapter.onUserClickListener listener;

    private Context mContext;
    public FriendsRecyclerAdapter(Context mContext, List<User> users) {
        this.users = users;
        this.mContext = mContext;
    }
    public FriendsRecyclerAdapter(Context mContext, List<User> users, FriendsRecyclerAdapter.onUserClickListener listener) {
        this.users = users;
        this.mContext = mContext;
        this.listener=listener;
    }

    @NonNull
    @Override
    public FriendsRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item_friend, parent, false);
        FriendsRecyclerAdapter.ViewHolder holder = new FriendsRecyclerAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull FriendsRecyclerAdapter.ViewHolder holder, int position) {

        holder.imageView.setImageResource(R.drawable.ic_user_54);
        holder.userName.setText(users.get(position).getUserName());
//        holder.parentLayout.setOnClickListener(v -> {
//            //listener.onClick();
//            if (holder.checkBox.isChecked())
//            {
//                holder.checkBox.setChecked(false);
//            }
//            else
//            {
//                holder.checkBox.setChecked(true);
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return users.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView imageView;
        TextView userName;
        TextView owe;
        TextView sum;

        LinearLayout parentLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageOfUser);
            userName = itemView.findViewById(R.id.nameOfUser);
            owe = itemView.findViewById(R.id.owe);
            sum = itemView.findViewById(R.id.sum);
            parentLayout = itemView.findViewById(R.id.parent);
        }
    }
}
