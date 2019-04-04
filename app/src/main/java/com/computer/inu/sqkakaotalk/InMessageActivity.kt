package com.computer.inu.sqkakaotalk

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_in_message.*

class InMessageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_in_message)
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
}
