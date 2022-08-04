package com.kkk.mylibrary.utils

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.isseiaoki.simplecropview.CropImageView
import com.kkk.mylibrary.contract.GalleryContract
import com.kkk.mylibrary.utils.enums.TakePhotoType
import com.kkk.mylibrary.utils.extensions.checkCameraPermission
import com.kkk.mylibrary.utils.extensions.checkGalleryPermission
import com.kkk.mylibrary.utils.extensions.showToast
import com.kkk.mylibrary.vo.CropImageData
import com.kkk.mylibrary.vo.OptionType
//import com.tamron.akoneyazay.contract.GalleryContract
//import com.tamron.akoneyazay.util.enums.TakePhotoType
//import com.tamron.akoneyazay.util.extensions.checkCameraPermission
//import com.tamron.akoneyazay.util.extensions.checkGalleryPermission
//import com.tamron.akoneyazay.util.extensions.showToast
//import com.tamron.akoneyazay.vo.CropImageData
import java.util.ArrayList

class TakePhotoUtility(activity: ComponentActivity,private val cropMode: CropImageView.CropMode?=CropImageView.CropMode.FREE) {
    private var galleryLauncher: ActivityResultLauncher<CropImageData>? = null
    private var chooseImageFromCamera:ActivityResultLauncher<Intent>? = null
    private var chooseImageFromGallery:ActivityResultLauncher<String>? = null
    private var requestGalleryPermissionLauncher:ActivityResultLauncher<String>? = null
    private var requestCameraPermissionLauncher:ActivityResultLauncher<String>? = null
    private var requestWriteStoragePermissionLauncher:ActivityResultLauncher<String>? = null
    private var isCropEnable:Boolean = false
    private var doAction: ((Uri) -> Unit?)? = null

    init {
        galleryLauncher = activity.registerForActivityResult(GalleryContract()) {
            it?.let {
                doAction?.let { it1 -> it1(Uri.parse(it)) }
            }
        }
        chooseImageFromCamera = activity.registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == Activity.RESULT_OK) {
                val imageBitmap = it.data?.extras?.get("data") as Bitmap
                val uriImage = ImageUtil.bitmapToImageUri(activity, imageBitmap)
                if (isCropEnable) {
                    galleryLauncher?.launch(
                        CropImageData(
                            uriImage.toString(),
                            cropMode!!
                        )
                    )
                } else {
                    doAction?.let { it1 -> it1(uriImage) }
                }
            }
        }
        chooseImageFromGallery = activity.registerForActivityResult(
            ActivityResultContracts.GetContent()
        ) {
            if (it!=null){
                if (isCropEnable) {
                    galleryLauncher?.launch(
                        CropImageData(
                            it.toString(),
                            cropMode!!
                        )
                    )
                } else {
                    doAction?.let { it1 -> it1(it) }
                }
            }
        }

        requestGalleryPermissionLauncher =
            activity.registerForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) { isGranted: Boolean ->
                if (isGranted) {
                    chooseImageFromGallery?.launch("image/*")
                } else {
                    activity.showToast("Gallery permission is not granted!")
                }
            }

        requestWriteStoragePermissionLauncher = activity.registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                chooseImageFromCamera?.launch(
                    Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                )
            } else {
                activity.showToast("Storage permission is not granted!")
            }
        }

        requestCameraPermissionLauncher =
            activity.registerForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) { isGranted: Boolean ->
                if (isGranted) {
                    requestWriteStoragePermissionLauncher?.let{ activity.checkGalleryPermission(it) }
                } else {
                    activity.showToast("Camera permission is not granted!")
                }
            }

    }

    private fun takeImageFromCamera(
        activity: ComponentActivity,
        isCropEnable: Boolean = true,
        doAction: (Uri) -> Unit
    ){
        this.isCropEnable = isCropEnable
        this.doAction = doAction
        requestCameraPermissionLauncher?.let { activity.checkCameraPermission(it) }
    }

    private fun takeImageFromGallery(
        activity: ComponentActivity,
        isCropEnable: Boolean = true,
        doAction: (Uri) -> Unit
    ){
        this.isCropEnable = isCropEnable
        this.doAction = doAction
        requestGalleryPermissionLauncher?.let { activity.checkGalleryPermission(it) }
    }

    fun takeImageFromCameraAndGallery(
        activity: ComponentActivity,
        isCropEnable: Boolean = true,
        isCamera: Boolean = true,
        doAction: (Uri) -> Unit
    ) {
        if (isCamera) {
            takeImageFromCamera(activity, isCropEnable, doAction)
        } else {
            takeImageFromGallery(activity, isCropEnable, doAction)

        }
    }

}
object TakePhotoOptionList{
    fun getData(): ArrayList<OptionType<TakePhotoType>> {
        val optionList = ArrayList<OptionType<TakePhotoType>>()
        val options = TakePhotoType.values()
        optionList.clear()
        for (value in options) {
            if (value.status != null){
                optionList.add(
                    OptionType(
                        image = value.image,
                        text = value.text,
                        optionType = value
                    )
                )
            }
        }
        return optionList
    }
    fun getSaveImageData(): ArrayList<OptionType<TakePhotoType>> {
        val optionList = ArrayList<OptionType<TakePhotoType>>()
        val options = TakePhotoType.values()
        optionList.clear()
        for (value in options) {
            if (value.status == null){
                optionList.add(
                    OptionType(
                        image = value.image,
                        text = value.text,
                        optionType = value
                    )
                )
            }
        }
        return optionList
    }
}