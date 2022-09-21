package com.kkk.mylibrary.domain.usecase

import kotlinx.coroutines.flow.MutableStateFlow

sealed class Result<out T> {

    open val data: T? = null

    abstract fun <R> map(f: (T) -> R): Result<R>

    inline fun doOnData(f: (T) -> Unit) {
        if (this is Success) {
            f(data)
        }
    }

    data class Success<out T>(override val data: T) : Result<T>() {
        override fun <R> map(f: (T) -> R): Result<R> = Success(f(data))
    }

    data class Error(val throwable: Throwable) : Result<Nothing>() {
        constructor(message: String) : this(Throwable(message))

        override fun <R> map(f: (Nothing) -> R): Result<R> = this
    }

    object Loading : Result<Nothing>() {
        override fun <R> map(f: (Nothing) -> R): Result<R> = this
    }

    object NoNewData : Result<Nothing>() {
        override fun <R> map(f: (Nothing) -> R): Result<R> = this
    }
}

fun <T> Result<T>.successOr(fallback: T): T {
    if (this is Result.Error) {
    }
    return (this as? Result.Success<T>)?.data ?: fallback
}

/**
 * Updates value of [MutableStateFlow] if [Result] is of type [Success]
 */
inline fun <reified T> Result<T>.updateOnSuccess(stateFlow: MutableStateFlow<T>) {
    if (this is Result.Success) {
        stateFlow.value = data
    }
}
