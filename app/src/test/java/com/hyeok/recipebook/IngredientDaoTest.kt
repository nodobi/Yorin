package com.hyeok.recipebook

import androidx.room3.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.hyeok.recipebook.data.database.AppDatabase
import com.hyeok.recipebook.data.database.entity.IngredientEntity
import com.hyeok.recipebook.data.database.entity.WeightUnitEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDate
import org.junit.After
import org.junit.Before
import org.junit.Test

class IngredientDaoTest {

    lateinit var db: AppDatabase
        private set

    @Before
    fun createDB() {
        db = Room.inMemoryDatabaseBuilder<AppDatabase>()
            .setDriver(BundledSQLiteDriver())
            .build()


        runBlocking {
            val weightUnitDao = db.weightUnitDao()
            val ingredientDao = db.ingredientDao()

            weightUnitDao.run {
                addUnit(WeightUnitEntity(name = "g"))
                addUnit(WeightUnitEntity(name = "kg"))
                addUnit(WeightUnitEntity(name = "ml"))
                addUnit(WeightUnitEntity(name = "L"))
            }

            ingredientDao.insertIngredient(
                IngredientEntity(
                    id = 1,
                    name = "김치",
                    purchaseDate = LocalDate(2026, 9, 4).toEpochDays(),
                    expirationDate = LocalDate(2026, 9, 6).toEpochDays(),
                    weight = 100,
                    weightUnitId = 1,
                    description = ""
                )
            )

            ingredientDao.insertIngredient(
                IngredientEntity(
                    id = 2,
                    name = "두부",
                    purchaseDate = LocalDate(2026, 9, 4).toEpochDays(),
                    expirationDate = LocalDate(2026, 9, 6).toEpochDays(),
                    weight = 200,
                    weightUnitId = 1,
                    description = ""
                )
            )
        }
    }

    @Test
    fun `재료 무게 단위 로드 테스트`() = runTest {
        val ingredient = db.ingredientDao().getIngredient(1).first()
        assert(ingredient.id == 1L)
        assert(ingredient.weightUnit == "g")
    }

    @Test
    fun `모든 재료 로드 테스트`() = runTest {
        val ingredients = db.ingredientDao().getAllIngredients().first()

        assert(ingredients.size == 2)
    }

    @After
    fun closeDB() {
        db.close()
    }
}