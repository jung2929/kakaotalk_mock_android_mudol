package com.computer.inu.sqkakaotalk.Fragment

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.computer.inu.sqkakaotalk.*
import com.computer.inu.sqkakaotalk.Adapter.BirthdayFriendListRecyclerViewAdapter
import com.computer.inu.sqkakaotalk.Adapter.EmoticonShopRecyclerViewAdapter
import com.computer.inu.sqkakaotalk.Adapter.FavoriteFriendListRecyclerViewAdapter
import com.computer.inu.sqkakaotalk.Adapter.FriendListRecyclerViewAdapter
import com.computer.inu.sqkakaotalk.Data.*
import com.computer.inu.sqkakaotalk.Main.MainActivity
import com.computer.inu.sqkakaotalk.delete.DeleteFriendInfoResponse
import com.computer.inu.sqkakaotalk.get.GetEmoticonResponse
import com.computer.inu.sqkakaotalk.get.GetFriendResponse
import com.computer.inu.sqkakaotalk.get.GetUserInfomationResponse
import com.computer.inu.sqkakaotalk.get.GetprofileResponse
import com.computer.inu.sqkakaotalk.network.ApplicationController
import com.computer.inu.sqkakaotalk.network.NetworkService
import com.computer.inu.sqkakaotalk.network.SqNetworkService
import com.computer.inu.sqkakaotalk.post.PostChatResponse
import com.computer.inu.sqkakaotalk.post.PostLoginResponse
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import kotlinx.android.synthetic.main.activity_emoticon_shop.*
import kotlinx.android.synthetic.main.activity_friend_list_fragment.*
import kotlinx.android.synthetic.main.activity_friend_list_fragment.view.*
import kotlinx.android.synthetic.main.activity_in_message.*
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class FriendListFragment : Fragment() {
    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }
    val SqnetworkService: SqNetworkService by lazy {
        ApplicationController.instance.SqnetworkService
    }
    companion object {
        private var instance : FriendListFragment?=null
        @Synchronized
        fun getInstance(size : String) :FriendListFragment{
            if(instance==null) {
                instance = FriendListFragment().apply {
                    arguments=Bundle().apply {
                        putString("size", FriendData.size.toString())
                    }

                }
            }
            return instance!!

        }

        val FriendData : ArrayList<FriendData> by lazy {
            ArrayList<FriendData>()
        }
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


        getFriendListpost()
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
                   toast( FriendData[position].Email.toString())
                    FriendData.removeAt(position)
                    FriendListRecyclerViewAdapter.notifyItemRemoved(position)
                    tv_friend_list_count.setText(FriendData.size.toString())
                    deleteFriendPost(FriendData[position].Email.toString())

                }
            }

        val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)
        itemTouchHelper.attachToRecyclerView(homeFragmentView.rl_friend_list_listpeople)




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

        iv_friend_addid.setOnClickListener {
       /*     val fragment = FriendListFragment() // Fragment 생성
            val bundle = Bundle(1) // 파라미터는 전달할 데이터 개수
            bundle.putString("size", FriendData.size.toString())
            fragment.setArguments(bundle)*/
            var intent = Intent(ctx,AddKakaotalkIdActivity::class.java)
            intent.putExtra("size", FriendData.size)
            startActivity(intent)
        }
        ll_friend_addfriend.visibility = View.GONE

        iv_friend_finishaddfriend.setOnClickListener {
            ll_friend_addfriend.visibility = View.GONE     //친구추가 X 버튼
            rl_friend_color.visibility = View.GONE
        }
        rl_friend_color.visibility = View.GONE
        iv_friend_plusfriend.setOnClickListener {
            ll_friend_addfriend.visibility = View.VISIBLE
            rl_friend_color.visibility = View.VISIBLE
        }
        rl_friend_color.setOnClickListener {
            ll_friend_addfriend.visibility = View.GONE     //친구추가 X 버튼
            rl_friend_color.visibility = View.GONE
        }


        iv_friend_birthdayfriendlis_button.setOnClickListener {
            if (rl_friend_list_birthdatpeople.visibility == View.VISIBLE) {
                iv_friend_birthdayfriendlis_button.setImageResource(R.drawable.downbutton)
                rl_friend_list_birthdatpeople.visibility = View.GONE

            } else if (rl_friend_list_birthdatpeople.visibility == View.GONE) {
                iv_friend_birthdayfriendlis_button.setImageResource(R.drawable.upbutton)
                rl_friend_list_birthdatpeople.visibility = View.VISIBLE

            }
        }
        iv_friend_favorite_button.setOnClickListener {
            if (rl_friend_list_favoritepeople.visibility == View.VISIBLE) {
                rl_friend_list_favoritepeople.visibility = View.GONE
                iv_friend_favorite_button.setImageResource(R.drawable.downbutton)
            } else if (rl_friend_list_favoritepeople.visibility == View.GONE) {
                rl_friend_list_favoritepeople.visibility = View.VISIBLE
                iv_friend_favorite_button.setImageResource(R.drawable.upbutton)
            }
        }
        iv_friend_recommend_button.setOnClickListener {
            if (rl_friend_list_recommend.visibility == View.VISIBLE) {
                rl_friend_list_recommend.visibility = View.GONE
                iv_friend_recommend_button.setImageResource(R.drawable.downbutton)
            } else if (rl_friend_list_recommend.visibility == View.GONE) {
                rl_friend_list_recommend.visibility = View.VISIBLE
                iv_friend_recommend_button.setImageResource(R.drawable.upbutton)
            }
        }
        iv_friend_plus_button.setOnClickListener {
            if (rl_friend_list_plus.visibility == View.VISIBLE) {
                rl_friend_list_plus.visibility = View.GONE
                iv_friend_plus_button.setImageResource(R.drawable.downbutton)
            } else if (rl_friend_list_plus.visibility == View.GONE) {
                rl_friend_list_plus.visibility = View.VISIBLE
                iv_friend_plus_button.setImageResource(R.drawable.upbutton)
            }
        }
        iv_friend_list_button.setOnClickListener {
            if (rl_friend_list_listpeople.visibility == View.VISIBLE) {
                rl_friend_list_listpeople.visibility = View.GONE
                iv_friend_list_button.setImageResource(R.drawable.downbutton)
            } else if (rl_friend_list_listpeople.visibility == View.GONE) {
                rl_friend_list_listpeople.visibility = View.VISIBLE
                iv_friend_list_button.setImageResource(R.drawable.upbutton)
            }
        }
        rl_friend_list_myprofile.setOnClickListener {
            val intent = Intent(ctx, MyprofileActivity::class.java)
            ctx.startActivity(intent)
            (ctx as MainActivity).overridePendingTransition(R.anim.sliding_up, R.anim.stay)
        }
        if (SharedPreferenceController.getKaKaOAuthorization(ctx).isNotEmpty()) { //카카오 로그인일때 통신
            getUserKAKAOInfoPost()
        } else if(SharedPreferenceController.getSQAuthorization(ctx).isNotEmpty()){
            getMyProfile()  //sq 통신
            getMyprofilePost()
        }
    }
    fun getFriendListpost() {
        var getFriendListResponse : Call<GetFriendResponse> = SqnetworkService.getFriendResponse("application/json",SharedPreferenceController.getSQAuthorization(ctx))
        getFriendListResponse.enqueue(object : Callback<GetFriendResponse> {
            override fun onResponse(call: Call<GetFriendResponse>?, response: Response<GetFriendResponse>?) {
                Log.v("TAG", "친구목록 불러오기")

                if (response!!.isSuccessful) {

                    FriendData.clear()


                val  FriendDataList = response.body()!!.result!!

                    for (i in 0..FriendDataList.size - 1) {
                        FriendData.add(FriendData(FriendDataList[i].Name,FriendDataList[i].Email,FriendDataList[i].Prof_img,FriendDataList[i].Back_img,FriendDataList[i].Status))
                    }
                    FriendListRecyclerViewAdapter= FriendListRecyclerViewAdapter(context!!, FriendData)
                    rl_friend_list_listpeople.adapter = FriendListRecyclerViewAdapter
                    rl_friend_list_listpeople.layoutManager = LinearLayoutManager(context!!)
                    tv_friend_list_count.setText(FriendData.size.toString())
                }else {

                }
            }

            override fun onFailure(call: Call<GetFriendResponse>?, t: Throwable?) {
                Log.v("TAG", "통신 실패 = " + t.toString())
                toast("통신실패")
            }
        })
    }
    fun deleteFriendPost(Friend_Email:String){
        var deleteFriendResponse: Call<DeleteFriendInfoResponse> = SqnetworkService.deleteFriendInfoResponse("application/json",SharedPreferenceController.getSQAuthorization(ctx),Friend_Email)
        deleteFriendResponse.enqueue(object : Callback<DeleteFriendInfoResponse> {
            override fun onResponse(call: Call<DeleteFriendInfoResponse>?, response: Response<DeleteFriendInfoResponse>?) {
                Log.v("TAG", "보드 서버 통신 연결")
                if (response!!.isSuccessful) {
                    if(response.body()!!.message=="성공") {


                    }
                }

            }
            override fun onFailure(call: Call<DeleteFriendInfoResponse>?, t: Throwable?) {
                Log.v("TAG", "통신 실패 = " +t.toString())
            }
        })
    }
    fun getMyprofilePost(){
        var jsonObject = JSONObject()
        jsonObject.put("Email",SharedPreferenceController.getEmail(ctx))
        jsonObject.put("Pw", SharedPreferenceController.getPW(ctx))

//Gson 라이브러리의 Json Parser을 통해 객체를 Json으로!
        val gsonObject = JsonParser().parse(jsonObject.toString()) as JsonObject
        var postLoginResponse: Call<PostLoginResponse> = SqnetworkService.postLoginResponse("application/json",gsonObject)
        postLoginResponse.enqueue(object : Callback<PostLoginResponse> {
            override fun onResponse(call: Call<PostLoginResponse>?, response: Response<PostLoginResponse>?) {
                Log.v("TAG", "보드 서버 통신 연결")
                if (response!!.isSuccessful) {
                    if(response.body()!!.message=="성공") {

                                tv_friend_myname.setText(response.body()!!.result[0].Name.toString())

                    }
                }

            }
            override fun onFailure(call: Call<PostLoginResponse>?, t: Throwable?) {
                Log.v("TAG", "통신 실패 = " +t.toString())
            }
        })
    }
    fun getMyProfile(){
        var getProfileResponse: Call<GetprofileResponse> = SqnetworkService.getprofileResponse("application/json",SharedPreferenceController.getSQAuthorization(ctx))
        getProfileResponse.enqueue(object : Callback<GetprofileResponse> {
            override fun onResponse(call: Call<GetprofileResponse>?, response: Response<GetprofileResponse>?) {
                if (response!!.isSuccessful) {
                    if(response.body()!!.message=="성공"){

                        Glide.with(ctx).load(response.body()!!.result.Prof_img.toString()).into(iv_friend_mypicture)
                    }
                }
                else{
                    Log.v("TAG", "채팅 실패")
                }
            }
            override fun onFailure(call: Call<GetprofileResponse>?, t: Throwable?) {
                Log.v("TAG", "통신 실패 = " +t.toString())
            }
        })
    }



    fun getUserKAKAOInfoPost(){
        var getUserInfomationResponse: Call<GetUserInfomationResponse> = networkService.getUserInfomationResponse("Bearer "+SharedPreferenceController.getKaKaOAuthorization(ctx))
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
/*        if (SharedPreferenceController.getIMAGE(ctx).isNotEmpty()){  //통신전 사진 이미지
            val decodedString = Base64.decode(SharedPreferenceController.getIMAGE(ctx), Base64.DEFAULT)
            val decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
            iv_friend_mypicture.setImageBitmap(decodedByte)
        }*/
    }

}