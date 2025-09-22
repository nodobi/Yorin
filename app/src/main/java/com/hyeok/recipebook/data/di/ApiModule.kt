package com.hyeok.recipebook.data.di

import com.hyeok.recipebook.data.api.RecipeApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    @Singleton
    fun provideRecipeApi(
        retrofit: Retrofit
    ): RecipeApi {
        return retrofit.create(RecipeApi::class.java)
    }
}