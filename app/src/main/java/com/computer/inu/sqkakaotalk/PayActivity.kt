package com.computer.inu.sqkakaotalk

import android.graphics.Color
import android.graphics.PorterDuff
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_pay.*

class PayActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pay)
    }

    override fun onPause() {
        super.onPause()
        iv_pay_image.setColorFilter(Color.parseColor("#1A1616"), PorterDuff.Mode.MULTIPLY);

    }

    override fun onRestart() {
        super.onRestart()
        iv_pay_image.setColorFilter(null)

    }
}
