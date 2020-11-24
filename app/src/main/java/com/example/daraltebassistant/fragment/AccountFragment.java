package com.example.daraltebassistant.fragment;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.daraltebassistant.R;
import com.example.daraltebassistant.databinding.FragmentAccountBinding;
import com.example.daraltebassistant.ui.LoginActivity;
import com.example.daraltebassistant.viewModel.AccountViewModel;

public class AccountFragment extends Fragment {

    private AccountViewModel viewModel;
    private FragmentAccountBinding binding;
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    public static AccountFragment newInstance() {
        return new AccountFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_account, container, false);
        pref = getContext().getSharedPreferences("UserData", 0);
        editor= pref.edit();
        binding.accountLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              editor.clear();
              editor.apply();
              startActivity(new Intent(getContext(), LoginActivity.class));

            }
        });
    return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(AccountViewModel.class);
        // TODO: Use the ViewModel
    }

}
