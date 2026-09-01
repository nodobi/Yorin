package com.hyeok.recipebook.data.di

import com.hyeok.recipebook.data.repository.IngredientRepository
import com.hyeok.recipebook.data.repository.RecipeRepository
import com.hyeok.recipebook.data.repository.impl.IngredientRepositoryImpl
import com.hyeok.recipebook.data.repository.impl.RecipeRepositoryImpl
import com.hyeok.recipebook.data.source.IngredientLocalDataSource
import com.hyeok.recipebook.data.source.RecipeLocalDataSource
import com.hyeok.recipebook.data.source.WeightUnitLocalDataSource
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
        ingredientLocalDataSource: IngredientLocalDataSource,
        weightUnitLocalDataSource: WeightUnitLocalDataSource
    ): IngredientRepository = IngredientRepositoryImpl(
        ingredientLocalDataSource,
        weightUnitLocalDataSource
    )

    @Provides
    @Singleton
    fun providesRecipeRepository(
        recipeLocalDataSource: RecipeLocalDataSource
    ): RecipeRepository = RecipeRepositoryImpl(
        recipeLocalDataSource
    )
}