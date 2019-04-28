package com.computer.inu.sqkakaotalk.Data

data class ChatRoomData (
    val content : String?,
    var image : String?,
    val check : Boolean// 상대방이면 false , 나면 true
)