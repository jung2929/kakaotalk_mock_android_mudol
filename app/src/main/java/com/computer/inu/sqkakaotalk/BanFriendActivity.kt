package com.computer.inu.sqkakaotalk

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.computer.inu.sqkakaotalk.Adapter.BanFriendListRecyclerViewAdapter
import com.computer.inu.sqkakaotalk.Adapter.FavoriteFriendListRecyclerViewAdapter
import com.computer.inu.sqkakaotalk.Data.FavoriteFriendData
import com.computer.inu.sqkakaotalk.get.GetDeleteFriendInfoResponse
import com.computer.inu.sqkakaotalk.get.GetFavoriteResponse
import com.computer.inu.sqkakaotalk.network.ApplicationController
import com.computer.inu.sqkakaotalk.network.SqNetworkService
import kotlinx.android.synthetic.main.activity_ban_friend.*
import kotlinx.android.synthetic.main.activity_friend_list_fragment.*
import org.jetbrains.anko.ctx
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.toast
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BanFriendActivity : AppCompatActivity() {
    val SqnetworkService: SqNetworkService by lazy {
        ApplicationController.instance.SqnetworkService
    }
    val FavoriteFriendData : ArrayList<FavoriteFriendData> by lazy {
        ArrayList<FavoriteFriendData>()
    }
    lateinit var BanFriendListRecyclerViewAdapter: BanFriendListRecyclerViewAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ban_friend)
        getBanFriendListpost()
    }
    fun getBanFriendListpost() {
        var getBanFriendListResponse : Call<GetDeleteFriendInfoResponse> = SqnetworkService.getDeleteFriendInfoResponse("application/json",SharedPreferenceController.getSQAuthorization(ctx))
        getBanFriendListResponse.enqueue(object : Callback<GetDeleteFriendInfoResponse> {
            override fun onResponse(call: Call<GetDeleteFriendInfoResponse>?, response: Response<GetDeleteFriendInfoResponse>?) {
                Log.v("TAG", "차단친구목록 불러오기")

                if (response!!.isSuccessful) {

                    FavoriteFriendData.clear()


                    val  FavoriteFriendDataList = response.body()!!.result!!

                    for (i in 0..FavoriteFriendDataList.size-1) {
                        FavoriteFriendData.add(FavoriteFriendData(FavoriteFriendDataList[i].Email,FavoriteFriendDataList[i].Name,FavoriteFriendDataList[i].Prof_img,FavoriteFriendDataList[i].Back_img,FavoriteFriendDataList[i].Status))
                    }

                    BanFriendListRecyclerViewAdapter= BanFriendListRecyclerViewAdapter(this@BanFriendActivity, FavoriteFriendData)
                    rv_banfriend.adapter = BanFriendListRecyclerViewAdapter
                    rv_banfriend.layoutManager = LinearLayoutManager(this@BanFriendActivity)
                }else {

                }
            }

            override fun onFailure(call: Call<GetDeleteFriendInfoResponse>?, t: Throwable?) {
                Log.v("TAG", "통신 실패 = " + t.toString())
                toast("통신실패")
            }
        })
    }
}
