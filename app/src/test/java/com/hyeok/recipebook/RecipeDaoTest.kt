package com.hyeok.recipebook

import androidx.room3.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.hyeok.recipebook.data.database.AppDatabase
import com.hyeok.recipebook.data.database.entity.RecipeEntity
import com.hyeok.recipebook.data.database.entity.RecipeIngredientEntity
import com.hyeok.recipebook.data.database.entity.RecipeRecordEntity
import com.hyeok.recipebook.data.database.entity.RecipeStepEntity
import com.hyeok.recipebook.data.database.entity.WeightUnitEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDate
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class RecipeDaoTest {
    lateinit var db: AppDatabase
        private set

    @Before
    fun createDB() {
        db = Room.inMemoryDatabaseBuilder<AppDatabase>()
            .setDriver(BundledSQLiteDriver())
            .build()

        runBlocking {
            val weightUnitDao = db.weightUnitDao()
            val recipeDao = db.recipeDao()

            weightUnitDao.run {
                addUnit(WeightUnitEntity(name = "g"))
                addUnit(WeightUnitEntity(name = "kg"))
                addUnit(WeightUnitEntity(name = "ml"))
                addUnit(WeightUnitEntity(name = "L"))
            }

            recipeDao.addRecipe(
                RecipeEntity(
                    id = 1,
                    name = "레시피1",
                    registerDate = LocalDate(2026, 9, 10).toEpochDays(),
                    cookingTime = 30,
                    photoUrl = ""
                )
            )
            recipeDao.addRecipeSteps(listOf(
                RecipeStepEntity(
                    id = 1,
                    order = 1,
                    description = "순서1",
                    recipeId = 1
                ),
                RecipeStepEntity(
                    id = 2,
                    order = 2,
                    description = "순서2",
                    recipeId = 1
                )
            ))

            recipeDao.addRecipeIngredient(listOf(
                RecipeIngredientEntity(
                    id = 1,
                    name = "재료1",
                    weightUnitId = 1,
                    requireQuantity = 60,
                    recipeId = 1
                ),
                RecipeIngredientEntity(
                    id = 2,
                    name = "재료2",
                    weightUnitId = 1,
                    requireQuantity = 60,
                    recipeId = 1
                ),
                RecipeIngredientEntity(
                    id = 3,
                    name = "재료3",
                    weightUnitId = 1,
                    requireQuantity = 60,
                    recipeId = 1
                ),
            ))

            recipeDao.addRecipeRecords(listOf(
                RecipeRecordEntity(
                    id = 1,
                    cookedAt = LocalDate(2026, 9, 10).toEpochDays(),
                    title = "기록1",
                    description = "설명1",
                    score = 4,
                    recipeId = 1
                )
            ))
        }
    }


    @Test
    fun `레시피 요약 모델 로드 테스트`() = runTest {
        val recipeSummaries = db.recipeDao().getRecipesSummaries().first()
        println(recipeSummaries)
        assertTrue(recipeSummaries[0].rawIngredients == "재료1,재료2,재료3")
    }

    @Test
    fun `레시피 세부사항 로드 테스트`() = runTest {
        val recipeWithDetails = db.recipeDao().getRecipeWithDetailsByRecipeId(1L).first()
        println(recipeWithDetails)

        assertTrue(recipeWithDetails.records.isNotEmpty())
    }

    @After
    fun closeDB() {
        db.close()
    }
}