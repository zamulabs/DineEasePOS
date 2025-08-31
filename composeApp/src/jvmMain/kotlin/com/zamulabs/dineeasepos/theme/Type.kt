package com.zamulabs.dineeasepos.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import dineeasepos.composeapp.generated.resources.Res
import dineeasepos.composeapp.generated.resources.be_vietnampro_black
import dineeasepos.composeapp.generated.resources.be_vietnampro_bold
import dineeasepos.composeapp.generated.resources.be_vietnampro_extrabold
import dineeasepos.composeapp.generated.resources.be_vietnampro_extralight
import dineeasepos.composeapp.generated.resources.be_vietnampro_light
import dineeasepos.composeapp.generated.resources.be_vietnampro_medium
import dineeasepos.composeapp.generated.resources.be_vietnampro_regular
import dineeasepos.composeapp.generated.resources.be_vietnampro_semibold
import dineeasepos.composeapp.generated.resources.be_vietnampro_thin
import org.jetbrains.compose.resources.Font

@Composable
fun beVietnamPro(): FontFamily {
    return FontFamily(
        Font(
            resource = Res.font.be_vietnampro_light,
            weight = FontWeight.Light
        ),
        Font(
            resource = Res.font.be_vietnampro_regular,
            weight = FontWeight.Normal
        ),
        Font(
            resource = Res.font.be_vietnampro_medium,
            weight = FontWeight.Medium
        ),
        Font(
            resource = Res.font.be_vietnampro_semibold,
            weight = FontWeight.SemiBold
        ),
        Font(
            resource = Res.font.be_vietnampro_bold,
            weight = FontWeight.Bold
        ),
    )
}



@Composable
internal fun getTypography(): Typography {
    val fontFamily = beVietnamPro()

    return Typography(
        displayLarge = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.Light,
            fontSize = 57.sp, // M3 spec
            lineHeight = 64.sp,
            letterSpacing = (-0.25).sp,
        ),
        displayMedium = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 45.sp,
            lineHeight = 52.sp,
        ),
        displaySmall = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 36.sp,
            lineHeight = 44.sp,
        ),
        headlineLarge = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 32.sp,
            lineHeight = 40.sp,
        ),
        headlineMedium = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 28.sp,
            lineHeight = 36.sp,
        ),
        headlineSmall = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 24.sp,
            lineHeight = 32.sp,
        ),
        titleLarge = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 22.sp,
            lineHeight = 28.sp,
        ),
        titleMedium = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            lineHeight = 24.sp,
            letterSpacing = 0.15.sp,
        ),
        titleSmall = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            lineHeight = 20.sp,
            letterSpacing = 0.1.sp,
        ),
        bodyLarge = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            lineHeight = 24.sp,
            letterSpacing = 0.5.sp,
        ),
        bodyMedium = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            lineHeight = 20.sp,
            letterSpacing = 0.25.sp,
        ),
        bodySmall = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            lineHeight = 16.sp,
            letterSpacing = 0.4.sp,
        ),
        labelLarge = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            lineHeight = 20.sp,
            letterSpacing = 0.1.sp,
        ),
        labelMedium = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 12.sp,
            lineHeight = 16.sp,
            letterSpacing = 0.5.sp,
        ),
        labelSmall = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 11.sp,
            lineHeight = 16.sp,
            letterSpacing = 0.5.sp,
        ),
    )
}

