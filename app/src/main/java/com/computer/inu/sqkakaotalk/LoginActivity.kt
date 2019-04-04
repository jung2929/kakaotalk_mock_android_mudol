package com.computer.inu.sqkakaotalk

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.computer.inu.sqkakaotalk.network.NetworkService
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


class LoginActivity : AppCompatActivity() {
    var backPressedTime: Long = 0
    val FINISH_INTERVAL_TIME = 2000
     private var callback: SessionCallback=SessionCallback()
    lateinit var networkService : NetworkService

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

        if(SharedPreferenceController.getAutoAuthorization(this).isNotEmpty()){
            startActivity<MainActivity>()
            toast("자동로그인 되었습니다")
        }


        btn_login_loginbutton.setOnClickListener {
            if(cb_login_autologinCheckbox.isChecked==true)
SharedPreferenceController.setAutoAuthorization(this,"token")
            startActivity<MainActivity>()
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
}
