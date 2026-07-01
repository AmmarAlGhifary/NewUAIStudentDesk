package com.ammar.studentdesk.sdui.data.repository

import com.ammar.studentdesk.network.source.NetworkDataSource
import com.ammar.studentdesk.sdui.domain.model.SduiScreen
import com.ammar.studentdesk.sdui.domain.repository.SduiRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.Json
import javax.inject.Inject

class SduiRepositoryImpl @Inject constructor(
    private val networkDataSource: NetworkDataSource,
    private val json: Json
) : SduiRepository {
    override fun getScreen(
        screenId: String,
        params: Map<String, String>
    ): Flow<Result<SduiScreen>> {
        return networkDataSource.getBlueprint(screenId, params).map { result ->
            result.fold(
                onSuccess = { jsonString ->
                    try {
                        val screen = json.decodeFromString<SduiScreen>(jsonString)
                        Result.success(screen)
                    } catch (e: Exception ) {
                        Result.failure(Exception("Failed to parse sdui JSON: ${e.message}", e))
                    }
                },
                onFailure = { error ->
                    Result.failure(error)
                }
            )
        }
    }
}