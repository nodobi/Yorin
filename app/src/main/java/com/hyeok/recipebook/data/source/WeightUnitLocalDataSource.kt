package com.hyeok.recipebook.data.source

import com.hyeok.recipebook.data.database.dao.WeightUnitDao
import com.hyeok.recipebook.data.database.entity.WeightUnitEntity
import javax.inject.Inject

class WeightUnitLocalDataSource @Inject constructor(
    private val weightUnitDao: WeightUnitDao
) {
    suspend fun getUnitById(id: Long): String = weightUnitDao.getNameById(id)

    suspend fun getUnitByName(name: String): Long = weightUnitDao.getIdByName(name)

    suspend fun getAllUnits(): List<WeightUnitEntity> = weightUnitDao.getAllUnits()
}