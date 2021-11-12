package com.example.splittingproject.remote

import retrofit2.Call
import retrofit2.http.GET

/**
 * Created By Huzayfa elhussein on 11/09/2021
 */
interface APIService {

    @GET(".")
    fun callTrueCallerWeb(): Call<String>
}