package com.zq.poem.view.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.zq.poem.R;
import com.zq.poem.service.RetrofitFactory;
import com.zq.poem.util.ExGlideEngine;
import com.zq.poem.util.PicUtil;
import com.zq.poem.util.StatusUtils;
import com.zq.poem.view.MyPageAdapter;
import com.zq.poem.view.fragment.AcrosticFragment;
import com.zq.poem.view.fragment.FreeFragment;
import com.zq.poem.view.fragment.MeaningFragment;
import com.zq.poem.view.fragment.RhymeFragment;
import com.google.android.material.tabs.TabLayout;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.internal.entity.CaptureStrategy;



import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;


/**
 * @Description: MainActivity which contains main business
 * @Author: SGXM
 */
public class HomeActivity extends AppCompatActivity {

    private final int LOGOUT = 0;

    private final int REQUEST_CODE_CHOOSE = 1;

    /**
     * Adapter design pattern
     * <p>
     * SDK has provided banner
     * this adapter is used to make my UI components to adapt its standard
     */
    private MyPageAdapter myPageAdapter = null;

    /**
     * List of view just like container
     */
    private List<Fragment> fragments = new ArrayList<>(4);

    /**
     * List of fragment's title
     */
    private List<String> titles = new ArrayList<>(4);

    /**
     * Banner provided by SDK
     */
    private ViewPager myViewPager = null;

    /**
     * used with ViewPager
     */
    private TabLayout tabLayout = null;

    /**
     * intent to Photo Acitivty
     **/
    private ImageView takePhoto = null;

    /**
     * changeBG
     **/
    private ImageView mButtonBG = null;

    /**
     * BG ImageView
     **/
    private ImageView mImgBG = null;

    /**
     * intent to Personal information
     **/
    private ImageView personInfo = null;

    private ImageView wholeBG = null;

    private ProgressBar prob = null;
    SharedPreferences sharedPreferences;

    private ImageView allpoem = null;
    private View tool = null;

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        tabLayout.getTabAt(2).select();
        Log.e("woggle", "g");
        String ct = intent.getStringExtra("CONTENT");
        if (ct != null && !ct.isEmpty() && fragments.size() > 2) {
            ((AcrosticFragment) fragments.get(2)).setHint(ct);
            ((MeaningFragment) fragments.get(3)).setHint(ct);
        }
    }


    /**
     * just like init()
     * in this method, I initialize my UI components,
     * add listeners and observe the observable
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        new StatusUtils().setStatusBarFullTransparent(this);
        myViewPager = findViewById(R.id.vp_main);
        tabLayout = findViewById(R.id.tab_main);
        sharedPreferences = getSharedPreferences("data", Context.MODE_PRIVATE);
        prob = findViewById(R.id.prob);
        // 跳转控件的绑定
        mButtonBG = findViewById(R.id.home_bg);
        mImgBG = findViewById(R.id.save_img_btn);
        wholeBG = findViewById(R.id.img_bg);
        tool = findViewById(R.id.layout_bt);
        fragments.add(new FreeFragment());
        fragments.add(new RhymeFragment());
        fragments.add(new AcrosticFragment());
        fragments.add(new MeaningFragment());
        titles.add("自由诗");
        titles.add("韵律诗");
        titles.add("藏头诗");
        titles.add("藏字诗");
        myPageAdapter = new MyPageAdapter(getSupportFragmentManager(), HomeActivity.this, fragments, titles);
        myViewPager.setAdapter(myPageAdapter);
        myViewPager.setOffscreenPageLimit(4);
        tabLayout.setupWithViewPager(myViewPager);
        reflectTypeface();
        reflectLengthColor();
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                myViewPager.setCurrentItem(tab.getPosition(), true);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        //添加到个人诗库
        mImgBG.setOnClickListener(v -> {

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                String arrs[] = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
                ActivityCompat.requestPermissions(this, arrs, REQUEST_CODE_CHOOSE);
            } else if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                String arrs[] = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
                ActivityCompat.requestPermissions(this, arrs, REQUEST_CODE_CHOOSE);
            } else {//getWindow().getDecorView().setDrawingCacheEnabled(true);
                getWindow().getDecorView().setDrawingCacheEnabled(false);
                tool.setVisibility(View.GONE);
                getWindow().getDecorView().destroyDrawingCache();
                getWindow().getDecorView().buildDrawingCache();
                tool.setVisibility(View.VISIBLE);
                mImgBG.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        Bitmap bitmap = capture(HomeActivity.this);
                        //prob.setVisibility(View.VISIBLE);
                        PicUtil.showPic(HomeActivity.this, bitmap);
                        String path = PicUtil.saveBitmap(bitmap);
                        Toast.makeText(HomeActivity.this, "已保存到本地", Toast.LENGTH_SHORT).show();
                        if (path != null && !path.isEmpty()) {
                            Log.e("woggle", getSharedPreferences("data", Context.MODE_PRIVATE).getString("user_token", "MTA0fERwU094R3QzSS1YUEVnbzZhcHV4bUNRenpsMHIyenFsckZMak5naVJLMzA="));
                        }
                    }
                }, 500);

            }

        });


        //更换背景初始化
        initPhotoSelect();

    }

    public void hideProb() {
        prob.setVisibility(View.GONE);
    }

    public static Bitmap capture(Activity activity) {

        Bitmap bmp = activity.getWindow().getDecorView().getDrawingCache();
        return bmp;
    }

    private void open() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            String arrs[] = {Manifest.permission.READ_EXTERNAL_STORAGE};
            ActivityCompat.requestPermissions(this, arrs, REQUEST_CODE_CHOOSE);
        } else {

            Matisse.from(this)
                    .choose(MimeType.ofImage(), false) // 选择 mime 的类型
                    .countable(true)
                    .capture(true)
                    .captureStrategy(new CaptureStrategy(true, "PhotoPicker"))
                    .maxSelectable(1) // 图片选择的最多数量
                    .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                    .thumbnailScale(0.8f) // 缩略图的比例
                    .imageEngine(new ExGlideEngine()) // 使用的图片加载引擎
                    .forResult(REQUEST_CODE_CHOOSE);
        }
    }

    private void initPhotoSelect() {

        mButtonBG.setOnClickListener(v -> {
            open();
        });
    }

    /**
     * Reflect
     * <p>
     * I use reflect to modify some property of the UI components
     * which didn't provide api to modify it, such as typeface
     */
    private void reflectTypeface() {
        ArrayList<TabLayout.Tab> tabs;
        Class tabLayoutClazz = tabLayout.getClass();
        try {
            Field tabsField = tabLayoutClazz.getDeclaredField("mTabs");
            tabsField.setAccessible(true);
            tabs = (ArrayList<TabLayout.Tab>) tabsField.get(tabLayout);
            for (TabLayout.Tab tab : tabs) {
                Class tabClass = tab.getClass();
                Field tabViewField = tabClass.getDeclaredField("mView");
                tabViewField.setAccessible(true);
                View tabView = (View) tabViewField.get(tab);
                Class tabViewClazz = tabView.getClass();
                Field textViewField = tabViewClazz.getDeclaredField("mTextView");
                textViewField.setAccessible(true);
                TextView cusTextView = (TextView) textViewField.get(tabView);
                cusTextView.setTypeface(Typeface.createFromAsset(getAssets(), "FZCSJW.TTF"));
            }

        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reflect
     *
     * @see #reflectTypeface()
     * modify TabLayout Strip color and length
     */
    private void reflectLengthColor() {
        Class<?> tabLayoutClazz = tabLayout.getClass();

        Field tabStrip = null;
        try {
            tabStrip = tabLayoutClazz.getDeclaredField("slidingTabIndicator");
            tabStrip.setAccessible(true);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }


        LinearLayout llTab = null;
        try {
            llTab = (LinearLayout) tabStrip.get(tabLayout);
            Method method = llTab.getClass().getDeclaredMethod("setSelectedIndicatorColor", int.class);
            method.setAccessible(true);
            method.invoke(llTab, getResources().getColor(R.color.colorLine));
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }

        int left = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, Resources.getSystem().getDisplayMetrics());
        int right = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, Resources.getSystem().getDisplayMetrics());

        for (int i = 0; i < (llTab != null ? llTab.getChildCount() : 0); i++) {
            View child = llTab.getChildAt(i);
            child.setPadding(0, 0, 0, 0);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
            params.leftMargin = left;
            params.rightMargin = right;
            child.setLayoutParams(params);
            child.invalidate();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_CODE_CHOOSE) {
                Glide.with(this).load(Matisse.obtainResult(data).get(0)).into(wholeBG);
            }
        }
    }

    private void setLogout() {
        int loginStatus = sharedPreferences.getInt("status", LOGOUT);
//        cloud_button.setOnClickListener {
//            LoginService.logout()
//            sharedPreferences.edit().putInt("status",LOGOUT).apply()
//            startActivity<WelcomeActivity>()
//            finish()
//        }
    }
}

