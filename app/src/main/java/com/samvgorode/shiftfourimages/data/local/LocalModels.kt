package com.samvgorode.shiftfourimages.data.local

import androidx.room.*

@Entity(tableName = "images")
data class ImageEntity(
    @PrimaryKey val id: String,
    val url: String?,
    val favorite: Boolean?
)

@Dao
interface FavoriteImageDao {
    @Query("SELECT * FROM images WHERE favorite IS 1")
    suspend fun getAllFavorite(): List<ImageEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertImages(images: List<ImageEntity>): List<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertImage(image: ImageEntity)
}
