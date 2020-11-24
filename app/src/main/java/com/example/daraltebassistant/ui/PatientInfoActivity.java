package com.example.daraltebassistant.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.daraltebassistant.R;
import com.example.daraltebassistant.databinding.ActivityPatientInfoBinding;
import com.example.daraltebassistant.util.Common;
import com.example.daraltebassistant.viewModel.AddAppointmentViewModel;
import com.example.daraltebassistant.viewModel.PatientInfoViewModel;

public class PatientInfoActivity extends AppCompatActivity {
    ActivityPatientInfoBinding binding;
    PatientInfoViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(PatientInfoActivity.this).get(PatientInfoViewModel.class);
        binding = DataBindingUtil.setContentView(PatientInfoActivity.this, R.layout.activity_patient_info);
        binding.setLifecycleOwner(PatientInfoActivity.this);
        binding.setInfo(viewModel);
        binding.patientInfoName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                viewModel.user_fullName.setValue(s.toString());
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                viewModel.user_fullName.setValue(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                viewModel.user_fullName.setValue(s.toString());
               Log.d("tag",viewModel.user_fullName.getValue());
            }
        });
        binding.patientInfoPhone.addTextChangedListener(new TextWatcher() {
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
        binding.patientInfoEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                viewModel.user_email.setValue(s.toString());
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length()==0){
                    viewModel.user_email.setValue("empty");
                }
                else
                viewModel.user_email.setValue(s.toString());
                //Log.d("tag",viewModel.user_fullName.getValue());
            }
        });
        binding.patientInfoDate.setText("Date : " + Common.currentDate);
        binding.patientInfoStartTime.setText("Time : " + Common.currentTimeSlot.slot);
        binding.patientInfoTimeToken.setText("Time Token : " + Common.currentTimeSlot.time_token);

        binding.patientInfoConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.confirmAppointment();
                Toast.makeText(getApplicationContext(),viewModel.message.getValue()+"",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();

            }
        });

    }
}
