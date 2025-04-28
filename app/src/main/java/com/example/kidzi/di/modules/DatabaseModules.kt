package com.example.kidzi.di.modules

import com.example.kidzi.di.db.AppDatabase
import com.example.kidzi.di.db.dao.FamilyDiseaseDao
import com.example.kidzi.di.db.dao.KidAlergyDao
import com.example.kidzi.di.db.dao.KidDiseaseDao
import com.example.kidzi.di.db.dao.KidGrowthDao
import com.example.kidzi.di.db.dao.KidNameDao
import com.example.kidzi.di.db.dao.KidSocialDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
class DatabaseModules {

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