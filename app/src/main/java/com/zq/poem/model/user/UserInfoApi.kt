package com.zq.poem.model.user

import android.media.Image
import kotlinx.coroutines.Deferred
import retrofit2.http.*

const val USER_BASE_URL = "http://ali.sinawan.top:81/api"

interface UserInfoApi {

    @FormUrlEncoded
    @POST("login")
    fun getCode(@Field("phone") phoneNum: String): Deferred<CommonBody<String>>

    @FormUrlEncoded
    @POST("check")
    fun checkCode(@Field("phone") phoneNum: String, @Field("code") code: String): Deferred<CommonBody<UserToken>>


    @GET("user/info")
    fun getUserInfo(@Header("Authorization") token: String): Deferred<CommonBody<UserInfo>>

    @FormUrlEncoded
    @POST("user/info")
    fun updateUserInfo(@Header("Authorization") token: String, @Field("username") UserName: String): Deferred<CommonBody<UserInfo>>

    @GET("user/poem")
    fun getUserPoems(@Header("Authorization") token: String): Deferred<CommonBody<List<Poem>>>

    @GET("poem/all")
    fun getAllPoems(@Header("Authorization") token: String, @Query("page") page: String): Deferred<CommonBody<List<Poem>>>

    @FormUrlEncoded
    @POST("user/poem")
    fun addPoem(@Header("Authorization") token: String,@Field("poem") poemImg: Image): Deferred<CommonBody<String>>

    @FormUrlEncoded
    @POST("poem/delete")
    fun deletePoem(@Header("Authorization") token: String,@Field("id") id: String): Deferred<CommonBody<String>>

//    @GET("user/poem/1")
//    fun getPoemInfo(): Deferred<CommonBody<PoemInfo>>

//    @POST("$USER_BASE_URL/user/like")
//    fun likePoem(@Field("id") poemId: Int): Deferred<CommonBody<String>>

//    @FormUrlEncoded
//    @POST("$USER_BASE_URL/user/comment")
//    fun commentPoem(@Field("id") poemId: Int, @Field("content") comment: String): Deferred<CommonBody<String>>

    companion object : UserInfoApi by MainModel()
}


data class UserInfo(
        var id: Int = 0,
        var username: String? = null,
        var phone: String? = null,
        var image: String? = null
)

data class UserToken(
        var token: String? = null
)

data class Poem(
        var id: Int = 0,
        var username: String? = null,
        var content: String? = null,
        var created_at: String? = null
)

data class PoemInfo(
        val poem: Poem,
        val like: Like,
        val comments: Comments
)

data class Like(
        var total: Int = 0,
        var user: List<*>? = null
)

data class Comments(
        var total: Int = 0,
        var content: List<*>? = null
)
