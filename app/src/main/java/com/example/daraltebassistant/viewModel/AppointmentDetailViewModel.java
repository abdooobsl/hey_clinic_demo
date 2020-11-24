package com.example.daraltebassistant.viewModel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.daraltebassistant.model.CancelAppointment;
import com.example.daraltebassistant.model.Message;
import com.example.daraltebassistant.model.TimeSlot;
import com.example.daraltebassistant.retrofit.RestFullApi;
import com.example.daraltebassistant.util.Common;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class AppointmentDetailViewModel extends AndroidViewModel {

    private RestFullApi restFullApi = Common.getApi();
    private CompositeDisposable disposable = new CompositeDisposable();
    public MutableLiveData<String> name = new MutableLiveData<>();
    public MutableLiveData<String> phone = new MutableLiveData<>();
    public MutableLiveData<String> email = new MutableLiveData<>();
    public MutableLiveData<String> appointment = new MutableLiveData<>();
    public MutableLiveData<String> time = new MutableLiveData<>();
    public MutableLiveData<String> createdAt = new MutableLiveData<>();
    public MutableLiveData<Boolean> status = new MutableLiveData<>();
    public MutableLiveData<String> statusText = new MutableLiveData<>();
    public MutableLiveData<Boolean> respopnce = new MutableLiveData<>();
    public MutableLiveData<String> message = new MutableLiveData<>();
    public AppointmentDetailViewModel(@NonNull Application application) {
        super(application);
    }

    public void observViewModel() {

        name.setValue(Common.currentAppointment.app_name);
        email.setValue(Common.currentAppointment.app_email);
        phone.setValue(Common.currentAppointment.app_phone);
        appointment.setValue(Common.currentAppointment.appointment_date);
        time.setValue(Common.currentAppointment.start_time);
        createdAt.setValue(Common.currentAppointment.created_at);
        if (Common.currentAppointment.status.equals("0")) {
            status.setValue(false);

        } else if (Common.currentAppointment.status.equals("1")) {
            status.setValue(true);

        }

    }

    public void cancelAppointment() {
        disposable.add(restFullApi.cancel_appointment(Common.currentAppointment.user_id, Common.currentAppointment.id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .onErrorResumeNext(new ObservableSource<CancelAppointment>() {
                    @Override
                    public void subscribe(Observer<? super CancelAppointment> observer) {
                        Log.d("tag",observer.toString());
                    }
                })
                .subscribe(new Consumer<CancelAppointment>() {
                    @Override
                    public void accept(CancelAppointment cancelAppointment) throws Exception {

                            message.setValue(cancelAppointment.data);
                            Log.d("tag",cancelAppointment.responce+" "+cancelAppointment.data);

                    }
                })
        );
    }

    public void updateStatus(String s) {
        disposable.add(restFullApi.updateStatus(Common.currentAppointment.id, s)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .onErrorResumeNext(new ObservableSource<Message>() {
                    @Override
                    public void subscribe(Observer<? super Message> observer) {
                        Log.d("tag", Common.currentAppointment.id + "    " + s + " " + observer.toString());
                    }
                })
                .subscribe(new Consumer<Message>() {
                    @Override
                    public void accept(Message message) throws Exception {
                        Log.d("tag", message.message);
                    }
                })
        );

    }


}
