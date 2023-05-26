package com.example.easysplit.view.adapters;

import android.annotation.SuppressLint;
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

import com.example.easysplit.R;
import com.example.easysplit.model.ExpenseInGroup;

import java.util.ArrayList;
import java.util.List;

public class ExpenseInGroupRecyclerAdapter extends RecyclerView.Adapter<ExpenseInGroupRecyclerAdapter.ViewHolder> {

    private static final String TAG = "ExpenseInGroupRecyclerA";
    private List<ExpenseInGroup> expensesInGroup =  new ArrayList<>();

    private Context mContext;

    public ExpenseInGroupRecyclerAdapter(Context mContext, List<ExpenseInGroup> expensesInGroup) {
        this.expensesInGroup = expensesInGroup;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ExpenseInGroupRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_expense_in_group, parent, false);
        ExpenseInGroupRecyclerAdapter.ViewHolder holder = new ExpenseInGroupRecyclerAdapter.ViewHolder(view);
        return holder;
    }
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ExpenseInGroupRecyclerAdapter.ViewHolder holder, int position) {
        String date = expensesInGroup.get(position).getDate();
        long sum = expensesInGroup.get(position).getExpenseSum();
        String month = getMonth(date.substring(3));
        String user = expensesInGroup.get(position).getUserName();
        Boolean yourExpense = expensesInGroup.get(position).getYourExpense();
        Long ownSum = expensesInGroup.get(position).getOwnSum();
        holder.monthOfExpense.setText(month);
        holder.dateOfExpense.setText(date.substring(0, 2));
        holder.nameOfExpense.setText(expensesInGroup.get(position).getExpenseName());
        holder.nameOfPayer.setText(user + " заплатил ₽" + Long.toString(sum));
        if (ownSum != 0)
        {
            if (yourExpense)
            {
                holder.own.setTextColor(ContextCompat.getColor(mContext, R.color.aqua));
                holder.cost.setTextColor(ContextCompat.getColor(mContext, R.color.aqua));
                holder.own.setText("тебе должны");
            }
            else
            {
                holder.own.setTextColor(ContextCompat.getColor(mContext, R.color.red));
                holder.cost.setTextColor(ContextCompat.getColor(mContext, R.color.red));
                holder.own.setText("ты должен");
            }
        }
        holder.cost.setText(Long.toString(ownSum));

    }
    public String getMonth(String month)
    {
        switch (month)
        {
            case "01":
                return "Янв";
            case "02":
                return "Фев";
            case "03":
                return "Март";
            case "04":
                return "Апр";
            case "05":
                return "Май";
            case "06":
                return "Июн";
            case "07":
                return "Июл";
            case "08":
                return "Авг";
            case "09":
                return "Сен";
            case "10":
                return "Окт";
            case "11":
                return "Ноя";
            case "12":
                return "Дек";
        }
        return "?";
    }

    @Override
    public int getItemCount() {
        return expensesInGroup.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView imageView;
        TextView monthOfExpense;
        TextView dateOfExpense;
        TextView nameOfExpense;
        TextView nameOfPayer;
        TextView own;
        TextView cost;
        LinearLayout parentLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageOfGroup);
            monthOfExpense = itemView.findViewById(R.id.monthOfExpense);
            dateOfExpense = itemView.findViewById(R.id.dateOfExpense);
            nameOfExpense = itemView.findViewById(R.id.nameOfExpense);
            nameOfPayer = itemView.findViewById(R.id.nameOfPayer);
            own = itemView.findViewById(R.id.own);
            cost = itemView.findViewById(R.id.cost);
            parentLayout = itemView.findViewById(R.id.parent);
        }

    }
}
