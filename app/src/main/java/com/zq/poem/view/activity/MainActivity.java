package com.zq.poem.view.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.zq.poem.R;
import com.zq.poem.util.StatusUtils;


/**
 * Created by SGXM on 2019/11/21.
 */

/**
 * Adapter,Observer,Single设计模式
 * MVVM+LiveData生命周期管理
 * 多View联动
 */

/**
* @Description: Entrance Activity
* @Author: SGXM
* @Date: 2019/11/19
*/
public class MainActivity extends AppCompatActivity {

private final int LOGOUT = 0;

private SharedPreferences sharedPreferences;

/** TextView of the background */
private TextView tv_bg = null;

/** TextView of the enter button */
private TextView tv_enter = null;

    /**
     * just like init()
     * in this method, I initialize my UI components,
     */
@Override
protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    new StatusUtils().setStatusBarFullTransparent(this);
    tv_bg = findViewById(R.id.tv_bg);
    tv_enter = findViewById(R.id.tv_enter);
    tv_bg.setTypeface(Typeface.createFromAsset(getAssets(), "HYshangweishoushuW.ttf"));
    tv_enter.setTypeface(Typeface.createFromAsset(getAssets(), "FZCSJW.TTF"));
    sharedPreferences = getSharedPreferences("data", Context.MODE_PRIVATE);
    tv_enter.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        }
    });

    }


}
