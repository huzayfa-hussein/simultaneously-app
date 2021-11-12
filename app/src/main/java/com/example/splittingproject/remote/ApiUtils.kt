package com.example.splittingproject.remote

import android.content.Context

/**
 * Created By Huzayfa elhussein on 11/09/2021
 */


class ApiUtils {


    companion object {
        private const val BASE_URL =
            "https://truecaller.blog/2018/01/22/life-as-an-android-engineer/"

        fun getApiService(context: Context): APIService? {
            return RetrofitClient.getClient(BASE_URL)?.create(APIService::class.java)
        }
    }
}