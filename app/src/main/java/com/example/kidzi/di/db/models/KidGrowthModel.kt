package com.example.kidzi.di.db.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "growth")
data class KidGrowthModel(
    @PrimaryKey
    val kidId : Int,
    val age : Int,
    val height:Double,
    val weight: Double,
    val head: Double

)
