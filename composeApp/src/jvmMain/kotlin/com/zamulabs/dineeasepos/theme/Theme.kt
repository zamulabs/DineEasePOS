package com.zamulabs.dineeasepos.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val DarkColors = darkColorScheme(
    primary = PrimaryColor,
    onPrimary = PrimaryTextColor,
    secondary = SecondaryColor,
    onSecondary = SecondaryTextColor,
    tertiary = PrimaryLightColor,
    onTertiary = PrimaryTextColor,
    background = BackgroundDarkColor,
    onBackground = PrimaryTextColor,
    surface = SurfaceDark,
    onSurface = PrimaryTextColor,
    surfaceVariant = SurfaceDark,
    onSurfaceVariant = SecondaryTextColor,
    secondaryContainer = SecondaryLightColor,
    onSecondaryContainer = PrimaryTextColor,
    error = ErrorColor,
    onError = OnErrorColor,
)

@Composable
internal fun DineEaseTheme(
    content: @Composable () -> Unit,
) {
    val colors = DarkColors // since your app has no light theme

    MaterialTheme(
        colorScheme = colors,
        typography = getTypography(),
        shapes = Shapes,
        content = content,
    )
}
