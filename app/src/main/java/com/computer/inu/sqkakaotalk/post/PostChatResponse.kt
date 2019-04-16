package com.computer.inu.sqkakaotalk.post

import com.computer.inu.sqkakaotalk.Data.ChatData

data class PostChatResponse (
    val result : ChatData,
    val code : Int,
    val message : String
)