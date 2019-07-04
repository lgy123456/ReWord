package com.intgo.reword.data;

import com.intgo.reword.app.MyApiService;
import com.intgo.reword.entity.RegisterEntity;
import com.intgo.reword.entity.UserEntity;
import com.intgo.reword.utils.MD5Util;

import java.util.HashMap;

import io.reactivex.Observable;


public class HttpDataSourceImpl implements HttpDataSource {
    private MyApiService apiService;
    private volatile static HttpDataSourceImpl INSTANCE = null;

    public static HttpDataSourceImpl getInstance(MyApiService apiService) {
        if (INSTANCE == null) {
            synchronized (HttpDataSourceImpl.class) {
                if (INSTANCE == null) {
                    INSTANCE = new HttpDataSourceImpl(apiService);
                }
            }
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    private HttpDataSourceImpl(MyApiService apiService) {
        this.apiService = apiService;
    }

    /**
     * 登陆
     * @param userName
     * @param passWord
     * @return
     */
    @Override
    public Observable<MyBaseResponse<UserEntity>> login(String userName, String passWord) {
        HashMap<String,String> map = new HashMap<>();
        map.put("userName", userName);
        map.put("password",passWord);
        return apiService.login(map);
    }

    /**
     * 短信注册
     * @param phone
     * @param passWord
     * @param smsCode
     * @return
     */
    @Override
    public Observable<MyBaseResponse<RegisterEntity>> registerSms(String phone, String passWord, String smsCode,String optionType) {
        HashMap<String,String> map = new HashMap<>();
        map.put("phone",phone);
        map.put("password",MD5Util.MD5(passWord));
        map.put("smsCode", smsCode);
        map.put("smsType", optionType);
        return apiService.registerSms(map);
    }

    /**
     * 邮箱注册
     * @param email
     * @param passWord
     * @param emailCode
     * @return
     */
    @Override
    public Observable<MyBaseResponse<RegisterEntity>> registerEmail(String email, String passWord, String emailCode,String optionType) {
        HashMap<String,String> map = new HashMap<>();
        map.put("email",email);
        map.put("password",MD5Util.MD5(passWord));
        map.put("smsCode", emailCode);
        map.put("smsType", optionType);
        return apiService.registerEmail(map);
    }

    /**
     * 获取验证码（短信）
     * @param phone
     * @param optionType
     * @return
     */
    @Override
    public Observable<MyBaseResponse<Object>> sendSms(String phone,String optionType) {
        HashMap<String,String> map = new HashMap<>();
        map.put("phone",phone);
        map.put("smsType", optionType);
        return apiService.sendSms(map);
    }

    /**
     * 获取验证码（邮箱）
     * @param email
     * @param optionType
     * @return
     */
    @Override
    public Observable<MyBaseResponse<Object>> sendEmail(String email, String optionType) {
        HashMap<String,String> map = new HashMap<>();
        map.put("email",email);
        map.put("smsType", optionType);
        return apiService.sendEmail(map);
    }

    /**
     * 修改密码
     * @param account
     * @param password
     * @param smsCode
     * @return
     */
    @Override
    public Observable<MyBaseResponse<Object>> forgetPassword(String account, String password, String smsCode) {
        return null;
    }
}
