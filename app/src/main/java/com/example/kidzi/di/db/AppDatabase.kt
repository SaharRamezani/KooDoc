package com.example.kidzi.di.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.kidzi.di.db.dao.*
import com.example.kidzi.di.db.models.*


@Database(
    entities = [
        KidNameModel::class,
        KidDiseaseModel::class,
        KidAlergyModel::class,
        KidSocialModel::class,
        FamilyDiseaseModel::class,
        KidGrowthModel::class
    ],
    version = 2,
    exportSchema = true,
    autoMigrations = []
)
abstract class AppDatabase:RoomDatabase() {
    abstract fun growthDataDao(): GrowthDataDao
    abstract fun kidNameDao(): KidNameDao
    abstract fun kidDiseaseDao(): KidDiseaseDao
    abstract fun kisAlergyDao(): KidAlergyDao
    abstract fun kidSocialDao(): KidSocialDao
    abstract fun familyDiseaseDao(): FamilyDiseaseDao
    abstract fun kidGrowthDao(): KidGrowthDao
}