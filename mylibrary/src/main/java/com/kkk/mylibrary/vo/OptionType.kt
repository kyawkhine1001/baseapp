package com.kkk.mylibrary.vo

data class OptionType<T>(
    val image: Int? = null,
    val text: Int,
    val optionType: T
)