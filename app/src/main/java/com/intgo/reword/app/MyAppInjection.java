package com.intgo.reword.app;

import com.intgo.reword.data.AppRepository;
import com.intgo.reword.data.HttpDataSource;
import com.intgo.reword.data.HttpDataSourceImpl;
import com.intgo.reword.data.LocalDataSource;
import com.intgo.reword.data.LocalDataSourceImpl;
import com.intgo.reword.data.RetrofitClient;

/**
 * 注入全局的数据仓库
 */
public class MyAppInjection {
    public static AppRepository provideAppRepository() {
        //网络API服务
        MyApiService apiService = RetrofitClient.getInstance().create(MyApiService.class);
        //网络数据源
        HttpDataSource httpDataSource = HttpDataSourceImpl.getInstance(apiService);
        //本地数据源
        LocalDataSource localDataSource = LocalDataSourceImpl.getInstance();
        //两条分支组成一个数据仓库
        return AppRepository.getInstance(httpDataSource, localDataSource);
    }
}
