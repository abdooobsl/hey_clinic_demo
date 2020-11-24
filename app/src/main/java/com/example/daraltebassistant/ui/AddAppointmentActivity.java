package com.example.daraltebassistant.ui;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.example.daraltebassistant.R;
import com.example.daraltebassistant.adapter.TimeSlotViewPager;
import com.example.daraltebassistant.databinding.ActivityAddAppointmentBinding;
import com.example.daraltebassistant.util.Common;
import com.example.daraltebassistant.viewModel.AddAppointmentViewModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class AddAppointmentActivity extends AppCompatActivity {
    ActivityAddAppointmentBinding binding;
    AddAppointmentViewModel viewModel;
    Calendar calender;
    private int c_day;
    private int c_month;
    private int c_year;
    SimpleDateFormat df, df2;
    String currentdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(AddAppointmentActivity.this).get(AddAppointmentViewModel.class);
        binding = DataBindingUtil.setContentView(AddAppointmentActivity.this, R.layout.activity_add_appointment);
        binding.setLifecycleOwner(AddAppointmentActivity.this);
        binding.setAddAppointment(viewModel);
        calender = Calendar.getInstance(TimeZone.getDefault());
        c_day = calender.get(Calendar.DAY_OF_MONTH);
        c_month = calender.get(Calendar.MONTH);
        c_year = calender.get(Calendar.YEAR);
        df = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        df2 = new SimpleDateFormat("HH:mm aa", Locale.ENGLISH);
        currentdate = c_year + "-" + (c_month + 1) + "-" + c_day;
        binding.addAppointmentButtonChooseDate.setText(currentdate);
        binding.addAppointmentButtonChooseDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateDialog();

            }
        });
        TimeSlotViewPager adapter = new TimeSlotViewPager(getSupportFragmentManager());
        binding.addAppointmentViewPager.setAdapter(adapter);
        binding.addAppointmentTabLayout.setupWithViewPager(binding.addAppointmentViewPager);
    }


    public void DateDialog() {

        DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                currentdate = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                //txtDate.setText(currentdate);
                c_year = year;
                c_day = dayOfMonth;
                c_month = monthOfYear + 1;
                Common.currentDate=currentdate;
                binding.addAppointmentButtonChooseDate.setText(currentdate);
                TimeSlotViewPager adapter = new TimeSlotViewPager(getSupportFragmentManager());
                binding.addAppointmentViewPager.setAdapter(adapter);
                binding.addAppointmentTabLayout.setupWithViewPager(binding.addAppointmentViewPager);
            }
        };

        DatePickerDialog dpDialog = new DatePickerDialog(this, listener, c_year, c_month, c_day);
        Date date = new Date();
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH, +30);
        dpDialog.getDatePicker().setMinDate(date.getTime());
        dpDialog.getDatePicker().setMaxDate(c.getTimeInMillis());
        dpDialog.show();

    }
}
