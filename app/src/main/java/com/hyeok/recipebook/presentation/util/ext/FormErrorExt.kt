package com.hyeok.recipebook.presentation.util.ext

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.hyeok.recipebook.R
import com.hyeok.recipebook.data.validation.FormError


@Composable
fun FormError.toUiText(): String = when(this) {
    FormError.Ingredient.NameContainsSpecialChar -> stringResource(R.string.error_ingredient_name_contains_special_char)
    FormError.Ingredient.NameLengthOutOfRange -> stringResource(R.string.error_ingredient_name_out_of_range)
    FormError.Weight.QuantityNotNumeric -> stringResource(R.string.error_weight_quantity_not_numeric)
    FormError.Weight.QuantityOutOfRange -> stringResource(R.string.error_weight_quantity_out_of_range)
    FormError.Recipe.NameContainsDisallowedChar -> stringResource(R.string.error_recipe_name_disallowed_char)
    FormError.Recipe.NameLengthOutOfRange -> stringResource(R.string.error_recipe_name_length)
    FormError.Recipe.CookingTimeNotNumeric -> stringResource(R.string.error_cooking_time_not_numeric)
    FormError.Recipe.CookingTimeOutOfRange -> stringResource(R.string.error_cooking_time_out_of_range)
    FormError.Recipe.StepLengthOutOfRange -> stringResource(R.string.error_step_length)
    FormError.Recipe.RecordTitleOutOfRange -> stringResource(R.string.error_record_title_out_of_range)
    FormError.Recipe.RecordDescriptionOutOfRange -> stringResource(R.string.error_record_description_out_of_range)
}