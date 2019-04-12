package com.computer.inu.sqkakaotalk.get

import com.computer.inu.sqkakaotalk.Data.SearchFriendData

data class GetSearchFriendInfoResponse (
    val result : SearchFriendData,
    val code : Int,
    val message : String
)