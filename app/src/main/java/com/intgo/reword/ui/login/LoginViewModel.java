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
import com.intgo.reword.entity.UserEntity;
import com.intgo.reword.utils.MD5Util;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.binding.command.BindingConsumer;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import me.goldze.mvvmhabit.utils.RxUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;

public class LoginViewModel extends BaseViewModel<AppRepository> {
    private String mMd5PassWord = null;
    //用户名的绑定
    public ObservableField<String> userName = new ObservableField<>("");
    //密码的绑定
    public ObservableField<String> password = new ObservableField<>("");
    //用户名清除按钮的显示隐藏绑定
    public ObservableInt clearBtnVisibility = new ObservableInt();
    //封装一个界面发生改变的观察者
    public UIChangeObservable uc = new UIChangeObservable();

    public LoginViewModel(@NonNull Application application, AppRepository model) {
        super(application, model);
    }

    public class UIChangeObservable {
        //密码开关观察者
        public SingleLiveEvent<Boolean> pSwitchEvent = new SingleLiveEvent<>();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        String tempUserName = model.getUserName();
        String tempPassWord = model.getPassword();
        if(!TextUtils.isEmpty(tempUserName) && !TextUtils.isEmpty(tempPassWord)) {
            userName.set(tempUserName);
            mMd5PassWord = tempPassWord;
            password.set(mMd5PassWord.substring(0,6));
            //自动登录
//            login(tempUserName,tempPassWord);
        }
    }

    //清除用户名的点击事件, 逻辑从View层转换到ViewModel层
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
            uc.pSwitchEvent.setValue(uc.pSwitchEvent.getValue() == null || !uc.pSwitchEvent.getValue());
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

    //忘记密码按钮点击事件
    public BindingCommand forgetPwdOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            //进入忘记密码界面
            startActivity(ForgetPwdActivity.class);
        }
    });

    //注册按钮点击事件
    public BindingCommand registerOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            //进入注册界面
            startActivity(RegisterActivity.class);
        }
    });

    //登录按钮的点击事件
    public BindingCommand loginOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            if (!TextUtils.isEmpty(mMd5PassWord) && !TextUtils.isEmpty(password.get())) {
                if(!mMd5PassWord.substring(0,6).equals(password.get())) {
                    mMd5PassWord = MD5Util.MD5(password.get());
                };
            } else {
                mMd5PassWord = MD5Util.MD5(password.get());
            }
            login(userName.get(),mMd5PassWord);
        }
    });

    /**
     * 登陆操作
     **/
    private void login(final String userName, final String password) {
        if (TextUtils.isEmpty(userName)) {
            ToastUtils.showShort("请输入账号！");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            ToastUtils.showShort("请输入密码！");
            return;
        }
        //RaJava模拟登录
        addSubscribe(model.login(userName, password)
                .compose(RxUtils.schedulersTransformer()) //线程调度
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        showDialog();
                    }
                })
                .subscribe(new Consumer<MyBaseResponse<UserEntity>>() {
                    @Override
                    public void accept(MyBaseResponse<UserEntity> o) throws Exception {
                        dismissDialog();
                        if (o.isOk()) {
                            //保存账号密码
                            model.saveUserName(userName);
                            model.savePassword(password);
                            //进入DemoActivity页面
                            startActivity(MainActivity.class);
                            //关闭页面
                            finish();
                        } else {
                            ToastUtils.showShort(TextUtils.isEmpty(o.getMsg())? "登录失败" : o.getMsg());
                        }
                    }
                }));


    }
}
