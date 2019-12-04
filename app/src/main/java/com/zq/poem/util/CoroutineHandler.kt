package com.zq.poem.util

import android.util.Log
import com.zq.poem.application.CommonContext
import kotlinx.coroutines.CoroutineExceptionHandler
import org.jetbrains.anko.getStackTraceString
import org.jetbrains.anko.toast
import java.net.SocketTimeoutException

public val coroutineHandler = CoroutineExceptionHandler { _, throwable ->
    //    CommonContext.application.toast(throwable.message!!)
    val errorMsg: String = when (throwable) {
        is SocketTimeoutException -> "网络连接超时"
        else -> throwable.message ?: ""
    }
    Log.e("woggle", errorMsg)
    MyToast.toast(errorMsg)

    Log.e("woggle", throwable.getStackTraceString())
}

object MyToast {
    var lastMsg = ""
    var lastTime = System.currentTimeMillis()
    fun toast(msg: String) {
        if (msg != lastMsg || System.currentTimeMillis() - lastTime > 2000) {
            var mMsg = msg
            if (msg == "HTTP 500") mMsg = "服务器出了问题"
            CommonContext.getCommonContext().toast(mMsg)
            lastMsg = msg
            lastTime = System.currentTimeMillis()
        }
    }
}