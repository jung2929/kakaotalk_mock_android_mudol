package com.computer.inu.sqkakaotalk

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.widget.Toast
import com.computer.inu.sqkakaotalk.Fragment.*
import com.computer.inu.sqkakaotalk.network.ApplicationController
import com.computer.inu.sqkakaotalk.network.NetworkService
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var backPressedTime: Long = 0
    val FINISH_INTERVAL_TIME = 2000
    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
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
        setContentView(R.layout.activity_main)


        addFragment(FriendListFragment())
        iv_main_human.setImageResource(R.drawable.clickedhuman)
        iv_main_human.setOnClickListener {
            replaceFragment(FriendListFragment())
            iv_main_human.setImageResource(R.drawable.clickedhuman)
            iv_main_message.setImageResource(R.drawable.message)
            iv_main_shap.setImageResource(R.drawable.shap)
            iv_main_star.setImageResource(R.drawable.star)
            iv_main_more.setImageResource(R.drawable.more)
        }
        iv_main_message.setOnClickListener {
            replaceFragment(Message_Fragment())
            iv_main_human.setImageResource(R.drawable.human)
            iv_main_message.setImageResource(R.drawable.clickedmessage)
            iv_main_shap.setImageResource(R.drawable.shap)
            iv_main_star.setImageResource(R.drawable.star)
            iv_main_more.setImageResource(R.drawable.more)
        }
        iv_main_shap.setOnClickListener {
            replaceFragment(NewsFragment())
            iv_main_human.setImageResource(R.drawable.human)
            iv_main_message.setImageResource(R.drawable.message)
            iv_main_shap.setImageResource(R.drawable.clickedshap)
            iv_main_star.setImageResource(R.drawable.star)
            iv_main_more.setImageResource(R.drawable.more)
        }
        iv_main_star.setOnClickListener {
            replaceFragment(GameFragment())
            iv_main_human.setImageResource(R.drawable.human)
            iv_main_message.setImageResource(R.drawable.message)
            iv_main_shap.setImageResource(R.drawable.shap)
            iv_main_star.setImageResource(R.drawable.clickedstar)
            iv_main_more.setImageResource(R.drawable.more)
        }
        iv_main_more.setOnClickListener {
            replaceFragment(MyProfile_fragment())
            iv_main_human.setImageResource(R.drawable.human)
            iv_main_message.setImageResource(R.drawable.message)
            iv_main_shap.setImageResource(R.drawable.shap)
            iv_main_star.setImageResource(R.drawable.star)
            iv_main_more.setImageResource(R.drawable.clickedmore)
        }
    }
    private fun addFragment(fragment : Fragment){
        val transaction : FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.fl_main_act_fragment_block, fragment)
        transaction.commit()
    }

    private fun replaceFragment(fragment: Fragment) {
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fl_main_act_fragment_block, fragment)
        transaction.commit()
    }


}
