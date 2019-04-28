package com.computer.inu.sqkakaotalk.Adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.computer.inu.sqkakaotalk.Data.ChatRoomData
import kotlinx.android.synthetic.main.rv_item_chat.view.*


class  ChatRecyclerViewAdapter(val ctx: Context, val dataList: ArrayList<ChatRoomData>) : RecyclerView.Adapter<ChatRecyclerViewAdapter.Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(ctx).inflate(com.computer.inu.sqkakaotalk.R.layout.rv_item_chat, parent, false)
        return Holder(view)

    }


    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: Holder, position: Int) {

        if(dataList[position].check==false){
        holder.content.text=dataList[position].content
            holder.mycontent.visibility=View.GONE
            holder.content.visibility=View.VISIBLE
            holder.image.visibility=View.GONE
        }
        else {
            holder.mycontent.text = dataList[position].content
            holder.mycontent.visibility = View.VISIBLE
            holder.content.visibility = View.GONE
            if (dataList[position].image == "") {
                holder.image.visibility = View.GONE
            } else {
                Glide.with(ctx).load(dataList[position].image).into(holder.image)
                holder.mycontent.visibility = View.GONE
            }
        }


    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image : ImageView = itemView.findViewById(com.computer.inu.sqkakaotalk.R.id.iv_myimage) as ImageView
        val content : TextView =itemView.findViewById(com.computer.inu.sqkakaotalk.R.id.rv_item_chatmessage) as TextView
        val mycontent : TextView =itemView.findViewById(com.computer.inu.sqkakaotalk.R.id.rv_item_mychatmessage) as TextView
      val rv_item_ll : LinearLayout=itemView.findViewById(com.computer.inu.sqkakaotalk.R.id.rv_item_ll) as LinearLayout
    }
}