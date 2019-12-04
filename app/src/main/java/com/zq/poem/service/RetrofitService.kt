package com.zq.poem.service

import com.zq.poem.model.user.CommonBody
import kotlinx.coroutines.Deferred
import okhttp3.MultipartBody
import retrofit2.http.*

/**
 * 网络请求接口
 */
interface RetrofitService {
    @POST("api/user/poem")
    @Multipart
    fun uploadPoem(@Part poem: MultipartBody.Part, @Header("Authorization") token: String): Deferred<CommonBody<Any>>

    // @GET("v3/direction/walking")
    // fun getDistance(@Query("key")key:String,@Query("origin") origin: String, @Query("destination") destination: String): Deferred<Objects>
}