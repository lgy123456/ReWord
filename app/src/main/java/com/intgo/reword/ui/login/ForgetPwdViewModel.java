package com.intgo.reword.ui.login;

import android.app.Application;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;

import com.intgo.reword.MainActivity;
import com.intgo.reword.data.AppRepository;
import com.intgo.reword.data.MyBaseResponse;
import com.intgo.reword.entity.RegisterEntity;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.binding.command.BindingConsumer;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import me.goldze.mvvmhabit.utils.RegexUtils;
import me.goldze.mvvmhabit.utils.RxUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;

public class ForgetPwdViewModel extends BaseViewModel<AppRepository> {
    //用户名的绑定
    public ObservableField<String> userName = new ObservableField<>("");
    //密码的绑定
    public ObservableField<String> password = new ObservableField<>("");
    //密码确认
    public ObservableField<String> passwordSecond = new ObservableField<>("");
    //验证码
    public ObservableField<String> code = new ObservableField<>("");
    //用户名清除按钮的显示隐藏绑定
    public ObservableInt clearBtnVisibility = new ObservableInt();
    //封装一个界面发生改变的观察者
    public UIChangeObservable uc = new UIChangeObservable();

    public ForgetPwdViewModel(@NonNull Application application, AppRepository model) {
        super(application, model);
    }

    public class UIChangeObservable {
        //密码开关观察者
        public SingleLiveEvent<Boolean> pReSwitchEent = new SingleLiveEvent<>();
    }

    //清除用户名的点击事件
    public BindingCommand clearUserNameOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            userName.set("");
        }
    });
    //密码显示开关 (防多次点击的功能)
    public BindingCommand passwordShowSwitchOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            //让观察者的数据改变,逻辑从ViewModel层转到View层，在View层的监听则会被调用
            uc.pReSwitchEent.setValue(uc.pReSwitchEent.getValue() == null || !uc.pReSwitchEent.getValue());
        }
    });
    //用户名输入框焦点改变的回调事件
    public BindingCommand<Boolean> onFocusChangeCommand = new BindingCommand<>(new BindingConsumer<Boolean>() {
        @Override
        public void call(Boolean hasFocus) {
            if (hasFocus) {
                clearBtnVisibility.set(View.VISIBLE);
            } else {
                clearBtnVisibility.set(View.INVISIBLE);
            }
        }
    });
    //忘记密码的点击事件
    public BindingCommand forgetPwdOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            forgetPwd();
        }
    });
    //获取验证码按钮的点击事件
    public BindingCommand sendCodeOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            sendCode();
        }
    });

    /**
     * 获取验证码
     */
    private void sendCode() {
        if (TextUtils.isEmpty(userName.get())) {
            ToastUtils.showShort("请先输入账号！");
            return;
        }
        Observable<MyBaseResponse<Object>> observable = null;
        if (RegexUtils.isMobileExact(userName.get())) {
            observable = model.sendSms(userName.get(),"1");
        } else if(RegexUtils.isEmail(userName.get())){
            observable = model.sendEmail(userName.get(),"1");
        } else {
            ToastUtils.showShort("请输入正确的手机号或邮箱！");
            return;
        }
        addSubscribe(observable
                .compose(RxUtils.schedulersTransformer()) //线程调度
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                    }
                })
                .subscribe(new Consumer<MyBaseResponse<Object>>() {
                    @Override
                    public void accept(MyBaseResponse<Object> o) throws Exception {
                        if (o.isOk()){
                            ToastUtils.showShort(TextUtils.isEmpty(o.getMsg())? "获取验证码成功" : o.getMsg());
                        } else {
                            ToastUtils.showShort(TextUtils.isEmpty(o.getMsg())? "获取验证码失败" : o.getMsg());
                        }
                    }
                }));

    }

    /**
     * 忘记密码
     **/
    private void forgetPwd() {
        if (TextUtils.isEmpty(userName.get())) {
            ToastUtils.showShort("请输入手机号或邮箱！");
            return;
        }
        if (TextUtils.isEmpty(code.get())) {
            ToastUtils.showShort("请输入验证码！");
            return;
        }
        Observable<MyBaseResponse<RegisterEntity>> observable = null;
        if (RegexUtils.isMobileExact(userName.get())) {
            observable = model.registerSms(userName.get(),password.get(),code.get(),"1");
        } else if (RegexUtils.isEmail(userName.get())) {
            observable = model.registerEmail(userName.get(),password.get(),code.get(),"1");
        } else {
            ToastUtils.showShort("请输入正确的手机号或邮箱！");
            return;
        }
        addSubscribe(model.forgetPassword(userName.get(),password.get(),code.get())
                .compose(RxUtils.schedulersTransformer()) //线程调度
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        showDialog();
                    }
                })
                .subscribe(new Consumer<MyBaseResponse<RegisterEntity>>() {
                    @Override
                    public void accept(MyBaseResponse<RegisterEntity> o) throws Exception {
                        dismissDialog();
                        if(o.isOk()) {
                            //进入DemoActivity页面
                            startActivity(MainActivity.class);
                            //关闭页面
                            finish();
                        } else {
                            ToastUtils.showShort(TextUtils.isEmpty(o.getMsg())? "注册失败" : o.getMsg());
                        }
                    }
                }));

    }


}
