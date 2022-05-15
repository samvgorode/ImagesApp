package com.samvgorode.shiftfourimages.domain.favorite

import android.content.SharedPreferences
import com.samvgorode.shiftfourimages.data.local.FavoriteImageDao
import com.samvgorode.shiftfourimages.domain.ImagesDomainMapper
import com.samvgorode.shiftfourimages.domain.ImagesRepository
import com.samvgorode.shiftfourimages.presentation.ImageUiModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class SetImageFavoriteUseCaseImpl(
    private val sharedPreferences: SharedPreferences,
    private val repository: ImagesRepository,
    private val mapper: ImagesDomainMapper,
) : SetImageFavoriteUseCase {

    override suspend operator fun invoke(imageUiModel: ImageUiModel) {
        withContext(Dispatchers.IO) {
            sharedPreferences.edit().putBoolean(imageUiModel.id, imageUiModel.favorite).apply()
            repository.insertImage(imageUiModel.let(mapper::mapToDomain))
        }
    }
}