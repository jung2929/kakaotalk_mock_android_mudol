package com.computer.inu.sqkakaotalk.post

import com.computer.inu.sqkakaotalk.Data.FavoritesData

data class PostFavoriteResponse (
    val result : ArrayList<FavoritesData>,
    val code : Int,
    val message : String
)