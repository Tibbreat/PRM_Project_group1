package com.example.order_food.adminFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.order_food.R;
import com.example.order_food.db.entity.User;
import com.example.order_food.service.UserService;

public class EditAccoutFragment extends Fragment {
    private Button btnUpdateAccount;
    private ImageButton btnBack;
    private EditText editAccountName, editAccountEmail, editAccountPhone, editAccountAddress, editAccountPassword;
    private RadioGroup radioGroupRole;
    public EditAccoutFragment() {
    }

    private void initUI(View view) {
        editAccountName = view.findViewById(R.id.edt_account_name_update);
        editAccountEmail = view.findViewById(R.id.edt_account_email_update);
        editAccountPhone = view.findViewById(R.id.edt_account_phone_update);
        editAccountAddress = view.findViewById(R.id.edt_account_address_update);
        editAccountPassword = view.findViewById(R.id.edt_account_password_update);
        radioGroupRole = view.findViewById(R.id.ra_role_update);
        btnUpdateAccount = view.findViewById(R.id.btn_update_account);
        btnBack = view.findViewById(R.id.buttonBackToAccountList);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_account, container, false);
        initUI(view);
        int userId;

        Bundle bundle = getArguments();
        if (bundle != null) {
            userId = bundle.getInt("userId", -1);
        } else {
            userId = -1;
        }


        if (userId == -1) {
            Toast.makeText(requireContext(), "Connect Error!!", Toast.LENGTH_SHORT).show();
        } else {
            UserService userService = UserService.getInstance(requireContext());
            User user = userService.getUserById(userId);

            editAccountName.setText(user.getName());
            editAccountEmail.setText(user.getEmail());
            editAccountPhone.setText(user.getPhone());
            editAccountAddress.setText(user.getAddress());
            editAccountPassword.setText(user.getPassword());

            int selectedRadioButtonId = user.getRole().equals("user") ? R.id.ra_role_user_update : R.id.ra_role_admin_update;
            radioGroupRole.check(selectedRadioButtonId);
        }

        btnUpdateAccount.setOnClickListener(v -> update(userId));

        btnBack.setOnClickListener(v -> replaceFragment(new ViewListAccoutsFragment()));

        return view;
    }
    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainerView2, fragment);
        transaction.commit();
    }

    private void update(int userId) {
        String roleName;
        int selectedRadioButtonId = radioGroupRole.getCheckedRadioButtonId();

        if (selectedRadioButtonId == R.id.ra_role_user_update) {
            roleName = "user";
        } else {
            roleName = "admin";
        }


        UserService userService = UserService.getInstance(requireContext());

        int isUpdated = userService.updateRole(userId, roleName);
        if (isUpdated == 1) {
            Toast.makeText(requireContext(), "Account update successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(requireContext(), "Failed to update account", Toast.LENGTH_SHORT).show();
        }
    }



}
