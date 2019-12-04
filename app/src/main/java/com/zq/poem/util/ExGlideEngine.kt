package com.zq.poem.util

import android.content.Context
import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.request.RequestOptions
import com.zhihu.matisse.engine.ImageEngine

/**
 * Created by SGXM on 2019/11/3.
 */

class ExGlideEngine : ImageEngine {

    override fun loadThumbnail(context: Context, resize: Int, placeholder: Drawable?, imageView: ImageView, uri: Uri?) {
        val options = RequestOptions()
            .centerCrop()
            .placeholder(placeholder)//这里可自己添加占位图
            .override(resize, resize)
        Glide.with(context)
            .asBitmap()
            .load(uri)
            .apply(options)
            .into(imageView)
    }

    override fun loadImage(context: Context, resizeX: Int, resizeY: Int, imageView: ImageView, uri: Uri?) {

        val options = RequestOptions()
            .centerCrop()
            .override(resizeX, resizeY)
            .priority(Priority.HIGH)
        Glide.with(context)
            .load(uri)
            .apply(options)
            .into(imageView)
    }

    override fun loadGifImage(context: Context, resizeX: Int, resizeY: Int, imageView: ImageView, uri: Uri?) {
        val options = RequestOptions()
            .centerCrop()
            .override(resizeX, resizeY)
        Glide.with(context)
            .asGif()
            .load(uri)
            .apply(options)
            .into(imageView)
    }

    override fun supportAnimatedGif(): Boolean = true

    override fun loadGifThumbnail(
        context: Context?,
        resize: Int,
        placeholder: Drawable?,
        imageView: ImageView?,
        uri: Uri?
    ) {
    }


}
