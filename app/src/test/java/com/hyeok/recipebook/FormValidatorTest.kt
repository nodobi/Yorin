package com.hyeok.recipebook

import com.hyeok.recipebook.data.validation.FormError
import com.hyeok.recipebook.data.validation.FormType
import com.hyeok.recipebook.data.validation.FormValidator
import org.junit.Assert.assertEquals
import org.junit.Test

class FormValidatorTest {


    @Test
    fun `공백을 재료 이름에 입력하면 에러가 발생한다`() {
        val error = FormValidator.validate(
            text = "",
            type = FormType.IngredientName
        )

        assertEquals(
            FormError.Ingredient.NameLengthOutOfRange,
            error
        )
    }

    @Test
    fun `재료 이름이 20자를 초과하는 경우 에러가 발생한다`() {
        val error = FormValidator.validate(
            text = "일이삼사오육칠팔구십십이십사십육십팔이십일",
            type = FormType.IngredientName
        )

        assertEquals(
            FormError.Ingredient.NameLengthOutOfRange,
            error
        )
    }

    @Test
    fun `특수문자를 재료 이름에 입력하면 에러가 발생한다`() {
        val error = FormValidator.validate(
            text = "validation!",
            type = FormType.IngredientName
        )

        assertEquals(
            FormError.Ingredient.NameContainsSpecialChar,
            error
        )
    }

    @Test
    fun `숫자가 아닌 문자를 무게 양에 입력하면 에러가 발생한다`() {
        val error = FormValidator.validate(
            text = "백십일",
            type = FormType.WeightQuantity
        )

        assertEquals(
            FormError.Weight.QuantityNotNumeric,
            error
        )
    }

    @Test
    fun `공백을 무게 양에 입력하면 에러가 발생한다`() {
        val error = FormValidator.validate(
            text = "",
            type = FormType.WeightQuantity
        )

        assertEquals(
            FormError.Weight.QuantityNotNumeric,
            error
        )
    }

    @Test
    fun `0을 무게 양에 입력하면 에러가 발생한다`() {
        val error = FormValidator.validate(
            text = "0",
            type = FormType.WeightQuantity
        )

        assertEquals(
            FormError.Weight.QuantityOutOfRange,
            error
        )
    }

    @Test
    fun `10000 이상의 수를 무게 양에 입력하면 에러가 발생한다`() {
        val error = FormValidator.validate(
            text = "10000",
            type = FormType.WeightQuantity
        )

        assertEquals(
            FormError.Weight.QuantityOutOfRange,
            error
        )
    }

    @Test
    fun `Int 범위를 벗어나는 수를 무게 양에 입력하면 에러가 발생한다`() {
        val error = FormValidator.validate(
            text = "${Int.MAX_VALUE + 1L}",
            type = FormType.WeightQuantity
        )

        assertEquals(
            FormError.Weight.QuantityOutOfRange,
            error
        )
    }
}