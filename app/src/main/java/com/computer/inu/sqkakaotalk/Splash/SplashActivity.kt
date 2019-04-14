package com.computer.inu.sqkakaotalk.Splash

import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import com.computer.inu.sqkakaotalk.LoginActivity
import com.computer.inu.sqkakaotalk.R
import org.jetbrains.anko.startActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Handler().postDelayed(Runnable {
            startActivity<LoginActivity>()
            finish()  //여기에 딜레이 후 시작할 작업들을 입력
        }, 3400)//
    }
}
