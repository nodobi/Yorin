package com.hyeok.recipebook.designsystem.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hyeok.recipebook.R
import com.hyeok.recipebook.designsystem.theme.YorinTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun YorinDatePicker(
    state: DatePickerState,
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit = {},
    onConfirm: (Long?) -> Unit = {}
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
                    onClick = {
                        onConfirm(state.selectedDateMillis)
                    },
                    shape = ButtonShape.Round,
                    size = ButtonSize.Large,
                    color = ButtonColor.Primary
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun IngredientDatePickerPreview() {
    YorinTheme {
        val datePickerState = rememberDatePickerState()

        YorinDatePicker(
            state = datePickerState,
        )
    }
}