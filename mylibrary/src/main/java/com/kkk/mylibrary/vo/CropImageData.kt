package com.kkk.mylibrary.vo

import android.net.Uri
import android.os.Parcelable
import com.isseiaoki.simplecropview.CropImageView
import kotlinx.parcelize.Parcelize

@Parcelize
data class CropImageData(
    val imageUri: String?,
    val cropMode: CropImageView.CropMode
):Parcelable