package com.example.easysplit.view.adapters;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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

    private static final String TAG = "UsersSplitUnequallyAdap";
    private Context mContext;

    private ArrayList<String> usersId;
    private long[] usersSum;


    public interface onSumClickListener{
        void onChangeSum(String userId, String sum);
    }

    private onSumClickListener listener;

    public UsersSplitUnequallyAdapter(Context mContext, List<User> users) {
        this.users = users;
        this.mContext = mContext;
    }

    public UsersSplitUnequallyAdapter(Context mContext, List<User> users, onSumClickListener listener) {
        this.users = users;
        this.mContext = mContext;
        this.listener = listener;
    }

    public UsersSplitUnequallyAdapter(Context mContext, List<User> users, ArrayList<String> usersId, long[] usersSum, onSumClickListener listener) {
        this.users = users;
        this.mContext = mContext;
        this.listener = listener;
        this.usersId = usersId;
        this.usersSum = usersSum;
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

        if (usersSum != null)
        {
            if (usersId.contains(users.get(position).getUID()))
            {
                int index = usersId.indexOf(users.get(position).getUID());
                holder.sum.setText(Long.toString(usersSum[index]));
            }
        }

        holder.sum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                listener.onChangeSum(users.get(position).getUID(), holder.sum.getText().toString());
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
