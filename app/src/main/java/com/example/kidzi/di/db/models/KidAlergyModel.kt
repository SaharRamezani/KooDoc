package com.example.kidzi.di.db.models

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "kid_alergy")
data class KidAlergyModel (
    @PrimaryKey
    val kidId : Int,
    val peanut : Boolean,
    val honey: Boolean,
    val lac: Boolean,
    val cow: Boolean,
    val alcohol: Boolean
)