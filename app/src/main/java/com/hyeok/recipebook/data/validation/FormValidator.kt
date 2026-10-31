package com.hyeok.recipebook.data.validation

object FormValidator {
    private val allowedIngredientNameRegex = Regex("[가-힣ㄱ-ㅎㅏ-ㅣa-zA-Z0-9 ]*")
    private val allowedNumberRegex = Regex("[0-9]*")
    private val allowedRecipeNameRegex = Regex("""[가-힣ㄱ-ㅎㅏ-ㅣa-zA-Z0-9 !"#$%&'()*+,./:;<=>?@\[\\\]^_`{|}~\-‘’“”]*""")

    const val WEIGHT_QUANTITY_MAX = 9999
    const val RECIPE_COOKING_TIME_MAX = 999
    const val RECIPE_NAME_MAX_LENGTH = 30
    const val RECIPE_STEP_MAX_LENGTH = 300
    const val RECIPE_RECORD_TITLE_MAX_LENGTH = 30
    const val RECIPE_RECORD_DESCRIPTION_MAX_LENGTH = 1000

    fun validate(text: String, type: FormType): FormError? = when (type) {
        FormType.IngredientName -> validateIngredientName(text)
        FormType.RecipeName -> validateRecipeName(text)
        FormType.RecipeCookingTime -> validateRecipeCookingTime(text)
        FormType.RecipeStepDescription -> validateRecipeStepDescription(text)
        FormType.RecipeRecordTitle -> validateRecipeRecordTitle(text)
        FormType.RecipeRecordDescription -> validateRecipeRecordDescription(text)
        FormType.WeightQuantity -> validateWeightQuantity(text)
    }

    private fun validateIngredientName(text: String): FormError.Ingredient? {
        val trimmedText = text.trim()

        return if (!allowedIngredientNameRegex.matches(trimmedText))
            FormError.Ingredient.NameContainsSpecialChar
        else if (trimmedText.length !in 1..20)
            FormError.Ingredient.NameLengthOutOfRange
        else
            null
    }

    private fun validateWeightQuantity(text: String): FormError.Weight? {
        val trimmedText = text.trimLeadingZero()

        return if (!allowedNumberRegex.matches(trimmedText) || (trimmedText.isEmpty() && text.isEmpty()))
            FormError.Weight.QuantityNotNumeric
        else if (trimmedText.toIntOrNull() !in 1..WEIGHT_QUANTITY_MAX)
            FormError.Weight.QuantityOutOfRange
        else
            null
    }

    private fun validateRecipeName(text: String): FormError.Recipe? {
        val trimmedText = text.trim()

        return if(!allowedRecipeNameRegex.matches(trimmedText)) {
            FormError.Recipe.NameContainsDisallowedChar
        }
        else if (trimmedText.length !in 1..RECIPE_NAME_MAX_LENGTH) {
            FormError.Recipe.NameLengthOutOfRange
        } else
            null
    }

    private fun validateRecipeCookingTime(text: String): FormError.Recipe? {
        val trimmedText = text.trimLeadingZero()

        return if (!allowedNumberRegex.matches(trimmedText))
            FormError.Recipe.CookingTimeNotNumeric
        else if (trimmedText.isNotEmpty() && trimmedText.toIntOrNull() !in 0..RECIPE_COOKING_TIME_MAX)
            FormError.Recipe.CookingTimeOutOfRange
        else
            null
    }

    private fun validateRecipeStepDescription(text: String): FormError.Recipe? {
        val trimmedText = text.trim()

        return if (trimmedText.length !in 1..RECIPE_STEP_MAX_LENGTH) {
            FormError.Recipe.StepLengthOutOfRange
        } else
            null
    }

    private fun validateRecipeRecordTitle(text: String): FormError.Recipe? {
        val trimmedText = text.trim()

        return if (trimmedText.length !in 0..RECIPE_RECORD_TITLE_MAX_LENGTH) {
            FormError.Recipe.RecordTitleOutOfRange
        } else
            null
    }

    private fun validateRecipeRecordDescription(text: String): FormError.Recipe? {
        val trimmedText = text.trim()

        return if (trimmedText.length !in 0..RECIPE_RECORD_DESCRIPTION_MAX_LENGTH) {
            FormError.Recipe.RecordDescriptionOutOfRange
        } else
            null
    }
}

private fun String.trimLeadingZero() = this.trimStart('0')