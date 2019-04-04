package com.computer.inu.sqkakaotalk

import android.content.Context
import android.content.SharedPreferences

object SharedPreferenceController {

    private val USER = "USER"
    private val AUTO = "AUTO"
    private val FRINED = "FRIEND"
    private  val IMAGE = "IMAGE"

    private  val FRIEND_Image = "image"
    private val myAutoAuth = "myAuth"
    private val USER_NAME: String = "user_name"
    private val USER_PW: String = "user_pw"
    private val USER_EMAIL: String = "user_email"
    private val  FRIEND_NAME : String= "friend_name"
//ID 집어 넣기

    fun setUserNAME(ctx: Context, input_id: String) {
        val pref: SharedPreferences = ctx.getSharedPreferences(USER, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = pref.edit()
        editor.putString(USER_NAME, input_id)
        editor.commit()
    }
    //PW 집어 넣기
    fun setUserPW(ctx: Context, input_pw: String) {
        val pref: SharedPreferences = ctx.getSharedPreferences(USER, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = pref.edit()
        editor.putString(USER_PW, input_pw)
        editor.commit()
    }

    fun getUserEmail(ctx: Context): String {
        val pref: SharedPreferences = ctx.getSharedPreferences(USER, Context.MODE_PRIVATE)
        return pref.getString(USER_EMAIL, "") // (키 명, 든게 없을때 리턴할 값)
    }
    fun setUserEMAIL(ctx: Context, input_email: String) {
        val pref: SharedPreferences = ctx.getSharedPreferences(USER, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = pref.edit()
        editor.putString(USER_EMAIL, input_email)
        editor.commit()
    }

    fun getFriendName(ctx: Context): String {
        val pref: SharedPreferences = ctx.getSharedPreferences(FRINED, Context.MODE_PRIVATE)
        return pref.getString(FRIEND_NAME, "") // (키 명, 든게 없을때 리턴할 값)
    }
    fun setFriendName(ctx: Context, friend_name: String) {
        val pref: SharedPreferences = ctx.getSharedPreferences(FRINED, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = pref.edit()
        editor.putString(FRIEND_NAME, friend_name)
        editor.commit()
    }
    fun FriendNameclear(context: Context) {
        val pref = context.getSharedPreferences(FRINED, Context.MODE_PRIVATE)
        val editor = pref.edit()
        editor.clear()
        editor.commit()
    }

    fun getUserName(ctx: Context): String {
        val pref: SharedPreferences = ctx.getSharedPreferences(USER, Context.MODE_PRIVATE)
        return pref.getString(USER_NAME, "") // (키 명, 든게 없을때 리턴할 값)
    }
    //PW 꺼내기
    fun getUserPW(ctx: Context): String {
        val pref: SharedPreferences = ctx.getSharedPreferences(AUTO, Context.MODE_PRIVATE)
        return pref.getString(USER_PW, "") // (키 명, 든게 없을때 리턴할 값)
    }

    // 오직 자동로그인을 위한 토큰
    fun setAutoAuthorization(context: Context, authorization: String) {
        val pref = context.getSharedPreferences(AUTO, Context.MODE_PRIVATE)
        val editor = pref.edit()
        editor.putString(myAutoAuth, authorization)
        editor.commit()
    }

    fun getAutoAuthorization(context: Context): String {
        val pref = context.getSharedPreferences(AUTO, Context.MODE_PRIVATE)
        return pref.getString(myAutoAuth, "")
    }

    //로그아웃시 자동로그인을 위한 토큰 초기화
    fun AutoclearSPC(context: Context) {
        val pref = context.getSharedPreferences(AUTO, Context.MODE_PRIVATE)
        val editor = pref.edit()
        editor.clear()
        editor.commit()
    }
// 회원가입 정보 초기화
    fun SetclearSignUp(context: Context) {
        val pref = context.getSharedPreferences(USER, Context.MODE_PRIVATE)
        val editor = pref.edit()
        editor.clear()
        editor.commit()
    }


    fun setIMAGE(ctx: Context, image: String) {
        val pref: SharedPreferences = ctx.getSharedPreferences(IMAGE, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = pref.edit()
        editor.putString(FRIEND_Image, image)
        editor.commit()
    }

    fun getIMAGE(ctx: Context): String {
        val pref: SharedPreferences = ctx.getSharedPreferences(IMAGE, Context.MODE_PRIVATE)
        return pref.getString(FRIEND_Image, "") // (키 명, 든게 없을때 리턴할 값)
    }
}