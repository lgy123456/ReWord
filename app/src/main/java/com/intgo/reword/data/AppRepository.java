package com.intgo.reword.data;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;


import com.intgo.reword.entity.RegisterEntity;
import com.intgo.reword.entity.UserEntity;

import io.reactivex.Observable;
import me.goldze.mvvmhabit.base.BaseModel;

/**
 * 统一模块的数据仓库，包含网络数据和本地数据
 */
public class AppRepository extends BaseModel implements HttpDataSource, LocalDataSource {
    private volatile static AppRepository INSTANCE = null;
    private final HttpDataSource mHttpDataSource;

    private final LocalDataSource mLocalDataSource;

    private AppRepository(@NonNull HttpDataSource httpDataSource,
                          @NonNull LocalDataSource localDataSource) {
        this.mHttpDataSource = httpDataSource;
        this.mLocalDataSource = localDataSource;
    }

    public static AppRepository getInstance(HttpDataSource httpDataSource,
                                             LocalDataSource localDataSource) {
        if (INSTANCE == null) {
            synchronized (AppRepository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new AppRepository(httpDataSource, localDataSource);
                }
            }
        }
        return INSTANCE;
    }

    @VisibleForTesting
    public static void destroyInstance() {
        INSTANCE = null;
    }

    @Override
    public Observable<MyBaseResponse<UserEntity>> login(String userName, String passWord) {
        return mHttpDataSource.login(userName,passWord);
    }

    @Override
    public Observable<MyBaseResponse<RegisterEntity>> registerSms(String phone, String passWord, String smsCode, String optionType) {
        return mHttpDataSource.registerSms(phone,passWord,smsCode,optionType);
    }

    @Override
    public Observable<MyBaseResponse<RegisterEntity>> registerEmail(String email, String passWord, String emailCode, String optionType) {
        return mHttpDataSource.registerEmail(email,passWord,emailCode,optionType);
    }

    @Override
    public Observable<MyBaseResponse<Object>> sendSms(String phone, String optionType) {
        return mHttpDataSource.sendSms(phone,optionType);
    }

    @Override
    public Observable<MyBaseResponse<Object>> sendEmail(String email, String optionType) {
        return mHttpDataSource.sendEmail(email,optionType);
    }

    @Override
    public Observable<MyBaseResponse<Object>> forgetPassword(String account, String password, String smsCode) {
        return mHttpDataSource.forgetPassword(account,password,smsCode);
    }

    @Override
    public void saveUserName(String userName) {
        mLocalDataSource.saveUserName(userName);
    }

    @Override
    public void savePassword(String password) {
        mLocalDataSource.savePassword(password);
    }

    @Override
    public String getUserName() {
        return mLocalDataSource.getUserName();
    }

    @Override
    public String getPassword() {
        return mLocalDataSource.getPassword();
    }
}
