package com.janey.bookstuff.ui.theme

import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.janey.bookstuff.R

val frauncesFontFamily = FontFamily(
    Font(R.font.fraunces_extralight, FontWeight.ExtraLight),
    Font(R.font.fraunces_thin, FontWeight.Thin),
    Font(R.font.fraunces_light, FontWeight.Light),
    Font(R.font.fraunces, FontWeight.Normal),
    Font(R.font.fraunces_medium, FontWeight.Medium),
    Font(R.font.fraunces_bold, FontWeight.Bold),
    Font(R.font.fraunces_semibold, FontWeight.SemiBold),
    Font(R.font.fraunces_extrabold, FontWeight.ExtraBold),
    Font(R.font.fraunces_black, FontWeight.Black),
)
@OptIn(ExperimentalMaterial3ExpressiveApi::class)
private val defaultTypography = Typography()

// Set of Material typography styles to start with
@OptIn(ExperimentalMaterial3ExpressiveApi::class)
val typography = Typography(
    displayLarge = defaultTypography.displayLarge.copy(
        fontFamily = frauncesFontFamily
    ),
    displayMedium = defaultTypography.displayMedium.copy(
        fontFamily = frauncesFontFamily
    ),
    displaySmall = defaultTypography.displaySmall.copy(
        fontFamily = frauncesFontFamily
    ),
    headlineLarge = defaultTypography.headlineLarge.copy(
        fontFamily = frauncesFontFamily
    ),
    headlineMedium = defaultTypography.headlineMedium.copy(
        fontFamily = frauncesFontFamily
    ),
    headlineSmall = defaultTypography.headlineSmall.copy(
        fontFamily = frauncesFontFamily
    ),
    titleLarge = defaultTypography.titleLarge.copy(
        fontFamily = frauncesFontFamily
    ),
    titleMedium = defaultTypography.titleMedium.copy(
        fontFamily = frauncesFontFamily
    ),
    titleSmall = defaultTypography.titleSmall.copy(
        fontFamily = frauncesFontFamily
    ),
    bodyLarge = defaultTypography.bodyLarge.copy(
        fontFamily = frauncesFontFamily
    ),
    bodyMedium = defaultTypography.bodyMedium.copy(
        fontFamily = frauncesFontFamily
    ),
    bodySmall = defaultTypography.bodySmall.copy(
        fontFamily = frauncesFontFamily
    ),
    displayLargeEmphasized = defaultTypography.displayLargeEmphasized.copy(
        fontFamily = frauncesFontFamily
    ),
    displayMediumEmphasized = defaultTypography.displayMediumEmphasized.copy(
        fontFamily = frauncesFontFamily
    ),
    displaySmallEmphasized = defaultTypography.displaySmallEmphasized.copy(
        fontFamily = frauncesFontFamily
    ),
    headlineLargeEmphasized = defaultTypography.headlineLargeEmphasized.copy(
        fontFamily = frauncesFontFamily
    ),
    headlineMediumEmphasized = defaultTypography.headlineMediumEmphasized.copy(
        fontFamily = frauncesFontFamily
    ),
    headlineSmallEmphasized = defaultTypography.headlineSmallEmphasized.copy(
        fontFamily = frauncesFontFamily
    ),
    titleLargeEmphasized = defaultTypography.titleLargeEmphasized.copy(
        fontFamily = frauncesFontFamily
    ),
    titleMediumEmphasized = defaultTypography.titleMediumEmphasized.copy(
        fontFamily = frauncesFontFamily
    ),
    titleSmallEmphasized = defaultTypography.titleSmallEmphasized.copy(
        fontFamily = frauncesFontFamily
    ),
    bodyLargeEmphasized = defaultTypography.bodyLargeEmphasized.copy(
        fontFamily = frauncesFontFamily
    ),
    bodyMediumEmphasized = defaultTypography.bodyMediumEmphasized.copy(
        fontFamily = frauncesFontFamily
    ),
    bodySmallEmphasized = defaultTypography.bodySmallEmphasized.copy(
        fontFamily = frauncesFontFamily
    )
)
