package com.example.daraltebassistant.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

import com.example.daraltebassistant.R;
import com.example.daraltebassistant.adapter.AppointmentAdapter;
import com.example.daraltebassistant.databinding.ActivityClinicAppointmentBinding;
import com.example.daraltebassistant.model.Appointment;
import com.example.daraltebassistant.util.Common;
import com.example.daraltebassistant.viewModel.ClinicAppointmentViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ClinicAppointmentActivity extends AppCompatActivity {
    private ActivityClinicAppointmentBinding binding;
    private ClinicAppointmentViewModel viewModel;
    private AppointmentAdapter adapter;
    private Date date;
    SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd");
    String from, to;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(ClinicAppointmentActivity.this).get(ClinicAppointmentViewModel.class);
        binding = DataBindingUtil.setContentView(ClinicAppointmentActivity.this, R.layout.activity_clinic_appointment);
        binding.setLifecycleOwner(ClinicAppointmentActivity.this);
        binding.setAppointmentViewModel(viewModel);
        date = Calendar.getInstance().getTime();




        viewModel.refresh();
        binding.clinicAppointmentsFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PopupMenu popupMenu = new PopupMenu(ClinicAppointmentActivity.this, v);
                popupMenu.getMenuInflater().inflate(R.menu.filter_menu,popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.today:
                                from = dateFormat.format(date);
                                Log.d("tag", from);
                                viewModel.fetchFromRemote(from,from);
                            return true;
                            case R.id.yesterday:
                                date.setDate(date.getDate()-1);
                                from = dateFormat.format(date);
                                date = Calendar.getInstance().getTime();
                                Log.d("tag", from);
                                viewModel.fetchFromRemote(from,from);
                                return true;
                            case R.id.tommorow:
                                date.setDate(date.getDate()+1);
                                from = dateFormat.format(date);
                                date = Calendar.getInstance().getTime();
                                viewModel.fetchFromRemote(from,from);
                                Log.d("tag", from);
                                return true;
                            case R.id.last7:
                                date.setDate(date.getDate()-7);
                                from = dateFormat.format(date);
                                date = Calendar.getInstance().getTime();
                                date.setDate(date.getDate()-1);
                                to=dateFormat.format(date);
                                date = Calendar.getInstance().getTime();
                                viewModel.fetchFromRemote(from,to);
                                Log.d("tag", from+"  "+to);
                                return true;
                            case R.id.nex7:
                                date.setDate(date.getDate()+1);
                                from = dateFormat.format(date);
                                date = Calendar.getInstance().getTime();
                                date.setDate(date.getDate()+7);
                                to=dateFormat.format(date);
                                date = Calendar.getInstance().getTime();
                                viewModel.fetchFromRemote(from,to);
                                Log.d("tag", from+"  "+to);
                                return true;
                            case R.id.last30:
                                date.setDate(date.getDate()-30);
                                from = dateFormat.format(date);
                                date = Calendar.getInstance().getTime();
                                date.setDate(date.getDate()-1);
                                to=dateFormat.format(date);
                                date = Calendar.getInstance().getTime();
                                viewModel.fetchFromRemote(from,to);
                                Log.d("tag", from+"  "+to);
                                return true;
                            case R.id.next30:
                                date.setDate(date.getDate()+30);
                                from = dateFormat.format(date);
                                date = Calendar.getInstance().getTime();
                                date.setDate(date.getDate()+1);
                                to=dateFormat.format(date);
                                date = Calendar.getInstance().getTime();
                                viewModel.fetchFromRemote(from,to);
                                Log.d("tag", from);
                                return true;
                            case R.id.clear:
                                viewModel.fetchFromRemote("","");

                                return true;

                        }
                        return true;
                    }
                });
                popupMenu.show();
            }
        });

        binding.swipeAppointment.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                viewModel.refresh();
                binding.swipeAppointment.setRefreshing(false);
            }
        });
        adapter = new AppointmentAdapter(new ArrayList<Appointment>(), ClinicAppointmentActivity.this);
        binding.rvAppointment.setLayoutManager(new LinearLayoutManager(this));
        binding.rvAppointment.setAdapter(adapter);
        observeViewModel();
        adapter.setOnItemClickListener(new AppointmentAdapter.onItemClickListener() {
            @Override
            public void onItemClick(Appointment item) {
                Common.currentAppointment = item;
                startActivity(new Intent(ClinicAppointmentActivity.this, AppointmentDetailActivity.class));
            }
        });
        binding.clinicAppointmentsRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  binding.swipeAppointment.setRefreshing(true);
                viewModel.refresh();

               // binding.swipeAppointment.setRefreshing(false);
            }
        });
        binding.clinicAppointmentsAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ClinicAppointmentActivity.this, AddAppointmentActivity.class));
            }
        });
    }

    private void observeViewModel() {
        viewModel.appointment.observe(this, appointmentList -> {

            if (appointmentList != null) {

                binding.rvAppointment.setVisibility(View.VISIBLE);
                adapter.updateList(appointmentList);
            }
        });
        viewModel.appointmentLoadError.observe(this, isError ->
        {
            if (isError != null && isError instanceof Boolean) {
                binding.appointmentError.setVisibility(isError ? View.VISIBLE : View.GONE);
            }
        });
        viewModel.loading.observe(this, isLoading -> {
                    if (isLoading != null && isLoading instanceof Boolean) {
                        binding.appointmentProgressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
                        if (isLoading) {
                            binding.appointmentError.setVisibility(View.GONE);
                            binding.rvAppointment.setVisibility(View.GONE);
                        }
                    }
                }
        );
    }
}
