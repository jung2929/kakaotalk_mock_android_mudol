package com.computer.inu.sqkakaotalk.Fragment

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.computer.inu.sqkakaotalk.*
import com.computer.inu.sqkakaotalk.Adapter.BirthdayFriendListRecyclerViewAdapter
import com.computer.inu.sqkakaotalk.Adapter.FavoriteFriendListRecyclerViewAdapter
import com.computer.inu.sqkakaotalk.Adapter.FriendListRecyclerViewAdapter
import com.computer.inu.sqkakaotalk.Data.BirthdayFriendData
import com.computer.inu.sqkakaotalk.Data.FavoriteFriendData
import com.computer.inu.sqkakaotalk.Data.FriendData
import com.computer.inu.sqkakaotalk.get.GetUserInfomationResponse
import com.computer.inu.sqkakaotalk.network.ApplicationController
import com.computer.inu.sqkakaotalk.network.NetworkService
import kotlinx.android.synthetic.main.activity_friend_list_fragment.*
import kotlinx.android.synthetic.main.activity_friend_list_fragment.view.*
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class FriendListFragment : Fragment() {
    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }
    lateinit var BirthdayFriendListRecyclerViewAdapter: BirthdayFriendListRecyclerViewAdapter
    lateinit var FavoriteFriendListRecyclerViewAdapter: FavoriteFriendListRecyclerViewAdapter
    lateinit var FriendListRecyclerViewAdapter: FriendListRecyclerViewAdapter
    val BirthdayFriendData : ArrayList<BirthdayFriendData> by lazy {
        ArrayList<BirthdayFriendData>()
    }
    val FavoriteFriendData : ArrayList<FavoriteFriendData> by lazy {
        ArrayList<FavoriteFriendData>()
    }
    val FriendData : ArrayList<FriendData> by lazy {
        ArrayList<FriendData>()
    }

    override fun onPause() {
        super.onPause()


    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val homeFragmentView: View = inflater!!.inflate(R.layout.activity_friend_list_fragment, container, false)
        BirthdayFriendListRecyclerViewAdapter=
            BirthdayFriendListRecyclerViewAdapter(context!!, BirthdayFriendData)
        BirthdayFriendData.add(BirthdayFriendData("곽민", "2019년 행복하길"))
        homeFragmentView.rl_friend_list_birthdatpeople.adapter = BirthdayFriendListRecyclerViewAdapter
        homeFragmentView.rl_friend_list_birthdatpeople.layoutManager = LinearLayoutManager(context!!)



   if (SharedPreferenceController.getFriendName(ctx).isNotEmpty()){
       FriendData.add(FriendData(SharedPreferenceController.getFriendName(ctx), "새로운 친구입니다."))
       SharedPreferenceController.FriendNameclear(ctx)
   }
        val simpleItemTouchCallback =
            object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    toast("on Move")
                    return true
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {

                    val position = viewHolder.adapterPosition
                    toast(position.toString())
                    FriendData.removeAt(position)
                    FriendListRecyclerViewAdapter.notifyItemRemoved(position)
                    tv_friend_list_count.setText(FriendData.size.toString())

                }
            }

        val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)
        itemTouchHelper.attachToRecyclerView(homeFragmentView.rl_friend_list_listpeople)


        FriendListRecyclerViewAdapter=
            FriendListRecyclerViewAdapter(context!!, FriendData)
        FriendData.add(FriendData("곽민", "2019년 행복하길"))
        FriendData.add(FriendData("고성진", "인천놀러와!~"))
        FriendData.add(FriendData("동기", ""))
        FriendData.add(FriendData("엄마", ""))
        FriendData.add(FriendData("아빠", "전진통하라"))
        FriendData.add(FriendData("2-7", ""))
        homeFragmentView.rl_friend_list_listpeople.adapter = FriendListRecyclerViewAdapter
        homeFragmentView.rl_friend_list_listpeople.layoutManager = LinearLayoutManager(context!!)
        FavoriteFriendListRecyclerViewAdapter=
            FavoriteFriendListRecyclerViewAdapter(context!!, FavoriteFriendData)
        FavoriteFriendData.add(FavoriteFriendData("엄마", ""))
        FavoriteFriendData.add(FavoriteFriendData("아빠", "전진통하라"))
        FavoriteFriendData.add(FavoriteFriendData("2-7", ""))
        FavoriteFriendData.add(FavoriteFriendData("동기", ""))
        homeFragmentView.rl_friend_list_favoritepeople.adapter = FavoriteFriendListRecyclerViewAdapter
        homeFragmentView.rl_friend_list_favoritepeople.layoutManager = LinearLayoutManager(context!!)
        return homeFragmentView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
   /*     if (SharedPreferenceController.getIMAGE(ctx).isNotEmpty()){  //통신 이전 부분
            val decodedString = Base64.decode(SharedPreferenceController.getIMAGE(ctx), Base64.DEFAULT)
            val decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
            iv_friend_mypicture.setImageBitmap(decodedByte)
        }*/
        tv_friend_list_count.setText(FriendData.size.toString())
        iv_friend_addid.setOnClickListener {
            startActivity<AddKakaotalkIdActivity>()
        }
        ll_friend_addfriend.visibility=View.GONE

        iv_friend_finishaddfriend.setOnClickListener {
            ll_friend_addfriend.visibility=View.GONE     //친구추가 X 버튼
            rl_friend_color.visibility=View.GONE
        }
        rl_friend_color.visibility=View.GONE
        iv_friend_plusfriend.setOnClickListener {
            ll_friend_addfriend.visibility=View.VISIBLE
            rl_friend_color.visibility=View.VISIBLE
        }
        rl_friend_color.setOnClickListener {
            ll_friend_addfriend.visibility=View.GONE     //친구추가 X 버튼
            rl_friend_color.visibility=View.GONE
        }


        iv_friend_birthdayfriendlis_button.setOnClickListener {
            if(rl_friend_list_birthdatpeople.visibility==View.VISIBLE){
                iv_friend_birthdayfriendlis_button.setImageResource(R.drawable.downbutton)
                rl_friend_list_birthdatpeople.visibility=View.GONE

            }else if (rl_friend_list_birthdatpeople.visibility==View.GONE){
                iv_friend_birthdayfriendlis_button.setImageResource(R.drawable.upbutton)
                rl_friend_list_birthdatpeople.visibility=View.VISIBLE

            }
        }
        iv_friend_favorite_button.setOnClickListener {
            if(rl_friend_list_favoritepeople.visibility==View.VISIBLE){
                rl_friend_list_favoritepeople.visibility=View.GONE
                iv_friend_favorite_button.setImageResource(R.drawable.downbutton)
            }else if (rl_friend_list_favoritepeople.visibility==View.GONE){
                rl_friend_list_favoritepeople.visibility=View.VISIBLE
                iv_friend_favorite_button.setImageResource(R.drawable.upbutton)
            }
        }
        iv_friend_recommend_button.setOnClickListener {
            if(rl_friend_list_recommend.visibility==View.VISIBLE){
                rl_friend_list_recommend.visibility=View.GONE
                iv_friend_recommend_button.setImageResource(R.drawable.downbutton)
            }else if (rl_friend_list_recommend.visibility==View.GONE){
                rl_friend_list_recommend.visibility=View.VISIBLE
                iv_friend_recommend_button.setImageResource(R.drawable.upbutton)
            }
        }
        iv_friend_plus_button.setOnClickListener {
            if(rl_friend_list_plus.visibility==View.VISIBLE){
                rl_friend_list_plus.visibility=View.GONE
                iv_friend_plus_button.setImageResource(R.drawable.downbutton)
            }else if (rl_friend_list_plus.visibility==View.GONE){
                rl_friend_list_plus.visibility=View.VISIBLE
                iv_friend_plus_button.setImageResource(R.drawable.upbutton)
            }
        }
        iv_friend_list_button.setOnClickListener {
            if(rl_friend_list_listpeople.visibility==View.VISIBLE){
                rl_friend_list_listpeople.visibility=View.GONE
                iv_friend_list_button.setImageResource(R.drawable.downbutton)
            }else if (rl_friend_list_listpeople.visibility==View.GONE){
                rl_friend_list_listpeople.visibility=View.VISIBLE
                iv_friend_list_button.setImageResource(R.drawable.upbutton)
            }
        }
        rl_friend_list_myprofile.setOnClickListener {
          val intent = Intent(ctx,MyprofileActivity::class.java)
          ctx.startActivity(intent)
            (ctx as MainActivity).overridePendingTransition(R.anim.sliding_up,R.anim.stay  )
        }
        getUserInfoPost()
    }
    fun getUserInfoPost(){
        var getUserInfomationResponse: Call<GetUserInfomationResponse> = networkService.getUserInfomationResponse("Bearer "+SharedPreferenceController.getAutoAuthorization(ctx))
        getUserInfomationResponse.enqueue(object : Callback<GetUserInfomationResponse> {
            override fun onResponse(call: Call<GetUserInfomationResponse>?, response: Response<GetUserInfomationResponse>?) {
                Log.v("TAG", "보드 서버 통신 연결")
                if (response!!.isSuccessful) {
                    tv_friend_myname.text = response.body()!!.properties.nickname
                    Glide.with(ctx).load(response.body()!!.properties.profile_image.toString()).into(iv_friend_mypicture)
                }
                else{
                    Log.v("TAG", "마이페이지 서버 값 전달 실패")
                }
            }
            override fun onFailure(call: Call<GetUserInfomationResponse>?, t: Throwable?) {
                Log.v("TAG", "통신 실패 = " +t.toString())
            }
        })
    }
    override fun onResume() {
        super.onResume()
        ll_friend_addfriend.visibility=View.GONE     //친구추가 X 버튼
        rl_friend_color.visibility=View.GONE
        if (SharedPreferenceController.getIMAGE(ctx).isNotEmpty()){
            val decodedString = Base64.decode(SharedPreferenceController.getIMAGE(ctx), Base64.DEFAULT)
            val decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
            iv_friend_mypicture.setImageBitmap(decodedByte)
        }
    }

}