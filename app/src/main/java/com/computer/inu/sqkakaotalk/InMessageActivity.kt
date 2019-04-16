package com.computer.inu.sqkakaotalk

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import com.computer.inu.sqkakaotalk.Main.MainActivity
import com.computer.inu.sqkakaotalk.network.ApplicationController
import com.computer.inu.sqkakaotalk.network.SqNetworkService
import com.computer.inu.sqkakaotalk.post.PostChatResponse
import com.computer.inu.sqkakaotalk.post.PostLoginResponse
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import kotlinx.android.synthetic.main.activity_in_message.*
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.ctx
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class InMessageActivity : AppCompatActivity() {
    val SqnetworkService: SqNetworkService by lazy {
        ApplicationController.instance.SqnetworkService
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_in_message)
        if(SharedPreferenceController.getSQAuthorization(this).isNotEmpty()){
        tv_inmessage_name.setText(intent.getStringExtra("name"))

            edt_message.setOnEditorActionListener({ textView, action, event ->
                var handled = false
                if (action == EditorInfo.IME_ACTION_DONE) {
                    chattingPost()
                    edt_message.setText("")
                }
                handled
            })
        }
    }

    override fun onCreateView(name: String?, context: Context?, attrs: AttributeSet?): View? {
        return super.onCreateView(name, context, attrs)
    }

    override fun onPause() {
        super.onPause()

        inmessage.setBackgroundResource(R.drawable.secret)
    }

    override fun onRestart() {
        super.onRestart()
        inmessage.setBackgroundResource(R.drawable.secretmessage)

    }


    override fun onStop() {
        super.onStop()
        inmessage.setBackgroundResource(R.drawable.secret)

    }

    override fun onDestroy() {
        super.onDestroy()
    }
    fun chattingPost(){
        var jsonObject = JSONObject()
        jsonObject.put("Name", intent.getStringExtra("name"))
        jsonObject.put("Text", edt_message.text.toString())

          //Gson 라이브러리의 Json Parser을 통해 객체를 Json으로!
        val gsonObject = JsonParser().parse(jsonObject.toString()) as JsonObject
        var postChatResponse: Call<PostChatResponse> = SqnetworkService.postChatResponse("application/json",SharedPreferenceController.getSQAuthorization(this),gsonObject)
        postChatResponse.enqueue(object : Callback<PostChatResponse> {
            override fun onResponse(call: Call<PostChatResponse>?, response: Response<PostChatResponse>?) {
                if (response!!.isSuccessful) {
                    if(response.body()!!.message=="성공"){
                         toast(response.body()!!.result.RText.toString())
                    }
                }
                else{
                    Log.v("TAG", "채팅 실패")
                }
            }
            override fun onFailure(call: Call<PostChatResponse>?, t: Throwable?) {
                Log.v("TAG", "통신 실패 = " +t.toString())
            }
        })
    }
}
