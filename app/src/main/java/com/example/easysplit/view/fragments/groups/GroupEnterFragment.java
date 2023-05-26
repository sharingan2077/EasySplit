package com.example.easysplit.view.fragments.groups;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.easysplit.R;
import com.example.easysplit.databinding.FragmentGroupEnterBinding;
import com.example.easysplit.model.DebtInGroup;
import com.example.easysplit.view.adapters.DebtInGroupAdapter;
import com.example.easysplit.view.adapters.ExpenseInGroupRecyclerAdapter;
import com.example.easysplit.view.listeners.CompleteListener2;
import com.example.easysplit.view.utils.NavigationUtils;
import com.example.easysplit.viewModel.groups.GroupEnterViewModel;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

public class GroupEnterFragment extends Fragment {
    private static final String TAG = "GroupEnterFragment";
    FragmentGroupEnterBinding binding;
    GroupEnterViewModel groupEnterViewModel;

    private ExpenseInGroupRecyclerAdapter adapter;
    private DebtInGroupAdapter adapter2;

    private String groupId;
    private String nameOfGroup;
    private int countGroupMembers;
    private int imageDrawable;

    NavController navController;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentGroupEnterBinding.inflate(inflater, container, false);
        navController = Navigation.findNavController(requireActivity(), R.id.navHostFragment);
        showProgressing();

        groupId = getArguments().getString("groupId", "0");
        nameOfGroup = getArguments().getString("nameOfGroup", "0");
        countGroupMembers = getArguments().getInt("countGroupMembers", 0);
        imageDrawable = getArguments().getInt("imageDrawable", 0);

        binding.imageView3.setImageResource(imageDrawable);

        binding.nameOfGroup.setText(nameOfGroup);

        settingTextCountOfMembers(countGroupMembers);

        groupEnterViewModel = new ViewModelProvider(requireActivity()).get(GroupEnterViewModel.class);

        if (countGroupMembers == 1)
        {
            hideFriendsInGroup();
        }
        groupEnterViewModel.init(groupId, data -> {
            if (data.equals("expense"))
            {
                showExpensesInGroup();
                adapter.notifyDataSetChanged();
            }
            else if (data.equals("debt"))
            {
                adapter2.notifyDataSetChanged();
            }
            else if (data.equals("noExpenses") && countGroupMembers > 1)
            {
                Log.d(TAG, "noExpenses");
                hideExpensesInGroup();
            }
        });
        initRecyclerView();
        final Observer<List<DebtInGroup>> observerNewDebtsInGroup = debtInGroup -> {
            Log.d(TAG, "size - " + Integer.toString(debtInGroup.size()));
            adapter.notifyDataSetChanged();
            if (debtInGroup.size() >= 1)
            {
                long totalSum = 0;
                for (DebtInGroup debt : debtInGroup)
                {
                    if (debt.getYouOwn())
                    {
                        totalSum += debt.getSum();
                    }
                    else totalSum -= debt.getSum();
                }
                if (totalSum >= 0)
                {
                    binding.owedOverall.setTextColor(Color.parseColor("#08FFC8"));
                    binding.owedOverall.setText("Всего тебе должны ₽" + Long.toString(totalSum));
                }
                else
                {
                    binding.owedOverall.setTextColor(Color.parseColor("#CB2424"));
                    binding.owedOverall.setText("Всего ты должен ₽" + Long.toString(totalSum * (-1)));
                }
            }
        };
        groupEnterViewModel.getDebtsInGroup().observe(requireActivity(), observerNewDebtsInGroup);

        binding.toolbar.back.setOnClickListener(v -> NavigationUtils.navigateSafe(navController, R.id.action_groupEnterFragment_to_groupsFragment, null));

        binding.toolbar.menu.setOnClickListener(v -> showPopup(binding.toolbar.menu));

        binding.addFriends.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("groupId", groupId);
            bundle.putString("nameOfGroup", nameOfGroup);
            bundle.putInt("countGroupMembers", countGroupMembers);

            Log.d(TAG, "Putting in bundle - " + Integer.toString(imageDrawable));
            bundle.putInt("imageDrawable", imageDrawable);

            NavigationUtils.navigateSafe(navController, R.id.action_groupEnterFragment_to_addFriendToGroupFragment, bundle);
        });

        return binding.getRoot();
    }

    private void initRecyclerView()
    {
        adapter = new ExpenseInGroupRecyclerAdapter(getActivity(), groupEnterViewModel.getExpensesInGroups().getValue());
        binding.recyclerViewExpense.setAdapter(adapter);
        binding.recyclerViewExpense.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter2 = new DebtInGroupAdapter(requireActivity(), groupEnterViewModel.getDebtsInGroup().getValue());
        binding.recyclerViewDebts.setAdapter(adapter2);
        binding.recyclerViewDebts.setLayoutManager(new LinearLayoutManager(getActivity()));

        binding.editNameOfGroup.setOnClickListener(v -> {
            AlertDialog.Builder alert = new AlertDialog.Builder(requireContext());
            final EditText edittext = new EditText(requireContext());
            edittext.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
            alert.setMessage("Введите в поле ниже новое название группы");
            alert.setTitle("Изменить название группы");
            alert.setView(edittext);
            alert.setPositiveButton("Изменить", (dialog, whichButton) -> {
            });

            alert.setNegativeButton("Отмена", (dialog, whichButton) -> {
                return;
            });
            final AlertDialog dialog = alert.create();
            dialog.show();
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v1 -> {
                Boolean wantToCloseDialog = false;
                String newNameOfGroup = edittext.getText().toString();
                String oldNameOfGroup = binding.nameOfGroup.getText().toString();
                if (newNameOfGroup.equals(oldNameOfGroup))
                {
                    Toast.makeText(requireContext(), "Группа уже имеет это название", Toast.LENGTH_SHORT).show();
                }
                else if (newNameOfGroup.equals(""))
                {
                    Toast.makeText(requireContext(), "Введите название группы", Toast.LENGTH_SHORT).show();
                }
                else if (containsWhiteSpace(newNameOfGroup))
                {
                    Toast.makeText(requireContext(), "Название группы не может содержать пробелов", Toast.LENGTH_SHORT).show();
                }
                else if (newNameOfGroup.length() > 20)
                {
                    Toast.makeText(requireContext(), "Длина названия не должна превышать 15 символов", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    wantToCloseDialog = true;
                }
                if(wantToCloseDialog)
                {
                    groupEnterViewModel.changeNameOfGroup(newNameOfGroup, groupId);
                    binding.nameOfGroup.setText(newNameOfGroup);
                    dialog.dismiss();
                }
            });
        });
    }

    private void settingTextCountOfMembers(int countGroupMembers)
    {
        if (countGroupMembers == 1)
        {
            hideFriendsInGroup();
            binding.countGroupMembers.setText("1 участник");
        }
        else
        {
            binding.countGroupMembers.setText(Integer.toString(countGroupMembers) + " участника");
        }
    }

    private void showProgressing()
    {
        binding.txtNoExpenses.setVisibility(View.GONE);
        binding.txtNoExpenses2.setVisibility(View.GONE);
        binding.arrow.setVisibility(View.GONE);
        binding.txtNoFriends.setVisibility(View.GONE);
        binding.addFriends.setVisibility(View.GONE);
        binding.recyclerViewExpense.setVisibility(View.GONE);
        binding.owedOverall.setVisibility(View.GONE);
        binding.recyclerViewDebts.setVisibility(View.GONE);
        binding.progressBar.setVisibility(View.VISIBLE);
    }


    private void showExpensesInGroup() {
        binding.txtNoFriends.setVisibility(View.GONE);
        binding.addFriends.setVisibility(View.GONE);
        binding.recyclerViewExpense.setVisibility(View.VISIBLE);
        binding.owedOverall.setVisibility(View.VISIBLE);
        binding.recyclerViewDebts.setVisibility(View.VISIBLE);
        binding.txtNoExpenses.setVisibility(View.GONE);
        binding.txtNoExpenses2.setVisibility(View.GONE);
        binding.arrow.setVisibility(View.GONE);
        binding.progressBar.setVisibility(View.GONE);

    }
    private void hideFriendsInGroup() {
        binding.txtNoExpenses.setVisibility(View.GONE);
        binding.txtNoExpenses2.setVisibility(View.GONE);
        binding.arrow.setVisibility(View.GONE);
        binding.txtNoFriends.setVisibility(View.VISIBLE);
        binding.addFriends.setVisibility(View.VISIBLE);
        binding.recyclerViewExpense.setVisibility(View.GONE);
        binding.owedOverall.setVisibility(View.GONE);
        binding.recyclerViewDebts.setVisibility(View.GONE);
        binding.progressBar.setVisibility(View.GONE);
    }

    private void hideExpensesInGroup() {
        binding.txtNoExpenses.setVisibility(View.VISIBLE);
        binding.txtNoExpenses2.setVisibility(View.VISIBLE);
        binding.arrow.setVisibility(View.VISIBLE);
        binding.txtNoFriends.setVisibility(View.GONE);
        binding.addFriends.setVisibility(View.GONE);
        binding.recyclerViewExpense.setVisibility(View.GONE);
        binding.owedOverall.setVisibility(View.GONE);
        binding.recyclerViewDebts.setVisibility(View.GONE);
        binding.progressBar.setVisibility(View.GONE);
    }

    public void showPopup(View v) {
        PopupMenu popup = new PopupMenu(requireActivity(), v);
        popup.inflate(R.menu.toopbar_menu);
        popup.setOnMenuItemClickListener(item -> {

            if (item.getItemId() == R.id.nav_add)
            {
                Bundle bundle = new Bundle();
                bundle.putString("groupId", groupId);
                bundle.putString("nameOfGroup", nameOfGroup);
                bundle.putInt("countGroupMembers", countGroupMembers);
                bundle.putInt("imageDrawable", imageDrawable);
                NavigationUtils.navigateSafe(navController, R.id.action_groupEnterFragment_to_addFriendToGroupFragment, bundle);
                return true;
            }

            else if (item.getItemId() == R.id.nav_leave)
            {
                showLeaveGroupDialog();
                return true;
            }
            else if (item.getItemId() == R.id.nav_delete)
            {
                showDeleteGroupDialog();
                return true;
            }
            return false;
        });
        setForceShowIcon(popup);
        popup.show();
    }

    private void showLeaveGroupDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        builder.setTitle("Покинуть группу?");
        builder.setMessage("Вы абсолютно уверены, что хотите покинуть данную группу?");
        builder.setPositiveButton("Покинуть группу", (dialog, which) -> {
            Log.d(TAG, groupId);
            groupEnterViewModel.leaveGroup(groupId);
            NavigationUtils.navigateSafe(navController, R.id.action_groupEnterFragment_to_groupsFragment, null);
        });
        builder.setNegativeButton("Отмена", (dialog, which) -> {
            return;
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void showDeleteGroupDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        builder.setTitle("Удалить группу?");
        builder.setMessage("Вы АБСОЛЮТНО уверены, что хотите удалить группу? Это приведет к удалению группы у всех пользователей, добавленных в неё.");
        builder.setPositiveButton("Ок", (dialog, which) -> {
            Log.d(TAG, groupId);
            groupEnterViewModel.deleteGroup(groupId);
            NavigationUtils.navigateSafe(navController, R.id.action_groupEnterFragment_to_groupsFragment, null);
        });
        builder.setNegativeButton("Отмена", (dialog, which) -> {
        });
        AlertDialog dialog = builder.create();
        dialog.show();
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
    public boolean containsWhiteSpace(final String testCode){
        if(testCode != null){
            for(int i = 0; i < testCode.length(); i++){
                if(Character.isWhitespace(testCode.charAt(i))){
                    return true;
                }
            }
        }
        return false;
    }




}