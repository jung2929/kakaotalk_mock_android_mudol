package com.computer.inu.sqkakaotalk

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_add_kakaotalk_id.*
import android.text.InputType
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.content.Intent
import com.computer.inu.sqkakaotalk.Main.MainActivity


class AddKakaotalkIdActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_kakaotalk_id)
        rl_add_myfriend.visibility=View.GONE
        ll_add_cantfind.visibility=View.GONE
        et_addkakaotalk_id.requestFocus()
        et_addkakaotalk_id.inputType = InputType.TYPE_CLASS_TEXT
        et_addkakaotalk_id.imeOptions = EditorInfo.IME_ACTION_SEARCH
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
        et_addkakaotalk_id.setOnEditorActionListener({ textView, action, event ->
            var handled = false
            if (action == EditorInfo.IME_ACTION_SEARCH) {
                if (et_addkakaotalk_id.text.toString() == "ejh9853") {
                    ll_add_myid.visibility=View.GONE
                    ll_add_cantfind.visibility=View.GONE
                    rl_add_myfriend.visibility=View.VISIBLE

                } else{
                    ll_add_myid.visibility=View.GONE
                    ll_add_cantfind.visibility=View.VISIBLE
                }
                val imm = applicationContext?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
                imm!!.hideSoftInputFromWindow(et_addkakaotalk_id.getWindowToken(), 0)
            }
            handled
        })

        btn_add_addfriend.setOnClickListener {
            val intent = Intent(this@AddKakaotalkIdActivity, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            SharedPreferenceController.setFriendName(this,tv_add_frinedname.text.toString())
            startActivity(intent)
        }

        iv_kakaotalk_finishaddfriend.setOnClickListener {
            finish()
        }

    }
}
