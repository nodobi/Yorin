package com.hyeok.recipebook.data.api

import com.hyeok.recipebook.data.dto.RecipeResponse
import retrofit2.Response
import retrofit2.http.GET

interface RecipeApi {
    @GET("rest/v1/recipes")
    suspend fun getRecipes(): Response<List<RecipeResponse>>
}