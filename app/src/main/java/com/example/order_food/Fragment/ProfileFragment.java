package com.example.order_food.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.order_food.ChangePasswordActivity;
import com.example.order_food.LoginActivity;
import com.example.order_food.MainActivity;
import com.example.order_food.R;
import com.example.order_food.RegisterActivity;
import com.example.order_food.service.UserService;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {
    SharedPreferences shared_pref;
    SharedPreferences.Editor editor;

    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        shared_pref = getActivity().getSharedPreferences("account", getActivity().MODE_PRIVATE);
        editor = shared_pref.edit();

        EditText txt_name = view.findViewById(R.id.txt_profile_name);
        EditText txt_address = view.findViewById(R.id.txt_profile_address);
        EditText txt_email = view.findViewById(R.id.txt_profile_email);
        EditText txt_phone = view.findViewById(R.id.txt_profile_phone);

        String email = shared_pref.getString("email","");
        String address = shared_pref.getString("address","");
        String name = shared_pref.getString("name","");
        String phone = shared_pref.getString("phone_number","");

        txt_name.setText(name);
        txt_address.setText(address);
        txt_email.setText(email);
        txt_phone.setText(phone);


        ((Button)view.findViewById(R.id.btn_logout)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor = shared_pref.edit();
                editor.remove("email");
                editor.remove("password");
                editor.commit();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });

        ((Button)view.findViewById(R.id.change_password)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ChangePasswordActivity.class);
                startActivity(intent);
            }
        });

        ((Button)view.findViewById(R.id.txt_profile_btn_save)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor = shared_pref.edit();
                String newName = txt_name.getText().toString();
                String newAddress = txt_address.getText().toString();
                String newPhone = txt_phone.getText().toString();
                editor.putString("address",newAddress);
                editor.putString("phone_number",newPhone);
                editor.putString("name",newName);
                UserService userService = UserService.getInstance(getContext());
                int rowEffect = userService.updateUserProfile(email,newName,newAddress,newPhone);
                editor.commit();
                if(rowEffect == 1){
                    Toast.makeText(getContext(), "Change profile successfully!", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(getContext(), "Change profile fail!", Toast.LENGTH_LONG).show();
                }
            }
        });
        return view;
    }

}