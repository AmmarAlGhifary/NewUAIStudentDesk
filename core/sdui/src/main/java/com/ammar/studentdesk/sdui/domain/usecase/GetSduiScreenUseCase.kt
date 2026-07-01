package com.ammar.studentdesk.sdui.domain.usecase

import com.ammar.studentdesk.sdui.domain.repository.SduiRepository
import javax.inject.Inject

class GetSduiScreenUseCase @Inject constructor(
    private val repository : SduiRepository
){
    operator fun invoke(
        screenId: String,
        params: Map<String, String> = emptyMap()
    ) = repository.getScreen(screenId, params)
}