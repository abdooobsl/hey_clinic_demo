package com.example.daraltebassistant.viewModel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.daraltebassistant.model.Clinic;
import com.example.daraltebassistant.model.Clinicinfo;
import com.example.daraltebassistant.retrofit.RestFullApi;
import com.example.daraltebassistant.util.Common;

import java.util.List;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainActivityViewModel extends AndroidViewModel {
    private RestFullApi restFullApi = Common.getApi();
    private CompositeDisposable disposable = new CompositeDisposable();
    public MutableLiveData<List<Clinicinfo>> clinicinfo = new MutableLiveData<List<Clinicinfo>>();
    public MutableLiveData<Boolean> clinicLoadError = new MutableLiveData<Boolean>();
    public MutableLiveData<Boolean> loading = new MutableLiveData<Boolean>();

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
    }

    public void refresh() {
        fetchFromRemote();
    }

    private void fetchFromRemote() {
        clinicLoadError.setValue(false);
        loading.setValue(true);
        disposable.add(restFullApi.get_services()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .onErrorResumeNext(new ObservableSource<Clinic>() {
                    @Override
                    public void subscribe(Observer<? super Clinic> observer) {
                        Log.d("tag",observer+"");
                        clinicLoadError.setValue(true);
                        loading.setValue(false);
                    }
                })
                .subscribe(new Consumer<Clinic>() {
                    @Override
                    public void accept(Clinic clinic) throws Exception {
                        clinicinfo.setValue(clinic.data.clinicinfo);
                        clinicLoadError.setValue(false);
                        loading.setValue(false);
                    }
                })

        );
    }
}
