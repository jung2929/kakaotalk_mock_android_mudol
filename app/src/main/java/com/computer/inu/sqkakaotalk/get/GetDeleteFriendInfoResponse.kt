package com.computer.inu.sqkakaotalk.get

import com.computer.inu.sqkakaotalk.Data.ListDeleteFrinedData

data class GetDeleteFriendInfoResponse (
    val result : ArrayList<ListDeleteFrinedData>,
    val code : Int,
    val message : String
)