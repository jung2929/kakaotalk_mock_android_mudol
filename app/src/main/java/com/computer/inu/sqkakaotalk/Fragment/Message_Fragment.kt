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
        homeFragmentView.rv_message.adapter = MessageRecyclerViewAdapter
        homeFragmentView.rv_message.layoutManager = LinearLayoutManager(context!!)
        return homeFragmentView
    }

}