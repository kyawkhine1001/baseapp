package com.kkk.mylibrary.ui.fragment

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.RectF
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.isseiaoki.simplecropview.CropImageView
import com.isseiaoki.simplecropview.callback.CropCallback
import com.isseiaoki.simplecropview.callback.LoadCallback
import com.isseiaoki.simplecropview.callback.SaveCallback
import com.kkk.mylibrary.databinding.FragmentCropImageBinding
import com.kkk.mylibrary.ui.activity.CropImageActivity
import com.kkk.mylibrary.utils.ImageUtil
import com.kkk.mylibrary.utils.extensions.showToast

class CropImageFragment : BaseViewBindingFragment<FragmentCropImageBinding>(),
    View.OnClickListener {


    private val mCompressFormat = Bitmap.CompressFormat.JPEG
    private var mFrameRect: RectF? = null
    private var mSourceUri: Uri? = null
    private var cropMode: CropImageView.CropMode? = null

    companion object {
        private const val KEY_FRAME_RECT = "FrameRect"
        private const val KEY_SOURCE_URI = "SourceUri"
        fun newInstance(): CropImageFragment {
            val fragment = CropImageFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }

        fun getMimeType(format: Bitmap.CompressFormat?): String {
            return when (format) {
                Bitmap.CompressFormat.JPEG -> "jpeg"
                Bitmap.CompressFormat.PNG -> "png"
                else -> "png"
            }
        }
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentCropImageBinding
        get() = FragmentCropImageBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState != null) {
            // restore data
            mFrameRect = savedInstanceState.getParcelable(KEY_FRAME_RECT)
            mSourceUri = savedInstanceState.getParcelable(KEY_SOURCE_URI)
        }
    }

    override fun setup() {
        // load image
        mSourceUri = (activity as CropImageActivity).getUri()
        cropMode = (activity as CropImageActivity).getCropMode()
        loadImage(mSourceUri)
    }

    // Callbacks ///////////////////////////////////////////////////////////////////////////////////
    private val mLoadCallback: LoadCallback = object : LoadCallback {
        override fun onSuccess() {

        }

        override fun onError(e: Throwable) {
            requireContext().showToast("File Not Found")
            activity?.finish()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        // save data
        outState.putParcelable(KEY_FRAME_RECT, binding.cropImageView.actualCropRect)
        outState.putParcelable(KEY_SOURCE_URI, binding.cropImageView.sourceUri)
    }

    private fun loadImage(uri: Uri?) {
        mSourceUri = uri
        binding.cropImageView.setCropMode(cropMode)
        // load image
        binding.cropImageView.load(mSourceUri)
            .initialFrameRect(mFrameRect)
            .useThumbnail(true)
            .execute(mLoadCallback)
    }

    override fun observers() {
    }

    override fun listeners() {
        binding.buttonDone.setOnClickListener(this)
        binding.buttonRotateLeft.setOnClickListener(this)
        binding.buttonRotateRight.setOnClickListener(this)
        binding.buttonDone.setOnClickListener(this)
    }

    private val mCropCallback: CropCallback = object : CropCallback {
        override fun onSuccess(cropped: Bitmap) {
            binding.cropImageView.save(cropped)
                .compressQuality(80)
                .compressFormat(mCompressFormat)
                .execute(createSaveUri(), mSaveCallback)
        }

        override fun onError(e: Throwable) {}
    }
    private val mSaveCallback: SaveCallback = object : SaveCallback {
        override fun onSuccess(outputUri: Uri) {
            (activity as CropImageActivity?)?.startResultActivity(outputUri)
        }

        override fun onError(e: Throwable) {
        }
    }

    private fun createSaveUri(): Uri? {
        return ImageUtil.createPhotoUri(requireContext())
    }
    private fun pickImage() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.type ="image/*"
        startActivityForResult(intent, 101)
    }

    override fun onClick(p0: View?) {
        when (p0) {
            binding.buttonDone -> {
                binding.cropImageView.crop(mSourceUri).execute(mCropCallback)
            }
            binding.buttonRotateLeft ->  binding.cropImageView.rotateImage(CropImageView.RotateDegrees.ROTATE_M90D)
            binding.buttonRotateRight -> binding.cropImageView.rotateImage(CropImageView.RotateDegrees.ROTATE_90D)
            binding.buttonPickImage -> {pickImage()}
        }
    }
}