package com.hyeok.recipebook.data.repository.impl

import com.hyeok.recipebook.data.database.model.toUiModel
import com.hyeok.recipebook.data.repository.RecipeRepository
import com.hyeok.recipebook.data.source.IngredientLocalDataSource
import com.hyeok.recipebook.data.source.RecipeLocalDataSource
import com.hyeok.recipebook.presentation.recipe.detail.RecipeUiModel
import com.hyeok.recipebook.presentation.recipe.list.RecipeItemUiModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RecipeRepositoryImpl @Inject constructor(
    private val recipeLocalDataSource: RecipeLocalDataSource,
    private val ingredientLocalDataSource: IngredientLocalDataSource
) : RecipeRepository {
    override suspend fun getRecipesByIngredient(ingredientId: Long): Result<List<RecipeItemUiModel>> {
        TODO("Not yet implemented")
    }

    override fun getRecipesSummaries(): Flow<Result<List<RecipeItemUiModel>>> = recipeLocalDataSource
        .getRecipesSummaries()
        .map { summaries ->
            summaries.map {
                it.toUiModel()
            }.let {
                Result.success(it)
            }
        }
        .catch {
            Result.failure<List<RecipeItemUiModel>>(it)
        }


    override fun getRecipeDetailsById(recipeId: Long): Flow<Result<RecipeUiModel>> = recipeLocalDataSource
        .getRecipesWithDetailsById(recipeId)
        .map { recipeDetailModel ->
            // 가지고 있는 재료 양 구하기, 이름이 정확하게 일치하는 경우만 구해짐
            val stockMap = recipeDetailModel.ingredients.associate {
                it.name to ingredientLocalDataSource.getIngredients(it.name).sumOf { it.weight }
            }

            recipeDetailModel.toUiModel(stockMap).let {
                Result.success(it)
            }
        }
        .catch {
            Result.failure<RecipeUiModel>(it)
        }
}