package com.example.easysplit.view.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.easysplit.databinding.FragmentProfileBinding;
import com.example.easysplit.model.FriendsImages;
import com.example.easysplit.view.listeners.CompleteListener;
import com.example.easysplit.view.utils.NavigationUtils;
import com.example.easysplit.R;
import com.example.easysplit.viewModel.MainActivityViewModel;
import com.example.easysplit.viewModel.ProfileViewModel;
import com.example.easysplit.viewModel.authentication.LoginRegisterViewModel;

public class ProfileFragment extends Fragment {
    private static final String TAG = "ProfileFragment";

    MainActivityViewModel mainActivityViewModel;
    FragmentProfileBinding binding;
    LoginRegisterViewModel loginRegisterViewModel;

    ProfileViewModel profileViewModel;

    NavController navController;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("MyTag", "ProfileFragment open!");
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        navController = Navigation.findNavController(requireActivity(), R.id.navHostFragment);
        mainActivityViewModel = new ViewModelProvider(requireActivity()).get(MainActivityViewModel.class);
        loginRegisterViewModel = new ViewModelProvider(requireActivity()).get(LoginRegisterViewModel.class);
        profileViewModel = new ViewModelProvider(requireActivity()).get(ProfileViewModel.class);
        profileViewModel.init(getContext());

        final Observer<Boolean> isGoToExpenseObserver = aBoolean -> {
            Bundle bundle = new Bundle();
            bundle.putInt("ActionToLastFragment", R.id.action_addExpenseFragment_to_profileFragment);
            if (aBoolean) NavigationUtils.navigateSafe(navController, R.id.action_profileFragment_to_addExpenseFragment, bundle);
        };
        mainActivityViewModel.getIsGoToMakeExpense().observe(getViewLifecycleOwner(), isGoToExpenseObserver);
        binding.leaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                NavigationUtils.navigateSafe(navController, R.id.action_profileFragment_to_loginFragment, null);
                loginRegisterViewModel.logOut();
            }
        });

        binding.changeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profileViewModel.changeImageUser(new CompleteListener() {
                    @Override
                    public void successful() {
                        profileViewModel.refreshUserImage();
                        mainActivityViewModel.setUserImage();
                    }

                    @Override
                    public void unSuccessful() {

                    }
                });
                //Toast.makeText(requireContext(), "Изображение профиля успешно обновлено!", Toast.LENGTH_SHORT).show();
            }
        });

        binding.changeUserName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(requireContext());
                final EditText edittext = new EditText(requireContext());
                edittext.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
                alert.setMessage("Введите в поле ниже свой новый никнейм (никнейм не должен содержать пробелов)");
                alert.setTitle("Изменить никнейм");
                alert.setView(edittext);
                alert.setPositiveButton("Изменить", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                });

                alert.setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        return;
                    }
                });
                final AlertDialog dialog = alert.create();
                dialog.show();
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        Boolean wantToCloseDialog = false;
                        String nickname = edittext.getText().toString();
                        String oldNickName = binding.userNameAndId.getText().toString();
                        int indexEndName = oldNickName.indexOf("#");
                        String id = "#" + oldNickName.substring(indexEndName + 1);
                        oldNickName = oldNickName.substring(0, indexEndName);
                        if (nickname.equals(oldNickName))
                        {
                            Toast.makeText(requireContext(), "У вас уже есть этот никнейм", Toast.LENGTH_SHORT).show();
                        }
                        else if (nickname.indexOf("#") != -1)
                        {
                            Toast.makeText(requireContext(), "Никнейм не может содержать символа #", Toast.LENGTH_SHORT).show();
                        }
                        else if (nickname.equals(""))
                        {
                            Toast.makeText(requireContext(), "Введите никнейм", Toast.LENGTH_SHORT).show();
                        }
                        else if (containsWhiteSpace(nickname))
                        {
                            Toast.makeText(requireContext(), "Никнейм не может содержать пробелов", Toast.LENGTH_SHORT).show();
                        }
                        else if (nickname.length() > 10)
                        {
                            Toast.makeText(requireContext(), "Длина никнейма не должна превышать 10 символов", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            wantToCloseDialog = true;
                        }
                        if(wantToCloseDialog)
                        {
                            profileViewModel.changeNickNameUser(nickname, id, new CompleteListener() {
                                @Override
                                public void successful() {
                                    profileViewModel.refreshUserNameAndId();
                                }
                                @Override
                                public void unSuccessful() {

                                }
                            });
                            dialog.dismiss();
                        }
                    }
                });

            }
        });

        final Observer<String> observerUserEmail = new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Log.d(TAG, "email is Changed! " + s);
                binding.userEmail.setText(s);
            }
        };
        profileViewModel.getUserEmail().observe(getViewLifecycleOwner(), observerUserEmail);

        final Observer<String> observerUserNameAndId = new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Log.d(TAG, "userNameAndId is Changed! " + s);
                binding.userNameAndId.setText(s);
            }
        };
        profileViewModel.getUserNameAndId().observe(getViewLifecycleOwner(), observerUserNameAndId);

        final Observer<String> observerUserImage = new Observer<String>() {
            @Override
            public void onChanged(String s) {
                FriendsImages friendsImages = new FriendsImages();
                int drawableId = friendsImages.getImageFriends().get(Integer.valueOf(s));
                binding.imageView2.setImageResource(drawableId);
            }
        };
        profileViewModel.getUserImage().observe(getViewLifecycleOwner(), observerUserImage);

        return binding.getRoot();
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