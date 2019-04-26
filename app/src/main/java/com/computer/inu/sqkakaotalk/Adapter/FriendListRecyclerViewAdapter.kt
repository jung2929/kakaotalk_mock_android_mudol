package com.computer.inu.sqkakaotalk.Adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.computer.inu.sqkakaotalk.AddKakaotalkIdActivity
import com.computer.inu.sqkakaotalk.Data.FriendData
import com.computer.inu.sqkakaotalk.Fragment.FriendListFragment
import com.computer.inu.sqkakaotalk.FriendProfileActivity
import com.computer.inu.sqkakaotalk.MyprofileActivity
import com.computer.inu.sqkakaotalk.R
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.support.v4.ctx

class  FriendListRecyclerViewAdapter(val ctx: Context, val dataList: ArrayList<FriendData>) : RecyclerView.Adapter<FriendListRecyclerViewAdapter.Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(ctx).inflate(R.layout.rv_item_friendlist, parent, false)
        return Holder(view)
    }


    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.name.text = dataList[position].Name
        holder.content.text = dataList[position].Status
        holder.rl_friendlist_item.setOnClickListener {
            var intent = Intent(ctx, FriendProfileActivity::class.java)
            intent.putExtra("position", position)
      ctx.startActivity(intent)
        }
        Glide.with(ctx).load(dataList[position].Prof_img).into(holder.rv_iv_friend_friendpicture)
    }



    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val name: TextView = itemView.findViewById(R.id.rv_tv_friend_friendname) as TextView
        val content : TextView =itemView.findViewById(R.id.rv_tv_friend_friendcontents) as TextView
        val rl_friendlist_item =itemView.findViewById(R.id.rl_friendlist_item) as RelativeLayout
        val  rv_iv_friend_friendpicture = itemView.findViewById(R.id.rv_iv_friend_friendpicture) as ImageView
    }
}