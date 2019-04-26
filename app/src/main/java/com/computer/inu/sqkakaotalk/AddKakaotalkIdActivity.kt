package com.computer.inu.sqkakaotalk

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_add_kakaotalk_id.*
import android.text.InputType
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.content.Intent
import android.util.Log
import com.bumptech.glide.Glide
import com.computer.inu.sqkakaotalk.Data.FriendData
import com.computer.inu.sqkakaotalk.Main.MainActivity
import com.computer.inu.sqkakaotalk.network.ApplicationController
import com.computer.inu.sqkakaotalk.network.SqNetworkService
import com.computer.inu.sqkakaotalk.post.PostAddFriendResponse
import com.computer.inu.sqkakaotalk.post.PostSignUpResponse
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import kotlinx.android.synthetic.main.activity_friend_list_fragment.*
import kotlinx.android.synthetic.main.activity_sign_up2.*
import org.jetbrains.anko.toast
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddKakaotalkIdActivity : AppCompatActivity() {
    val SqnetworkService: SqNetworkService by lazy {
        ApplicationController.instance.SqnetworkService
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_kakaotalk_id)
        rl_add_myfriend.visibility=View.GONE
        ll_add_cantfind.visibility=View.GONE
        et_addkakaotalk_id.requestFocus()
        et_addkakaotalk_id.inputType = InputType.TYPE_CLASS_TEXT
        et_addkakaotalk_id.imeOptions = EditorInfo.IME_ACTION_SEARCH



        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
        et_addkakaotalk_id.setOnEditorActionListener({ textView, action, event ->
            var handled = false
            if (action == EditorInfo.IME_ACTION_SEARCH) {
                AddFriendPost()
         /*       if (et_addkakaotalk_id.text.toString() == "ejh9853") {
                    ll_add_myid.visibility=View.GONE
                    ll_add_cantfind.visibility=View.GONE
                    rl_add_myfriend.visibility=View.VISIBLE

                } else{
                    ll_add_myid.visibility=View.GONE
                    ll_add_cantfind.visibility=View.VISIBLE
                }*/
                val imm = applicationContext?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
                imm!!.hideSoftInputFromWindow(et_addkakaotalk_id.getWindowToken(), 0)
            }
            handled
        })

        btn_add_addfriend.setOnClickListener {
            val intent = Intent(this@AddKakaotalkIdActivity, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

            startActivity(intent)
        }

        iv_kakaotalk_finishaddfriend.setOnClickListener {
            finish()
        }

    }

    fun AddFriendPost(){ // 이메일로 친구추가
        var jsonObject = JSONObject()
        jsonObject.put("Friend_Email", et_addkakaotalk_id.text.toString())
        jsonObject.put("Tel", "")

        val gsonObject = JsonParser().parse(jsonObject.toString()) as JsonObject
        var addfriendPost: Call<PostAddFriendResponse> = SqnetworkService.postAddFriendResponse("application/json",SharedPreferenceController.getSQAuthorization(this),gsonObject)
        addfriendPost.enqueue(object : Callback<PostAddFriendResponse> {
            override fun onResponse(call: Call<PostAddFriendResponse>?, response: Response<PostAddFriendResponse>?) {
                if (response!!.isSuccessful) {

                    if (response.body()!!.result.isNotEmpty()) {

                        ll_add_myid.visibility=View.GONE
                        ll_add_cantfind.visibility=View.GONE
                        rl_add_myfriend.visibility=View.VISIBLE
                        toast(response.body()!!.result[intent.getIntExtra("size",-1)].Name)
                        Glide.with(this@AddKakaotalkIdActivity).load(response.body()!!.result[intent.getIntExtra("size",-1)].Prof_img.toString()).into(iv_add_friend_pic)
                        tv_add_frinedname.setText(response.body()!!.result[intent.getIntExtra("size",-1)].Name)

                    } else{
                        ll_add_myid.visibility=View.GONE
                        ll_add_cantfind.visibility=View.VISIBLE
                    }

                }
                else{
                    Log.v("TAG", "친구추가 통신 실패")
                }
            }
            override fun onFailure(call: Call<PostAddFriendResponse>?, t: Throwable?) {
                Log.v("TAG", "통신 실패 = " +t.toString())
            }
        })
    }
}
