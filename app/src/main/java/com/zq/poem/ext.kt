package com.zq.poem

import android.content.Context
import android.view.View
import com.zq.poem.view.PoemDisplayView
import org.jetbrains.anko.*
import org.jetbrains.anko.collections.forEachWithIndex
import org.jetbrains.anko.constraint.layout.constraintLayout

/**
 * Created by SGXM on 2019/11/4.
 */
object Util {
    @JvmStatic
    fun getListener(cx: Context, vs: MutableList<String>, pv: PoemDisplayView): View.OnClickListener {
        val l = object : View.OnClickListener {
            override fun onClick(v: View?) {
                if (vs.isEmpty()) return
                cx.alert {
                    customView {
                        constraintLayout {
                            lparams {
                                width = wrapContent
                                height = wrapContent
                                padding = dip(12)
                            }
                            val edt = editText {}
                            var tx = ""
                            vs.forEachWithIndex { i, s ->
                                tx += s
                                if (i != vs.size - 1)
                                    tx += ","
                            }
                            edt.setText(tx)
                            positiveButton("确认修改") {
                                pv.verses = edt.text.split(",")
                            }
                            negativeButton("取消") {

                            }
                        }

                    }

                }.show()
            }

        }
        return l
    }
}