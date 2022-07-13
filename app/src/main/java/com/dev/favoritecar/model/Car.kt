package com.dev.favoritecar.model

import androidx.annotation.DrawableRes
import java.text.FieldPosition

data class Car(
    val position: Int,
    val name: String,
    @DrawableRes val picture: Int,
    val sku: String?,
    val strength: Int = 100
)