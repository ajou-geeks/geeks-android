package com.geeks.retrofit

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {
    override  fun intercept(chain: Interceptor.Chain): Response {
        val request=chain.request().newBuilder()
            //.addHeader("Authorization", "Bearer "+getToken()?:"")
            .build()

        return chain.proceed(request)
    }

    /*private fun getToken(): String {
        return MainActivity.GlobalApplication.prefs.getString("token", "xxxxxx")
    }*/
}