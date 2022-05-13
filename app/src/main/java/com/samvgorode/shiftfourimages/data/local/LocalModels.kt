package com.samvgorode.shiftfourimages.data.local

import androidx.room.*

@Entity(tableName = "image")
data class ImageEntity(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "url") val url: String?
)

@Dao
interface ImageDao {
    @Query("SELECT * FROM image LIMIT :limit OFFSET :offset")
    suspend fun getAll(limit: Int, offset: Int): List<ImageEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertImages(images: List<ImageEntity>): List<Long>
}
