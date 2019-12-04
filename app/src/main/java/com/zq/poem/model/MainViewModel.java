package com.zq.poem.model;

import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;


import com.zq.poem.application.CommonContext;
import com.zq.poem.util.CharacterUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by SGXM on 2019/11/21.
 */

/**
 * @Description: DataModel which serve as the belt between network request and UI update
 * @Author: SGXM
 * @Date: 2019/11/19
 */
public class MainViewModel implements Writer {
    /**
     * Observe design pattern
     *
     * Livedata is a kind of data with lifecycle
     * I use it to save verse request from the serve
     * when its value changes, my observer will update UI
     * and according with the lifecycle of UI component
     */
    private MutableLiveData<List<String>> freeData = new MutableLiveData<>();
    private MutableLiveData<List<String>> rhymeData = new MutableLiveData<>();
    private MutableLiveData<List<String>> arcrosticData = new MutableLiveData<>();
    private MutableLiveData<List<String>> meaningData = new MutableLiveData<>();

    /**
     * livedata which saves the status of network request,when its value changes to false
     * the observer will toast message
     */
    private MutableLiveData<Boolean> status = new MutableLiveData<>();

    /** Singleton design pattern*/
    private static final MainViewModel ourInstance = new MainViewModel();

    /**
     * Static Proxy design pattern
     *
     * MODELAPI is the real object and MainViewModel is the proxy
     * both of them implements the interface Writer
     */
    private static final Writer MODELAPI = new ModelApi();

    /**
     * @return the instance of this class
     */
    public static MainViewModel getInstance() {
        return ourInstance;
    }

    /**
     * initialize, observe the livedata
     */
    private MainViewModel() {
        status.observeForever(new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if (!aBoolean)
                    Toast.makeText(CommonContext.getCommonContext(), "写得太累了,休息一下", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * @return the reference of freeData
     */
    public MutableLiveData<List<String>> getFreeData() {
        return freeData;
    }

    /**
     * @return the reference of rhymeData
     */
    public MutableLiveData<List<String>> getRhymeData() {
        return rhymeData;
    }

    /**
     * @return the reference of arcrosticData
     */
    public MutableLiveData<List<String>> getArcrosticData() {
        return arcrosticData;
    }

    /**
     * @return the reference of meaningData
     */
    public MutableLiveData<List<String>> getMeaningData() {
        return meaningData;
    }

    /**
     * a method updates data in UI thread, which can be called in any thread
     *
     * @param verse the verses get from the serve
     * @param times times of the network request for the same click
     * @param path request api
     * @param type the kind of the verse will be generate
     * @param hint the param of the last two kinds of verse
     */
    void updateVerses(String verse, int times, String path, ModelApi.TYPE type, String hint) {

        List<String> verses = new ArrayList<>();
        verses.addAll(Arrays.asList(verse.replaceAll("，", "。").split("。")));
        if (check(verses))
            switch (type) {
                case FREE:
                    freeData.postValue(verses);
                    break;
                case RHYME:
                    rhymeData.postValue(verses);
                    break;
                case ACROSTIC:
                    arcrosticData.postValue(verses);
                    break;
                case MEANING:
                    meaningData.postValue(verses);
                    break;
            }
        else if (times < 5) {
            write(path, type, hint, ++times);
        } else {
            status.postValue(false);
        }

    }

    /**
     * Check whether all characters are Chinese character
     * and at least four columns
     *
     * @param verses
     * @return
     */
    private Boolean check(List<String> verses) {
        if (verses.size() < 4) return false;
        for (String v : verses) {
            if (v.isEmpty()) return false;
            else if (!CharacterUtil.checkCharacter(v)) return false;
        }
        return true;
    }

    /**
     * the proxy method
     *
     * @see #updateVerses(String, int, String, ModelApi.TYPE, String)
     */
    @Override
    public void write(String path, ModelApi.TYPE type, String hint, int times) {
        MODELAPI.write(path, type, hint, times);
    }
}
