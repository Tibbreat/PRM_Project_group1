package com.example.order_food.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.order_food.R;
import com.example.order_food.adminFragment.EditAccoutFragment;
import com.example.order_food.db.entity.User;
import com.example.order_food.service.UserService;

import java.util.List;

public class AccountListAdapter extends RecyclerView.Adapter<AccountListAdapter.AccountViewHolder> {
    private List<User> accountList;
    private Context context;

    public AccountListAdapter(Context context, List<User> accountList) {
        this.context = context;
        this.accountList = accountList;
    }

    @NonNull
    @Override
    public AccountViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.view_list_account_manage, parent, false);
        return new AccountViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AccountViewHolder holder, int position) {
        User user = accountList.get(position);

        holder.accountNameTextView.setText("Name: " + user.getName());
        holder.accountEmailTextView.setText("Email: " + user.getEmail());

        holder.btnDelete.setOnClickListener(v -> {
            boolean isDeleted = UserService.getInstance(context).deleteUser(user);
            if (isDeleted) {
                accountList.remove(position);
                notifyItemRemoved(position);
            }
        });

        holder.btnEdit.setOnClickListener(v -> {
            EditAccoutFragment editAccoutFragment = new EditAccoutFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("userId", user.getId());
            editAccoutFragment.setArguments(bundle);

            ((AppCompatActivity) context).getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainerView2, editAccoutFragment)
                    .addToBackStack(null)
                    .commit();
        });
    }


    @Override
    public int getItemCount() {
        return accountList.size();
    }

    static class AccountViewHolder extends RecyclerView.ViewHolder {
        TextView accountNameTextView;
        TextView accountEmailTextView;

        ImageButton btnDelete;
        ImageButton btnEdit;

        AccountViewHolder(View itemView) {
            super(itemView);
            accountNameTextView = itemView.findViewById(R.id.account_name);
            accountEmailTextView = itemView.findViewById(R.id.account_email);
            btnDelete = itemView.findViewById(R.id.btn_account_delete);
            btnEdit = itemView.findViewById(R.id.btn_account_edit);

        }
    }
}


