package com.ammar.studentdesk.auth.data

import com.ammar.studentdesk.network.source.ApiService
import com.ammar.studentdesk.network.source.model.LoginRequest
import com.ammar.studentdesk.network.source.model.LoginResponse
import com.ammar.studentdesk.network.utils.TokenManager
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val apiService: ApiService,
    private val tokenManager: TokenManager
) {
    suspend fun login(nim: String, password: String): Result<LoginResponse> {
        return withContext(IO) {
            try {
                val response = apiService.auth(LoginRequest(nim, password))
                if (response.isSuccessful && response.body() != null) {
                    val body = response.body()!!
                    tokenManager.saveToken(body.token)
                    Result.success(body)
                } else {
                    Result.failure(Exception("Login failed: ${response.code()}"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
}