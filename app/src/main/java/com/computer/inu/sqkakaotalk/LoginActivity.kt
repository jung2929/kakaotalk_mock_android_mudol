package com.computer.inu.sqkakaotalk

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.computer.inu.sqkakaotalk.network.ApplicationController
import com.computer.inu.sqkakaotalk.network.NetworkService
import com.computer.inu.sqkakaotalk.network.SqNetworkService
import com.computer.inu.sqkakaotalk.post.PostLoginResponse
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.kakao.auth.ErrorCode
import com.kakao.auth.ISessionCallback
import com.kakao.auth.Session
import com.kakao.network.ErrorResult
import com.kakao.usermgmt.UserManagement
import com.kakao.usermgmt.callback.MeResponseCallback
import com.kakao.usermgmt.response.model.UserProfile
import com.kakao.util.exception.KakaoException
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginActivity : AppCompatActivity() {
    var backPressedTime: Long = 0
    val FINISH_INTERVAL_TIME = 2000
     private var callback: SessionCallback=SessionCallback()
    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }
    val SqnetworkService: SqNetworkService by lazy {
        ApplicationController.instance.SqnetworkService
    }
    override fun onBackPressed() {
        var tempTime = System.currentTimeMillis()
        var intervalTime = tempTime - backPressedTime

        if (0 <= intervalTime && FINISH_INTERVAL_TIME >= intervalTime) {
            super.onBackPressed()
        } else {
            backPressedTime = tempTime
            Toast.makeText(applicationContext, "한번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show()
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)

        callback = SessionCallback()
        Session.getCurrentSession().addCallback(callback)
        Session.getCurrentSession().checkAndImplicitOpen()

        if(intent.getStringExtra("clear")=="clear")
            SharedPreferenceController.SetclearSignUp(this)

        btn_login_loginbutton.setOnClickListener {
            LoginPost()    //로그인통신
        }

        tv_login_signup.setOnClickListener {
            startActivity<SignUpActivity>()
        }

    }

    private inner class SessionCallback : ISessionCallback {
        override fun onSessionOpened() {

            UserManagement.requestMe(object : MeResponseCallback() {
                override fun onFailure(errorResult: ErrorResult?) {
                    val message = "failed to get user info. msg=" + errorResult!!
                    val result = ErrorCode.valueOf(errorResult.errorCode)
                    if (result == ErrorCode.CLIENT_ERROR_CODE) {
                        //에러로 인한 로그인 실패
                        //                        finish();
                    } else {
                        //redirectMainActivity();
                    }
                }

                override fun onSessionClosed(errorResult: ErrorResult) {
                    toast("onSessionOpenClosed")
                }

                override fun onNotSignedUp() {
                    toast("onNotSignedUp")
                }

                override fun onSuccess(userProfile: UserProfile) {
                    //로그인에 성공하면 로그인한 사용자의 일련번호, 닉네임, 이미지url등을 리턴합니다.
                    //사용자 ID는 보안상의 문제로 제공하지 않고 일련번호는 제공합니다.

                    //                    Log.e("UserProfile", userProfile.toString());
                    //                    Log.e("UserProfile", userProfile.getId() + "");

                    val token = Session.getCurrentSession().accessToken
                    SharedPreferenceController.setAutoAuthorization(this@LoginActivity,token)
                    Log.v("TAG", token)
                    toast(token)
                     startActivity<MainActivity>()
                }
            })

        }

        override fun onSessionOpenFailed(exception: KakaoException) {
            toast("onSessionOpenFailed")
            // 세션 연결이 실패했을때

        }
    }
    private fun LoginPost() {
//Json 형식의 객체 만들기
        var jsonObject = JSONObject()
        jsonObject.put("name", et_login_email.text.toString())
        jsonObject.put("pwd", et_login_pwd.text.toString())

//Gson 라이브러리의 Json Parser을 통해 객체를 Json으로!
        val gsonObject = JsonParser().parse(jsonObject.toString()) as JsonObject
        val postLoginResponse: Call<PostLoginResponse> =
            SqnetworkService.postLoginResponse("application/json", gsonObject)
        postLoginResponse.enqueue(object : Callback<PostLoginResponse> {
            override fun onFailure(call: Call<PostLoginResponse>, t: Throwable) {

                toast("알수 없는 오류")
            }
            //통신 성공 시 수행되는 메소드
            override fun onResponse(call: Call<PostLoginResponse>, response: Response<PostLoginResponse>) {
                if (response.isSuccessful) {
                   var message = response.body()!!.message!!
                    if(message == "성공"){
                        toast("로그인 성공")
                        startActivity<MainActivity>()
                    }
                    else{
                        toast("로그인 실패")
                    }
                }
            }
        })
    }
}
