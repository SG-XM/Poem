package com.zq.poem.view.item

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import cn.edu.twt.retrox.recyclerviewdsl.Item
import cn.edu.twt.retrox.recyclerviewdsl.ItemController
import com.bumptech.glide.Glide
import com.zq.poem.R
import com.zq.poem.model.user.Poem

class PhotoItem(val poem: Poem, val cont: Context, val username: String, val deleteid: (String) -> Unit) : Item {
    companion object Controller : ItemController {

        private val baseImgUrl = "http://ali.sinawan.top:81/storage"

        override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_img, parent, false)
            return PhotoHolder(view)
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: Item) {
            holder as PhotoHolder
            item as PhotoItem
            holder.apply {
                Glide.with(item.cont).load(baseImgUrl + item.poem.content).into(imgleft)
                // 创作时间
                time.text = item.poem.created_at
                if (item.username == "") {
                    // 个人中心
                    // 看不见用户名
                    usernameIcon.visibility = View.GONE
                    username.visibility = View.GONE
                    // 有删除功能
                    deletePho.setOnClickListener {
                        item.deleteid(item.poem.id.toString())
                    }
                } else {
                    // 没有删除功能
                    deletePho.visibility = View.GONE
                    username.text = item.username
                }
            }
        }
    }

    class PhotoHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgleft = itemView.findViewById<ImageView>(R.id.iv_left)
        val time = itemView.findViewById<TextView>(R.id.tv_time)
        val usernameIcon = itemView.findViewById<ImageView>(R.id.iv_user)
        val username = itemView.findViewById<TextView>(R.id.tv_user)
        val deletePho = itemView.findViewById<ImageView>(R.id.delete)
    }

    override val controller: ItemController get() = Controller
}

fun MutableList<Item>.addPhos(poem: Poem, cont: Context, username: String, deleteid: (String) -> Unit) =
        add(PhotoItem(poem, cont, username, deleteid))
