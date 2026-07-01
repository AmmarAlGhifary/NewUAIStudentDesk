package com.ammar.studentdesk.sdui.domain.repository

import com.ammar.studentdesk.sdui.domain.model.SduiScreen
import kotlinx.coroutines.flow.Flow

interface SduiRepository {

    fun getScreen(
        screenId: String,
        params: Map<String, String> = emptyMap()
    ): Flow<Result<SduiScreen>>
}