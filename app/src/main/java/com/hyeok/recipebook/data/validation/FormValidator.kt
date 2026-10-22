package com.hyeok.recipebook.data.validation

object FormValidator {
    private val allowedIngredientNameRegex = Regex("[가-힣ㄱ-ㅎㅏ-ㅣa-zA-Z0-9 ]+")
    private val allowedWeightQuantityRegex = Regex("[0-9]+")

    const val WEIGHT_QUANTITY_MAX = 10000

    fun validate(text: String, type: FormType): FormError? = when (type) {
        FormType.IngredientName -> validateIngredientName(text)
        FormType.WeightQuantity -> validateWeightQuantity(text)
    }

    private fun validateIngredientName(text: String): FormError.Ingredient? {
        val trimmedText = text.trim()

        return if (trimmedText.isNotEmpty() && !allowedIngredientNameRegex.matches(trimmedText))
            FormError.Ingredient.NameContainsSpecialChar
        else if (trimmedText.length !in 1..20)
            FormError.Ingredient.NameLengthOutOfRange
        else
            null
    }

    private fun validateWeightQuantity(text: String): FormError.Weight? {
        return if (text.isEmpty() || !allowedWeightQuantityRegex.matches(text))
            FormError.Weight.QuantityNotNumeric
        else if (text.toIntOrNull() !in 1 until WEIGHT_QUANTITY_MAX)
            FormError.Weight.QuantityOutOfRange
        else
            null
    }
}