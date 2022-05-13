package com.samvgorode.shiftfourimages.di

import android.content.Context
import androidx.room.Room
import com.samvgorode.shiftfourimages.data.DataMapper
import com.samvgorode.shiftfourimages.data.ImagesRepositoryImpl
import com.samvgorode.shiftfourimages.data.local.ImageDao
import com.samvgorode.shiftfourimages.data.local.ImagesDatabase
import com.samvgorode.shiftfourimages.data.remote.ApiService
import com.samvgorode.shiftfourimages.domain.ImagesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private const val DB_NAME = "com.samvgorode.shiftfourimages.di.images_database"
    private const val BASE_URL = "https://api.unsplash.com/"
    private const val NETWORK_TIMEOUT_SECONDS = 10L

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): ImagesDatabase =
        Room.databaseBuilder(context, ImagesDatabase::class.java, DB_NAME)
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideImageDao(database: ImagesDatabase): ImageDao = database.imageDao()

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient().newBuilder()
        .callTimeout(NETWORK_TIMEOUT_SECONDS, TimeUnit.SECONDS)
        .connectTimeout(NETWORK_TIMEOUT_SECONDS, TimeUnit.SECONDS)
        .readTimeout(NETWORK_TIMEOUT_SECONDS, TimeUnit.SECONDS)
        .writeTimeout(NETWORK_TIMEOUT_SECONDS, TimeUnit.SECONDS)
        .build()

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    @Singleton
    @Provides
    fun provideImagesRepository(
        apiService: ApiService,
        imageDao: ImageDao,
        imageMapper: DataMapper
    ): ImagesRepository = ImagesRepositoryImpl(apiService, imageDao, imageMapper)
}