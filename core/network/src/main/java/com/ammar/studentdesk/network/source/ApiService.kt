package com.ammar.studentdesk.network.source

import com.ammar.studentdesk.network.source.model.LoginRequest
import com.ammar.studentdesk.network.source.model.LoginResponse
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.QueryMap
import retrofit2.http.Url
interface ApiService {

    // Get Screen Blueprint
    @GET
    suspend fun getScreenBlueprint(
        @Url url: String,
        @QueryMap queryParams: Map<String, String> = emptyMap()
    ) : Response<ResponseBody>

    // Get Auth
    @POST("api/login")
    suspend fun auth(
        @Body request: LoginRequest
    ): Response<LoginResponse>

}
