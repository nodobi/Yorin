package com.hyeok.recipebook.data.database.entity

import androidx.room3.Embedded
import androidx.room3.Relation

data class RecipeWithDetailsRecord(
    @Embedded
    val recipe: RecipeEntity,

    @Relation(
        entity = RecipeIngredientEntity::class,
        parentColumns = ["id"],
        entityColumns = ["recipeId"]
    )
    val ingredients: List<RecipeIngredientEntity>,

    @Relation(
        entity = RecipeStepEntity::class,
        parentColumns = ["id"],
        entityColumns = ["recipeId"]
    )
    val steps: List<RecipeStepEntity>,

    @Relation(
        entity = RecipeRecordEntity::class,
        parentColumns = ["id"],
        entityColumns = ["recipeId"]
    )
    val records: List<RecipeRecordEntity>,
)