package com.hyeok.recipebook.presentation.home.model

import androidx.compose.runtime.Stable
import com.hyeok.recipebook.data.database.model.RecommendationReason
import com.hyeok.recipebook.presentation.recipe.detail.ingredients.RecipeIngredientUiModel

/**
 * 레시피 추천에 사용되는 UiModel
 *
 * @property id 레시피 id
 * @property name 레시피 이름
 * @property cookingTime 레시피 조리시간
 * @property averageScore 레시피 평균 점수
 * @property photoUrl 레시피 사진
 * @property ingredients 레시피 재료
 */
@Stable
data class RecipeRecommendationUiModel(
    val id: Long,
    val name: String,
    val cookingTime: Int,
    val averageScore: Float,
    val photoUrl: String?,
    val ingredients: List<RecipeIngredientUiModel>,
    val reason: Set<RecommendationReason>
)