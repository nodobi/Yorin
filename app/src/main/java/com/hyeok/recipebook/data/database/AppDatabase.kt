package com.hyeok.recipebook.data.database

import android.content.Context
import androidx.room3.Database
import androidx.room3.Room
import androidx.room3.RoomDatabase
import androidx.sqlite.SQLiteConnection
import com.hyeok.recipebook.data.database.dao.IngredientDao
import com.hyeok.recipebook.data.database.dao.RecipeDao
import com.hyeok.recipebook.data.database.dao.WeightUnitDao
import com.hyeok.recipebook.data.database.entity.IngredientEntity
import com.hyeok.recipebook.data.database.entity.WeightUnitEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

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

        fun getDatabase(context: Context, scope: CoroutineScope): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "ingredient_database"
                ).addCallback(AppDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

    private class AppDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {
        override suspend fun onCreate(connection: SQLiteConnection) {
            super.onCreate(connection)
            scope.launch {
                INSTANCE?.let { database ->
                    val weightUnitDao = database.weightUnitDao()
                    weightUnitDao.addUnit(WeightUnitEntity(name = "g"))
                    weightUnitDao.addUnit(WeightUnitEntity(name = "kg"))
                    weightUnitDao.addUnit(WeightUnitEntity(name = "ml"))
                    weightUnitDao.addUnit(WeightUnitEntity(name = "L"))
                }
            }
        }
    }
}