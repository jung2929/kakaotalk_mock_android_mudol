package com.computer.inu.sqkakaotalk.get

import com.computer.inu.sqkakaotalk.Data.EmoticonData

data class GetEmoticonResponse (
    val result : ArrayList<EmoticonData>,
    val code : Int,
    val message : String
)