package com.example.daraltebassistant.viewModel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.daraltebassistant.model.Appointment;
import com.example.daraltebassistant.model.AppointmentData;
import com.example.daraltebassistant.retrofit.RestFullApi;
import com.example.daraltebassistant.util.Common;

import java.util.Collections;
import java.util.List;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ClinicAppointmentViewModel extends AndroidViewModel {
    private RestFullApi restFullApi = Common.getApi();
    private CompositeDisposable disposable = new CompositeDisposable();
    public MutableLiveData<List<Appointment>> appointment = new MutableLiveData<List<Appointment>>();
    public MutableLiveData<Boolean> appointmentLoadError = new MutableLiveData<Boolean>();
    public MutableLiveData<Boolean> loading = new MutableLiveData<Boolean>();

    public ClinicAppointmentViewModel(@NonNull Application application) {
        super(application);
    }


    public void refresh() {
        fetchFromRemote("","");
    }


    public void fetchFromRemote(String from,String to) {
        appointmentLoadError.setValue(false);
        loading.setValue(true);
        disposable.add(restFullApi.get_appointments(Common.currentClinic.bus_id,from,to)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .onErrorResumeNext(new ObservableSource<AppointmentData>() {
                    @Override
                    public void subscribe(Observer<? super AppointmentData> observer) {
                        Log.d("tagError",observer+"");
                        appointmentLoadError.setValue(true);
                        loading.setValue(false);
                    }
                })
                .subscribe(new Consumer<AppointmentData>() {
                    @Override
                    public void accept(AppointmentData appointmentData) throws Exception {
                        Log.d("tag",  appointmentData.data+"");
                        appointment.setValue(appointmentData.data);
                        appointmentLoadError.setValue(false);
                        loading.setValue(false);
                    }
                })
        );
    }
}
