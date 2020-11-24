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

import com.example.daraltebassistant.R;
import com.example.daraltebassistant.adapter.TimeSlotAdapter;
import com.example.daraltebassistant.databinding.MorningFragmentBinding;
import com.example.daraltebassistant.model.Time;
import com.example.daraltebassistant.ui.PatientInfoActivity;
import com.example.daraltebassistant.ui.ServicesActivity;
import com.example.daraltebassistant.util.Common;
import com.example.daraltebassistant.viewModel.MorningViewModel;
import com.example.daraltebassistant.viewModel.PatientInfoViewModel;

import java.util.ArrayList;

public class MorningFragment extends Fragment {
    MorningFragmentBinding binding;
    private MorningViewModel mViewModel;
    TimeSlotAdapter adapter=new TimeSlotAdapter(new ArrayList<>(),getContext());

    public static MorningFragment newInstance() {
        return new MorningFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.morning_fragment, container, false);
        View view = binding.getRoot();
        binding.setMorning(mViewModel);
        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MorningViewModel.class);
        mViewModel.fetchFromRemote();
        observeViewModel();
        binding.rvMorning.setLayoutManager(new LinearLayoutManager(getContext()));

        binding.rvMorning.setAdapter(adapter);
        adapter.setOnItemClickListener(new TimeSlotAdapter.onItemClickListener() {
            @Override
            public void onItemClick(Time item) {
                Common.currentTimeSlot=item;
                startActivity(new Intent(getContext(), PatientInfoActivity.class));
            }
        });
    }
    private void observeViewModel() {
        mViewModel.times.observe(getViewLifecycleOwner(), timeList -> {
            if (timeList != null) {
                //binding.rvMorning.setVisibility(View.VISIBLE);
               adapter.updateList(timeList);
            }
            else {
                Log.e("error","Rrrr");
            }
        });


    }
}
