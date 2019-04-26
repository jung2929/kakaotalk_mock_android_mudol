package com.computer.inu.sqkakaotalk

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.bumptech.glide.Glide
import com.computer.inu.sqkakaotalk.Adapter.FriendListRecyclerViewAdapter
import com.computer.inu.sqkakaotalk.Data.FriendData
import com.computer.inu.sqkakaotalk.Fragment.FriendListFragment
import com.computer.inu.sqkakaotalk.get.GetFriendResponse
import com.computer.inu.sqkakaotalk.network.ApplicationController
import com.computer.inu.sqkakaotalk.network.SqNetworkService
import com.computer.inu.sqkakaotalk.post.PostFavoriteResponse
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import kotlinx.android.synthetic.main.activity_friend_list_fragment.*
import kotlinx.android.synthetic.main.activity_friend_profile.*
import kotlinx.android.synthetic.main.activity_myprofile.*
import kotlinx.android.synthetic.main.activity_sign_up2.*
import org.jetbrains.anko.ctx
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.toast
import org.jetbrains.anko.toast
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FriendProfileActivity : AppCompatActivity() {
    val SqnetworkService: SqNetworkService by lazy {
        ApplicationController.instance.SqnetworkService
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_friend_profile)
        iv_idfinish.setOnClickListener {
            finish()
        }
        iv_friendprofile_star.setOnClickListener {
            addFavoriteopost() //즐찾 추가

        }
        getFriendInfopost()
    }
    fun getFriendInfopost() {
        var getFriendListResponse : Call<GetFriendResponse> = SqnetworkService.getFriendResponse("application/json",SharedPreferenceController.getSQAuthorization(ctx))
        getFriendListResponse.enqueue(object : Callback<GetFriendResponse> {
            override fun onResponse(call: Call<GetFriendResponse>?, response: Response<GetFriendResponse>?) {
                Log.v("TAG", "친구정보 받아오기")

                if (response!!.isSuccessful) {
              var position  =    intent.getIntExtra("position",-1)
                    tv_friendprofile_friendname.setText(response.body()!!.result!![position].Name)
                    tv_friendprofile_phonenumber.setText(response.body()!!.result!![position].Email)
                    tv_friendprofile_status.setText(response.body()!!.result!![position].Status)
                    Glide.with(ctx).load(response.body()!!.result!![position].Prof_img.toString()).into(iv_friendprofile_friendpicture)
                    Glide.with(ctx).load(response.body()!!.result!![position].Back_img.toString()).into(tv_friendprofile_background)
                  }
                }


            override fun onFailure(call: Call<GetFriendResponse>?, t: Throwable?) {
                Log.v("TAG", "통신 실패 = " + t.toString())
                toast("통신실패")
            }
        })
    }
    fun addFavoriteopost() {
        var jsonObject = JSONObject()
        jsonObject.put("Friend_Email", tv_friendprofile_phonenumber.text.toString())

        val gsonObject = JsonParser().parse(jsonObject.toString()) as JsonObject
        var addFavoriteResponse : Call<PostFavoriteResponse> = SqnetworkService.postFavoriteResponse("application/json",SharedPreferenceController.getSQAuthorization(ctx),gsonObject)
        addFavoriteResponse.enqueue(object : Callback<PostFavoriteResponse> {
            override fun onResponse(call: Call<PostFavoriteResponse>?, response: Response<PostFavoriteResponse>?) {
                Log.v("TAG", "즐겨찾기 추가")

                if (response!!.isSuccessful) {
                  toast("즐겨찾기 성공")
                }
            }


            override fun onFailure(call: Call<PostFavoriteResponse>?, t: Throwable?) {
                Log.v("TAG", "통신 실패 = " + t.toString())
                toast("통신실패")
            }
        })
    }
}
