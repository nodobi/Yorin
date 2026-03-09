package com.hyeok.recipebook.presentation.ingredient.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hyeok.recipebook.R
import com.hyeok.recipebook.designsystem.components.ButtonColor
import com.hyeok.recipebook.designsystem.components.ButtonShape
import com.hyeok.recipebook.designsystem.components.ButtonSize
import com.hyeok.recipebook.designsystem.components.YorinAppbar
import com.hyeok.recipebook.designsystem.components.YorinModalBottomSheet
import com.hyeok.recipebook.designsystem.components.YorinText
import com.hyeok.recipebook.designsystem.components.YorinTextButton
import com.hyeok.recipebook.designsystem.components.YorinTextField
import com.hyeok.recipebook.designsystem.theme.YorinTheme
import com.hyeok.recipebook.presentation.ingredient.model.IngredientUiModel
import com.hyeok.recipebook.presentation.ingredient.state.IngredientEditUiState
import com.hyeok.recipebook.presentation.util.ext.toLocalDate
import kotlinx.datetime.LocalDate
import kotlinx.datetime.format
import kotlinx.datetime.format.char
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalMaterial3Api::class, ExperimentalTime::class)
@Composable
fun IngredientEditSheet(
    ingredientEditUiState: IngredientEditUiState,
    modifier: Modifier = Modifier,
    sheetState: SheetState = rememberModalBottomSheetState(),
    onDismiss: () -> Unit = {},
    onComplete: (IngredientUiModel) -> Unit = {}
) {
    val nameState = rememberTextFieldState(ingredientEditUiState.ingredient?.name ?: "")
    val purchaseState = rememberTextFieldState(ingredientEditUiState.ingredient?.formatPurchaseDate() ?: "")
    val expirationState = rememberTextFieldState(ingredientEditUiState.ingredient?.formatExpirationDate() ?: "")
    val weightState = rememberTextFieldState(ingredientEditUiState.ingredient?.weight?.toString() ?: "")
    val weightUnitState = rememberTextFieldState(ingredientEditUiState.ingredient?.weightUnit ?: "")
    val descriptionState = rememberTextFieldState(ingredientEditUiState.ingredient?.description ?: "")

    YorinModalBottomSheet(
        modifier = Modifier.fillMaxWidth(),
        sheetState = sheetState,
        onDismiss = onDismiss
    ) {
        IngredientEditLayout(
            modifier = modifier,
            title = if (ingredientEditUiState.ingredient == null)
                stringResource(R.string.ingredient_edit_add_title)
            else
                stringResource(R.string.ingredient_edit_edit_title),
            nameState = nameState,
            purchaseState = purchaseState,
            expirationState = expirationState,
            weightState = weightState,
            weightUnitState = weightUnitState,
            descriptionState = descriptionState,
            onChangePurchaseDate = { utcEpochMilliseconds ->
                purchaseState.edit {
                    val timestamp = utcEpochMilliseconds.toLocalDate().format(
                        LocalDate.Format {
                            year()
                            char('-')
                            monthNumber()
                            char('-')
                            day()
                        }
                    )
                    replace(0, purchaseState.text.length, timestamp)
                }
            },
            onChangeExpirationDate = { utcEpochMilliseconds ->
                expirationState.edit {
                    val timestamp = utcEpochMilliseconds.toLocalDate().format(
                        LocalDate.Format {
                            year()
                            char('-')
                            monthNumber()
                            char('-')
                            day()
                        }
                    )
                    replace(0, purchaseState.text.length, timestamp)
                }
            },
            onCompleteEdit = {
                onComplete(
                    IngredientUiModel(
                        name = nameState.text.toString(),
                        purchaseDate = LocalDate.parse(purchaseState.text.toString(), LocalDate.Format {
                            year()
                            char('-')
                            monthNumber()
                            char('-')
                            day()
                        }),
                        expirationDate = LocalDate.parse(
                            expirationState.text.toString(),
                            LocalDate.Format {
                                year()
                                char('-')
                                monthNumber()
                                char('-')
                                day()
                            }),
                        weight = weightState.text.toString().toInt(),
                        weightUnit = weightUnitState.text.toString(),
                        description = descriptionState.text.toString(),
                    )
                )
            },
            onDismiss = onDismiss
        )
    }
}

@OptIn(ExperimentalTime::class, ExperimentalMaterial3Api::class)
@Composable
fun IngredientEditLayout(
    title: String,
    nameState: TextFieldState,
    purchaseState: TextFieldState,
    expirationState: TextFieldState,
    weightState: TextFieldState,
    weightUnitState: TextFieldState,
    descriptionState: TextFieldState,
    modifier: Modifier = Modifier,
    onChangePurchaseDate: (Long) -> Unit = {},
    onChangeExpirationDate: (Long) -> Unit = {},
    onCompleteEdit: () -> Unit = {},
    onDismiss: () -> Unit = {},
) {
    Column(
        modifier = modifier
    ) {
        YorinAppbar(
            title = title,
            titleAlignment = Alignment.CenterStart,
            action = {
                Icon(
                    modifier = Modifier
                        .clickable {
                            onDismiss()
                        },
                    imageVector = ImageVector.vectorResource(R.drawable.ic_close),
                    contentDescription = null
                )
            }
        )
        Column(
            modifier = Modifier
                .padding(horizontal = 24.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            LabeledTextField(
                modifier = Modifier.fillMaxWidth(),
                state = nameState,
                label = stringResource(R.string.ingredient_edit_name_label),
                placeHolder = stringResource(R.string.ingredient_edit_name_hint)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                LabeledTextField(
                    modifier = Modifier.weight(1f),
                    state = weightState,
                    label = stringResource(R.string.ingredient_edit_amount_label),
                    placeHolder = stringResource(R.string.ingredient_edit_amount_hint)
                )

                LabeledTextField(
                    modifier = Modifier.weight(1f),
                    state = weightUnitState,
                    label = stringResource(R.string.ingredient_edit_amount_unit_label),
                    placeHolder = stringResource(R.string.ingredient_edit_amount_unit_hint)
                )
            }

            LabeledSpinner(
                modifier = Modifier.fillMaxWidth(),
                state = purchaseState,
                label = stringResource(R.string.ingredient_edit_purchase_label),
                placeHolder = stringResource(R.string.ingredient_edit_purchase_hint),
                onSelectDate = onChangePurchaseDate
            )

            LabeledSpinner(
                modifier = Modifier.fillMaxWidth(),
                state = expirationState,
                label = stringResource(R.string.ingredient_edit_expiration_label),
                placeHolder = stringResource(R.string.ingredient_edit_expiration_hint),
                onSelectDate = onChangeExpirationDate
            )

            LabeledTextField(
                modifier = Modifier.fillMaxWidth(),
                state = descriptionState,
                label = stringResource(R.string.ingredient_edit_description_label),
                placeHolder = stringResource(R.string.ingredient_edit_description_hint)
            )
        }
        HorizontalDivider(
            color = YorinTheme.colors.black5
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 16.dp)
        ) {
            YorinTextButton(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(R.string.btn_do_save),
                shape = ButtonShape.Round,
                size = ButtonSize.Large,
                onClick = onCompleteEdit
            )
        }
    }
}

@Composable
private fun LabeledTextField(
    label: String,
    state: TextFieldState,
    modifier: Modifier = Modifier,
    placeHolder: String = ""
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        YorinText(
            modifier = Modifier
                .fillMaxWidth(),
            text = label,
            textAlign = TextAlign.Start,
            style = YorinTheme.typography.body2
        )

        YorinTextField(
            modifier = Modifier
                .fillMaxWidth(),
            state = state,
            placeHolder = placeHolder
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalTime::class)
@Composable
private fun LabeledSpinner(
    label: String,
    state: TextFieldState,
    onSelectDate: (Long) -> Unit,
    modifier: Modifier = Modifier,
    placeHolder: String = "",
    selectableDates: SelectableDates = DatePickerDefaults.AllDates
) {
    var showDatePicker by remember { mutableStateOf(false) }
    var selectedDate by remember { mutableStateOf<Long?>(null) }
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = Clock.System.now().toEpochMilliseconds(),
        selectableDates = selectableDates
    )

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        YorinText(
            modifier = Modifier
                .fillMaxWidth(),
            text = label,
            textAlign = TextAlign.Start,
            style = YorinTheme.typography.body2
        )

        YorinTextField(
            modifier = Modifier
                .fillMaxWidth()
                .pointerInput(selectedDate) {
                    awaitEachGesture {
                        awaitFirstDown(pass = PointerEventPass.Initial)
                        val upEvent = waitForUpOrCancellation(pass = PointerEventPass.Initial)
                        if (upEvent != null) {
                            showDatePicker = true
                        }
                    }
                },
            state = state,
            enabled = false,
            readOnly = true,
            placeHolder = placeHolder,
            leadingIcon = {
                Icon(
                    modifier = Modifier.size(16.dp),
                    imageVector = ImageVector.vectorResource(R.drawable.ic_calendar),
                    contentDescription = null
                )
            },
            trailingIcon = {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_arrow_down_mini),
                    contentDescription = null
                )
            }
        )
    }

    if (showDatePicker) {
        IngredientDatePicker(
            state = datePickerState,
            onDismiss = {
                showDatePicker = false
            },
            onConfirm = {
                onSelectDate(datePickerState.selectedDateMillis ?: 0L)
                showDatePicker = false
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun IngredientDatePicker(
    state: DatePickerState,
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit = {},
    onConfirm: () -> Unit = {}
) {
    DatePickerDialog(
        modifier = modifier,
        onDismissRequest = onDismiss,
        confirmButton = {},
        dismissButton = {},
        colors = DatePickerDefaults.colors(
            containerColor = YorinTheme.colors.black7
        )
    ) {
        Column {
            DatePicker(
                state = state,
                title = null,
                headline = null,
                showModeToggle = false,
                colors = DatePickerDefaults.colors(
                    containerColor = YorinTheme.colors.black7,
                    selectedDayContainerColor = YorinTheme.colors.main2,
                    selectedDayContentColor = YorinTheme.colors.black7,
                    todayDateBorderColor = YorinTheme.colors.main2,
                    weekdayContentColor = YorinTheme.colors.main1
                )
            )
            Row(
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp, bottom = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                YorinTextButton(
                    modifier = Modifier.weight(1f),
                    text = stringResource(R.string.btn_cancel),
                    onClick = onDismiss,
                    shape = ButtonShape.Round,
                    size = ButtonSize.Large,
                    color = ButtonColor.Secondary
                )

                YorinTextButton(
                    modifier = Modifier.weight(1f),
                    text = stringResource(R.string.btn_confirm),
                    onClick = onConfirm,
                    shape = ButtonShape.Round,
                    size = ButtonSize.Large,
                    color = ButtonColor.Primary
                )
            }
        }
    }
}

@Preview
@Composable
private fun IngredientEditSheetPreview() {
    YorinTheme {
        IngredientEditLayout(
            modifier = Modifier.background(YorinTheme.colors.black7),
            title = "재료 수정",
            nameState = rememberTextFieldState(),
            purchaseState = rememberTextFieldState(),
            expirationState = rememberTextFieldState(),
            weightState = rememberTextFieldState(),
            weightUnitState = rememberTextFieldState(),
            descriptionState = rememberTextFieldState()
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun IngredientDatePickerPreview() {
    YorinTheme {
        val datePickerState = rememberDatePickerState()

        IngredientDatePicker(
            state = datePickerState,
        )
    }
}