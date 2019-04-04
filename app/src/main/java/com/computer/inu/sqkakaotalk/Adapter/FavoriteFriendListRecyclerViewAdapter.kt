package com.computer.inu.sqkakaotalk.Adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.computer.inu.sqkakaotalk.Data.FavoriteFriendData
import com.computer.inu.sqkakaotalk.R

class  FavoriteFriendListRecyclerViewAdapter(val ctx: Context, val dataList: ArrayList<FavoriteFriendData>) : RecyclerView.Adapter<FavoriteFriendListRecyclerViewAdapter.Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(ctx).inflate(R.layout.rv_item_favoritefriend, parent, false)
        return Holder(view)
    }


    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.name.text =dataList[position].name
        holder.content.text=dataList[position].content
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val name: TextView = itemView.findViewById(R.id.rv_tv_friend_friendname) as TextView
        val content : TextView =itemView.findViewById(R.id.rv_tv_friend_friendcontents) as TextView

    }
}