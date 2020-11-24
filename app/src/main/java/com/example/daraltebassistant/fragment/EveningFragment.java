package com.example.daraltebassistant.fragment;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.daraltebassistant.R;
import com.example.daraltebassistant.adapter.TimeSlotAdapter;
import com.example.daraltebassistant.databinding.EveningFragmentBinding;
import com.example.daraltebassistant.databinding.MorningFragmentBinding;
import com.example.daraltebassistant.model.Time;
import com.example.daraltebassistant.ui.PatientInfoActivity;
import com.example.daraltebassistant.ui.ServicesActivity;
import com.example.daraltebassistant.util.Common;
import com.example.daraltebassistant.viewModel.EveningViewModel;
import com.example.daraltebassistant.viewModel.MorningViewModel;

import java.util.ArrayList;

public class EveningFragment extends Fragment {
    EveningFragmentBinding binding;
    TimeSlotAdapter adapter=new TimeSlotAdapter(new ArrayList<>(),getContext());
    private EveningViewModel mViewModel;

    public static EveningFragment newInstance() {
        return new EveningFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.evening_fragment, container, false);
        View view = binding.getRoot();
        binding.setEvening(mViewModel);
        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(EveningViewModel.class);
        mViewModel.fetchFromRemote();
        observeViewModel();
        binding.rvEvening.setLayoutManager(new LinearLayoutManager(getContext()));

        binding.rvEvening.setAdapter(adapter);
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
