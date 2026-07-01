package com.ammar.studentdesk.network.source

import kotlinx.coroutines.flow.Flow

interface NetworkDataSource {
    fun getBlueprint(
        screenId: String,
        queryParams: Map<String, String> = emptyMap()
    ): Flow<Result<String>>
}