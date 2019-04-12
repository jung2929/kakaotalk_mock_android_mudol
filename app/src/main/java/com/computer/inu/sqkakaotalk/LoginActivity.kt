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
import org.jetbrains.anko.ctx
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
if(SharedPreferenceController.getAutoLoginAuthorization(this).isNotEmpty()){
    toast("자동로그인 되었습니다.")
    startActivity<MainActivity>()
}
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
                    SharedPreferenceController.setKaKaOAuthorization(this@LoginActivity,token)
                    Log.v("TAG", token)

                     startActivity<MainActivity>()
                }
            })

        }

        override fun onSessionOpenFailed(exception: KakaoException) {
            toast("onSessionOpenFailed")
            // 세션 연결이 실패했을때

        }
    }

    fun LoginPost(){
        var jsonObject = JSONObject()
        jsonObject.put("Email", et_login_email.text.toString())
        jsonObject.put("Pw", et_login_pwd.text.toString())

//Gson 라이브러리의 Json Parser을 통해 객체를 Json으로!
        val gsonObject = JsonParser().parse(jsonObject.toString()) as JsonObject
        var postLoginResponse: Call<PostLoginResponse> = SqnetworkService.postLoginResponse("application/json",gsonObject)
        postLoginResponse.enqueue(object : Callback<PostLoginResponse> {
            override fun onResponse(call: Call<PostLoginResponse>?, response: Response<PostLoginResponse>?) {
                Log.v("TAG", "보드 서버 통신 연결")
                if (response!!.isSuccessful) {
                    if(response.body()!!.message=="성공"){
                        SharedPreferenceController.clearKaKaoSPC(ctx)
                        SharedPreferenceController.clearSQSPC(ctx)
                   SharedPreferenceController.setSQAuthorization(ctx,response.body()!!.token.jwt.toString())

                        if(cb_login_autologinCheckbox.isChecked==true){
                            SharedPreferenceController.setAutoLoginAuthorization(this@LoginActivity,"auto")
                        }

                    startActivity<MainActivity>()}
                    else {
                        toast("아이디 또는 비밀번호가 틀렸습니다.")
                    }
                }
                else{
                    Log.v("TAG", "마이페이지 서버 값 전달 실패")
                }
            }
            override fun onFailure(call: Call<PostLoginResponse>?, t: Throwable?) {
                Log.v("TAG", "통신 실패 = " +t.toString())
            }
        })
    }


}