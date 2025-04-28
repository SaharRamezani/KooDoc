package com.example.kidzi.di.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.kidzi.di.db.dao.FamilyDiseaseDao
import com.example.kidzi.di.db.dao.KidAlergyDao
import com.example.kidzi.di.db.dao.KidDiseaseDao
import com.example.kidzi.di.db.dao.KidGrowthDao
import com.example.kidzi.di.db.dao.KidNameDao
import com.example.kidzi.di.db.dao.KidSocialDao
import com.example.kidzi.di.db.models.FamilyDiseaseModel
import com.example.kidzi.di.db.models.KidAlergyModel
import com.example.kidzi.di.db.models.KidDiseaseModel
import com.example.kidzi.di.db.models.KidGrowthModel
import com.example.kidzi.di.db.models.KidNameModel
import com.example.kidzi.di.db.models.KidSocialModel


@Database(
    entities = [
        KidNameModel::class,
        KidDiseaseModel::class,
        KidAlergyModel::class,
        KidSocialModel::class,
        FamilyDiseaseModel::class,
        KidGrowthModel::class
    ],
    version = 1,
    exportSchema = true,
    autoMigrations = []
)
abstract class AppDatabase:RoomDatabase() {

    abstract fun kidNameDao(): KidNameDao
    abstract fun kidDiseaseDao(): KidDiseaseDao
    abstract fun kisAlergyDao(): KidAlergyDao
    abstract fun kidSocialDao(): KidSocialDao
    abstract fun familyDiseaseDao(): FamilyDiseaseDao
    abstract fun kidGrowthDao(): KidGrowthDao

}