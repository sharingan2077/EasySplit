package com.example.easysplit.view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStructure;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easysplit.R;
import com.example.easysplit.model.FriendsImages;
import com.example.easysplit.model.User;

import java.util.ArrayList;
import java.util.List;

public class UsersSplitEquallyAdapter extends RecyclerView.Adapter<UsersSplitEquallyAdapter.ViewHolder>{

    public interface onUserClickListener{
        void onClickAdd(String userId);
        void onClickRemove(String userId);
    }
    private List<User> users =  new ArrayList<>();

    private List<String> usersId;
    UsersSplitEquallyAdapter.onUserClickListener listener;

    private Context mContext;
    public UsersSplitEquallyAdapter(Context mContext, List<User> users) {
        this.users = users;
        this.mContext = mContext;
    }
    public UsersSplitEquallyAdapter(Context mContext, List<User> users, UsersSplitEquallyAdapter.onUserClickListener listener) {
        this.users = users;
        this.mContext = mContext;
        this.listener=listener;
    }

    public UsersSplitEquallyAdapter(Context mContext, List<User> users, List<String> usersId, onUserClickListener listener) {
        this.users = users;
        this.usersId = usersId;
        this.listener = listener;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public UsersSplitEquallyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_split_equally_item, parent, false);
        UsersSplitEquallyAdapter.ViewHolder holder = new UsersSplitEquallyAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull UsersSplitEquallyAdapter.ViewHolder holder, int position) {

        FriendsImages friendsImages = new FriendsImages();
        int image = friendsImages.getImageFriends().get(Integer.valueOf(users.get(position).getUserImage()));
        holder.imageView.setImageResource(image);

        holder.userName.setText(users.get(position).getUserName());

        if (!usersId.contains(users.get(position).getUID()))
        {
            holder.checkBox.setChecked(false);
        }


        holder.parentLayout.setOnClickListener(v -> {
            //listener.onClick();
            if (holder.checkBox.isChecked())
            {
                listener.onClickRemove(users.get(position).getUID());
                holder.checkBox.setChecked(false);
            }
            else
            {
                listener.onClickAdd(users.get(position).getUID());
                holder.checkBox.setChecked(true);
            }
        });
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!holder.checkBox.isChecked())
                {
                    listener.onClickRemove(users.get(position).getUID());
                }
                else
                {
                    listener.onClickAdd(users.get(position).getUID());
                }
            }
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

        CheckBox checkBox;
        ConstraintLayout parentLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageOfUser);
            userName = itemView.findViewById(R.id.nameOfUser);
            checkBox = itemView.findViewById(R.id.checkbox);
            parentLayout = itemView.findViewById(R.id.parent);
        }
    }

}
