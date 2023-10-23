package com.example.order_food.adminFragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;

import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.example.order_food.R;
import com.example.order_food.db.entity.User;
import com.example.order_food.service.UserService;

import java.io.FileOutputStream;
import java.io.InputStream;

public class CreateAccoutFragment extends Fragment {
    private Button btnAddAccount;
    private ImageButton btnBack;
    private EditText editAccountName, editAccountEmail, editAccountPhone, editAccountAddress, editAccountPassword, editAccountRePassword;
    private RadioButton radioUser;
    private RadioButton radioAdmin;
    public CreateAccoutFragment() {
    }

    private void initUI(View view) {
        editAccountName = view.findViewById(R.id.edt_account_name);
        editAccountEmail = view.findViewById(R.id.edt_account_email);
        editAccountPhone = view.findViewById(R.id.edt_account_phone);
        editAccountAddress = view.findViewById(R.id.edt_account_address);
        editAccountPassword = view.findViewById(R.id.edt_account_password);
        editAccountRePassword = view.findViewById(R.id.edt_account_re_password);
        radioUser = view.findViewById(R.id.ra_role_User);
        radioAdmin = view.findViewById(R.id.ra_role_admin);
        btnAddAccount = view.findViewById(R.id.btn_add_account);
        btnBack = view.findViewById(R.id.buttonBackToAccountManagementC);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_account, container, false);
        initUI(view);

        btnAddAccount.setOnClickListener(v -> insert());
        btnBack.setOnClickListener(v -> replaceFragment(new AccountManagementFragment()));

        return view;
    }
    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainerView2, fragment);
        transaction.commit();
    }

    private void insert() {
        String accountName = editAccountName.getText().toString().trim();
        String accountEmail = editAccountEmail.getText().toString().trim();
        String accountPhone = editAccountPhone.getText().toString().trim();
        String accountAddress = editAccountAddress.getText().toString().trim();
        String accountPassword = editAccountPassword.getText().toString().trim();
        String accountRePassword = editAccountRePassword.getText().toString().trim();
        String roleName;
        boolean isUserSelected = radioUser.isChecked();

        if (isUserSelected){
            roleName = "user";
        } else {
            roleName = "admin";
        }

        if (validateInput(accountName, accountEmail, accountPhone, accountAddress, accountPassword, accountRePassword)) {

            User user = new User(accountName, accountEmail, accountPassword, roleName, accountAddress, accountPhone);
            UserService userService = UserService.getInstance(requireContext());

            boolean isInserted = userService.insert(user);
            if (isInserted) {
                clearFields();
                Toast.makeText(requireContext(), "Account added successfully", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(requireContext(), "Failed to add account", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean validateInput(String accountName, String accountEmail, String accountPhone, String accountAddress, String accountPassword, String accountRePassword) {
        if (accountName.isEmpty() || accountEmail.isEmpty() || accountPhone.isEmpty() || accountAddress.isEmpty() || accountPassword.isEmpty() || accountRePassword.isEmpty()) {
            showToast("All fields are required");
            return false;
        }
        if(accountPhone.length() != 10){
            showToast("* The phone number must have exactly 10 digits");
            return false;
        }
        if(!accountRePassword.equals(accountPassword)){
            showToast("The password not matching");
            return false;
        }
        return true;
    }

    private void clearFields() {
        editAccountName.setText("");
        editAccountEmail.setText("");
        editAccountPhone.setText("");
        editAccountAddress.setText("");
        editAccountPassword.setText("");
        editAccountRePassword.setText("");
    }

    private void showToast(String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }

}
