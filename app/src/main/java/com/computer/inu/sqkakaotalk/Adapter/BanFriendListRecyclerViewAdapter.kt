package com.computer.inu.sqkakaotalk.Adapter

import android.app.Activity
import android.content.Context
import android.provider.ContactsContract
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.computer.inu.sqkakaotalk.BanFriendActivity
import com.computer.inu.sqkakaotalk.Data.FavoriteFriendData
import com.computer.inu.sqkakaotalk.Main.MainActivity
import com.computer.inu.sqkakaotalk.R
import com.computer.inu.sqkakaotalk.SharedPreferenceController
import com.computer.inu.sqkakaotalk.delete.DeleteFriendInfoResponse
import com.computer.inu.sqkakaotalk.network.ApplicationController
import com.computer.inu.sqkakaotalk.network.SqNetworkService
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.toast
import org.jetbrains.anko.toast
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response



class  BanFriendListRecyclerViewAdapter(val ctx: Context, val dataList: ArrayList<FavoriteFriendData>) : RecyclerView.Adapter<BanFriendListRecyclerViewAdapter.Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(ctx).inflate(R.layout.rv_item_banfriendlist, parent, false)
        return Holder(view)
    }


    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.name.text =dataList[position].Name
        holder.content.text=dataList[position].Status
        holder.noban.setOnClickListener {

/*            val position = viewHolder.adapterPosition
            deleteFriendPost(FriendData[position].Email.toString())
            FriendData.removeAt(position)
            FriendListRecyclerViewAdapter.notifyItemRemoved(position)*/
            recoverFriendPost(dataList[position].Email.toString())  //차단해제 통신
            (ctx as Activity).finish()


        }
        Glide.with(ctx).load(dataList[position].Prof_img).into(holder.image)
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val name: TextView = itemView.findViewById(R.id.rv_tv_banfriendlistfriendname) as TextView
        val content : TextView =itemView.findViewById(R.id.rv_tv_banfriendlist_friendcontents) as TextView
        val image : ImageView =itemView.findViewById(R.id.rv_iv_banfriendlist_friendpicture) as ImageView
        val noban : TextView=itemView.findViewById(R.id.tv_banfriendlist_notban) as TextView


    }
    fun recoverFriendPost(Friend_Email:String){
        val SqnetworkService: SqNetworkService by lazy {
            ApplicationController.instance.SqnetworkService
        }
        var jsonObject = JSONObject()
        jsonObject.put("Friend_Email",Friend_Email)

        val gsonObject = JsonParser().parse(jsonObject.toString()) as JsonObject
        var deleteFriendResponse: Call<DeleteFriendInfoResponse> = SqnetworkService.RecoverFriendInfoResponse("application/json",
            SharedPreferenceController.getSQAuthorization(ctx),gsonObject)
        deleteFriendResponse.enqueue(object : Callback<DeleteFriendInfoResponse> {
            override fun onResponse(call: Call<DeleteFriendInfoResponse>?, response: Response<DeleteFriendInfoResponse>?) {
                Log.v("TAG", "차단해제")
                if (response!!.isSuccessful) {
                    if(response.body()!!.message=="성공") {

                       ctx.toast("차단 해제")
                    }
                }

            }
            override fun onFailure(call: Call<DeleteFriendInfoResponse>?, t: Throwable?) {
                Log.v("TAG", "통신 실패 = " +t.toString())
            }
        })
    }
}