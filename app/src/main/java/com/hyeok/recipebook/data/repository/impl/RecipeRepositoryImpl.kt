package com.hyeok.recipebook.data.repository.impl

import com.hyeok.recipebook.data.database.model.RecipeRecommendationModel
import com.hyeok.recipebook.data.database.model.toModel
import com.hyeok.recipebook.data.database.model.toUiModel
import com.hyeok.recipebook.data.repository.RecipeRepository
import com.hyeok.recipebook.data.source.IngredientLocalDataSource
import com.hyeok.recipebook.data.source.RecipeLocalDataSource
import com.hyeok.recipebook.presentation.home.model.RecommendationReason
import com.hyeok.recipebook.presentation.recipe.detail.records.RecipeRecordUiModel
import com.hyeok.recipebook.presentation.recipe.edit.model.RecipeEditUiModel
import com.hyeok.recipebook.presentation.recipe.list.RecipeItemUiModel
import com.hyeok.recipebook.presentation.recipe.model.RecipeUiModel
import com.hyeok.recipebook.presentation.util.DateTimeUtil
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn
import javax.inject.Inject
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

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


    override fun getRecipeDetailsById(recipeId: Long): Flow<Result<RecipeUiModel?>> = recipeLocalDataSource
        .getRecipesWithDetailsById(recipeId)
        .map { recipeDetailModel ->
            if (recipeDetailModel == null) return@map Result.success(null)

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

    override suspend fun updateRecipe(
        recipeId: Long,
        recipe: RecipeEditUiModel
    ): Result<Long> = runCatching {
        recipeLocalDataSource.updateRecipe(
            recipe.toModel(
                recipeId = recipeId
            )
        )
    }

    override suspend fun addRecipe(
        recipe: RecipeEditUiModel
    ): Result<Long> = runCatching {
        recipeLocalDataSource.addRecipe(
            recipe.toModel(),
            DateTimeUtil.currentLocalDate().toEpochDays()
        )
    }

    override suspend fun addRecipeRecord(recipeId: Long, record: RecipeRecordUiModel): Result<Unit> = runCatching {
        recipeLocalDataSource.addRecipeRecord(
            recipeId = recipeId,
            record = record.toModel()
        )
    }

    override suspend fun removeRecipe(recipeId: Long): Result<Unit> = runCatching {
        recipeLocalDataSource.removeRecipe(recipeId)
    }

    @OptIn(ExperimentalTime::class)
    override fun getRecipeRecommendationsStream(): Flow<RecipeRecommendationModel> = flow {
        val recipes = recipeLocalDataSource.getRecipesWithDetails()
        val stockMap = ingredientLocalDataSource.getIngredients().first().associateBy { it.name }

        val recommends = recipes.map { recipeDetail ->
            val reason = mutableSetOf<RecommendationReason>()
            val today = Clock.System.todayIn(TimeZone.currentSystemDefault()).toEpochDays()

            val missingIngredient = recipeDetail.ingredients.filter {
                it.requireQuantity > (stockMap[it.name]?.weight ?: 0)
            }

            val expiringIngredients = recipeDetail.ingredients.map { recipeIngredient ->
                val ownedIngredient = stockMap[recipeIngredient.name] ?: return@map null
                val expirationDay = ownedIngredient.expirationDate ?: return@map null
                val daysToExpiration = today - expirationDay

                if (daysToExpiration in 0..2)
                    ownedIngredient
                else
                    null
            }.filterNotNull()

            if (missingIngredient.isEmpty()) {
                reason.add(RecommendationReason.ReadyToCook)
            } else if (missingIngredient.size < 2) {
                reason.add(RecommendationReason.MissingIngredients(missingIngredient))
            }

            if (expiringIngredients.isNotEmpty()) {
                reason.add(RecommendationReason.UseExpiringIngredients(expiringIngredients))
            }

            if (reason.isEmpty()) null
            else RecipeRecommendationModel(
                recipe = recipeDetail,
                reason = reason
            )
        }.filterNotNull()

        recommends.forEach {
            emit(it)
        }
    }
}