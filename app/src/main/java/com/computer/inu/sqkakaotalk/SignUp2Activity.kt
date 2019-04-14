package com.computer.inu.sqkakaotalk

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import kotlinx.android.synthetic.main.activity_sign_up2.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast


class SignUp2Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up2)

        tv_sign_up2_confirm_number_send_message.isClickable=false


        if (intent.getIntExtra("check",-1)==1)
        {
            et_sign_up2_name.setText(SharedPreferenceController.getUserName(this))
            et_sign_up2_email.setText(SharedPreferenceController.getUserEmail(this))
            et_sign_up2_pw.setText(SharedPreferenceController.getUserPW(this))
        }

        tv_sign_up2_overlap_check.setOnClickListener {
            if (et_sign_up2_email.text.toString().equals("dlfb77@gmail.com")) {
                tv_sign_up2_confirm_number_send_message.isClickable=false
                val alert = AlertDialog.Builder(this@SignUp2Activity)
                alert.setPositiveButton("확인") { dialog, which ->
                    dialog.dismiss()     //닫기
                }
                alert.setMessage("이미 가입된 메일입니다")
                alert.show()
                alert.setCancelable(false)
            } else {
                tv_sign_up2_confirm_number_send_message.isClickable = true
                toast("사용가능한 이메일 입니다.")
            }
        }
        tv_sign_up2_confirm_number_send_message.setOnClickListener {
                val alert = AlertDialog.Builder(this@SignUp2Activity)
                alert.setPositiveButton("가입완료") { dialog, which ->
                    startActivity<LoginActivity>("clear" to "clear")
                    dialog.dismiss()     //닫기
                }
                alert.setMessage("환영합니다.")
                alert.show()
                alert.setCancelable(false)
        }
    }

    override fun onPause() {
        super.onPause()
        val input_name : String = et_sign_up2_name.text.toString()
        val input_email : String =et_sign_up2_email.text.toString()
        val input_pwd : String = et_sign_up2_pw.text.toString()
        SharedPreferenceController.setUserEMAIL(this,input_email)
        SharedPreferenceController.setUserNAME(this,input_name)
        SharedPreferenceController.setUserPW(this,input_pwd)
    }



}
