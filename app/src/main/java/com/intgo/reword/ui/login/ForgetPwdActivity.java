package com.intgo.reword.ui.login;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import com.intgo.reword.R;
import com.intgo.reword.app.MyAppViewModelFactory;
import com.intgo.reword.databinding.ActivityForgetPwdBinding;

import me.goldze.mvvmhabit.BR;
import me.goldze.mvvmhabit.base.BaseActivity;

public class ForgetPwdActivity extends BaseActivity<ActivityForgetPwdBinding, ForgetPwdViewModel> {

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_forget_pwd;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public ForgetPwdViewModel initViewModel() {
        MyAppViewModelFactory factory = MyAppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this, factory).get(ForgetPwdViewModel.class);
    }

}
