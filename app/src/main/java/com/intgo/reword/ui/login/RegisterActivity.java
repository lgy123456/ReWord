package com.intgo.reword.ui.login;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;

import com.intgo.reword.R;
import com.intgo.reword.app.MyAppViewModelFactory;
import com.intgo.reword.databinding.ActivityLoginBinding;
import com.intgo.reword.databinding.ActivityRegisterBinding;

import me.goldze.mvvmhabit.BR;
import me.goldze.mvvmhabit.base.BaseActivity;

public class RegisterActivity extends BaseActivity<ActivityRegisterBinding, RegisterViewModel> {

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_register;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public RegisterViewModel initViewModel() {
        MyAppViewModelFactory factory = MyAppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this, factory).get(RegisterViewModel.class);
    }

    @Override
    public void initViewObservable() {
        //监听ViewModel中pSwitchObservable的变化
        viewModel.uc.pReSwitchEent.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                //pSwitchObservable是boolean类型的观察者,所以可以直接使用它的值改变密码开关的图标
                if (viewModel.uc.pReSwitchEent.getValue()) {
                    //密码可见
                    //在xml中定义id后,使用binding可以直接拿到这个view的引用,不再需要findViewById去找控件了
                    binding.ivSwichPasswrod.setImageResource(R.drawable.login_iv_show_psw_normal);
                    binding.etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    binding.etPasswordSecond.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    //密码不可见
                    binding.ivSwichPasswrod.setImageResource(R.drawable.login_iv_showpsw_ic_pressed);
                    binding.etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    binding.etPasswordSecond.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
    }
}
