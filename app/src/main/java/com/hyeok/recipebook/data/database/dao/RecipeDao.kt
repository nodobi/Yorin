package com.hyeok.recipebook.data.database.dao

import androidx.room3.Dao
import androidx.room3.Insert
import androidx.room3.Query
import androidx.room3.Transaction
import androidx.room3.Upsert
import com.hyeok.recipebook.data.database.entity.RecipeEntity
import com.hyeok.recipebook.data.database.entity.RecipeIngredientEntity
import com.hyeok.recipebook.data.database.entity.RecipeRecordEntity
import com.hyeok.recipebook.data.database.entity.RecipeStepEntity
import com.hyeok.recipebook.data.database.entity.RecipeWithDetailsRecord
import com.hyeok.recipebook.data.database.model.RecipeIngredientModel
import com.hyeok.recipebook.data.database.model.RecipeRecordModel
import com.hyeok.recipebook.data.database.model.RecipeStepModel
import com.hyeok.recipebook.data.database.model.RecipeSummaryModel
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeDao {
    @Upsert
    suspend fun upsertRecipe(recipe: RecipeEntity): Long

    @Upsert
    suspend fun upsertRecipeIngredients(ingredients: List<RecipeIngredientEntity>)

    @Upsert
    suspend fun upsertRecipeSteps(steps: List<RecipeStepEntity>)

    @Insert
    suspend fun insertRecipeRecord(record: RecipeRecordEntity)

    @Upsert
    suspend fun upsertRecipeRecords(records: List<RecipeRecordEntity>)

    @Query(
        """
        SELECT
            r.id AS id,
            r.name AS name,
            r.registerDate AS registerDate,
            r.cookingTime AS cookingTime,
            r.photoUrl AS photoUrl,
            GROUP_CONCAT(DISTINCT ri.name) AS rawIngredients,
            COALESCE((
                SELECT AVG(rr.score)
                FROM recipe_record rr
                WHERE rr.recipeId = r.id
            ), 0.0) AS averageScore
        FROM recipe r
        LEFT JOIN recipe_ingredient ri ON ri.recipeId = r.id
        GROUP BY r.id
    """
    )
    fun getRecipesSummaries(): Flow<List<RecipeSummaryModel>>

    @Transaction
    @Query("SELECT * FROM recipe WHERE id = :id")
    fun getRecipeWithDetailsByRecipeId(id: Long): Flow<RecipeWithDetailsRecord>

    @Transaction
    suspend fun upsertRecipeWithDetails(
        recipe: RecipeEntity,
        ingredientModels: List<RecipeIngredientModel>,
        stepModels: List<RecipeStepModel>,
        recordModels: List<RecipeRecordModel>,
        weightUnitMap: Map<String, Long>
    ) {
        val recipeId = upsertRecipe(recipe)

        upsertRecipeIngredients(
            ingredientModels.map { model ->
                RecipeIngredientEntity(
                    id = model.id,
                    name = model.name,
                    weightUnitId = weightUnitMap.getValue(model.weightUnit),
                    requireQuantity = model.requireQuantity,
                    recipeId = recipeId
                )
            }
        )

        upsertRecipeSteps(
            stepModels.map { model ->
                RecipeStepEntity(
                    id = model.id,
                    order = model.order,
                    description = model.description,
                    recipeId = recipeId
                )
            }
        )

        upsertRecipeRecords(
            recordModels.map { model ->
                RecipeRecordEntity(
                    id = model.id,
                    cookedAt = model.cookedAt,
                    title = model.title,
                    description = model.description,
                    score = model.score,
                    recipeId = recipeId
                )
            }
        )
    }
}