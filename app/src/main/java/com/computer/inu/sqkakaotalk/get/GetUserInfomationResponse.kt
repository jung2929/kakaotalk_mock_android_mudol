package com.computer.inu.sqkakaotalk.get

import com.computer.inu.sqkakaotalk.Data.kakao_accountData
import com.computer.inu.sqkakaotalk.Data.propertiesData

data class GetUserInfomationResponse (
    var id : Int,
    var properties : propertiesData,
    var kakao_account : kakao_accountData
)