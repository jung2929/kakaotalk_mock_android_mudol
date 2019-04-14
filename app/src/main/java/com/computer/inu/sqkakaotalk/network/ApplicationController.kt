package com.computer.inu.sqkakaotalk.network

import android.app.Activity
import android.app.Application
import com.computer.inu.sqkakaotalk.KakaoSDKAdapter
import com.kakao.auth.KakaoSDK
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApplicationController : Application() {

    private val baseURL = "https://kapi.kakao.com"
    lateinit var networkService: NetworkService

    private val kacaoURL = "http://kaca5.com"
    lateinit var SqnetworkService: SqNetworkService


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
        SqbuildNetWork()
    }

    fun buildNetWork() {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        networkService = retrofit.create(NetworkService::class.java)
    }

    fun SqbuildNetWork() {
        val sqretrofit: Retrofit = Retrofit.Builder()
            .baseUrl(kacaoURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        SqnetworkService = sqretrofit.create(SqNetworkService::class.java)
    }
}