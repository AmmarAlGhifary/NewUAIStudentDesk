package com.ammar.studentdesk.network.interceptor

import com.ammar.studentdesk.network.utils.TokenManager
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val tokenManager: TokenManager) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()

        val token = runBlocking { tokenManager.getToken().first() }

        if (!token.isNullOrBlank() && !chain.request().url.encodedPath.contains("api/login")) {
            requestBuilder.addHeader("Authorization", "Bearer $token")
        }

        val response = chain.proceed(requestBuilder.build())

        if (response.code == 401) {
            runBlocking { tokenManager.clearToken() }
        }

        return response
    }
}
