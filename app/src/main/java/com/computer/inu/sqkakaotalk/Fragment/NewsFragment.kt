package com.computer.inu.sqkakaotalk.Fragment

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.computer.inu.sqkakaotalk.R
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import android.widget.RelativeLayout
import com.computer.inu.sqkakaotalk.MainActivity
import kotlinx.android.synthetic.main.activity_news_fragment.*
import net.daum.mf.map.api.MapView
import org.jetbrains.anko.support.v4.ctx
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapReverseGeoCoder;


class NewsFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val homeFragmentView: View = inflater!!.inflate(R.layout.activity_news_fragment, container, false)
        return homeFragmentView

    }
}
