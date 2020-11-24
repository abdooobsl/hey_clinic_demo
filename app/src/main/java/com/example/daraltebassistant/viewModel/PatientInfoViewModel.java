package com.example.daraltebassistant.viewModel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.daraltebassistant.model.Confirmation;
import com.example.daraltebassistant.retrofit.RestFullApi;
import com.example.daraltebassistant.util.Common;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class PatientInfoViewModel extends AndroidViewModel {
    public PatientInfoViewModel(@NonNull Application application) {
        super(application);
    }
    private RestFullApi restFullApi = Common.getApi();
    private CompositeDisposable disposable = new CompositeDisposable();
    public MutableLiveData<String> user_fullName=new MutableLiveData<>();
    public MutableLiveData<String> user_phone=new MutableLiveData<>();
    public MutableLiveData<String> user_email=new MutableLiveData<>();
    public MutableLiveData<Boolean> message=new MutableLiveData<>();
    public MutableLiveData<String> b_id=new MutableLiveData<>();
    public MutableLiveData<String> date=new MutableLiveData<>();
    public MutableLiveData<String> startTime=new MutableLiveData<>();
    public MutableLiveData<String> timeToken=new MutableLiveData<>();
    public MutableLiveData<String> services=new MutableLiveData<>();

    public void confirmAppointment(){
        Log.d("tag", user_fullName.getValue());
        disposable.add(restFullApi.add_appointment(Common.currentUser.user_id, user_fullName.getValue(),user_email.getValue(),user_phone.getValue(),Common.currentDate,
                Common.currentTimeSlot.slot,Common.currentTimeSlot.time_token,Common.currentClinic.bus_id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .onErrorResumeNext(new ObservableSource<Confirmation>() {
                    @Override
                    public void subscribe(Observer<? super Confirmation> observer) {

                    }
                })
                .subscribe(new Consumer<Confirmation>() {
                    @Override
                    public void accept(Confirmation confirmation) throws Exception {
                        message.setValue(confirmation.responce);
                       Log.d("tag", confirmation.error+" "+confirmation.responce);
                    }
                })
        );

    }
}
