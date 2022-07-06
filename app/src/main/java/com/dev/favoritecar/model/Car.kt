package com.dev.favoritecar.model

import androidx.annotation.DrawableRes

data class Car(
    val name: String,
    @DrawableRes val picture: Int,
    val sku: String?,
    val strength: Int = 100
)