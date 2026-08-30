package com.hyeok.recipebook.presentation.recipe.detail.ingredients

/**
 * 레시피 화면의 재료 데이터를 갖는 UiModel
 *
 * @property id 재료의 id
 * @property name 재료의 이름
 * @property unit 단위
 * @property requireQuantity 필요한 재료의 양
 * @property stockQuantity 내가 가지고 있는 재료의 양
 * @property isInStock 요리에 사용할 재료가 있는지 여부
 */
data class RecipeIngredientUiModel(
    val id: Long,
    val name: String,
    val unit: String,
    val requireQuantity: Int,
    val stockQuantity: Int
) {
    val isInStock: Boolean = requireQuantity <= stockQuantity

    companion object Companion {
        val dummy1 = RecipeIngredientUiModel(
            id = 0,
            name = "재료 이름",
            unit = "g",
            requireQuantity = 100,
            stockQuantity = 200
        )
        val dummy2 = RecipeIngredientUiModel(
            id = 1,
            name = "재료 이름",
            unit = "g",
            requireQuantity = 100,
            stockQuantity = 80
        )
    }
}