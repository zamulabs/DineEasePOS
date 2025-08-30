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
fun montserrat(): FontFamily {
    val montserratRegular =
        Font(
            resource = Res.font.be_vietnampro_regular,
            weight = FontWeight.Normal,
            style = FontStyle.Normal,
        )

    val montserratBold =
        Font(
            resource = Res.font.be_vietnampro_bold,
            FontWeight.Bold,
            FontStyle.Normal,
        )

    val montserratLight =
        Font(
            resource = Res.font.be_vietnampro_light,
            FontWeight.Light,
            FontStyle.Normal,
        )

    val montserratMedium =
        Font(
            resource = Res.font.be_vietnampro_medium,
            FontWeight.Medium,
            FontStyle.Normal,
        )

    val montserratSemiBold =
        Font(
            resource = Res.font.be_vietnampro_semibold,
            FontWeight.SemiBold,
            FontStyle.Normal,
        )

    val montserratThin =
        Font(
            resource = Res.font.be_vietnampro_thin,
            FontWeight.Thin,
            FontStyle.Normal,
        )

    val montserratExtraBold =
        Font(
            resource = Res.font.be_vietnampro_extrabold,
            FontWeight.ExtraBold,
            FontStyle.Normal,
        )

    val montserratExtraLight =
        Font(
            resource = Res.font.be_vietnampro_extralight,
            FontWeight.ExtraLight,
            FontStyle.Normal,
        )
    val montserratBlack = Font(
        resource = Res.font.be_vietnampro_black,
        FontWeight.Black,
        FontStyle.Normal,
    )

     return FontFamily(
        montserratThin,
        montserratExtraLight,
        montserratLight,
        montserratRegular,
        montserratMedium,
        montserratSemiBold,
        montserratBold,
        montserratExtraBold,
        montserratBlack,
    )
}

@Composable
internal fun getTypography(): Typography {
    val montserrat = montserrat()
    return Typography(
        displayLarge = TextStyle(
            fontFamily = montserrat,
            fontWeight = FontWeight.W400,
            fontSize = 50.sp,
        ),
        displayMedium = TextStyle(
            fontFamily = montserrat,
            fontWeight = FontWeight.W400,
            fontSize = 40.sp,
        ),
        displaySmall = TextStyle(
            fontFamily = montserrat,
            fontWeight = FontWeight.W400,
            fontSize = 30.sp,
        ),
        headlineLarge = TextStyle(
            fontFamily = montserrat,
            fontWeight = FontWeight.W400,
            fontSize = 28.sp,
        ),
        headlineMedium = TextStyle(
            fontFamily = montserrat,
            fontWeight = FontWeight.W400,
            fontSize = 24.sp,
        ),
        headlineSmall = TextStyle(
            fontFamily = montserrat,
            fontWeight = FontWeight.W400,
            fontSize = 20.sp,
        ),
        titleLarge = TextStyle(
            fontFamily = montserrat,
            fontWeight = FontWeight.W700,
            fontSize = 18.sp,
        ),
        titleMedium = TextStyle(
            fontFamily = montserrat,
            fontWeight = FontWeight.W700,
            fontSize = 14.sp,
        ),
        titleSmall = TextStyle(
            fontFamily = montserrat,
            fontWeight = FontWeight.W500,
            fontSize = 12.sp,
        ),
        bodyLarge = TextStyle(
            fontFamily = montserrat,
            fontWeight = FontWeight.W400,
            fontSize = 14.sp,
        ),
        bodyMedium = TextStyle(
            fontFamily = montserrat,
            fontWeight = FontWeight.W400,
            fontSize = 12.sp,
        ),
        bodySmall = TextStyle(
            fontFamily = montserrat,
            fontWeight = FontWeight.W400,
            fontSize = 11.sp,
        ),
        labelLarge = TextStyle(
            fontFamily = montserrat,
            fontWeight = FontWeight.W400,
            fontSize = 13.sp,
        ),
        labelMedium = TextStyle(
            fontFamily = montserrat,
            fontWeight = FontWeight.W400,
            fontSize = 11.sp,
        ),
        labelSmall = TextStyle(
            fontFamily = montserrat,
            fontWeight = FontWeight.W500,
            fontSize = 9.sp,
        ),
    )
}
