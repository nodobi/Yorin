package com.hyeok.recipebook.data.database

import android.content.Context
import androidx.room3.Database
import androidx.room3.Room
import androidx.room3.RoomDatabase
import com.hyeok.recipebook.data.database.dao.IngredientDao
import com.hyeok.recipebook.data.database.dao.RecipeDao
import com.hyeok.recipebook.data.database.dao.WeightUnitDao
import com.hyeok.recipebook.data.database.entity.IngredientEntity
import com.hyeok.recipebook.data.database.entity.WeightUnitEntity

@Database(
    entities = [IngredientEntity::class, WeightUnitEntity::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun ingredientDao(): IngredientDao
    abstract fun recipeDao(): RecipeDao
    abstract fun weightUnitDao(): WeightUnitDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "ingredient_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}