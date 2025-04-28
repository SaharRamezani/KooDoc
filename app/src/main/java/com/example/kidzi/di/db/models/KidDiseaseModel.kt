package com.example.kidzi.di.db.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "kid_disease")
data class KidDiseaseModel(
    @PrimaryKey
    val kidId : Int,
    val diabetes: Boolean,
    val faw: Boolean,
    val meta: Boolean,
    val asm: Boolean,
    val fibr: Boolean,
    val seli : Boolean,
    val other: String
)
