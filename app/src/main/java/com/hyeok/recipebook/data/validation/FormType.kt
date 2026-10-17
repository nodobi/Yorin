package com.hyeok.recipebook.data.validation

sealed interface FormType {
    // 재료의 이름
    object IngredientName : FormType

    // 중량
    object WeightQuantity : FormType
}