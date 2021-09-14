package com.example.a5_week_first

import android.app.Application
import com.kakao.sdk.common.KakaoSdk

class GlobalApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        KakaoSdk.init(this, "9b8e6fa51ce5fa6f457ccff5d9d56303")
    }
}