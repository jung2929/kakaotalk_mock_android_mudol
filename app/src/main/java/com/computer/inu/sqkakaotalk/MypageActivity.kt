package com.computer.inu.sqkakaotalk

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_mypage.*
import org.jetbrains.anko.ctx
import org.jetbrains.anko.startActivity

class MypageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mypage)


        //카카오 로그인일때 로그아웃
        tv_mypage_logout.setOnClickListener {
            if (SharedPreferenceController.getKaKaOAuthorization(ctx).isNotEmpty()) {
                SharedPreferenceController.clearKaKaoSPC(this)
            } else if (SharedPreferenceController.getSQAuthorization(ctx).isNotEmpty()) {
                  SharedPreferenceController.clearSQSPC(this)
            }
            finish()
            startActivity<LoginActivity>()
        }
        iv_mypage_backbutton.setOnClickListener {
            finish()
        }
     /*   if (SharedPreferenceController.getIMAGE(this).isNotEmpty()){  //통신하기 전 사진 받아오기

            val decodedString = Base64.decode(SharedPreferenceController.getIMAGE(this), Base64.DEFAULT)
            val decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
            iv_mypage_mypicture.setImageBitmap(decodedByte)
        }*/
    }


}
