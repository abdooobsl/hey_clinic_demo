package com.example.daraltebassistant.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.example.daraltebassistant.R;
import com.example.daraltebassistant.databinding.ActivityEditAppointmentBinding;
import com.example.daraltebassistant.util.Common;
import com.example.daraltebassistant.viewModel.EditAppointmentViewModel;
import com.example.daraltebassistant.viewModel.PatientInfoViewModel;

public class EditAppointmentActivity extends AppCompatActivity {
    ActivityEditAppointmentBinding binding;
    EditAppointmentViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(EditAppointmentActivity.this).get(EditAppointmentViewModel.class);
        binding = DataBindingUtil.setContentView(EditAppointmentActivity.this, R.layout.activity_edit_appointment);
        binding.setLifecycleOwner(EditAppointmentActivity.this);
        binding.setEditAppointment(viewModel);
        binding.editAppointmentName.setText(Common.currentAppointment.app_name);
        binding.editAppointmentEmail.setText(Common.currentAppointment.app_email);
        binding.editAppointmentPhone.setText(Common.currentAppointment.app_phone);
        binding.editAppointmentDate.setText(Common.currentAppointment.appointment_date);
        binding.editAppointmentStartTime.setText(Common.currentAppointment.start_time);
        binding.editAppointmentTimeToken.setText("Time Token : "+Common.currentAppointment.time_token);


        binding.editAppointmentName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                viewModel.user_fullName.setValue(s.toString());
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                viewModel.user_fullName.setValue(s.toString());
                //Log.d("tag",viewModel.user_fullName.getValue());
            }
        });
        binding.editAppointmentEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                viewModel.user_email.setValue(s.toString());
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 0) {
                    viewModel.user_email.setValue("empty");
                } else
                    viewModel.user_email.setValue(s.toString());
                //Log.d("tag",viewModel.user_fullName.getValue());
            }
        });
        binding.editAppointmentPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                viewModel.user_phone.setValue(s.toString());
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                    viewModel.user_phone.setValue(s.toString());
                //Log.d("tag",viewModel.user_fullName.getValue());
            }
        });
        binding.editAppointmentDate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                viewModel.date.setValue(s.toString());
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                    viewModel.date.setValue(s.toString());
                //Log.d("tag",viewModel.user_fullName.getValue());
            }
        });
        binding.editAppointmentStartTime.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                viewModel.startTime.setValue(s.toString());
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                    viewModel.startTime.setValue(s.toString());
                //Log.d("tag",viewModel.user_fullName.getValue());
            }
        });
        binding.editAppointmentStartTime.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                viewModel.timeToken.setValue(s.toString());
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                    viewModel.timeToken.setValue(s.toString());
                //Log.d("tag",viewModel.user_fullName.getValue());
            }
        });

        binding.editAppointmentConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }
}
