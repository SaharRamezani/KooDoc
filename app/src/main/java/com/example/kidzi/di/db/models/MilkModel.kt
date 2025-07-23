package com.example.kidzi.di.db.models

data class MilkModel(
    val persianName: String,
    val englishName: String,
    val startAge: Int,
    val endAge: Int,
    val lac: Int,
    val usage: String,
    val milkType: String,
    var isSelected: Boolean = false,
)
