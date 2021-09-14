package com.example.a5_week_first

import com.example.a5_week_first.data.Library
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

class OpenApi {
    companion object {
        val DOMAIN = "http://openAPI.seoul.go.kr:8088/" //http://openapi.seoul.go.kr:8088/(인증키)/xml/SeoulFoodHygieneBizHealthImport/1/5/
        val API_KEY = "755171535668617239366170785071"
    }
}

interface SeoulOpenService {
    @GET("{api_key}/json/SeoulPublicLibraryInfo/1/200") //https://openapi.gg.go.kr/Publtolt?
    fun getLibrary(@Path("api_key") key:String) : Call<Library>
}