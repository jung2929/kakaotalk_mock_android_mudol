package com.computer.inu.sqkakaotalk.post

import com.computer.inu.sqkakaotalk.Data.ChatData

data class PostChatResponse (
    var restult : ChatData,
    var code : Int,
    var message : String
)