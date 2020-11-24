package com.example.daraltebassistant.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.daraltebassistant.R;
import com.example.daraltebassistant.databinding.ActivityAppointmentDetailBinding;
import com.example.daraltebassistant.util.Common;
import com.example.daraltebassistant.viewModel.AppointmentDetailViewModel;
import com.suke.widget.SwitchButton;

public class AppointmentDetailActivity extends AppCompatActivity {
    private AppointmentDetailViewModel viewModel;
    private ActivityAppointmentDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(AppointmentDetailActivity.this).get(AppointmentDetailViewModel.class);
        binding = DataBindingUtil.setContentView(AppointmentDetailActivity.this, R.layout.activity_appointment_detail);
        binding.setLifecycleOwner(AppointmentDetailActivity.this);
        binding.setAppointmentDetail(viewModel);
        viewModel.observViewModel();
        observViewModel();
        Log.d("tag", Common.currentAppointment.user_id + "  " + Common.currentAppointment.id);
        binding.appointmentDetailSwitch.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                if (isChecked) {
                    viewModel.updateStatus("1");
                    viewModel.status.setValue(true);
                    Toast.makeText(AppointmentDetailActivity.this, "appointment is active", Toast.LENGTH_SHORT).show();
                } else if (!isChecked) {
                    viewModel.updateStatus("0");
                    viewModel.status.setValue(false);
                    Toast.makeText(AppointmentDetailActivity.this, "appointment is cancelled", Toast.LENGTH_SHORT).show();
                }
            }
        });
        binding.appointmentDetailCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("tag", Common.currentAppointment.user_id + "  " + Common.currentAppointment.id);
                viewModel.cancelAppointment();
                Toast.makeText(getApplicationContext(), viewModel.message.getValue(), Toast.LENGTH_SHORT).show();
                observViewModel();
            }
        });
        binding.appointmentDetailEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
startActivity(new Intent(getApplicationContext(),EditAppointmentActivity.class));
            }
        });
    }

    private void observViewModel() {
        viewModel.status.observe(AppointmentDetailActivity.this, isChecked -> {
            if (isChecked) {
                binding.appointmentDetailSwitch.setChecked(true);

            } else
                binding.appointmentDetailSwitch.setChecked(false);
        });
        viewModel.respopnce.observe(AppointmentDetailActivity.this, isChecked -> {
            if (isChecked) {
                Toast.makeText(getApplicationContext(), viewModel.message.getValue(), Toast.LENGTH_SHORT).show();
                finish();

            }
        });
    }
}
