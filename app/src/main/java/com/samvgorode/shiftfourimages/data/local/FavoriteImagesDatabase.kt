package com.samvgorode.shiftfourimages.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ImageEntity::class], version = 1)
abstract class FavoriteImagesDatabase : RoomDatabase() {
    abstract fun imageDao(): FavoriteImageDao
}