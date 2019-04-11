package com.computer.inu.sqkakaotalk.get

import com.computer.inu.sqkakaotalk.Data.imgdata

data class GetprofileResponse (
    val result : imgdata,
    val code : Int,
    val message : String
)