package com.computer.inu.sqkakaotalk.Adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import com.computer.inu.sqkakaotalk.Data.MessageData
import com.computer.inu.sqkakaotalk.InMessageActivity
import com.computer.inu.sqkakaotalk.R
import org.jetbrains.anko.startActivity

class  MessageRecyclerViewAdapter(val ctx: Context, val dataList: ArrayList<MessageData>) : RecyclerView.Adapter<MessageRecyclerViewAdapter.Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(ctx).inflate(R.layout.rv_item_message, parent, false)
        return Holder(view)
    }


    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.content.text = dataList[position].content
        holder.word.text =dataList[position].word
        holder.time.text=dataList[position].time
        holder.rl_message.setOnClickListener {
            ctx.startActivity<InMessageActivity>("name" to dataList[position].content)
        }
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val content: TextView = itemView.findViewById(R.id.rv_item_content) as TextView
        val word : TextView =itemView.findViewById(R.id.rv_item_content_word) as TextView
        val time:TextView = itemView.findViewById(R.id.rv_item_time) as TextView
        val rl_message:RelativeLayout=itemView.findViewById(R.id.rl_message) as RelativeLayout
    }
}