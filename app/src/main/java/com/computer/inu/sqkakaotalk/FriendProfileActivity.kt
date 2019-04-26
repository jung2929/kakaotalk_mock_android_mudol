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
import kotlinx.android.synthetic.main.activity_friend_list_fragment.*
import kotlinx.android.synthetic.main.activity_friend_profile.*
import kotlinx.android.synthetic.main.activity_myprofile.*
import org.jetbrains.anko.ctx
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.toast
import org.jetbrains.anko.toast
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
}
