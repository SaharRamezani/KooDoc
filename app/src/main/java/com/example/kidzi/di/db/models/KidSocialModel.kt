package com.example.kidzi.di.db.models

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "kid_social")
data class KidSocialModel (
    @PrimaryKey
    val kidId : Int,
    val social: Boolean
)