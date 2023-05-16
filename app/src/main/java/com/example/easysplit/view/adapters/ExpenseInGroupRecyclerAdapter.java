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
import androidx.recyclerview.widget.RecyclerView;

import com.example.easysplit.R;
import com.example.easysplit.model.ExpenseInGroup;

import java.util.ArrayList;
import java.util.List;

public class ExpenseInGroupRecyclerAdapter extends RecyclerView.Adapter<ExpenseInGroupRecyclerAdapter.ViewHolder> {

    public interface onGroupClickListener{
        void onClick();
    }
    private static final String TAG = "RecyclerViewAdapter";
    private List<ExpenseInGroup> expensesInGroup =  new ArrayList<>();
    ExpenseInGroupRecyclerAdapter.onGroupClickListener listener;

    private Context mContext;

    public ExpenseInGroupRecyclerAdapter(Context mContext, List<ExpenseInGroup> expensesInGroup) {
        this.expensesInGroup = expensesInGroup;
        this.mContext = mContext;
    }

    public ExpenseInGroupRecyclerAdapter(Context mContext, List<ExpenseInGroup> expensesInGroup, ExpenseInGroupRecyclerAdapter.onGroupClickListener listener) {
        this.expensesInGroup = expensesInGroup;;
        this.mContext = mContext;
        this.listener=listener;
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
        int sum = expensesInGroup.get(position).getExpenseSum();
        String month = getMonth(date.substring(0, 2));
        String user = expensesInGroup.get(position).getUserName();
        holder.monthOfExpense.setText(month);
        holder.dateOfExpense.setText(date.substring(3));
        holder.nameOfExpense.setText(expensesInGroup.get(position).getExpenseName());
        holder.nameOfPayer.setText(user + " заплатил " + Integer.toString(sum));
        if (user != "Ты")
        {
            holder.own.setText("ты должен");
        }
        else
        {
            holder.own.setText("тебе должны");
        }
        holder.cost.setText(Integer.toString(sum));
        //holder.imageView.setImageResource(R.drawable.group_item_1);
//        holder.parentLayout.setOnClickListener(v -> {
//            listener.onClick();
//        });

    }
    public String getMonth(String month)
    {
        switch (month)
        {
            case "01":
                return "Янв";
            case "09":
                return "Сен";
            case "04":
                return "Апр";
        }
        return "Авг";
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
