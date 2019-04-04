package com.computer.inu.sqkakaotalk

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethod
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast


class LoginActivity : AppCompatActivity() {
    var backPressedTime: Long = 0
    val FINISH_INTERVAL_TIME = 2000

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


}
