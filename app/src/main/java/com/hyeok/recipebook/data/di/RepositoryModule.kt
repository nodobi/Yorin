package com.hyeok.recipebook.data.di

import com.hyeok.recipebook.data.repository.IngredientRepository
import com.hyeok.recipebook.data.repository.impl.IngredientRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {


    @Provides
    @Singleton
    fun providesIngredientRepository(
    ): IngredientRepository = IngredientRepositoryImpl()
}