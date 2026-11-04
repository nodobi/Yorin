package com.hyeok.recipebook.presentation.recipe.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement.spacedBy
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hyeok.recipebook.R
import com.hyeok.recipebook.designsystem.components.ButtonColor
import com.hyeok.recipebook.designsystem.components.ButtonShape
import com.hyeok.recipebook.designsystem.components.ButtonSize
import com.hyeok.recipebook.designsystem.components.YorinText
import com.hyeok.recipebook.designsystem.components.YorinTextButton
import com.hyeok.recipebook.designsystem.theme.YorinTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeRemoveDialog(
    onDismiss: () -> Unit,
    onRemoveRecipe: () -> Unit,
    modifier: Modifier = Modifier
) {
    BasicAlertDialog(
        modifier = modifier,
        onDismissRequest = {}
    ) {
        RecipeRemoveDialogContent(
            onDismiss = onDismiss,
            onRemove = onRemoveRecipe
        )
    }
}

@Composable
private fun RecipeRemoveDialogContent(
    onDismiss: () -> Unit,
    onRemove: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .clip(YorinTheme.shapes.medium)
            .background(YorinTheme.colors.black7)
            .padding(horizontal = 20.dp, vertical = 24.dp),
        verticalArrangement = spacedBy(20.dp)
    ) {

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = spacedBy(12.dp)
        ) {
            YorinText(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(R.string.recipe_remove_title),
                style = YorinTheme.typography.title1.copy(textAlign = TextAlign.Center)
            )
            YorinText(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(R.string.recipe_remove_description),
                color = YorinTheme.colors.black2,
                style = YorinTheme.typography.body5.copy(textAlign = TextAlign.Center)
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = spacedBy(8.dp)
        ) {
            YorinTextButton(
                modifier = Modifier.weight(1f),
                text = stringResource(R.string.btn_cancel),
                shape = ButtonShape.Round,
                size = ButtonSize.Large,
                color = ButtonColor.Secondary,
                onClick = onDismiss
            )

            YorinTextButton(
                modifier = Modifier.weight(1f),
                text = stringResource(R.string.btn_delete),
                shape = ButtonShape.Round,
                size = ButtonSize.Large,
                color = ButtonColor.Warning,
                onClick = onRemove
            )
        }
    }
}

@Preview
@Composable
private fun RecipeRemoveDialogPreview() {
    YorinTheme {
        RecipeRemoveDialogContent(
            modifier = Modifier,
            onDismiss = { },
            onRemove = { },
        )
    }
}