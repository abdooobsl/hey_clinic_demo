package com.example.daraltebassistant.viewModel;

import android.app.Application;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.daraltebassistant.model.LoginUser;
import com.example.daraltebassistant.model.UserData;
import com.example.daraltebassistant.retrofit.RestFullApi;
import com.example.daraltebassistant.util.Common;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class LoginViewModel extends AndroidViewModel {
    public LoginViewModel(@NonNull Application application) {
        super(application);
    }

    private RestFullApi restFullApi = Common.getApi();
    private CompositeDisposable disposable = new CompositeDisposable();
    public MutableLiveData<String> EmailAddress = new MutableLiveData<>();
    public MutableLiveData<String> Password = new MutableLiveData<>();
    public MutableLiveData<Boolean> logged = new MutableLiveData<>();


    public void fetchFromRemote(String E, String P) {
        disposable.add(restFullApi.login(E, P)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .onErrorResumeNext(new ObservableSource<UserData>() {
                    @Override
                    public void subscribe(Observer<? super UserData> observer) {
                        Log.d("tag", observer.toString());
                    }
                })
                .subscribe(new Consumer<UserData>() {
                    @Override
                    public void accept(UserData userData) throws Exception {

                        logged.setValue(userData.responce);
                        Common.currentUser = userData.data;
                    }
                })


        );

    }
}
