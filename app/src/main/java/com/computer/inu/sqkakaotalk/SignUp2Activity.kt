package com.computer.inu.sqkakaotalk

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.util.Log
import com.computer.inu.sqkakaotalk.Main.MainActivity
import com.computer.inu.sqkakaotalk.network.ApplicationController
import com.computer.inu.sqkakaotalk.network.SqNetworkService
import com.computer.inu.sqkakaotalk.post.PostEmailAuthenticateResponse
import com.computer.inu.sqkakaotalk.post.PostLoginResponse
import com.computer.inu.sqkakaotalk.post.PostSignUpResponse
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_sign_up2.*
import org.jetbrains.anko.ctx
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SignUp2Activity : AppCompatActivity() {
    val SqnetworkService: SqNetworkService by lazy {
        ApplicationController.instance.SqnetworkService
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up2)

        tv_sign_up2_confirm_number_send_message.isClickable=false


        if (intent.getIntExtra("check",-1)==1)
        {
            et_sign_up2_name.setText(SharedPreferenceController.getUserName(this))
            et_sign_up2_email.setText(SharedPreferenceController.getUserEmail(this))
            et_sign_up2_pw.setText(SharedPreferenceController.getUserPW(this))
        }

        tv_sign_up2_overlap_check.setOnClickListener {
            postEmailAuthenticateResponse()
    //email post
        }
        tv_sign_up2_confirm_number_send_message.setOnClickListener {
            SignUpPost()
        }
    }

    override fun onPause() {
        super.onPause()
        val input_name : String = et_sign_up2_name.text.toString()
        val input_email : String =et_sign_up2_email.text.toString()
        val input_pwd : String = et_sign_up2_pw.text.toString()
        SharedPreferenceController.setUserEMAIL(this,input_email)
        SharedPreferenceController.setUserNAME(this,input_name)
        SharedPreferenceController.setUserPW(this,input_pwd)
    }
    fun postEmailAuthenticateResponse(){ //이메일 인증
        var jsonObject = JSONObject()
        jsonObject.put("SendEmail", et_sign_up2_email.text.toString())
        val gsonObject = JsonParser().parse(jsonObject.toString()) as JsonObject
        var postEmailAuthenticateResponse: Call<PostEmailAuthenticateResponse> = SqnetworkService.postEmailAuthenticateResponse(gsonObject)
        postEmailAuthenticateResponse.enqueue(object : Callback<PostEmailAuthenticateResponse> {
            override fun onResponse(call: Call<PostEmailAuthenticateResponse>?, response: Response<PostEmailAuthenticateResponse>?) {
                Log.v("TAG", "보드 서버 통신 연결")
                if (response!!.isSuccessful) {
                    if(response.body()!!.message=="성공"){
                      toast("인증번호 전송")
                    }
                }
                else{
                    Log.v("TAG", "이메일 인증번호 전달 실패")
                }
            }
            override fun onFailure(call: Call<PostEmailAuthenticateResponse>?, t: Throwable?) {
                Log.v("TAG", "통신 실패 = " +t.toString())
            }
        })
    }
    fun SignUpPost(){ //회원가입
        var jsonObject = JSONObject()
        jsonObject.put("Email", et_sign_up2_email.text.toString())
        jsonObject.put("Pw", et_sign_up2_pw.text.toString())
        jsonObject.put("Name", et_sign_up2_name.text.toString())
        jsonObject.put("Tel", et_sign_up2_phoneNumber.text.toString())
        jsonObject.put("Random", et_sign_up2_emailAuth.text.toString())

        val gsonObject = JsonParser().parse(jsonObject.toString()) as JsonObject
        var posetSignup: Call<PostSignUpResponse> = SqnetworkService.postSignUpResponse(gsonObject)
        posetSignup.enqueue(object : Callback<PostSignUpResponse> {
            override fun onResponse(call: Call<PostSignUpResponse>?, response: Response<PostSignUpResponse>?) {
                Log.v("TAG", "보드 서버 통신 연결")
                if (response!!.isSuccessful) {
                    toast(response.body()!!.message)
                    if(response.body()!!.message=="성공"){
                        val alert = AlertDialog.Builder(this@SignUp2Activity)
                        alert.setPositiveButton("가입완료") { dialog, which ->
                            startActivity<LoginActivity>("clear" to "clear")
                            dialog.dismiss()     //닫기
                        }
                        alert.setMessage("환영합니다.")
                        alert.show()
                        alert.setCancelable(false)
                    }
                }
                else{
                    Log.v("TAG", "이메일 인증번호 전달 실패")
                }
            }
            override fun onFailure(call: Call<PostSignUpResponse>?, t: Throwable?) {
                Log.v("TAG", "통신 실패 = " +t.toString())
            }
        })
    }

}
