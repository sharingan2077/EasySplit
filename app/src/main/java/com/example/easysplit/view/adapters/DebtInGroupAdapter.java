package com.example.easysplit.view.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easysplit.R;
import com.example.easysplit.model.DebtInGroup;

import java.util.ArrayList;
import java.util.List;

public class DebtInGroupAdapter extends RecyclerView.Adapter<DebtInGroupAdapter.ViewHolder> {

    private List<DebtInGroup> debtsInGroup =  new ArrayList<>();
    private Context mContext;

    public DebtInGroupAdapter(Context mContext, List<DebtInGroup> debtsInGroup) {
        this.debtsInGroup = debtsInGroup;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public DebtInGroupAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item_debts, parent, false);
        DebtInGroupAdapter.ViewHolder holder = new DebtInGroupAdapter.ViewHolder(view);
        return holder;
    }
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull DebtInGroupAdapter.ViewHolder holder, int position) {
        holder.user.setText(debtsInGroup.get(position).getUser() + " должен тебе");
        holder.sum.setText(Integer.toString(debtsInGroup.get(position).getSum()) + "руб");
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
