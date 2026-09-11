package com.hyeok.recipebook.data.database.dao

import androidx.room3.Dao
import androidx.room3.Insert
import androidx.room3.Query
import androidx.room3.Transaction
import com.hyeok.recipebook.data.database.entity.RecipeEntity
import com.hyeok.recipebook.data.database.entity.RecipeIngredientEntity
import com.hyeok.recipebook.data.database.entity.RecipeRecordEntity
import com.hyeok.recipebook.data.database.entity.RecipeStepEntity
import com.hyeok.recipebook.data.database.entity.RecipeWithDetailsRecord
import com.hyeok.recipebook.data.database.model.RecipeSummaryModel
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeDao {
    @Insert
    suspend fun addRecipe(recipe: RecipeEntity)

    @Insert
    suspend fun addRecipeIngredient(ingredients: List<RecipeIngredientEntity>)

    @Insert
    suspend fun addRecipeSteps(steps: List<RecipeStepEntity>)

    @Insert
    suspend fun addRecipeRecords(records: List<RecipeRecordEntity>)

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
}