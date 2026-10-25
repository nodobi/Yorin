package com.hyeok.recipebook.data.database.dao

import androidx.room3.Dao
import androidx.room3.Delete
import androidx.room3.Insert
import androidx.room3.Query
import androidx.room3.Transaction
import androidx.room3.Upsert
import com.hyeok.recipebook.data.database.entity.RecipeEntity
import com.hyeok.recipebook.data.database.entity.RecipeIngredientEntity
import com.hyeok.recipebook.data.database.entity.RecipeRecordEntity
import com.hyeok.recipebook.data.database.entity.RecipeStepEntity
import com.hyeok.recipebook.data.database.entity.RecipeWithDetailsRecord
import com.hyeok.recipebook.data.database.model.RecipeEditModel
import com.hyeok.recipebook.data.database.model.RecipeIngredientModel
import com.hyeok.recipebook.data.database.model.RecipeRecordModel
import com.hyeok.recipebook.data.database.model.RecipeStepModel
import com.hyeok.recipebook.data.database.model.RecipeSummaryModel
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeDao {
    @Insert
    suspend fun insertRecipe(recipe: RecipeEntity): Long

    @Query(
        """
        UPDATE recipe
        SET
            name = :name,
            cookingTime = :cookingTime,
            photoUrl = :photoUrl
        WHERE id = :id
    """
    )
    suspend fun updateRecipe(
        id: Long,
        name: String,
        cookingTime: Int,
        photoUrl: String?
    )

    @Upsert
    suspend fun upsertRecipeIngredients(ingredients: List<RecipeIngredientEntity>)

    @Delete
    suspend fun deleteRecipeIngredients(ingredients: List<RecipeIngredientEntity>)

    @Query("SELECT * FROM recipe_ingredient WHERE recipeId = :recipeId")
    suspend fun getRecipeIngredientsByRecipeId(recipeId: Long): List<RecipeIngredientEntity>

    /**
     * 레시피 재료를 새로 들어온 리스트로 교체
     */
    @Transaction
    suspend fun replaceAllRecipeIngredients(newIngredients: List<RecipeIngredientEntity>) {
        val oldIngredients = getRecipeIngredientsByRecipeId(newIngredients.first().recipeId)

        // 새로운 재료에 없는 기존 재료는 삭제되어야 한다
        val ingredientsToDelete = oldIngredients.filter { oldIngredient ->
            newIngredients.none { it.id == oldIngredient.id }
        }


        deleteRecipeIngredients(ingredientsToDelete)

        upsertRecipeIngredients(newIngredients)
    }

    @Upsert
    suspend fun upsertRecipeSteps(steps: List<RecipeStepEntity>)

    @Query("SELECT * FROM recipe_step WHERE recipeId = :recipeId")
    suspend fun getRecipeStepsByRecipeId(recipeId: Long): List<RecipeStepEntity>

    @Delete
    suspend fun deleteRecipeSteps(steps: List<RecipeStepEntity>)

    @Transaction
    suspend fun replaceRecipeSteps(newSteps: List<RecipeStepEntity>) {
        val savedSteps = getRecipeStepsByRecipeId(newSteps.first().recipeId)

        val stepsToDelete = savedSteps.filter { oldStep ->
            newSteps.none { it.id == oldStep.id }
        }

        deleteRecipeSteps(stepsToDelete)

        upsertRecipeSteps(newSteps)
    }

    @Insert
    suspend fun insertRecipeRecord(record: RecipeRecordEntity)

    @Upsert
    suspend fun upsertRecipeRecords(records: List<RecipeRecordEntity>)

    @Query("SELECT * FROM recipe_record WHERE recipeId = :recipeId")
    suspend fun getRecipeRecordsByRecipeId(recipeId: Long): List<RecipeRecordEntity>

    @Delete
    suspend fun deleteRecipeRecords(records: List<RecipeRecordEntity>)

    @Transaction
    suspend fun replaceRecipeRecords(newRecords: List<RecipeRecordEntity>) {
        val savedRecords = getRecipeRecordsByRecipeId(newRecords.first().recipeId)

        val recordsToDelete = savedRecords.filter { oldRecord ->
            newRecords.none { it.id == oldRecord.id }
        }

        deleteRecipeRecords(recordsToDelete)
        upsertRecipeRecords(newRecords)
    }

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
    suspend fun insertRecipeWithDetails(
        recipe: RecipeEditModel,
        registerDate: Long,
        weightUnitMap: Map<String, Long>
    ): Long {
        val recipeId = insertRecipe(
            RecipeEntity(
                name = recipe.name,
                registerDate = registerDate,
                cookingTime = recipe.cookingTime,
                photoUrl = recipe.photoUrl
            )
        )

        // 새 레시피에는 기존 데이터가 없으므로 교체 없이 그대로 저장
        // TODO::Upsert 에서 insert 로 변경
        upsertRecipeIngredients(recipe.ingredients.toEntities(recipeId, weightUnitMap))
        upsertRecipeSteps(recipe.steps.toEntities(recipeId))
        upsertRecipeRecords(recipe.records.toEntities(recipeId))

        return recipeId
    }

    @Transaction
    suspend fun updateRecipeWithDetails(
        recipe: RecipeEditModel,
        weightUnitMap: Map<String, Long>
    ) {
        updateRecipe(
            id = recipe.id,
            name = recipe.name,
            cookingTime = recipe.cookingTime,
            photoUrl = recipe.photoUrl
        )

        replaceAllRecipeIngredients(recipe.ingredients.toEntities(recipe.id, weightUnitMap))
        replaceRecipeSteps(recipe.steps.toEntities(recipe.id))
        replaceRecipeRecords(recipe.records.toEntities(recipe.id))
    }
}

@JvmName("ingredientModelToEntities")
private fun List<RecipeIngredientModel>.toEntities(
    recipeId: Long,
    weightUnitMap: Map<String, Long>
) = map { model ->
    RecipeIngredientEntity(
        id = model.id,
        name = model.name,
        weightUnitId = weightUnitMap.getValue(model.weightUnit),
        requireQuantity = model.requireQuantity,
        recipeId = recipeId
    )
}

@JvmName("stepModelToEntities")
private fun List<RecipeStepModel>.toEntities(recipeId: Long) = map { model ->
    RecipeStepEntity(
        id = model.id,
        order = model.order,
        description = model.description,
        recipeId = recipeId
    )
}

@JvmName("recordModelToEntities")
private fun List<RecipeRecordModel>.toEntities(recipeId: Long) = map { model ->
    RecipeRecordEntity(
        id = model.id,
        cookedAt = model.cookedAt,
        title = model.title,
        description = model.description,
        score = model.score,
        recipeId = recipeId
    )
}