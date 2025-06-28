package com.example.kidzi.di.modules

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.kidzi.di.db.AppDatabase
import com.example.kidzi.di.db.PreferenceManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModules{
    @Provides
    @Singleton
    fun providesPreferences(@ApplicationContext context: Context): PreferenceManager {
        return PreferenceManager(context)
    }
}