package com.zq.poem.application;

import android.app.Application;
import android.content.Context;

import java.lang.ref.WeakReference;

/**
 * Created by SGXM on 2019/11/19.
 */

/**
* @Description: a CommonContext which I can use everywhere
* @Author: SGXM
* @Date: 2019/11/19
*/
public class CommonContext {
private static WeakReference applicationReference = null;

static void registerApplication(Application application) {
    applicationReference = new WeakReference(application);
}

public static Context getCommonContext() {
    return (Context) applicationReference.get();
}
}
