package com.zq.poem.util

import android.app.Activity
import android.app.Dialog
import android.graphics.Bitmap
import android.os.Environment
import android.provider.MediaStore
import android.widget.ImageView
import com.zq.poem.application.CommonContext
import java.io.File
import java.io.FileOutputStream

/**
 * Created by SGXM on 2019/10/8.
 */
object PicUtil {
    @JvmStatic
    fun showPic(ac: Activity, pic: Bitmap) {

        Dialog(ac).apply {
            val img = ImageView(ac).apply {
                setOnClickListener { dismiss() }
                this.setImageBitmap(pic)
            }
            setContentView(img)
            setCanceledOnTouchOutside(true)
            show()
        }
    }

    @JvmStatic
    fun saveBitmap(bitmap: Bitmap):String? {
        // 首先保存图片
        val appDir = File(Environment.getExternalStorageDirectory(), "poem")
        if (!appDir.exists()) {
            appDir.mkdir()
        }
        val fileName = System.currentTimeMillis().toString() + ".png"
        val file = File(appDir, fileName)

        try {
            val fos = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
            fos.flush()
            fos.close()
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
        // 把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(CommonContext.getCommonContext().getContentResolver(), file.getAbsolutePath(), fileName, null)
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
        return file.absolutePath
    }

}