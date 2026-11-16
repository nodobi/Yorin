package com.hyeok.recipebook.data.repository.impl

import com.hyeok.recipebook.data.database.model.RecipeDetailsModel
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

        // 이름이 같은 재료가 있을 수 있다
        val stockMap = ingredientLocalDataSource.getIngredients().first().groupBy { it.name }

        val recommends = recipes.map { recipeDetail ->
            val reason = mutableSetOf<RecommendationReason>()
            val today = Clock.System.todayIn(TimeZone.currentSystemDefault()).toEpochDays()

            val missingIngredient = recipeDetail.ingredients.filter { requiredIngredient ->
                val ownedQuantity = stockMap[requiredIngredient.name].orEmpty().sumOf {
                    when (it.weightUnit) {
                        "kg", "L" -> it.weight * 1000L
                        else -> it.weight.toLong()
                    }
                }
                val requireQuantity = when (requiredIngredient.weightUnit) {
                    "kg", "L" -> requiredIngredient.requireQuantity * 1000L
                    else -> requiredIngredient.requireQuantity.toLong()
                }
                requireQuantity > ownedQuantity
            }

            // 같은 이름을 가진 재료들이 모두 소비기한에 가깝다면 모두 포함한다
            val expiringIngredients = recipeDetail.ingredients
                .flatMap { stockMap[it.name].orEmpty() }
                .filter { ownedIngredient ->
                    val expirationDay = ownedIngredient.expirationDate ?: return@filter false
                    val daysToExpiration = today - expirationDay

                    daysToExpiration in 0..2
                }

            if (missingIngredient.isEmpty()) {
                reason.add(RecommendationReason.ReadyToCook)
            } else if (missingIngredient.size <= 2) {
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

    override suspend fun getReadyToCookRecipes(limit: Int): Result<List<RecipeDetailsModel>> = runCatching {
        val recipes = recipeLocalDataSource.getRecipesWithDetails()
        val ingredients = ingredientLocalDataSource.getIngredients().first()

        // 같은 이름인 재료는 수를 더한다
        val stockMap = ingredients
            .groupBy { it.name }
            .mapValues { (_, ownedIngredient) ->
                ownedIngredient.sumOf {
                    when (it.weightUnit) {
                        "kg", "L" -> it.weight * 1000L
                        else -> it.weight.toLong()
                    }
                }
            }

        recipes.filter {
            it.ingredients.all { requiredIngredient ->
                val ownedQuantity = stockMap[requiredIngredient.name] ?: 0L
                val requireQuantity = when (requiredIngredient.weightUnit) {
                    "kg", "L" -> requiredIngredient.requireQuantity * 1000L
                    else -> requiredIngredient.requireQuantity.toLong()
                }
                ownedQuantity >= requireQuantity
            }
        }.take(limit)
    }

    override suspend fun getRecentlyCookedRecipes(limit: Int): Result<List<RecipeDetailsModel>> = runCatching {
        recipeLocalDataSource.getRecentlyCookedRecipes(limit)
    }
}