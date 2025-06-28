package com.example.kidzi.di.modules

import com.example.kidzi.di.db.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import android.content.Context
import androidx.room.Room
import com.example.kidzi.di.db.dao.*
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class DatabaseModules {

    @Provides
    fun provideGrowthDataDao(appDatabase: AppDatabase): GrowthDataDao {
        return appDatabase.growthDataDao()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "kidzi_db"
        ).build()
    }

    @Provides
    fun provideKidName(appDatabase: AppDatabase): KidNameDao {
        return appDatabase.kidNameDao()
    }

    @Provides
    fun provideKidDisease(appDatabase: AppDatabase): KidDiseaseDao{
        return appDatabase.kidDiseaseDao()
    }

    @Provides
    fun provideKidAlergy(appDatabase: AppDatabase): KidAlergyDao{
        return appDatabase.kisAlergyDao()
    }

    @Provides
    fun provideKidSocial(appDatabase: AppDatabase): KidSocialDao {
        return appDatabase.kidSocialDao()
    }

    @Provides
    fun provideFamilyDisease(appDatabase: AppDatabase): FamilyDiseaseDao{
        return appDatabase.familyDiseaseDao()
    }

    @Provides
    fun provideKidGrowth(appDatabase: AppDatabase): KidGrowthDao {
        return appDatabase.kidGrowthDao()
    }

}