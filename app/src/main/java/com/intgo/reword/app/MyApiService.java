package com.intgo.reword.app;

import com.intgo.reword.data.MyBaseResponse;
import com.intgo.reword.entity.RegisterEntity;
import com.intgo.reword.entity.UserEntity;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface MyApiService {
    /**
     * 登陆
     */
    @POST("signin")
    Observable<MyBaseResponse<UserEntity>> login(@Body Map<String,String> map);

    /**
     * 注册（短信方式）
     */
    @POST("register")
    Observable<MyBaseResponse<RegisterEntity>> registerSms(@Body Map<String,String> map);

    /**
     * 注册（邮箱方式）
     */
    @POST("emailRegister")
    Observable<MyBaseResponse<RegisterEntity>> registerEmail(@Body Map<String,String> map);

    /**
     * 获取验证码（短信方式）
     */
    @POST("sendSms")
    Observable<MyBaseResponse<Object>> sendSms(@Body Map<String,String> map);

    /**
     * 获取验证码（邮箱方式）
     */
    @POST("sendEmail")
    Observable<MyBaseResponse<Object>> sendEmail(@Body Map<String,String> map);

    /**
     * 修改密码
     */
    @POST("forgetPassword")
    Observable<MyBaseResponse<Object>> forgetPassword(@Body Map<String,String> map);
}
