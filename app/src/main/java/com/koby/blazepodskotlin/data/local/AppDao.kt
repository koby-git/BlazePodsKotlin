package com.koby.blazepodskotlin.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.koby.blazepodskotlin.data.model.Item

@Dao
interface AppDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(item: List<Item>): LongArray

    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun insertItem(item: Item): Long

    @Query("SELECT * FROM item_table")
    fun getAllItems() : LiveData<List<Item>>

    @Query("SELECT * FROM item_table WHERE favorite = 1")
    fun getAllFavoriteItems() : LiveData<List<Item>>

    /*
        @Query("DELETE FROM app_table WHERE x = 1")
        suspend fun deleteDiscoverMovies()
    */

}