package com.example.daraltebassistant.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.daraltebassistant.retrofit.RestFullApi;
import com.example.daraltebassistant.util.Common;

import io.reactivex.disposables.CompositeDisposable;

public class EditAppointmentViewModel extends AndroidViewModel {
    public EditAppointmentViewModel(@NonNull Application application) {
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
}
