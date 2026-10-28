package com.hyeok.recipebook.data.validation

sealed interface FormType {

    /* 재료 */
    data object IngredientName : FormType


    /* 레시피 */
    data object RecipeName : FormType
    data object RecipeCookingTime : FormType
    data object RecipeStepDescription : FormType
    data object RecipeRecordTitle : FormType
    data object RecipeRecordDescription : FormType

    /* 단위 */
    data object WeightQuantity : FormType
}