package com.hyeok.recipebook.data.validation

sealed interface FormError {
    sealed interface Ingredient : FormError {
        // 이름의 글자수가 범위를 벗어난 경우
        data object NameLengthOutOfRange : Ingredient

        // 이름에 특수문자가 포함된 경우
        data object NameContainsSpecialChar : Ingredient

    }

    sealed interface Recipe : FormError {
        // 레시피 이름의 글자수가 범위를 벗어난 경우
        data object NameLengthOutOfRange : Recipe

        // 레시피 이름에 허용하지 않은 특수문자가 포함된 경우
        data object NameContainsDisallowedChar : Recipe

        // 레시피 조리 시간이 숫자가 아닌 경우
        data object CookingTimeNotNumeric : Recipe

        // 레시피 조리 시간의 범위를 벗어난 경우
        data object CookingTimeOutOfRange : Recipe

        // 레시피 조리 순서의 내용 글자수가 범위를 벗어난 경우
        data object StepLengthOutOfRange : Recipe

        // 레시피 기록의 한줄 평 글자수가 범위를 벗어난 경우
        data object RecordTitleOutOfRange : Recipe

        // 레시피 기록의 코멘트 글자수가 범위를 벗어난 경우
        data object RecordDescriptionOutOfRange : Recipe

    }

    sealed interface Weight : FormError {
        // 중량이 숫자가 아닌 경우
        data object QuantityNotNumeric : Weight

        // 중량의 범위를 벗어난 경우
        data object QuantityOutOfRange : Weight
    }
}