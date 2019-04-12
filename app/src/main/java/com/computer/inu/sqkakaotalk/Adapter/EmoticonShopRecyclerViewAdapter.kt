package com.computer.inu.sqkakaotalk.Adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.computer.inu.sqkakaotalk.Data.EmoticonData
import com.computer.inu.sqkakaotalk.R


class  EmoticonShopRecyclerViewAdapter(val ctx: Context, val dataList: ArrayList<EmoticonData>) : RecyclerView.Adapter<EmoticonShopRecyclerViewAdapter.Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(ctx).inflate(R.layout.rv_item_emoticonshop, parent, false)
        return Holder(view)
    }


    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.Name.text =dataList[position].Name
        holder.Made.text=dataList[position].Made
        Glide.with(ctx).load(dataList[position].image).into(holder.image)
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val Name: TextView = itemView.findViewById(R.id.rv_emoticon_name) as TextView
        val Made : TextView =itemView.findViewById(R.id.rv_emoticon_made) as TextView
        val image:ImageView=itemView.findViewById(R.id.rv_emoticon_image) as ImageView
        // val Eno


    }
}