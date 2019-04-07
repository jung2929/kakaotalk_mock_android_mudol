package com.computer.inu.sqkakaotalk.Fragment

import android.Manifest
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import com.computer.inu.sqkakaotalk.R
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import kotlinx.android.synthetic.main.activity_news_fragment.*
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView
import org.jetbrains.anko.support.v4.ctx
import java.util.*


class NewsFragment : Fragment() {
    internal val API_KEY = "d752d08c98a48b862f5747ba31e015fd"
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val homeFragmentView: View = inflater!!.inflate(R.layout.activity_news_fragment, container, false)
        return homeFragmentView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val permissionlistener = object : PermissionListener {
            override fun onPermissionGranted() {

                // MapView 객체생성 및 API Key 설정
                val mapView = MapView(ctx)
                mapView.setDaumMapApiKey(API_KEY)

                val mapViewContainer = map_view as RelativeLayout
                mapViewContainer.addView(mapView)


                // 중심점 변경
                mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(37.53737528, 127.00557633), true)


                // 마커 생성 및 설정
                val marker = MapPOIItem()
                marker.itemName = "Default Marker"
                marker.tag = 0
                marker.mapPoint = MapPoint.mapPointWithGeoCoord(37.53737528, 127.00557633)
                marker.markerType = MapPOIItem.MarkerType.BluePin // 기본으로 제공하는 BluePin 마커 모양.
                marker.selectedMarkerType = MapPOIItem.MarkerType.RedPin // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.
                mapView.addPOIItem(marker)
                btn_mapjava.setOnClickListener(View.OnClickListener {
                    mapView.currentLocationTrackingMode =
                        MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading
                })

            }

            override fun onPermissionDenied(deniedPermissions: ArrayList<String>) {

            }

        }

         TedPermission.with(ctx)
            .setPermissionListener(permissionlistener)
            .setRationaleMessage("지도 서비스를 사용하기 위해서는 위치 접근 권한이 필요해요")
            .setDeniedMessage("왜 거부하셨어요...\n하지만 [설정] > [권한] 에서 권한을 허용할 수 있어요.")
            .setPermissions(Manifest.permission.ACCESS_FINE_LOCATION)
            .check()
    }
}

