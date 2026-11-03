package com.hyeok.recipebook.data.validation

sealed interface FormError {
    enum class Ingredient : FormError {
        // 이름의 글자수가 범위를 벗어난 경우
        NameLengthOutOfRange,

        // 이름에 특수문자가 포함된 경우
        NameContainsSpecialChar
    }

    enum class Recipe : FormError {
        // 레시피 이름의 글자수가 범위를 벗어난 경우
        NameLengthOutOfRange,

        // 레시피 이름에 허용하지 않은 특수문자가 포함된 경우
        NameContainsDisallowedChar,

        // 레시피 조리 시간이 숫자가 아닌 경우
        CookingTimeNotNumeric,

        // 레시피 조리 시간의 범위를 벗어난 경우
        CookingTimeOutOfRange,

        // 레시피 조리 순서의 내용 글자수가 범위를 벗어난 경우
        StepLengthOutOfRange,

        // 레시피 기록의 한줄 평 글자수가 범위를 벗어난 경우
        RecordTitleOutOfRange,

        // 레시피 기록의 코멘트 글자수가 범위를 벗어난 경우
        RecordDescriptionOutOfRange
    }

    enum class Weight : FormError {
        // 중량이 숫자가 아닌 경우
        QuantityNotNumeric,

        // 중량의 범위를 벗어난 경우
        QuantityOutOfRange
    }
}