package com.zq.poem.model;


import com.zq.poem.util.HttpUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by SGXM on 2019/11/19.
 */

/**
* @Description: the class which serve as the Model to request the data
* @Author: SGXM
* @Date: 2019/11/19
*/
public class ModelApi implements Writer {
private static final int POEMPORT = 5000;
public enum TYPE {
    FREE, //自由诗
    RHYME,//押韵
    ACROSTIC,//藏头
    MEANING//根据给定内容写诗
}

    /**
     * in new thread, send a get request to retrieve data
     * and send it back to the main thread
     *
     * @param times times of the network request for the same click
     * @param path request api
     * @param type the kind of the verse will be generate
     * @param hint the param of the last two kinds of verse
     */
@Override
public void write(final String path, final TYPE type, final String hint, final int times) {
    new Thread() {
        @Override
        public void run() {
            super.run();
            Map<String, String> query = new HashMap<>();
            String response = null;
            switch (type) {
                case FREE:
                    query.put("style", "1");
                    response = HttpUtil.getInstance().httpGet(path, POEMPORT, query);
                    break;
                case RHYME:
                    query.put("style", "2");
                    response = HttpUtil.getInstance().httpGet(path, POEMPORT, query);
                    break;
                case ACROSTIC:
                    query.put("style", "3");
                    query.put("start", hint);
                    response = HttpUtil.getInstance().httpGet(path, POEMPORT, query);
                    break;
                case MEANING:
                    query.put("style", "4");
                    query.put("start", hint);
                    response = HttpUtil.getInstance().httpGet(path, POEMPORT, query);
            }
            MainViewModel.getInstance().updateVerses(response, times, path, type, hint);
        }
    }.start();
}
}