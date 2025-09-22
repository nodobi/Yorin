package com.hyeok.recipebook.data.api

import kotlinx.serialization.Serializable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface RecipeApi {

    // TODO:: 테스트 이후 삭제
    @GET("rest/v1/Sample")
    suspend fun getSample(): Response<List<Sample>>

}

// TODO:: 테스트 이후 삭제
@Serializable
data class Sample(
    val id: Int,
    val name: String
)