package com.example.easysplit.view.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easysplit.R;
import com.example.easysplit.model.DebtInGroup;

import java.util.ArrayList;
import java.util.List;

public class DebtInGroupAdapter extends RecyclerView.Adapter<DebtInGroupAdapter.ViewHolder> {

    private List<DebtInGroup> debtsInGroup =  new ArrayList<>();
    private Context mContext;

    private static final String TAG = "DebtInGroupAdapter";

    private long totalSum = 0;

    public DebtInGroupAdapter(Context mContext, List<DebtInGroup> debtsInGroup) {
        this.debtsInGroup = debtsInGroup;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public DebtInGroupAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item_debts, parent, false);
        DebtInGroupAdapter.ViewHolder holder = new DebtInGroupAdapter.ViewHolder(view);
        return holder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull DebtInGroupAdapter.ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder");
        String name = debtsInGroup.get(position).getUser();
        long sum = debtsInGroup.get(position).getSum();
        Boolean youOwn = debtsInGroup.get(position).getYouOwn();
        if (youOwn)
        {
            totalSum += sum;
            Log.d(TAG, "total sum - " + Long.toString(totalSum));
            holder.user.setText(name + " должен тебе");
            holder.sum.setTextColor(ContextCompat.getColor(mContext, R.color.blue));
        }
        else
        {
            totalSum -= sum;
            Log.d(TAG, "total sum - " + Long.toString(totalSum));
            holder.user.setText("Ты должен " + name);
            holder.sum.setTextColor(ContextCompat.getColor(mContext, R.color.red));
        }
        holder.sum.setText(Long.toString(sum) + "руб");




    }
    @Override
    public int getItemCount() {
        return debtsInGroup.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView user;
        TextView sum;
        LinearLayout parentLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            user = itemView.findViewById(R.id.user);
            sum = itemView.findViewById(R.id.sum);
            parentLayout = itemView.findViewById(R.id.parent);
        }

    }
}
