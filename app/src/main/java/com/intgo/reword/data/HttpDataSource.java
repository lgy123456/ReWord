package com.intgo.reword.data;


import com.intgo.reword.entity.RegisterEntity;
import com.intgo.reword.entity.UserEntity;

import io.reactivex.Observable;


public interface HttpDataSource {
    //登录
    Observable<MyBaseResponse<UserEntity>> login(String userName, String passWord);
    //注册(短信方式)
    Observable<MyBaseResponse<RegisterEntity>> registerSms(String phone, String passWord, String smsCode, String optionType);
    //注册（邮箱方式）
    Observable<MyBaseResponse<RegisterEntity>> registerEmail(String email, String passWord, String emailCode,String optionType);
    //获取验证码（短信方式）
    Observable<MyBaseResponse<Object>> sendSms(String phone,String optionType);
    //获取验证码（邮箱方式）
    Observable<MyBaseResponse<Object>> sendEmail(String email,String optionType);
    //修改密码
    Observable<MyBaseResponse<Object>> forgetPassword(String account,String password,String smsCode);
}
