package com.hyeok.recipebook.data.database.model

import androidx.room3.Embedded
import androidx.room3.Relation
import com.hyeok.recipebook.data.database.entity.RecipeEntity
import com.hyeok.recipebook.data.database.entity.RecipeIngredientEntity
import com.hyeok.recipebook.data.database.entity.RecipeRecordEntity

data class RecipesWithIngredients(
    @Embedded val recipe: RecipeEntity,

    @Relation(
        entity = RecipeIngredientEntity::class,
        parentColumns = ["id"],
        entityColumns = ["recipeId"]
    )
    val ingredients: List<RecipeIngredientEntity>,

    @Relation(
        entity = RecipeRecordEntity::class,
        parentColumns = ["id"],
        entityColumns = ["recipeId"]
    )
    val records: List<RecipeRecordEntity>
) {
    val averageScore: Float get() = if(records.isEmpty()) 0f else records.map { it.score }.average().toFloat()
}