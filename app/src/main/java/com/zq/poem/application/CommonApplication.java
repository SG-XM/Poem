package com.zq.poem.application;

import android.app.Application;

/**
 * Created by SGXM on 2019/11/19.
 */

/**
 * @Description: my custom Application
 * @Author: SGXM
 * @Date: 2019/11/19
 */
public class CommonApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        CommonContext.registerApplication(this);
    }
}

