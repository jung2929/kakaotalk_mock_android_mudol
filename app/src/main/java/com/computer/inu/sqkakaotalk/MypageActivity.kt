package com.computer.inu.sqkakaotalk

import android.graphics.BitmapFactory
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import kotlinx.android.synthetic.main.activity_mypage.*
import org.jetbrains.anko.startActivity

class MypageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mypage)
        tv_mypage_logout.setOnClickListener {
            SharedPreferenceController.AutoclearSPC(this)
            startActivity<LoginActivity>()
        }
        iv_mypage_backbutton.setOnClickListener {
            finish()
        }
        if (SharedPreferenceController.getIMAGE(this).isNotEmpty()){

            val decodedString = Base64.decode(SharedPreferenceController.getIMAGE(this), Base64.DEFAULT)
            val decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
            iv_mypage_mypicture.setImageBitmap(decodedByte)
        }
    }


}
