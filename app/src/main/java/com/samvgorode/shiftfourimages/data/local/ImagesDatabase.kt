package com.samvgorode.shiftfourimages.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ImageEntity::class], version = 1)
abstract class ImagesDatabase : RoomDatabase() {
    abstract fun imageDao(): ImageDao
}