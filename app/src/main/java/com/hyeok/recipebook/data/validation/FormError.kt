package com.hyeok.recipebook.data.validation

sealed interface FormError {
    sealed interface Ingredient : FormError {
        // 이름의 글자수가 범위를 벗어난 경우
        data object NameLengthOutOfRange : Ingredient

        // 이름에 특수문자가 포함된 경우
        data object NameContainsSpecialChar : Ingredient

    }

    sealed interface Weight : FormError {
        // 중량이 숫자가 아닌 경우
        data object QuantityNotNumeric : Weight

        // 중량의 범위를 벗어난 경우
        data object QuantityOutOfRange : Weight
    }
}