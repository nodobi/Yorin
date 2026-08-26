package com.hyeok.recipebook.data.source

import com.hyeok.recipebook.data.database.AppDatabase
import javax.inject.Inject

class IngredientLocalDataSource @Inject constructor(
    private val database: AppDatabase
) {

}