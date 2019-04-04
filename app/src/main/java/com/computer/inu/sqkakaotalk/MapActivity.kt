package com.computer.inu.sqkakaotalk

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_map.*

class MapActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        iv_map_mapplus.visibility=View.GONE
        btn_map_mapbutton.setOnClickListener {
           if(iv_map_mapplus.visibility==View.GONE){
               btn_map_mapbutton.setBackgroundResource(R.drawable.mapicon)
               iv_map_map.visibility=View.GONE
               iv_map_mapplus.visibility=View.VISIBLE
           }
            else{
               btn_map_mapbutton.setBackgroundResource(R.drawable.nomap)
               iv_map_mapplus.visibility=View.GONE
               iv_map_map.visibility=View.VISIBLE
           }
        }
    }
}
