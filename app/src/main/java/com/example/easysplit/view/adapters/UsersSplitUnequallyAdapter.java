package com.example.easysplit.view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easysplit.R;
import com.example.easysplit.model.User;

import java.util.ArrayList;
import java.util.List;

public class UsersSplitUnequallyAdapter extends RecyclerView.Adapter<UsersSplitUnequallyAdapter.ViewHolder> {

    private List<User> users =  new ArrayList<>();
    private Context mContext;

    public UsersSplitUnequallyAdapter(Context mContext, List<User> users) {
        this.users = users;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public UsersSplitUnequallyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_split_unequally_item, parent, false);
        UsersSplitUnequallyAdapter.ViewHolder holder = new UsersSplitUnequallyAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull UsersSplitUnequallyAdapter.ViewHolder holder, int position) {

        holder.imageView.setImageResource(R.drawable.ic_user_54);
        holder.userName.setText(users.get(position).getUserName());
        holder.sum.setTransformationMethod(null);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView imageView;
        TextView userName;
        EditText sum;

        LinearLayout parentLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageOfUser);
            userName = itemView.findViewById(R.id.nameOfUser);
            sum = itemView.findViewById(R.id.sum);
            parentLayout = itemView.findViewById(R.id.parent);
        }
    }
}
