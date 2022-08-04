package com.kkk.mylibrary.utils.extensions

import android.Manifest
import android.content.Context
import android.os.Build
import androidx.activity.result.ActivityResultLauncher


fun Context.checkCameraPermission(
    permissionLauncher: ActivityResultLauncher<String>
) = run {
    permissionLauncher.launch(
        Manifest.permission.CAMERA
    )
}

fun Context.checkGalleryPermission(
    permissionLauncher: ActivityResultLauncher<String>,
) = run {
    permissionLauncher.launch(
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            Manifest.permission.READ_EXTERNAL_STORAGE
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        } else {
            Manifest.permission.READ_EXTERNAL_STORAGE
            Manifest.permission.WRITE_EXTERNAL_STORAGE

        }
    )
}
