package com.example.kidzi.di.db.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "kid_growth")
data class KidGrowthModel(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val kidId: Int,
    val ageWeeks: Int,
    val height: Double,
    val weight: Double,
    val headCircumference: Double
)
