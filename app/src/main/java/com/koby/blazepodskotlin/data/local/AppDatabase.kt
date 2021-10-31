package com.koby.blazepodskotlin.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.koby.blazepodskotlin.data.model.Item

@Database(entities = [Item::class,], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun appDao():AppDao
}
