package com.example.kidzi.di.db.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "kid_name")
data class KidNameModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val weight: Double,
    val height: Double,
    val birthDate: String,
    val type: Int
)
