package com.example.easysplit.view.fragments.profile;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

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
import com.example.easysplit.viewModel.mainActivity.MainActivityViewModel;
import com.example.easysplit.viewModel.profile.ProfileViewModel;
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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
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
        binding.leaveAccount.setOnClickListener(v -> {

            NavigationUtils.navigateSafe(navController, R.id.action_profileFragment_to_loginFragment, null);
            loginRegisterViewModel.logOut();
        });

        binding.changeImage.setOnClickListener(v -> profileViewModel.changeImageUser(new CompleteListener() {
            @Override
            public void successful() {
                profileViewModel.refreshUserImage();
                mainActivityViewModel.setUserImage();
            }

            @Override
            public void unSuccessful() {

            }
        }));

        binding.changeUserName.setOnClickListener(v -> {
            AlertDialog.Builder alert = new AlertDialog.Builder(requireContext());
            final EditText edittext = new EditText(requireContext());
            edittext.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
            alert.setMessage("Введите в поле ниже свой новый никнейм (никнейм не должен содержать пробелов)");
            alert.setTitle("Изменить никнейм");
            alert.setView(edittext);
            alert.setPositiveButton("Изменить", (dialog, whichButton) -> {
            });

            alert.setNegativeButton("Отмена", (dialog, whichButton) -> {
            });
            final AlertDialog dialog = alert.create();
            dialog.show();
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v1 -> {
                boolean wantToCloseDialog = false;
                String nickname = edittext.getText().toString();
                String oldNickName = binding.userNameAndId.getText().toString();
                int indexEndName = oldNickName.indexOf("#");
                String id = "#" + oldNickName.substring(indexEndName + 1);
                oldNickName = oldNickName.substring(0, indexEndName);
                if (nickname.equals(oldNickName))
                {
                    Toast.makeText(requireContext(), "У вас уже есть этот никнейм", Toast.LENGTH_SHORT).show();
                }
                else if (nickname.contains("#"))
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
            });

        });

        final Observer<String> observerUserEmail = s -> {
            Log.d(TAG, "email is Changed! " + s);
            binding.userEmail.setText(s);
        };
        profileViewModel.getUserEmail().observe(getViewLifecycleOwner(), observerUserEmail);

        final Observer<String> observerUserNameAndId = s -> {
            Log.d(TAG, "userNameAndId is Changed! " + s);
            binding.userNameAndId.setText(s);
        };
        profileViewModel.getUserNameAndId().observe(getViewLifecycleOwner(), observerUserNameAndId);

        final Observer<String> observerUserImage = s -> {
            FriendsImages friendsImages = new FriendsImages();
            int drawableId = friendsImages.getImageFriends().get(Integer.parseInt(s));
            binding.imageView2.setImageResource(drawableId);
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