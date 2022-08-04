package com.kkk.mylibrary.contract

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import com.google.gson.Gson
import com.kkk.mylibrary.ui.activity.CropImageActivity
import com.kkk.mylibrary.utils.constants.BundleConstants
import com.kkk.mylibrary.vo.CropImageData

class GalleryContract : ActivityResultContract<CropImageData, String>() {

    override fun createIntent(context: Context, input: CropImageData): Intent {
        return Intent(context, CropImageActivity::class.java).apply {
            val gson = Gson()
            val myJson = gson.toJson(input)
            putExtra(BundleConstants.EXTRA_CROP_IMAGE_DATA, myJson)
        }
    }

    override fun parseResult(resultCode: Int, intent: Intent?): String? {
        val data = intent?.getStringExtra(BundleConstants.EXTRA_URI)
        return if (resultCode == Activity.RESULT_OK && data != null) data
        else null
    }
}