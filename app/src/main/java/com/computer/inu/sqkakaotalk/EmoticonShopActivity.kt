package com.computer.inu.sqkakaotalk

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.computer.inu.sqkakaotalk.Adapter.EmoticonShopRecyclerViewAdapter
import com.computer.inu.sqkakaotalk.Data.EmoticonData
import kotlinx.android.synthetic.main.activity_emoticon_shop.*

class EmoticonShopActivity : AppCompatActivity() {
    lateinit var EmoticonShopRecyclerViewAdapter: EmoticonShopRecyclerViewAdapter
    val EmoticonData : ArrayList<EmoticonData> by lazy {
        ArrayList<EmoticonData>()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_emoticon_shop)
        EmoticonShopRecyclerViewAdapter=
            EmoticonShopRecyclerViewAdapter(this, EmoticonData)

        EmoticonData.add(EmoticonData(2, "언행불일치","똑띠","http://kaca5.com/imageE/E2/1.png"))
        this.rv_emoticonshop.adapter = EmoticonShopRecyclerViewAdapter
        this.rv_emoticonshop.layoutManager = LinearLayoutManager(this)
    }
}
