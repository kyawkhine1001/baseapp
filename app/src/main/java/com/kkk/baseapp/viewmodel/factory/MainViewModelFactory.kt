package com.kkk.baseapp.viewmodel.factory

import androidx.lifecycle.ViewModelProvider
import com.kkk.baseapp.domain.repo.MainRepository
import com.kkk.mylibrary.network.rx.SchedulerProvider

class MainViewModelFactory(private val repo: MainRepository, val schedulers:SchedulerProvider) : ViewModelProvider.Factory {
//    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
//        return MainViewModel(repo,schedulers) as T
//    }
}
