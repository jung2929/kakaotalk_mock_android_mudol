package com.computer.inu.sqkakaotalk.get

import com.computer.inu.sqkakaotalk.Data.StoryHistory


data class GetMystoryResponse (
    val result : ArrayList<StoryHistory>,
    val code : Int,
    val message : String
)