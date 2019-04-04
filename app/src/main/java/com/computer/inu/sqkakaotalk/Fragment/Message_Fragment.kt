package com.computer.inu.sqkakaotalk.Fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.computer.inu.sqkakaotalk.Adapter.MessageRecyclerViewAdapter
import com.computer.inu.sqkakaotalk.Data.MessageData
import com.computer.inu.sqkakaotalk.R
import kotlinx.android.synthetic.main.activity_message__fragment.view.*

class Message_Fragment : Fragment() {
    lateinit var MessageRecyclerViewAdapter: MessageRecyclerViewAdapter
    val dataList : ArrayList<MessageData> by lazy {
        ArrayList<MessageData>()
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var homeFragmentView: View = inflater!!.inflate(R.layout.activity_message__fragment, container, false)
        MessageRecyclerViewAdapter= MessageRecyclerViewAdapter(context!!, dataList)
        dataList.add(MessageData("손기창", "비밀메세지", "오후 11:31"))
        dataList.add(MessageData("김은수", "뭐해", "오후 9:31"))
        dataList.add(MessageData("이규영", "개강이야 이제 아이고", "오후 8:14"))
        dataList.add(MessageData("손정범", "공부하자 공부 !!!", "오후 7:59"))
        dataList.add(MessageData("양승희", "오늘 몇시야", "오후 6:13"))
        dataList.add(MessageData("김우영", "귀~", "오후 4:23"))
        dataList.add(MessageData("박재휘", "4호선", "오전 11:03"))
        dataList.add(MessageData("고동한", "오 좋다 ㄱ ㄱ", "오전 09:28"))
        dataList.add(MessageData("이찬호", "일어남?????", "오전 08:02"))
        dataList.add(MessageData("설수연", "오늘 홍대에서 보는거다", "오전 7:01"))
        homeFragmentView.rv_message.adapter = MessageRecyclerViewAdapter
        homeFragmentView.rv_message.layoutManager = LinearLayoutManager(context!!)

        return homeFragmentView
    }

}