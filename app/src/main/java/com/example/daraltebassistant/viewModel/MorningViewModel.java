package com.example.daraltebassistant.viewModel;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.daraltebassistant.model.Clinicinfo;
import com.example.daraltebassistant.model.Slots;
import com.example.daraltebassistant.model.Time;
import com.example.daraltebassistant.model.TimeSlot;
import com.example.daraltebassistant.retrofit.RestFullApi;
import com.example.daraltebassistant.ui.MainActivity;
import com.example.daraltebassistant.util.Common;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MorningViewModel extends AndroidViewModel {
    public MorningViewModel(@NonNull Application application) {
        super(application);
    }
    private RestFullApi restFullApi = Common.getApi();
    private CompositeDisposable disposable = new CompositeDisposable();
    public MutableLiveData<String> time=new MutableLiveData<>();
    public MutableLiveData<List<Time>> times = new MutableLiveData<List<Time>>();

    public void fetchFromRemote(){

        disposable.add(restFullApi.get_time_slot(Common.currentClinic.bus_id,Common.currentDate)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .onErrorResumeNext(new ObservableSource<TimeSlot>() {
                    @Override
                    public void subscribe(Observer<? super TimeSlot> observer) {
                        Log.e("tag",observer.toString());
                    }
                })
                .subscribe(timeSlot -> {
                    if (timeSlot != null) {
                        Log.e("tag",timeSlot.data+"");
                        times.setValue(timeSlot.data.morning);
                    }


                }, throwable -> {
                    // showToast(throwable.getMessage());
                    Log.e("error", "error : " + throwable.getMessage());
                }))
               ;

    }

}
