package com.example.easysplit.view.fragments.groups;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;

import com.example.easysplit.R;
import com.example.easysplit.databinding.FragmentGroupEnterBinding;
import com.example.easysplit.model.DebtInGroup;
import com.example.easysplit.model.ExpenseInGroup;
import com.example.easysplit.model.Group;
import com.example.easysplit.view.adapters.DebtInGroupAdapter;
import com.example.easysplit.view.adapters.ExpenseInGroupRecyclerAdapter;
import com.example.easysplit.view.adapters.GroupsRecyclerAdapter;
import com.example.easysplit.view.utils.NavigationUtils;
import com.example.easysplit.viewModel.AddExpenseViewModel;
import com.example.easysplit.viewModel.MainActivityViewModel;
import com.example.easysplit.viewModel.groups.GroupEnterViewModel;
import com.example.easysplit.viewModel.groups.GroupsViewModel;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

public class GroupEnterFragment extends Fragment {
    FragmentGroupEnterBinding binding;
    MainActivityViewModel mainActivityViewModel;
    GroupEnterViewModel groupEnterViewModel;

    GroupsViewModel groupsViewModel;

    AddExpenseViewModel addExpenseViewModel;

    private ExpenseInGroupRecyclerAdapter adapter;
    private DebtInGroupAdapter adapter2;

    NavController navController;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentGroupEnterBinding.inflate(inflater, container, false);
        navController = Navigation.findNavController(requireActivity(), R.id.navHostFragment);
        mainActivityViewModel = new ViewModelProvider(requireActivity()).get(MainActivityViewModel.class);
        groupEnterViewModel = new ViewModelProvider(requireActivity()).get(GroupEnterViewModel.class);
        addExpenseViewModel = new ViewModelProvider(requireActivity()).get(AddExpenseViewModel.class);
        groupsViewModel = new ViewModelProvider(requireActivity()).get(GroupsViewModel.class);
        addExpenseViewModel.setLastFragmentAction(R.id.action_addExpenseFragment_to_groupEnterFragment);
        groupEnterViewModel.init();
        initRecyclerView();

        final Observer<List<ExpenseInGroup>> observerNewExpenseInGroup = new Observer<List<ExpenseInGroup>>() {
            @Override
            public void onChanged(List<ExpenseInGroup> expenseInGroup) {
                adapter.notifyDataSetChanged();
                if (adapter.getItemCount() != 0)
                {
                    showExpensesInGroup();
                }
                else
                {
                    hideExpensesInGroup();
                }
            }
        };
        groupEnterViewModel.getExpensesInGroups().observe(requireActivity(), observerNewExpenseInGroup);

        final Observer<List<DebtInGroup>> observerNewDebtsInGroup = new Observer<List<DebtInGroup>>() {
            @Override
            public void onChanged(List<DebtInGroup> debtInGroup) {
                adapter.notifyDataSetChanged();
                if (adapter.getItemCount() != 0)
                {
                    showExpensesInGroup();
                }
                else
                {
                    hideExpensesInGroup();
                }
            }
        };
        groupEnterViewModel.getDebtsInGroup().observe(requireActivity(), observerNewDebtsInGroup);



        final Observer<Boolean> isGoToExpenseObserver = aBoolean -> {
            if (aBoolean) NavigationUtils.navigateSafe(navController, R.id.action_groupEnterFragment_to_addExpenseFragment, null);
        };
        mainActivityViewModel.getIsGoToMakeExpense().observe(getViewLifecycleOwner(), isGoToExpenseObserver);

        binding.toolbar.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavigationUtils.navigateSafe(navController, R.id.action_groupEnterFragment_to_groupsFragment, null);
            }
        });

        binding.toolbar.menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopup(binding.toolbar.menu);
            }
        });




        return binding.getRoot();
    }

    private void initRecyclerView()
    {
        adapter = new ExpenseInGroupRecyclerAdapter(getActivity(), groupEnterViewModel.getExpensesInGroups().getValue());
        adapter2 = new DebtInGroupAdapter(requireActivity(), groupEnterViewModel.getDebtsInGroup().getValue());
//        adapter = new GroupsRecyclerAdapter(getActivity(), groupsViewModel.getGroups().getValue(), () -> {
//            NavigationUtils.navigateSafe(navController, R.id.action_groupsFragment_to_groupEnterFragment, null);
//        });
        binding.recyclerViewExpense.setAdapter(adapter);
        binding.recyclerViewExpense.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.recyclerViewDebts.setAdapter(adapter2);
        binding.recyclerViewDebts.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void showExpensesInGroup()
    {
        binding.txtNoExpenses.setVisibility(View.GONE);
        binding.addFriends.setVisibility(View.GONE);
        binding.copyLink.setVisibility(View.GONE);
        binding.recyclerViewExpense.setVisibility(View.VISIBLE);
        binding.owedOverall.setVisibility(View.VISIBLE);
        binding.recyclerViewDebts.setVisibility(View.VISIBLE);

    }
    private void hideExpensesInGroup()
    {
        binding.txtNoExpenses.setVisibility(View.VISIBLE);
        binding.addFriends.setVisibility(View.VISIBLE);
        binding.copyLink.setVisibility(View.VISIBLE);
        binding.recyclerViewExpense.setVisibility(View.GONE);
        binding.owedOverall.setVisibility(View.GONE);
        binding.recyclerViewDebts.setVisibility(View.GONE);
    }
    public void showPopup(View v) {
        PopupMenu popup = new PopupMenu(requireActivity(), v);
        popup.inflate(R.menu.toopbar_menu);
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.nav_delete)
                {



                    return true;
                }
                return false;
            }
        });
        setForceShowIcon(popup);
        popup.show();
    }
    public static void setForceShowIcon(PopupMenu popupMenu) {
        try {
            Field[] fields = popupMenu.getClass().getDeclaredFields();
            for (Field field : fields) {
                if ("mPopup".equals(field.getName())) {
                    field.setAccessible(true);
                    Object menuPopupHelper = field.get(popupMenu);
                    Class<?> classPopupHelper = Class.forName(menuPopupHelper
                            .getClass().getName());
                    Method setForceIcons = classPopupHelper.getMethod(
                            "setForceShowIcon", boolean.class);
                    setForceIcons.invoke(menuPopupHelper, true);
                    break;
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }


}