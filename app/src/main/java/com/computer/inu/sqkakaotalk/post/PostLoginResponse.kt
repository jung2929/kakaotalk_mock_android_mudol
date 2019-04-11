package com.computer.inu.sqkakaotalk.post

import com.computer.inu.sqkakaotalk.Data.SqUserData
import com.computer.inu.sqkakaotalk.Data.Token

data class PostLoginResponse (
    val token : Token,
    val result : ArrayList<SqUserData>,
    val code : Int,
    val message : String
)