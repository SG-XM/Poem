package com.zq.poem.model.user

import com.zq.poem.service.RefreshState
import com.zq.poem.service.awaitAndHandle
import com.zq.poem.util.coroutineHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

object UserApiManager {

    fun getVertification(phone: String, callback: suspend (RefreshState<CommonBody<String>>) -> (Unit)) =
            GlobalScope.launch(Dispatchers.Main + coroutineHandler) {
                UserInfoApi.getCode(phone).awaitAndHandle {
                    callback(RefreshState.Failure(it))
                }?.let {
                    callback(RefreshState.Success(it))
                }
            }

    fun login(phone: String, code: String, callback: suspend (RefreshState<CommonBody<UserToken>>) -> (Unit)) =
            GlobalScope.launch(Dispatchers.Main + coroutineHandler) {
                UserInfoApi.checkCode(phone, code).awaitAndHandle {
                    callback(RefreshState.Failure(it))
                }?.let {
                    callback(RefreshState.Success(it))
                }
            }


    fun getUserInfo(token: String, callback: suspend (RefreshState<CommonBody<UserInfo>>) -> (Unit)) =
            GlobalScope.launch(coroutineHandler) {
                UserInfoApi.getUserInfo(token).awaitAndHandle {
                    callback(RefreshState.Failure(it))
                }?.let {
                    callback(RefreshState.Success(it))
                }
            }

    fun updateUserInfo(token: String, userName: String, callback: suspend (RefreshState<CommonBody<UserInfo>>) -> (Unit)) {
        GlobalScope.async {
            UserInfoApi.updateUserInfo(token, userName).awaitAndHandle {
                callback(RefreshState.Failure(it))
            }?.let {
                callback(RefreshState.Success(it))
            }
        }
    }

    fun getUserPoems(token: String, callback: suspend (RefreshState<CommonBody<List<Poem>>>) -> (Unit)) =
            GlobalScope.launch(coroutineHandler) {
                UserInfoApi.getUserPoems(token).awaitAndHandle {
                    callback(RefreshState.Failure(it))
                }?.let {
                    callback(RefreshState.Success(it))
                }
            }

    fun getAllPoems(token: String, page: String, callback: suspend (RefreshState<CommonBody<List<Poem>>>) -> (Unit)) =
            GlobalScope.launch {
                UserInfoApi.getAllPoems(token, page).awaitAndHandle {
                    callback(RefreshState.Failure(it))
                }?.let {
                    callback(RefreshState.Success(it))
                }
            }

    fun deleteUserPoems(token: String, id: String, callback: suspend (RefreshState<CommonBody<String>>) -> (Unit)) =
            GlobalScope.launch(coroutineHandler) {
                UserInfoApi.deletePoem(token, id).awaitAndHandle {
                    callback(RefreshState.Failure(it))
                }?.let {
                    callback(RefreshState.Success(it))
                }
            }


}