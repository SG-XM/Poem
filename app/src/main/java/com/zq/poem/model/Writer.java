package com.zq.poem.model;

/**
 * Created by SGXM on 2019/11/19.
 */

    /**
    * @Description: Main business Interface
    * @Author: SGXM
    * @Date: 2019/11/19
    */
public interface Writer {
    void write(final String path, final ModelApi.TYPE type, final String hint, final int times);
}
