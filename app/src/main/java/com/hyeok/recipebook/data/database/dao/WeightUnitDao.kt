package com.hyeok.recipebook.data.database.dao

import androidx.room3.Dao
import androidx.room3.Query
import com.hyeok.recipebook.data.database.entity.WeightUnitEntity

@Dao
interface WeightUnitDao {

    @Query("SELECT name from weight_unit WHERE id = :id")
    suspend fun getNameById(id: Long): String

    @Query("SELECT id from weight_unit WHERE name = :name")
    suspend fun getIdByName(name: String): Long

    @Query("SELECT * from weight_unit")
    suspend fun getAllUnits(): List<WeightUnitEntity>
}