package com.computer.inu.sqkakaotalk.Adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import com.computer.inu.sqkakaotalk.Data.ChatRoomData
import com.computer.inu.sqkakaotalk.R




class  ChatRecyclerViewAdapter(val ctx: Context, val dataList: ArrayList<ChatRoomData>) : RecyclerView.Adapter<ChatRecyclerViewAdapter.Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(ctx).inflate(com.computer.inu.sqkakaotalk.R.layout.rv_item_chat, parent, false)
        return Holder(view)

    }


    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: Holder, position: Int) {

        holder.content.text=dataList[position].content
/*
        holder.rv_item_ll.setBackground(ctx.getResources().getDrawable(com.computer.inu.sqkakaotalk.R.drawable.speach))*/



    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val content : TextView =itemView.findViewById(com.computer.inu.sqkakaotalk.R.id.rv_item_chatmessage) as TextView
/*       val rv_item_ll : FrameLayout=itemView.findViewById(com.computer.inu.sqkakaotalk.R.id.rv_item_ll) as FrameLayout*/
    }
}