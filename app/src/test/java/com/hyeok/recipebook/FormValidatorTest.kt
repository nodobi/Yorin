package com.hyeok.recipebook

import com.hyeok.recipebook.data.validation.FormError
import com.hyeok.recipebook.data.validation.FormType
import com.hyeok.recipebook.data.validation.FormValidator
import org.junit.Assert.assertEquals
import org.junit.Test
import kotlin.sequences.sequence

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
            text = "가".repeat(21),
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
    fun `0으로 시작하는 수를 무게 양에 입력하면 에러가 발생하지 않는다`() {
        val error = FormValidator.validate(
            text = "090",
            type = FormType.WeightQuantity
        )

        assertEquals(
            null,
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

    @Test
    fun `레시피 이름이 30자를 초과하면 에러가 발생한다`() {
        val error = FormValidator.validate(
            text = "가".repeat(31),
            type = FormType.RecipeName
        )

        assertEquals(
            FormError.Recipe.NameLengthOutOfRange,
            error
        )
    }

    @Test
    fun `레시피 이름이 공백이면 에러가 발생한다`() {
        val error = FormValidator.validate(
            text = "",
            type = FormType.RecipeName
        )

        assertEquals(
            FormError.Recipe.NameLengthOutOfRange,
            error
        )
    }

    @Test
    fun `레시피 이름에 허용된 특수문자를 입력하면 에러가 발생하지 않는다`() {
        val error = FormValidator.validate(
            text = "!@#$%^&*()-=_+,<.>;:/?'\"",
            type = FormType.RecipeName
        )

        assertEquals(
            null,
            error
        )
    }

    @Test
    fun `레시피 이름에 이모지를 입력하면 에러가 발생한다`() {
        val error = FormValidator.validate(
            text = "emoji 👍",
            type = FormType.RecipeName
        )

        assertEquals(
            FormError.Recipe.NameContainsDisallowedChar,
            error
        )
    }

    @Test
    fun `숫자가 아닌 문자를 조리 시간에 입력하면 오류가 발생한다`() {
        val error = FormValidator.validate(
            text = "numeric123",
            type = FormType.RecipeCookingTime
        )

        assertEquals(
            FormError.Recipe.CookingTimeNotNumeric,
            error
        )
    }

    @Test
    fun `999 보다 큰 자연수를 조리 시간에 입력하면 에러가 발생한다`() {
        val error = FormValidator.validate(
            text = "1000",
            type = FormType.RecipeCookingTime
        )

        assertEquals(
            FormError.Recipe.CookingTimeOutOfRange,
            error
        )
    }

    @Test
    fun `Int 범위를 벗어나는 수를 조리 시간에 입력하면 에러가 발생한다`() {
        val error = FormValidator.validate(
            text = "${Int.MAX_VALUE + 1L}",
            type = FormType.RecipeCookingTime
        )

        assertEquals(
            FormError.Recipe.CookingTimeOutOfRange,
            error
        )
    }

    @Test
    fun `공백을 조리 시간에 입력하면 에러가 발생하지 않는다`() {
        val error = FormValidator.validate(
            text = "",
            type = FormType.RecipeCookingTime
        )

        assertEquals(
            null,
            error
        )
    }

    @Test
    fun `0 을 조리 시간에 입력하면 에러가 발생하지 않는다`() {
        val error = FormValidator.validate(
            text = "0",
            type = FormType.RecipeCookingTime
        )

        assertEquals(
            null,
            error
        )
    }

    @Test
    fun `0 으로 시작하는 수를 조리 시간에 입력하면 에러가 발생하지 않는다`() {
        val error = FormValidator.validate(
            text = "030",
            type = FormType.RecipeCookingTime
        )

        assertEquals(
            null,
            error
        )
    }

    @Test
    fun `조리 순서 내용이 300자를 초과하면 에러가 발생한다`() {
        val error = FormValidator.validate(
            text = "가".repeat(301),
            type = FormType.RecipeStepDescription
        )

        assertEquals(
            FormError.Recipe.StepLengthOutOfRange,
            error
        )
    }

    @Test
    fun `조리 순서 내용이 공백이면 에러가 발생한다`() {
        val error = FormValidator.validate(
            text = "",
            type = FormType.RecipeStepDescription
        )

        assertEquals(
            FormError.Recipe.StepLengthOutOfRange,
            error
        )
    }

    @Test
    fun `조리 기록 한줄 평이 30자를 초과하면 에러가 발생한다`() {
        val error = FormValidator.validate(
            text = "가".repeat(31),
            type = FormType.RecipeRecordTitle
        )

        assertEquals(
            FormError.Recipe.RecordTitleOutOfRange,
            error
        )
    }

    @Test
    fun `조리 기록 한줄 평에 공백을 입력하면 에러가 발생하지 않는다`() {
        val error = FormValidator.validate(
            text = "",
            type = FormType.RecipeRecordTitle
        )

        assertEquals(
            null,
            error
        )
    }

    @Test
    fun `조리 기록 코멘트가 1000자를 초과하면 에러가 발생한다`() {
        val error = FormValidator.validate(
            text = "가".repeat(1001),
            type = FormType.RecipeRecordDescription
        )

        assertEquals(
            FormError.Recipe.RecordDescriptionOutOfRange,
            error
        )
    }

    @Test
    fun `조리 기록 코멘트에 공백을 입력하면 에러가 발생하지 않는다`() {
        val error = FormValidator.validate(
            text = "",
            type = FormType.RecipeRecordDescription
        )

        assertEquals(
            null,
            error
        )
    }
}