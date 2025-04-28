package com.example.kidzi.di.db.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "family_disease")
data class FamilyDiseaseModel(
    @PrimaryKey
    val parentId : Int,
    val asm: Boolean,
    val alergy: Boolean,
    val diabetes: Boolean,
    val cancer: Boolean,
    val govaresh: Boolean,
    val self: Boolean,
)
