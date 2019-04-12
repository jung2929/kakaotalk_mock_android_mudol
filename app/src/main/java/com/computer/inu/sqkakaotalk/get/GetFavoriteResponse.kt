package com.computer.inu.sqkakaotalk.get

import com.computer.inu.sqkakaotalk.Data.FavoritesData


data class GetFavoriteResponse (
    val result : FavoritesData,
    val code : Int,
    val message : String
)