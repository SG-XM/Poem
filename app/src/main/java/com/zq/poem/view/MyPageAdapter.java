package com.zq.poem.view;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by SGXM on 2019/11/21.
 */

/**
* @Description: My Adapter
* @Author: SGXM
* @Date: 2019/11/19
*/
public class MyPageAdapter extends FragmentPagerAdapter {

/** context will be used*/
private Context context;

/** lists of window */
private List<Fragment> fragmentList;

/** list of window's title*/
private List<String> list_Title;


    /**
     * main constructor
     *
     * @param fm manager to manage fragments
     * @param context context will be used
     * @param fragmentList list of fragment
     * @param list_Title list of fragment's title
     */
public MyPageAdapter(FragmentManager fm, Context context, List<Fragment> fragmentList, List<String> list_Title) {
    super(fm);
    this.context = context;
    this.fragmentList = fragmentList;
    this.list_Title = list_Title;

}

@Override
public int getCount() {
    return fragmentList.size();
}


@Override
public Fragment getItem(int position) {
    return fragmentList.get(position);
}

@Override
public CharSequence getPageTitle(int position) {
    return list_Title.get(position);
}
}