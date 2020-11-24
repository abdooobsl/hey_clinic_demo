package com.example.daraltebassistant.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.daraltebassistant.R;
import com.example.daraltebassistant.adapter.ClinicsAdapter;
import com.example.daraltebassistant.databinding.FragmentHomeBinding;
import com.example.daraltebassistant.model.Clinicinfo;

import com.example.daraltebassistant.ui.ClinicAppointmentActivity;
import com.example.daraltebassistant.util.Common;
import com.example.daraltebassistant.viewModel.HomeViewModel;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private HomeViewModel viewModel;
    private FragmentHomeBinding binding;
    private ClinicsAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        viewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        // binding.setLifecycleOwner(HomeFragment.this);
        binding.setHome(viewModel);
        viewModel.refresh();
        adapter = new ClinicsAdapter(new ArrayList<Clinicinfo>(), getContext());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.swipeClinics.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                viewModel.refresh();
                binding.swipeClinics.setRefreshing(false);
            }
        });
        binding.rvClinics.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvClinics.setAdapter(adapter);
        observeViewModel();
        adapter.setOnItemClickListener(new ClinicsAdapter.onItemClickListener() {
            @Override
            public void onItemClick(Clinicinfo item) {
                Common.currentClinic=item;
                startActivity(new Intent(getContext(), ClinicAppointmentActivity.class));
                Log.d("tag",Common.currentClinic.bus_id);
            }
        });
    }

    private void observeViewModel() {
        viewModel.clinicinfo.observe(getViewLifecycleOwner(), clinicList -> {
            if (clinicList != null) {
                binding.rvClinics.setVisibility(View.VISIBLE);
                adapter.updateList(clinicList);
            }
        });
        viewModel.clinicLoadError.observe(getViewLifecycleOwner(), isError ->
        {
            if (isError != null && isError instanceof Boolean) {
                binding.errorClinics.setVisibility(isError ? View.VISIBLE : View.GONE);
            }
        });
        viewModel.loading.observe(getViewLifecycleOwner(), isLoading -> {
                    if (isLoading != null && isLoading instanceof Boolean) {
                        binding.loadingClinics.setVisibility(isLoading ? View.VISIBLE : View.GONE);
                        if (isLoading) {
                            binding.errorClinics.setVisibility(View.GONE);
                            binding.rvClinics.setVisibility(View.GONE);
                        }
                    }
                }
        );
    }
}
