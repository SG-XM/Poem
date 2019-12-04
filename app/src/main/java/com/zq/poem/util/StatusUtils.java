package com.zq.poem.util;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by SGXM on 2019/11/19.
 */

/**
* @Description: An Util to modify UI, status bar
* @Author: SGXM
* @Date: 2019/11/19
*/
public class StatusUtils {
    /**
     * adapt full screen make status bar transparent
     *
     * @param activity which activity will be modify
     */
public void setStatusBarFullTransparent(Activity activity) {
    if (Build.VERSION.SDK_INT >= 21) {
        Window window = activity.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.TRANSPARENT);
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }
}
}
