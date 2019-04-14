package com.computer.inu.sqkakaotalk.delete

import com.computer.inu.sqkakaotalk.Data.FavoritesData

data class DeleteFavoriteResponse (
    var result : ArrayList<FavoritesData>,
    var code : Int,
    var  message : String
)