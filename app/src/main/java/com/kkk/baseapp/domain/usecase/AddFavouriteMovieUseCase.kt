package com.kkk.baseapp.domain.usecase

import com.kkk.baseapp.di.hilt.IoDispatcher
import com.kkk.baseapp.domain.repo.MainRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class AddFavouriteMovieUseCase @Inject constructor(
    private val mainRepo: MainRepository,
    @IoDispatcher private val coroutineDispatcher: CoroutineDispatcher
) {
    operator fun invoke(movieId:Int,isFavourite:Int){
        return mainRepo.updateFavoriteDataByMovieType(movieId,isFavourite)
    }
}