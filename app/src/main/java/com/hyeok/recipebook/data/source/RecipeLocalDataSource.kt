package com.hyeok.recipebook.data.source

import com.hyeok.recipebook.data.database.dao.RecipeDao
import com.hyeok.recipebook.data.database.dao.WeightUnitDao
import com.hyeok.recipebook.data.database.entity.RecipeEntity
import com.hyeok.recipebook.data.database.entity.RecipeRecordEntity
import com.hyeok.recipebook.data.database.model.RecipeDetailsModel
import com.hyeok.recipebook.data.database.model.RecipeIngredientModel
import com.hyeok.recipebook.data.database.model.RecipeRecordModel
import com.hyeok.recipebook.data.database.model.RecipeStepModel
import com.hyeok.recipebook.data.database.model.RecipeSummaryModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RecipeLocalDataSource @Inject constructor(
    private val recipeDao: RecipeDao,
    private val weightUnitDao: WeightUnitDao,
) {
    fun getRecipesSummaries(): Flow<List<RecipeSummaryModel>> = recipeDao.getRecipesSummaries()

    fun getRecipesWithDetailsById(recipeId: Long): Flow<RecipeDetailsModel> = recipeDao.getRecipeWithDetailsByRecipeId(recipeId)
        .map { (recipe, ingredients, steps, records) ->
            RecipeDetailsModel(
                id = recipe.id,
                name = recipe.name,
                registerDate = recipe.registerDate,
                cookingTime = recipe.cookingTime,
                photoUrl = recipe.photoUrl,
                ingredients = ingredients.map { ingredient ->
                    RecipeIngredientModel(
                        id = ingredient.id,
                        name = ingredient.name,
                        weightUnit = weightUnitDao.getNameById(ingredient.weightUnitId),
                        requireQuantity = ingredient.requireQuantity,
                    )
                },
                steps = steps.map { step ->
                    RecipeStepModel(
                        id = step.id,
                        order = step.order,
                        description = step.description
                    )
                },
                records = records.map { record ->
                    RecipeRecordModel(
                        id = record.id,
                        cookedAt = record.cookedAt,
                        title = record.title,
                        description = record.description,
                        score = record.score,
                    )
                }
            )
        }

    suspend fun upsertRecipeWithDetails(
        recipeWithDetails: RecipeDetailsModel
    ) {
        val weightUnitMap = weightUnitDao.getAllUnits().associate { (id, unit) ->
            unit to id
        }

        recipeDao.upsertRecipeWithDetails(
            recipe = RecipeEntity(
                id = recipeWithDetails.id,
                name = recipeWithDetails.name,
                registerDate = recipeWithDetails.registerDate,
                cookingTime = recipeWithDetails.cookingTime,
                photoUrl = recipeWithDetails.photoUrl
            ),
            ingredientModels = recipeWithDetails.ingredients,
            stepModels = recipeWithDetails.steps,
            recordModels = recipeWithDetails.records,
            weightUnitMap = weightUnitMap,
        )
    }

    suspend fun addRecipeRecord(recipeId: Long, record: RecipeRecordModel) {
        recipeDao.insertRecipeRecord(
            record = RecipeRecordEntity(
                id = record.id,
                cookedAt = record.cookedAt,
                title = record.title,
                description = record.description,
                score = record.score,
                recipeId = recipeId
            )
        )
    }
}