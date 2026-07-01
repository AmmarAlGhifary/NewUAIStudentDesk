package com.ammar.studentdesk.network.source

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NetworkDataSourceImpl @Inject constructor(
    private val apiService: ApiService
) : NetworkDataSource {

    override fun getBlueprint(screenId: String, queryParams: Map<String, String>): Flow<Result<String>> = flow {
        try {
            val url = "api/$screenId"
            val response = apiService.getScreenBlueprint(url, queryParams)
            if (response.isSuccessful && response.body() != null) {
                val rawJson = response.body()!!.string()
                emit(Result.success(rawJson))
            } else {
                emit(Result.failure(Exception("Network error: ${response.code()} + ${response.message()}")))
            }
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }
}