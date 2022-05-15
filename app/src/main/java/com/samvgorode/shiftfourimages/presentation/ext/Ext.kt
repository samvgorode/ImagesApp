package com.samvgorode.shiftfourimages.presentation.ext

import android.content.ContentResolver
import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import com.samvgorode.shiftfourimages.presentation.image.ImageActivity
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

// This method is blocking main thread !!!
fun saveTheImageLegacy(bitmap: Bitmap): Uri?{
    val imagesDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
    val filename = "IMG_${System.currentTimeMillis()}.jpg"
    val image = File(imagesDir, filename)
    val fos = FileOutputStream(image)
    fos.use {bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)}
    return if(image.exists()) image.toUri() else null
}

// This method is blocking main thread !!!
@RequiresApi(Build.VERSION_CODES.Q)
fun saveImageInQ(bitmap: Bitmap, contentResolver: ContentResolver): Uri? {
    val filename = "IMG_${System.currentTimeMillis()}.jpg"
    var fos: OutputStream? = null
    var imageUri: Uri?
    val contentValues = ContentValues().apply {
        put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
        put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
        put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
        put(MediaStore.Video.Media.IS_PENDING, 1)
    }
    contentResolver.also { resolver ->
        imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
        fos = imageUri?.let { resolver.openOutputStream(it) }
    }
    fos?.use { bitmap.compress(Bitmap.CompressFormat.JPEG, 70, it) }
    contentValues.clear()
    contentValues.put(MediaStore.Video.Media.IS_PENDING, 0)
    imageUri?.let {
        contentResolver.update(it, contentValues, null, null)
    }
    return imageUri
}

/**
 * receiver - starter activity
 * T - target activity
 */
inline fun <reified T: AppCompatActivity> AppCompatActivity.startAnotherActivity() =
    startActivity(Intent(this, T::class.java))