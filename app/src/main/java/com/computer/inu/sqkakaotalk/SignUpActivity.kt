package com.computer.inu.sqkakaotalk

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import kotlinx.android.synthetic.main.activity_sign_up1.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import android.widget.Toast


class SignUpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up1)

        if(SharedPreferenceController.getUserName(this).isNotEmpty()) {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("작성 중인 작업이 있습니다.")
            builder.setMessage("값 불러오기")
            builder.setPositiveButton(
                "불러오기"
            ) { dialog, which ->
                Toast.makeText(applicationContext, "이동", Toast.LENGTH_LONG).show()
                startActivity<SignUp2Activity>("check" to 1)
            }
            builder.setNegativeButton(
                "취소"
            ) { dialog, which -> Toast.makeText(applicationContext, "취소", Toast.LENGTH_LONG).show()
                 SharedPreferenceController.SetclearSignUp(this)
            }
            builder.show()

        }
        cb_sign_up1_personal_check.setOnClickListener {
            if(cb_sign_up1_personal_check.isChecked()){
                cb_sign_up1_personal_check.setChecked(true)
                if( cb_sign_up1_personal_check.isChecked()&& cb_sign_up1_using_agree_check.isChecked()&& cb_sign_up1_membership_agree_check.isChecked()){
                    cb_sign_up1_all_agree_check.setChecked(true)
                }else{
                    cb_sign_up1_all_agree_check.setChecked(false)
                }
            }else{
                cb_sign_up1_personal_check.setChecked(false)
                if( cb_sign_up1_personal_check.isChecked()&& cb_sign_up1_using_agree_check.isChecked()&& cb_sign_up1_membership_agree_check.isChecked()){
                    cb_sign_up1_all_agree_check.setChecked(true)
                }else{
                    cb_sign_up1_all_agree_check.setChecked(false)
                }
            }
        }
        cb_sign_up1_membership_agree_check.setOnClickListener {
            if(cb_sign_up1_membership_agree_check.isChecked()){
                cb_sign_up1_membership_agree_check.setChecked(true)
                if( cb_sign_up1_personal_check.isChecked()&& cb_sign_up1_using_agree_check.isChecked()&& cb_sign_up1_membership_agree_check.isChecked()){
                    cb_sign_up1_all_agree_check.setChecked(true)
                }else{
                    cb_sign_up1_all_agree_check.setChecked(false)
                }
            }else{
                cb_sign_up1_membership_agree_check.setChecked(false)
                if( cb_sign_up1_personal_check.isChecked()&& cb_sign_up1_using_agree_check.isChecked()&& cb_sign_up1_membership_agree_check.isChecked()){
                    cb_sign_up1_all_agree_check.setChecked(true)
                }else{
                    cb_sign_up1_all_agree_check.setChecked(false)
                }
            }
        }
        cb_sign_up1_using_agree_check.setOnClickListener {
            if(cb_sign_up1_using_agree_check.isChecked()){
                cb_sign_up1_using_agree_check.setChecked(true)
                if( cb_sign_up1_personal_check.isChecked()&& cb_sign_up1_using_agree_check.isChecked()&& cb_sign_up1_membership_agree_check.isChecked()){
                    cb_sign_up1_all_agree_check.setChecked(true)
                }else{
                    cb_sign_up1_all_agree_check.setChecked(false)
                }
            }else{
                cb_sign_up1_using_agree_check.setChecked(false)
                if( cb_sign_up1_personal_check.isChecked()&& cb_sign_up1_using_agree_check.isChecked()&& cb_sign_up1_membership_agree_check.isChecked()){
                    cb_sign_up1_all_agree_check.setChecked(true)
                }else{
                    cb_sign_up1_all_agree_check.setChecked(false)
                }
            }
        }
        cb_sign_up1_all_agree_check.setOnClickListener {
            if (cb_sign_up1_all_agree_check.isChecked()) {
                cb_sign_up1_personal_check.setChecked(true)
                cb_sign_up1_using_agree_check.setChecked(true)
                cb_sign_up1_membership_agree_check.setChecked(true)
            } else {
                cb_sign_up1_personal_check.setChecked(false)
                cb_sign_up1_using_agree_check.setChecked(false)
                cb_sign_up1_membership_agree_check.setChecked(false)
            }
        }

        tv_sign_up1_next_btn.setOnClickListener {
            if( cb_sign_up1_all_agree_check.isChecked()) {
                startActivity<SignUp2Activity>()
            }else{
                toast("모두 동의하셔야합니다.")
            }
        }
    }
}
