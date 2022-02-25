package com.kkk.baseapp.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kkk.baseapp.viewmodel.MainViewModel
import com.kkk.baseapp.data.repositories.MainRepository

class MainViewModelFactory(val repo: MainRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(repo) as T
    }
}
