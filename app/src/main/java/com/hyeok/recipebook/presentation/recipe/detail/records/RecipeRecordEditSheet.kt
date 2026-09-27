package com.hyeok.recipebook.presentation.recipe.detail.records

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.hyeok.recipebook.R
import com.hyeok.recipebook.designsystem.components.ButtonColor
import com.hyeok.recipebook.designsystem.components.ButtonShape
import com.hyeok.recipebook.designsystem.components.ButtonSize
import com.hyeok.recipebook.designsystem.components.YorinDatePicker
import com.hyeok.recipebook.designsystem.components.YorinModalBottomSheet
import com.hyeok.recipebook.designsystem.components.YorinText
import com.hyeok.recipebook.designsystem.components.YorinTextButton
import com.hyeok.recipebook.designsystem.components.YorinTextField
import com.hyeok.recipebook.designsystem.theme.BackgroundPreview
import com.hyeok.recipebook.designsystem.theme.YorinTheme
import com.hyeok.recipebook.presentation.util.DateTimeUtil
import com.hyeok.recipebook.presentation.util.ext.toLocalDate
import kotlinx.datetime.LocalDate
import kotlinx.datetime.format
import kotlin.time.Clock
import kotlin.time.ExperimentalTime


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeRecordEditSheet(
    modifier: Modifier = Modifier,
    sheetState: SheetState = rememberModalBottomSheetState(),
    record: RecipeRecordUiModel? = null,
    onDismiss: () -> Unit = {},
    onConfirm: (RecipeRecordUiModel) -> Unit = {}
) {
    YorinModalBottomSheet(
        modifier = modifier,
        sheetState = sheetState,
        onDismiss = onDismiss
    ) {
        RecipeRecordEditSheetContent(
            record = record,
            onCancel = onDismiss,
            onConfirm = onConfirm
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RecipeRecordEditSheetContent(
    modifier: Modifier = Modifier,
    record: RecipeRecordUiModel? = null,
    onCancel: () -> Unit = {},
    onConfirm: (RecipeRecordUiModel) -> Unit = {},
) {
    var date by remember(record?.id) { mutableStateOf(record?.cookedAt) }
    val title = remember(record?.id) { TextFieldState(record?.title ?: "") }
    val description = remember(record?.id) { TextFieldState(record?.description ?: "") }
    var score by remember(record?.id) { mutableIntStateOf(record?.score ?: 0) }

    Column(
        modifier = modifier,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                YorinText(
                    text = stringResource(R.string.recipe_detail_record_date_title),
                    style = YorinTheme.typography.body2,
                )
                DatePickerField(
                    initialDate = date,
                    onSelectDate = {
                        date = it
                    },
                    placeHolder = stringResource(R.string.recipe_detail_record_date_placeholder)
                )
            }
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                YorinText(
                    text = stringResource(R.string.recipe_detail_record_title_title)
                )
                YorinTextField(
                    state = title,
                    lineLimits = TextFieldLineLimits.MultiLine(minHeightInLines = 1, maxHeightInLines = 1)
                )
            }
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                YorinText(
                    text = stringResource(R.string.recipe_detail_record_description_title)
                )
                YorinTextField(
                    state = description,
                    lineLimits = TextFieldLineLimits.MultiLine(minHeightInLines = 2, maxHeightInLines = 4)
                )
            }
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                YorinText(
                    text = stringResource(R.string.recipe_detail_record_score_title)
                )
                RatingPicker(
                    modifier = Modifier.fillMaxWidth(),
                    initialScore = score,
                    onChangeRating = {
                        score = it
                    }
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            YorinTextButton(
                modifier = Modifier.weight(1f),
                text = stringResource(R.string.btn_cancel),
                onClick = onCancel,
                size = ButtonSize.Large,
                shape = ButtonShape.Round,
                color = ButtonColor.Secondary
            )
            YorinTextButton(
                modifier = Modifier.weight(1f),
                text = stringResource(R.string.btn_save),
                onClick = {
                    onConfirm(
                        record?.copy(
                            cookedAt = date!!,
                            title = title.text.toString(),
                            description = description.text.toString(),
                            score = score
                        ) ?: RecipeRecordUiModel(
                            id = 0,
                            cookedAt = date!!,
                            title = title.text.toString(),
                            description = description.text.toString(),
                            score = score
                        )
                    )
                },
                size = ButtonSize.Large,
                shape = ButtonShape.Round,
                enabled = date != null
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalTime::class)
@Composable
private fun DatePickerField(
    onSelectDate: (LocalDate) -> Unit,
    modifier: Modifier = Modifier,
    initialDate: LocalDate? = null,
    placeHolder: String = "",
    selectableDates: SelectableDates = DatePickerDefaults.AllDates
) {
    val dateTextState = rememberTextFieldState(
        initialDate?.format(DateTimeUtil.Patterns.YYYYMMDD) ?: ""
    )

    var showDatePicker by remember { mutableStateOf(false) }
    var selectedDate by remember { mutableStateOf<Long?>(null) }
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = Clock.System.now().toEpochMilliseconds(),
        selectableDates = selectableDates
    )

    YorinTextField(
        modifier = modifier
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
        state = dateTextState,
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

    if (showDatePicker) {
        YorinDatePicker(
            state = datePickerState,
            onDismiss = {
                showDatePicker = false
            },
            onConfirm = { selectedMillis ->
                dateTextState.edit {
                    replace(0, dateTextState.text.length, selectedMillis.toLocalDate().format(DateTimeUtil.Patterns.YYYYMMDD))
                }
                onSelectDate(selectedMillis.toLocalDate())

                showDatePicker = false
            }
        )
    }
}


// REVIEW:: StateHolder 만들어서 사용하기?
@Composable
fun RatingPicker(
    modifier: Modifier = Modifier,
    initialScore: Int = 0,
    onChangeRating: (Int) -> Unit = {},
) {
    var rating by remember { mutableStateOf(initialScore) }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 40.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        repeat(5) { idx ->
            Icon(
                modifier = Modifier
                    .size(40.dp)
                    .clickable {
                        rating = idx + 1
                        onChangeRating(rating)
                    },
                imageVector = ImageVector.vectorResource(R.drawable.ic_star_filled),
                contentDescription = null,
                tint = if (idx < rating) YorinTheme.colors.main2 else YorinTheme.colors.main6,
            )
        }
    }
}

@BackgroundPreview
@Composable
private fun RecipeRecordEditSheetContentPreview() {
    YorinTheme {
        RecipeRecordEditSheetContent()
    }
}