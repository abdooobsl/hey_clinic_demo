package com.example.daraltebassistant.fragment;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.daraltebassistant.R;
import com.example.daraltebassistant.viewModel.TimeSlotViewModel;

public class TimeSlotFragment extends Fragment {

    private TimeSlotViewModel mViewModel;

    public static TimeSlotFragment newInstance() {
        return new TimeSlotFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.time_slot_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(TimeSlotViewModel.class);
        // TODO: Use the ViewModel
    }

}
