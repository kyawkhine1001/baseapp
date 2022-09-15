package com.kkk.mylibrary.ui.activity

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import com.google.gson.Gson
import com.isseiaoki.simplecropview.CropImageView
import com.kkk.mylibrary.R
import com.kkk.mylibrary.databinding.ActivityCropImageBinding
import com.kkk.mylibrary.ui.fragment.CropImageFragment
import com.kkk.mylibrary.utils.constants.BundleConstants
import com.kkk.mylibrary.vo.CropImageData

class CropImageActivity : BaseViewBindingActivity<ActivityCropImageBinding>(),
    View.OnClickListener {
    private var imageUri: Uri? = null
    private var cropMode: CropImageView.CropMode? = null

    companion object {
        fun newIntent(context: Context, cropImageData: CropImageData): Intent {
            val intent = Intent(context, CropImageActivity::class.java)

            val gson = Gson()
            intent.putExtra(BundleConstants.EXTRA_URI, gson.toJson(cropImageData))

            return intent
        }
    }

    override val bindingInflater: (LayoutInflater) -> ActivityCropImageBinding
        get() = ActivityCropImageBinding::inflate

    override fun setup() {
        binding.tbCropImage.tvToolbarTitle.text = getString(R.string.txt_crop_image)
        supportFragmentManager.beginTransaction()
            .add(R.id.container, CropImageFragment.newInstance()).commit()
        val gson = Gson()
        val myExtraData: CropImageData = gson.fromJson(
            intent.getStringExtra(BundleConstants.EXTRA_CROP_IMAGE_DATA),
            CropImageData::class.java
        )
        imageUri = Uri.parse(myExtraData.imageUri.toString())
        cropMode = myExtraData.cropMode
    }

    override fun observers() {
    }

    override fun listeners() {
        binding.tbCropImage.tbBackArrow.setOnClickListener(this)
    }

    fun getUri(): Uri? {
        return imageUri
    }

    fun getCropMode(): CropImageView.CropMode? {
        return cropMode
    }

    override fun onClick(p0: View?) {
        when (p0) {
            binding.tbCropImage.tbBackArrow -> {
                onBackPressed()
            }
        }
    }

    fun startResultActivity(uri: Uri?) {
        val output = Intent()
        output.putExtra(BundleConstants.EXTRA_URI, uri.toString())
        setResult(RESULT_OK, output)
        finish()
    }

}