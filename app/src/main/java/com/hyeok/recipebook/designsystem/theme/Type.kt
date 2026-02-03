package com.hyeok.recipebook.designsystem.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.sp
import com.hyeok.recipebook.R

@Immutable
data class AppTypography(
    val title1: TextStyle = TextStyle.Default,
    val title2: TextStyle = TextStyle.Default,
    val body1: TextStyle = TextStyle.Default,
    val body2: TextStyle = TextStyle.Default,
    val body3: TextStyle = TextStyle.Default,
    val body4: TextStyle = TextStyle.Default,
    val body5: TextStyle = TextStyle.Default,
    val button1: TextStyle = TextStyle.Default,
    val button2: TextStyle = TextStyle.Default,
    val caption1: TextStyle = TextStyle.Default,
    val caption2: TextStyle = TextStyle.Default
) {
    companion object {
        @Stable
        val Default = AppTypography(
            title1 = Title1,
            title2 = Title2,
            body1 = Body1,
            body2 = Body2,
            body3 = Body3,
            body4 = Body4,
            body5 = Body5,
            button1 = Button1,
            button2 = Button2,
            caption1 = Caption1,
            caption2 = Caption2
        )
    }
}

internal val Pretendard =
    FontFamily(
        Font(R.font.pretendard_bold, FontWeight.Bold, FontStyle.Normal),
        Font(R.font.pretendard_bold, FontWeight.W600, FontStyle.Normal),
        Font(R.font.pretendard_medium, FontWeight.Medium, FontStyle.Normal),
        Font(R.font.pretendard_regular, FontWeight.Normal, FontStyle.Normal)
    )

internal val DefaultTextStyle: TextStyle =
    TextStyle(
        fontStyle = FontStyle.Normal,
        fontFamily = Pretendard,
        platformStyle =
            PlatformTextStyle(
                includeFontPadding = false
            ),
        lineHeightStyle =
            LineHeightStyle(
                alignment = LineHeightStyle.Alignment.Center,
                trim = LineHeightStyle.Trim.None
            ),
        letterSpacing = 0.sp
    )

internal val Title1 =
    DefaultTextStyle.copy(
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        lineHeight = 26.sp
    )

internal val Title2 =
    DefaultTextStyle.copy(
        fontSize = 20.sp,
        fontWeight = FontWeight.Medium,
        lineHeight = 26.sp
    )

internal val Body1 =
    DefaultTextStyle.copy(
        fontSize = 17.sp,
        fontWeight = FontWeight.Normal,
        lineHeight = 22.sp
    )

internal val Body2 =
    DefaultTextStyle.copy(
        fontSize = 16.sp,
        fontWeight = FontWeight.Medium,
        lineHeight = 21.sp
    )

internal val Body3 =
    DefaultTextStyle.copy(
        fontSize = 16.sp,
        fontWeight = FontWeight.Normal,
        lineHeight = 21.sp
    )

internal val Body4 =
    DefaultTextStyle.copy(
        fontSize = 14.sp,
        fontWeight = FontWeight.Medium,
        lineHeight = 18.sp
    )

internal val Body5 =
    DefaultTextStyle.copy(
        fontSize = 14.sp,
        fontWeight = FontWeight.Normal,
        lineHeight = 18.sp
    )

internal val Button1 =
    DefaultTextStyle.copy(
        fontSize = 14.sp,
        fontWeight = FontWeight.Medium,
        lineHeight = 18.sp
    )

internal val Button2 =
    DefaultTextStyle.copy(
        fontSize = 14.sp,
        fontWeight = FontWeight.Normal,
        lineHeight = 18.sp
    )

internal val Caption1 =
    DefaultTextStyle.copy(
        fontSize = 12.sp,
        fontWeight = FontWeight.Normal,
        lineHeight = 16.sp
    )

internal val Caption2 =
    DefaultTextStyle.copy(
        fontSize = 10.sp,
        fontWeight = FontWeight.Normal,
        lineHeight = 13.sp
    )

val Typography = Typography(
    displayLarge = Title1,
    displayMedium = Title1,
    displaySmall = Title1,
    headlineLarge = Title1,
    headlineMedium = Title1,
    headlineSmall = Title1,
    titleLarge = Title2,
    titleMedium = Body1,
    titleSmall = Body2,
    bodyLarge = Body3,
    bodyMedium = Body4,
    bodySmall = Caption1,
    labelLarge = Body4,
    labelMedium = Caption1,
    labelSmall = Caption2
)

//data class AppTypography(
//    val title1: TextStyle = Title1,
//    val title2: TextStyle = Title2,
//    val body1: TextStyle = Body1,
//    val body2: TextStyle = Body2,
//    val body3: TextStyle = Body3,
//    val body4: TextStyle = Body4,
//    val body5: TextStyle = Body5,
//    val button1: TextStyle = Button1,
//    val button2: TextStyle = Button2,
//    val caption1: TextStyle = Caption1,
//    val caption2: TextStyle = Caption2
//)