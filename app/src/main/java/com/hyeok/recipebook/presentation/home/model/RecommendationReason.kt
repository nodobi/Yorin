package com.hyeok.recipebook.presentation.home.model

import com.hyeok.recipebook.data.database.model.IngredientModel
import com.hyeok.recipebook.data.database.model.RecipeIngredientModel

/**
 * 레시피 추천 사유
 */
sealed interface RecommendationReason {
    // 바로 조리 가능한 상태
    data object ReadyToCook : RecommendationReason

    // 소비 기한이 임박한 재료 사용
    data class UseExpiringIngredients(val expiringIngredients: List<IngredientModel>) : RecommendationReason

    // 몇 가지 재료만 추가로 있으면 조리 가능
    data class MissingIngredients(val missingIngredients: List<RecipeIngredientModel>) : RecommendationReason
}