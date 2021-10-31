package com.koby.blazepodskotlin.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.koby.blazepodskotlin.data.local.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
class TestAppModule {

    @Provides
    @Named("test_db")
    fun provideInMemoryDb(
        @ApplicationContext context: Context
    ) = Room.inMemoryDatabaseBuilder(
        context,
        AppDatabase::class.java
    ).allowMainThreadQueries()
        .build()

}