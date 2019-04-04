package com.computer.inu.sqkakaotalk.Adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.computer.inu.sqkakaotalk.Data.GameAdData
import com.computer.inu.sqkakaotalk.R

class  GameAdRecyclerViewAdapter(val ctx: Context, val dataList: ArrayList<GameAdData>) : RecyclerView.Adapter<GameAdRecyclerViewAdapter.Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(ctx).inflate(R.layout.rv_item_game_adimage, parent, false)
        return Holder(view)
    }


    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
     Glide.with(ctx).load(R.id.iv_game_ad).into(holder.image)

    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val image: ImageView = itemView.findViewById(R.id.iv_game_ad) as ImageView


    }
}