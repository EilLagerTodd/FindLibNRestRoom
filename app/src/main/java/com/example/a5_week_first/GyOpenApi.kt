package com.example.a5_week_first

import com.example.a5_week_first.TTdata.Parking
import com.example.a5_week_first.Tdata.Test
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

class GyOpenApi {
    companion object {
        val DOMAIN = "http://openAPI.seoul.go.kr:8088/" //http://openapi.seoul.go.kr:8088/(인증키)/xml/SeoulFoodHygieneBizHealthImport/1/5/
        val API_KEY = "5955716f6568617236395952524d6b"
    }
}

interface PubltoltOpen {
    @GET("{api_key}/json/GetParkInfo/1/200")  //https://openapi.gg.go.kr/Publtolt?
    fun getTest(@Path("api_key") key:String) : Call<Parking>
}