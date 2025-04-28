package com.example.kidzi.ui.toxic.adapters

import android.graphics.drawable.Drawable

data class ToxicPlantsModel(
    val name: String,
    val about: String,
    val img: Drawable,
    var expand: Boolean
)
