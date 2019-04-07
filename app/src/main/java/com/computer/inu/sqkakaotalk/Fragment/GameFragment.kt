package com.computer.inu.sqkakaotalk.Fragment

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.computer.inu.sqkakaotalk.KakaoTranslateActivity
import com.computer.inu.sqkakaotalk.MainActivity
import com.computer.inu.sqkakaotalk.R
import kotlinx.android.synthetic.main.activity_game_fragment.*
import org.jetbrains.anko.support.v4.ctx

class GameFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val homeFragmentView: View = inflater!!.inflate(R.layout.activity_game_fragment, container, false)
        return homeFragmentView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        iv_game_translate.setOnClickListener {
            val intent = Intent(ctx, KakaoTranslateActivity::class.java)
            ctx.startActivity(intent)
            (ctx as MainActivity).overridePendingTransition(R.anim.sliding_up,R.anim.stay )
        }
    }
}