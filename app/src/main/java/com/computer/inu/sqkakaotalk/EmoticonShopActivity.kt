package com.computer.inu.sqkakaotalk

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.computer.inu.sqkakaotalk.Adapter.EmoticonShopRecyclerViewAdapter
import com.computer.inu.sqkakaotalk.Data.EmoticonData
import com.computer.inu.sqkakaotalk.Data.EmoticonOneImageData
import com.computer.inu.sqkakaotalk.get.GetEmoticonResponse
import com.computer.inu.sqkakaotalk.network.ApplicationController
import com.computer.inu.sqkakaotalk.network.SqNetworkService
import kotlinx.android.synthetic.main.activity_emoticon_shop.*
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class EmoticonShopActivity : AppCompatActivity() {
    val sqNetworkService: SqNetworkService by lazy {
        ApplicationController.instance.SqnetworkService
    }
    lateinit var requestManager: RequestManager
    lateinit var EmoticonShopRecyclerViewAdapter: EmoticonShopRecyclerViewAdapter
   var EmoticonItemList = ArrayList<EmoticonData>()
   var EmoticonOneImageiItemData = ArrayList<EmoticonOneImageData>()
    var EmoticonItemData = ArrayList<EmoticonData>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_emoticon_shop)
        requestManager = Glide.with(this)
        getEmoticon()
    }

    override fun onCreateView(name: String?, context: Context?, attrs: AttributeSet?): View? {
        return super.onCreateView(name, context, attrs)

    }
    fun getEmoticon() {
        var getemoticonResponse : Call<GetEmoticonResponse> = sqNetworkService.getEmoticonResponse("application/json",SharedPreferenceController.getSQAuthorization(this))
        getemoticonResponse.enqueue(object : Callback<GetEmoticonResponse> {
            override fun onResponse(call: Call<GetEmoticonResponse>?, response: Response<GetEmoticonResponse>?) {
                Log.v("TAG", "이모티콘샵 서버 통신 연결")

                if (response!!.isSuccessful) {
                    toast("통신")
                    EmoticonItemList.clear()
                    EmoticonItemData.clear()
                    EmoticonOneImageiItemData.clear()

                    EmoticonItemList = response.body()!!.result

                    for (i in 0..EmoticonItemList.size - 1) {
                        EmoticonOneImageiItemData.add(EmoticonOneImageData(EmoticonItemList[i].Eno, EmoticonItemList[i].Name, EmoticonItemList[i].Made, EmoticonItemList[i].image!![0]))
                    }
                    EmoticonShopRecyclerViewAdapter= EmoticonShopRecyclerViewAdapter(this@EmoticonShopActivity, EmoticonOneImageiItemData,requestManager)
                    rv_emoticonshop.adapter = EmoticonShopRecyclerViewAdapter
                    rv_emoticonshop.layoutManager = LinearLayoutManager(this@EmoticonShopActivity)
                }else {
                    toast("응답은 성공인데 통신실패")
                }
            }

            override fun onFailure(call: Call<GetEmoticonResponse>?, t: Throwable?) {
                Log.v("TAG", "통신 실패 = " + t.toString())
                toast("통신실패")
            }
        })
    }

}
