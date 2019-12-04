package com.zq.poem.model.user

import com.zq.poem.service.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object MainModel {

    private const val BASE_URL = "http://ali.sinawan.top:81/api/"
    private val client = OkHttpClient
        .Builder()
        .connectTimeout(10, TimeUnit.SECONDS)
        .build()
    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()

    inline operator fun <reified T> invoke(): T = retrofit.create(T::class.java)
}

data class CommonBody<out T>(
        var error_no: Int = 0,
        var error_msg: String? = null,
        val data: T?
)
