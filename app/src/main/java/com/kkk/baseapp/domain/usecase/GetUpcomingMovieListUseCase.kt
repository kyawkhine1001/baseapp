package com.kkk.baseapp.domain.usecase

import com.kkk.baseapp.di.hilt.IoDispatcher
import com.kkk.baseapp.domain.pojo.MovieDomain
import com.kkk.baseapp.domain.repo.MainRepository
import com.kkk.mylibrary.network.ResourceState
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class GetUpcomingMovieListUseCase @Inject constructor(
    private val mainRepo: MainRepository,
    @IoDispatcher private val coroutineDispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(): ResourceState<List<MovieDomain>?> {
        return mainRepo.fetchUpcomingMovieData()
    }
}