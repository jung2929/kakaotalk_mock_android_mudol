package com.computer.inu.sqkakaotalk.post

import com.computer.inu.sqkakaotalk.Data.FrinedInfoData

data class PostAddFriendResponse (
    val result : ArrayList<FrinedInfoData>,
    val code : Int,
    val message : String
)