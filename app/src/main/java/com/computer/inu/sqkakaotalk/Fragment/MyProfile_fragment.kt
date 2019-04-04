package com.computer.inu.sqkakaotalk.Fragment

import android.graphics.BitmapFactory
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.computer.inu.sqkakaotalk.LoginActivity
import com.computer.inu.sqkakaotalk.PayActivity
import com.computer.inu.sqkakaotalk.R
import com.computer.inu.sqkakaotalk.SharedPreferenceController
import com.kakao.usermgmt.UserManagement
import com.kakao.usermgmt.callback.LogoutResponseCallback
import kotlinx.android.synthetic.main.activity_my_profile_fragment.*
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast

class MyProfile_fragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val homeFragmentView: View = inflater!!.inflate(R.layout.activity_my_profile_fragment, container, false)
        return homeFragmentView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if (SharedPreferenceController.getIMAGE(ctx).isNotEmpty()){
            val decodedString = Base64.decode(SharedPreferenceController.getIMAGE(ctx), Base64.DEFAULT)
            val decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
            iv_main_mypicture.setImageBitmap(decodedByte)
        }
        lr_profile_detail_mypge_allview.visibility=View.GONE
        iv_main_mypage.setOnClickListener {
            lr_profile_allview.visibility=View.GONE
            lr_profile_detail_mypge_allview.visibility=View.VISIBLE
        }
        iv_mypage_backbutton.setOnClickListener {
            lr_profile_detail_mypge_allview.visibility=View.GONE
            lr_profile_allview.visibility=View.VISIBLE
        }
        tv_myprofile_logout.setOnClickListener {
            SharedPreferenceController.AutoclearSPC(ctx)
            toast("로그아웃")
            startActivity<LoginActivity>()
        }
        tv_myprofile_pay.setOnClickListener {
            startActivity<PayActivity>()
        }

    }
}
