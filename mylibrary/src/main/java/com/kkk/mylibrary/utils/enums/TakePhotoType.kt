package com.kkk.mylibrary.utils.enums

import com.kkk.mylibrary.R

enum class TakePhotoType(val text: Int, val image: Int?, val status: Int?) {
    TAKE_PHOTO(R.string.txt_camera, R.drawable.ic_camera_take_photo, 1),
    CHOOSE_PHOTO(R.string.txt_gallery, R.drawable.ic_gallrey_choose_image, 1),
    SAVE_PHOTO(R.string.txt_save, R.drawable.ic_save_image, null)
}