package com.computer.inu.sqkakaotalk.network

import android.app.Application
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import android.app.Activity
import com.computer.inu.sqkakaotalk.KakaoSDKAdapter
import com.kakao.auth.KakaoSDK

class ApplicationController : Application() {
    private val baseURL = "https://kapi.kakao.com"
    lateinit var networkService: NetworkService
    companion object {
        lateinit var instance: ApplicationController
        @Volatile
        private var currentActivity: Activity? = null
        @Volatile
        private var obj: ApplicationController? = null
        fun setCurrentActivity(currentActivity: Activity) {
            ApplicationController.currentActivity = currentActivity
        }
        fun getGlobalApplicationContext(): ApplicationController {
            return obj!!
        }

        fun getCurrentActivity(): Activity {
            return currentActivity!!
        }

        // Activity가 올라올때마다 Activity의 onCreate에서 호출해줘야한다.

    }
    override fun onCreate() {
        super.onCreate()
        obj = this
        KakaoSDK.init(KakaoSDKAdapter())
        instance = this
        buildNetWork()

    }

    fun buildNetWork() {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        networkService = retrofit.create(NetworkService::class.java)
    }
}