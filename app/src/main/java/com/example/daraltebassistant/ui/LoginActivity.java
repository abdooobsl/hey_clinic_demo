package com.example.daraltebassistant.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.example.daraltebassistant.R;
import com.example.daraltebassistant.databinding.ActivityLoginBinding;
import com.example.daraltebassistant.util.Common;
import com.example.daraltebassistant.viewModel.LoginViewModel;

public class LoginActivity extends AppCompatActivity {
    private LoginViewModel viewModel;

    private ActivityLoginBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(LoginViewModel.class);

        binding = DataBindingUtil.setContentView(LoginActivity.this, R.layout.activity_login);

        binding.setLifecycleOwner(this);

        binding.setLogin(viewModel);

        SharedPreferences preferences = getApplicationContext().getSharedPreferences("UserData", 0);
        SharedPreferences.Editor editor=preferences.edit();
        if (preferences.getBoolean("rememberMe", false))
        {
            binding.loginEmail.setText(preferences.getString("user_email", ""));
            binding.loginPassword.setText(preferences.getString("user_password", ""));
            binding.loginCheckBox.setChecked(true);
        }


        binding.loginCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                editor.putBoolean("rememberMe",isChecked);
                editor.apply();
            }
        });

        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(binding.loginEmail.getText())) {
                    binding.loginEmail.setError("Enter an E-Mail Address");
                    binding.loginEmail.requestFocus();
                }
                else if (TextUtils.isEmpty(binding.loginPassword.getText())) {
                    binding.loginPassword.setError("Enter a Password");
                    binding.loginPassword.requestFocus();

                }
                else if (Patterns.EMAIL_ADDRESS.matcher(binding.loginEmail.getText()).matches()) {
                    editor.putString("user_password",binding.loginPassword.getText().toString());
                    editor.apply();
                    viewModel.fetchFromRemote(binding.loginEmail.getText().toString(), binding.loginPassword.getText().toString());

                }
                else {
                    binding.loginEmail.setError("Enter a Valid E-mail Address");
                    binding.loginEmail.requestFocus();
                }

            }
        });
        observeViewModel();


    }

    private void observeViewModel() {
        viewModel.logged.observe(LoginActivity.this, isLogged -> {
            if (isLogged != null && isLogged) {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
            }
            else
                Toast.makeText(getApplicationContext(),"wrong username or password",Toast.LENGTH_SHORT).show();
                    });
    }
}
