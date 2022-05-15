package com.samvgorode.shiftfourimages.di

import android.content.Context
import android.content.SharedPreferences
import com.samvgorode.shiftfourimages.data.DataMapper
import com.samvgorode.shiftfourimages.data.ImagesRepositoryImpl
import com.samvgorode.shiftfourimages.data.remote.ApiService
import com.samvgorode.shiftfourimages.domain.ImagesDomainMapper
import com.samvgorode.shiftfourimages.domain.ImagesRepository
import com.samvgorode.shiftfourimages.domain.favorite.GetImageFavoriteUseCase
import com.samvgorode.shiftfourimages.domain.favorite.GetImageFavoriteUseCaseImpl
import com.samvgorode.shiftfourimages.domain.getList.GetImagesListUseCase
import com.samvgorode.shiftfourimages.domain.getList.GetImagesListUseCaseImpl
import com.samvgorode.shiftfourimages.domain.lastSelected.GetSelectedImageUseCase
import com.samvgorode.shiftfourimages.domain.lastSelected.GetSelectedImageUseCaseImpl
import com.samvgorode.shiftfourimages.domain.lastSelected.SetSelectedImageUseCase
import com.samvgorode.shiftfourimages.domain.lastSelected.SetSelectedImageUseCaseImpl
import com.samvgorode.shiftfourimages.domain.favorite.SetImageFavoriteUseCase
import com.samvgorode.shiftfourimages.domain.favorite.SetImageFavoriteUseCaseImpl
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

    private const val SP_NAME = "com.samvgorode.shiftfourimages.di.SharedPreferences"
    private const val BASE_URL = "https://api.unsplash.com/"
    private const val NETWORK_TIMEOUT_SECONDS = 10L

    @Singleton
    @Provides
    fun provideSharedPreference(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
    }

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
        imageMapper: DataMapper
    ): ImagesRepository = ImagesRepositoryImpl(apiService, imageMapper)

    @Provides
    fun provideGetImagesListUseCase(
        repository: ImagesRepository,
        mapper: ImagesDomainMapper
    ): GetImagesListUseCase = GetImagesListUseCaseImpl(repository, mapper)

    @Provides
    fun provideSetImageFavoriteUseCase(
        sharedPreferences: SharedPreferences
    ): SetImageFavoriteUseCase = SetImageFavoriteUseCaseImpl(sharedPreferences)

    @Provides
    fun provideGetImageFavoriteUseCase(
        sharedPreferences: SharedPreferences
    ): GetImageFavoriteUseCase = GetImageFavoriteUseCaseImpl(sharedPreferences)

    @Provides
    fun provideGetSelectedImageUseCase(
        repository: ImagesRepository,
        mapper: ImagesDomainMapper
    ): GetSelectedImageUseCase = GetSelectedImageUseCaseImpl(repository, mapper)

    @Provides
    fun provideSetSelectedImageUseCase(
        repository: ImagesRepository,
        mapper: ImagesDomainMapper
    ): SetSelectedImageUseCase = SetSelectedImageUseCaseImpl(repository, mapper)
}