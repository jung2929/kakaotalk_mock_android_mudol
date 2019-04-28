package com.computer.inu.sqkakaotalk

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import com.computer.inu.sqkakaotalk.delete.DeleteFavoriteResponse
import com.computer.inu.sqkakaotalk.get.GetFavoriteResponse
import com.computer.inu.sqkakaotalk.get.GetFriendResponse
import com.computer.inu.sqkakaotalk.network.ApplicationController
import com.computer.inu.sqkakaotalk.network.SqNetworkService
import com.computer.inu.sqkakaotalk.post.PostFavoriteResponse
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import kotlinx.android.synthetic.main.activity_favorite_friend.*
import kotlinx.android.synthetic.main.activity_friend_profile.*
import org.jetbrains.anko.ctx
import org.jetbrains.anko.toast
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FavoriteFriendActivity : AppCompatActivity() {
    val SqnetworkService: SqNetworkService by lazy {
        ApplicationController.instance.SqnetworkService
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite_friend)
        getFavoriteFriendInfopost()
        iv_FVfavorite_idfinish.setOnClickListener {
            finish()
        }
        iv_FVfriendprofile_star.setOnClickListener {
            deleteFavoriteopost()  //즐찾 삭제
        }
    }

    fun getFavoriteFriendInfopost() {
        var getFriendListResponse : Call<GetFavoriteResponse> = SqnetworkService.getFavoriteResponse("application/json",SharedPreferenceController.getSQAuthorization(ctx))
        getFriendListResponse.enqueue(object : Callback<GetFavoriteResponse> {
            override fun onResponse(call: Call<GetFavoriteResponse>?, response: Response<GetFavoriteResponse>?) {
                Log.v("TAG", "친구정보 받아오기")

                if (response!!.isSuccessful) {
                    var position  =    intent.getIntExtra("position",-1)
                    tv_FVfriendprofile_friendname.setText(response.body()!!.result!![position].Name)
                    tv_FVfriendprofile_phonenumber.setText(response.body()!!.result!![position].Email)
                    tv_FVfriendprofile_status.setText(response.body()!!.result!![position].Status)
                    Glide.with(ctx).load(response.body()!!.result!![position].Prof_img.toString()).into(iv_FVfriendprofile_friendpicture)
                    Glide.with(ctx).load(response.body()!!.result!![position].Back_img.toString()).into(tv_FVfriendprofile_background)
                }
            }


            override fun onFailure(call: Call<GetFavoriteResponse>?, t: Throwable?) {
                Log.v("TAG", "통신 실패 = " + t.toString())
                toast("통신실패")
            }
        })
    }
    fun deleteFavoriteopost() {
        var jsonObject = JSONObject()
        jsonObject.put("Friend_Email", tv_FVfriendprofile_phonenumber.text.toString())

        val gsonObject = JsonParser().parse(jsonObject.toString()) as JsonObject
        var deleteFavoriteResponse : Call<DeleteFavoriteResponse> = SqnetworkService.deleteFavoriteResponse("application/json",SharedPreferenceController.getSQAuthorization(ctx),gsonObject)
        deleteFavoriteResponse.enqueue(object : Callback<DeleteFavoriteResponse> {
            override fun onResponse(call: Call<DeleteFavoriteResponse>?, response: Response<DeleteFavoriteResponse>?) {
                Log.v("TAG", "즐겨찾기 삭제")

                if (response!!.isSuccessful) {
                    toast("즐겨찾기 삭제")
                }
            }


            override fun onFailure(call: Call<DeleteFavoriteResponse>?, t: Throwable?) {
                Log.v("TAG", "통신 실패 = " + t.toString())
                toast("통신실패")
            }
        })
    }



}
