package com.zq.poem.view.fragment;

/**
 * Created by SGXM on 2019/11/21.
 */

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.ViewStubCompat;
import androidx.fragment.app.Fragment;

/**
 * 一个实现懒加载的Fragment
 */
public abstract class LazyFragment extends Fragment {
    private View mRootView = null;
    private Boolean isViewCreated = false;
    private ViewStubCompat mViewStub;
    private Boolean isUserVisible = true;
    private Boolean isLoaded = false;

    //获取真正的数据视图
    public abstract int getResId();

    //当视图真正加载时调用
    public abstract void onRealViewLoaded(View view);

    @SuppressLint("RestrictedApi")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)

    {
        if (mRootView != null) {
            isViewCreated = true;
            return mRootView;
        }

        Context context = inflater.getContext();
        FrameLayout root = new FrameLayout(context);

        mViewStub = new ViewStubCompat(context, null);
        mViewStub.setLayoutResource(getResId());
        root.addView(mViewStub, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
        root.setLayoutParams(new ViewGroup.MarginLayoutParams(ViewGroup.MarginLayoutParams.MATCH_PARENT, ViewGroup.MarginLayoutParams.MATCH_PARENT));

        mRootView = root;
        if (isUserVisible) {
            realLoad();
        }

        isViewCreated = true;
        return mRootView;
    }

    @SuppressLint("RestrictedApi")
    private void realLoad() {
        if (isLoaded) {
            return;
        }
        isLoaded = true;
        onRealViewLoaded(mViewStub.inflate());
    }

    @Override
    public void onDestroyView() {
        isViewCreated = false;
        super.onDestroyView();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isUserVisible = isVisibleToUser;
        if (isUserVisible && isViewCreated) {
            realLoad();
        }
    }
}