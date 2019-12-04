package com.zq.poem.service

import android.content.Context
import android.util.Log
import com.zq.poem.application.CommonContext
import com.zq.poem.util.HttpLoggingInterceptor
import com.zq.poem.util.coroutineHandler
import com.zq.poem.view.activity.HomeActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import org.jetbrains.anko.toast
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLSession

/**
 * 网络请求工厂
 */
object RetrofitFactory {
    private val loggingInterceptor = HttpLoggingInterceptor()

    private val client = OkHttpClient.Builder()
            .retryOnConnectionFailure(false)
            //.addNetworkInterceptor(loggingInterceptor)
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .hostnameVerifier(object : HostnameVerifier {
                override fun verify(hostname: String?, session: SSLSession?): Boolean {
                    return true
                }
            })
            .build()

    private val retrofit: Retrofit = Retrofit.Builder()
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("http://ali.sinawan.top:81/")
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()

    val api: RetrofitService = retrofit.create(RetrofitService::class.java)

    public fun uploadPoem(path: String, ac: HomeActivity) {
        val requestFile1 = RequestBody.create(MediaType.parse("image/png"), File(path))
//        val requestFile1 = RequestBody.create(MediaType.parse("image/jpeg"), File(path))
        val imgbody1 = MultipartBody.Part.createFormData("poem", path, requestFile1)
        GlobalScope.launch(Dispatchers.Main + coroutineHandler) {
            val data = api.uploadPoem(imgbody1, ac.getSharedPreferences("data", Context.MODE_PRIVATE).getString("user_token", "MTA0fERwU094R3QzSS1YUEVnbzZhcHV4bUNRenpsMHIyenFsckZMak5naVJLMzA=")).await()
            if (data?.error_no == 1) {
                CommonContext.getCommonContext().toast("保存到个人中心成功")
            }else{
                Log.e("woggle",data?.error_msg?:"")
            }
        }.invokeOnCompletion {
            it?.printStackTrace()
            ac.hideProb()
        }
    }

}