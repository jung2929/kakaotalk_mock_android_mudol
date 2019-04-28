package com.computer.inu.sqkakaotalk.get

import com.computer.inu.sqkakaotalk.Data.FriendData
import com.computer.inu.sqkakaotalk.Data.FriendListData
import com.computer.inu.sqkakaotalk.Data.StoryHistory
import com.kakao.friends.response.model.FriendInfo


data class GetFriendResponse (
    val result : ArrayList<FriendData>,
    val code : Int,
    val message : String
)