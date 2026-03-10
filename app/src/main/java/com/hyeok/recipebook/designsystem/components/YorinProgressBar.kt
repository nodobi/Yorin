package com.hyeok.recipebook.designsystem.components

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.hyeok.recipebook.designsystem.theme.YorinTheme

@Composable
fun YorinProgressBar(
    progress: Float,
    modifier: Modifier = Modifier,
    containerColor: Color = YorinTheme.colors.black6,
    progressBarColor: Color = YorinTheme.colors.main2,
    animationSpec: AnimationSpec<Float> = tween(durationMillis = 100)
) {
    val aniProgress by animateFloatAsState(
        targetValue = progress, animationSpec = animationSpec
    )

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(8.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(containerColor)
    ) {
        Spacer(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(aniProgress)
                .background(progressBarColor)
        )
    }
}

