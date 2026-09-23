package com.hyeok.recipebook.presentation.recipe.detail.step

// TODO:: 순서 변경은 나중에 구현
data class RecipeStepUiModel(
    val id: Long,
    val order: Int,
    val description: String
) {
    companion object {
        fun fake() = RecipeStepUiModel(
            id = 1,
            order = 1,
            description = "Recipe Step 1"
        )

        fun fakes(): List<RecipeStepUiModel> = listOf(
            RecipeStepUiModel(1, 1, "Recipe Step 1"),
            RecipeStepUiModel(2, 2, "Recipe Step 2")
        )
    }
}