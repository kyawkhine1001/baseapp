package com.kkk.baseapp.domain.usecase

import androidx.paging.PagingData
import com.kkk.baseapp.di.hilt.IoDispatcher
import com.kkk.baseapp.domain.pojo.MovieDomain
import com.kkk.baseapp.domain.repo.MainRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPopularMovieListUseCase @Inject constructor(
    private val mainRepo: MainRepository,
    @IoDispatcher private val coroutineDispatcher: CoroutineDispatcher
) {
     operator fun invoke(): Flow<PagingData<MovieDomain>> {
        return mainRepo.fetchPopularMovieData()
    }
}