package com.hyeok.recipebook.data.di

import android.content.Context
import com.hyeok.recipebook.data.database.AppDatabase
import com.hyeok.recipebook.data.database.dao.IngredientDao
import com.hyeok.recipebook.data.database.dao.RecipeDao
import com.hyeok.recipebook.data.database.dao.WeightUnitDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun providesAppDatabase(
        @ApplicationContext context: Context,
        @ApplicationScope scope: CoroutineScope
    ): AppDatabase = AppDatabase.getDatabase(context, scope)

    @Singleton
    @Provides
    fun providesIngredientDao(
        appDatabase: AppDatabase
    ): IngredientDao = appDatabase.ingredientDao()

    @Singleton
    @Provides
    fun providesRecipeDao(
        appDatabase: AppDatabase
    ): RecipeDao = appDatabase.recipeDao()

    @Singleton
    @Provides
    fun providesWeightUnitDao(
        appDatabase: AppDatabase
    ): WeightUnitDao = appDatabase.weightUnitDao()
}