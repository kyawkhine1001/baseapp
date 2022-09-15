package com.kkk.mylibrary.utils

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.kkk.mylibrary.utils.constants.RequestConstants
import com.kkk.mylibrary.utils.listeners.RequestPermissionCallback

object PermissionUtils {

    fun checkCameraPermission(
        context: Context,
        requestPermissions: RequestPermissionCallback,
        activity: Activity
    ) {
        if ((ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CAMERA,
            ) == PackageManager.PERMISSION_GRANTED) &&
            (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.READ_EXTERNAL_STORAGE,
            ) == PackageManager.PERMISSION_GRANTED) && (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
        ) {
            requestPermissions.onPermissionResult(RequestConstants.MY_CAMERA_READ_PERMISSION_REQUEST_CODE)
        } else if ((ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CAMERA,
            ) == PackageManager.PERMISSION_GRANTED) &&
            (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.READ_EXTERNAL_STORAGE,
            ) == PackageManager.PERMISSION_GRANTED) &&
            (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
            ) == PackageManager.PERMISSION_GRANTED)
        ) {
            requestPermissions.onPermissionResult(RequestConstants.MY_CAMERA_READ_PERMISSION_REQUEST_CODE)
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                activity.requestPermissions(
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
                        arrayOf(
                            Manifest.permission.CAMERA,
                            Manifest.permission.READ_EXTERNAL_STORAGE
                        ) else arrayOf(
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    ),
                    RequestConstants.MY_CAMERA_READ_PERMISSION_REQUEST_CODE
                )
            }
        }
    }

//    fun checkReadWritePermission(
//        context: Context,
//        requestPermissions: ReqPermissionCallback,
//        activity: Activity
//    ) {
//        if ((ActivityCompat.checkSelfPermission(
//                context,
//                Manifest.permission.READ_EXTERNAL_STORAGE,
//            ) == PackageManager.PERMISSION_GRANTED) && (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) &&
//            (ActivityCompat.checkSelfPermission(
//                context,
//                Manifest.permission.MANAGE_EXTERNAL_STORAGE,
//            ) == PackageManager.PERMISSION_GRANTED)
//        ) {
//            requestPermissions.onPermissionResult(RequestConstants.MY_READ_PERMISSION_REQUEST_CODE)
//        } else if ((ActivityCompat.checkSelfPermission(
//                context,
//                Manifest.permission.READ_EXTERNAL_STORAGE,
//            ) == PackageManager.PERMISSION_GRANTED) &&
//            (ContextCompat.checkSelfPermission(
//                context,
//                Manifest.permission.WRITE_EXTERNAL_STORAGE,
//            ) == PackageManager.PERMISSION_GRANTED) && (ContextCompat.checkSelfPermission(
//                context,
//                Manifest.permission.MANAGE_EXTERNAL_STORAGE,
//            ) == PackageManager.PERMISSION_GRANTED)
//        ) {
//            requestPermissions.onPermissionResult(RequestConstants.MY_READ_PERMISSION_REQUEST_CODE)
//        } else {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                activity.requestPermissions(
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
//                    {
//                        arrayOf(
//                            Manifest.permission.READ_EXTERNAL_STORAGE,
//                            Manifest.permission.MANAGE_EXTERNAL_STORAGE,
//                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                        )
//                    }
//
//                    else arrayOf(
//                        Manifest.permission.READ_EXTERNAL_STORAGE,
//                        Manifest.permission.WRITE_EXTERNAL_STORAGE
//                    ),
//                    RequestConstants.MY_READ_PERMISSION_REQUEST_CODE
//                )
//            }
//        }
//    }

    fun checkReadWritePermission(
        context: Context,
        requestPermissions: RequestPermissionCallback,
        activity: Activity
    ) {
        if ((ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.READ_EXTERNAL_STORAGE,
            ) == PackageManager.PERMISSION_GRANTED) && (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
        ) {
            requestPermissions.onPermissionResult(RequestConstants.MY_READ_PERMISSION_REQUEST_CODE)
        } else if ((ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.READ_EXTERNAL_STORAGE,
            ) == PackageManager.PERMISSION_GRANTED) &&
            (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
            ) == PackageManager.PERMISSION_GRANTED)
        ) {
            requestPermissions.onPermissionResult(RequestConstants.MY_READ_PERMISSION_REQUEST_CODE)
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                activity.requestPermissions(
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
                        arrayOf(
                            Manifest.permission.READ_EXTERNAL_STORAGE
                        )
                    else arrayOf(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ),
                    RequestConstants.MY_READ_PERMISSION_REQUEST_CODE
                )
            }
        }
    }

    fun checkPermissionGranted(grantResults: IntArray): Boolean {
        return isAllPermissionGranted(grantResults)
    }

    private fun isAllPermissionGranted(grantResults: IntArray): Boolean {
        for (i in grantResults) {
            if (i != PackageManager.PERMISSION_GRANTED) {
                return false
            }
        }
        return true
    }

    fun checkPhoneCallPermission(
        context: Context,
        requestPermissions: RequestPermissionCallback,
        activity: Activity
    ) {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.CALL_PHONE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions.onPermissionResult(RequestConstants.PHONE_CALL_PERMISSION_REQUEST_CODE)
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission.CALL_PHONE
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    activity.let {
                        ActivityCompat.requestPermissions(
                            it,
                            arrayOf(Manifest.permission.CALL_PHONE),
                            RequestConstants.PHONE_CALL_PERMISSION_REQUEST_CODE
                        )
                    }
                } else {
                    requestPermissions.onPermissionResult(RequestConstants.PHONE_CALL_PERMISSION_REQUEST_CODE)
                }
            } else {
                requestPermissions.onPermissionResult(RequestConstants.PHONE_CALL_PERMISSION_REQUEST_CODE)
            }
        }
    }

    fun checkLocationPermission(
        context: Context, requestPermissions: RequestPermissionCallback,
        activity: Activity
    ) {
        if ((ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION,
            ) == PackageManager.PERMISSION_GRANTED) &&
            (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION,
            ) == PackageManager.PERMISSION_GRANTED)
        ) {
            requestPermissions.onPermissionResult(RequestConstants.LOCATION_PERMISSION_REQUEST_CODE)
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if ((ActivityCompat.checkSelfPermission(
                        context,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                    ) != PackageManager.PERMISSION_GRANTED) &&
                    (ActivityCompat.checkSelfPermission(
                        context,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                    ) != PackageManager.PERMISSION_GRANTED)
                ) {
                    activity.let {
                        ActivityCompat.requestPermissions(
                            it,
                            arrayOf(
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_COARSE_LOCATION
                            ),
                            RequestConstants.LOCATION_PERMISSION_REQUEST_CODE
                        )
                    }
                } else {
                    requestPermissions.onPermissionResult(RequestConstants.LOCATION_PERMISSION_REQUEST_CODE)
                }
            } else {
                requestPermissions.onPermissionResult(RequestConstants.LOCATION_PERMISSION_REQUEST_CODE)
            }
        }
    }

}