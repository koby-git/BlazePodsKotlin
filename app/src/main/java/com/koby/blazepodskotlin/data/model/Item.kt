package com.koby.blazepodskotlin.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "item_table")
data class Item (

    @PrimaryKey
    var id: Int = 0,
    var title: String? = null,
    var body: String? = null,
    var favorite:Boolean = false,
)