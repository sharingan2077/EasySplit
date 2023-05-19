package com.example.easysplit.view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.easysplit.R;
import com.example.easysplit.model.FriendsImages;
import com.example.easysplit.model.User;
import com.squareup.picasso.Picasso;

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
        FriendsImages friendsImages = new FriendsImages();
        int image = friendsImages.getImageFriends().get(Integer.valueOf(users.get(position).getUserImage()));
        holder.imageView.setImageResource(image);


        holder.userName.setText(users.get(position).getUserName());
        long ownSum = users.get(position).getSumOwn();
        if (ownSum != 0)
        {
            Boolean youOwn = users.get(position).getYouOwn();
            if (youOwn)
            {
                holder.owe.setTextColor(ContextCompat.getColor(mContext, R.color.aqua));
                holder.sum.setTextColor(ContextCompat.getColor(mContext, R.color.aqua));
                holder.owe.setText("должен тебе");
                holder.sum.setText(Long.toString(ownSum) + "руб");
            }
            else
            {
                holder.owe.setTextColor(ContextCompat.getColor(mContext, R.color.red));
                holder.sum.setTextColor(ContextCompat.getColor(mContext, R.color.red));
                holder.owe.setText("ты должен");
                holder.sum.setText(Long.toString(ownSum) + "руб");
            }
        }
        else
        {
            holder.owe.setVisibility(View.GONE);
            holder.sum.setVisibility(View.GONE);
        }
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
