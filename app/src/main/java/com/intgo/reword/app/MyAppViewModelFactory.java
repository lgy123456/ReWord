package com.intgo.reword.app;

import android.annotation.SuppressLint;
import android.app.Application;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;

import com.intgo.reword.data.AppRepository;
import com.intgo.reword.ui.login.ForgetPwdViewModel;
import com.intgo.reword.ui.login.LoginViewModel;
import com.intgo.reword.ui.login.RegisterActivity;
import com.intgo.reword.ui.login.RegisterViewModel;

public class MyAppViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    @SuppressLint("StaticFieldLeak")
    private static volatile MyAppViewModelFactory INSTANCE;
    private final Application mApplication;
    private final AppRepository mRepository;

    public static MyAppViewModelFactory getInstance(Application application) {
        if (INSTANCE == null) {
            synchronized (MyAppViewModelFactory.class) {
                if (INSTANCE == null) {
                    INSTANCE = new MyAppViewModelFactory(application, MyAppInjection.provideAppRepository());
                }
            }
        }
        return INSTANCE;
    }

    @VisibleForTesting
    public static void destroyInstance() {
        INSTANCE = null;
    }

    private MyAppViewModelFactory(Application application, AppRepository repository) {
        this.mApplication = application;
        this.mRepository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(LoginViewModel.class)) {
            return (T) new LoginViewModel(mApplication, mRepository);
        }else if(modelClass.isAssignableFrom(RegisterViewModel.class)) {
            return (T) new RegisterViewModel(mApplication, mRepository);
        }else if(modelClass.isAssignableFrom(ForgetPwdViewModel.class)) {
            return (T) new ForgetPwdViewModel(mApplication, mRepository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}
