package com.kkk.mylibrary.utils

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.Activity
import android.app.DownloadManager
import android.content.ContentResolver
import android.content.ContentUris
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.util.Base64
import androidx.annotation.RequiresApi
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.OutputStream


object ImageUtil {
    private const val filePath = "/KKKBase/"
    var nopath = "Select Video Only"

    fun bitmapToBase64(imgList: List<Bitmap>): List<String> {

        val imageStringList = mutableListOf<String>()
        imgList.map {
            val byteArrayOutputStream: ByteArrayOutputStream = ByteArrayOutputStream();
            it.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);

            val byteArray = byteArrayOutputStream.toByteArray()

            val encoded: String = Base64.encodeToString(byteArray, Base64.DEFAULT)
            imageStringList.add(encoded)
        }
        return imageStringList

    }

    fun bitmapToBase64(img: Bitmap): String {

        val byteArrayOutputStream: ByteArrayOutputStream = ByteArrayOutputStream();
        img.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);

        val byteArray = byteArrayOutputStream.toByteArray()

        return Base64.encodeToString(byteArray, Base64.DEFAULT)

    }

    fun bitmapFromBase64(encodedImage: String): Bitmap {
        val decodedString: ByteArray = Base64.decode(encodedImage, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)

    }

    fun createPhotoUri(context: Context): Uri? {
        val imageCollection = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
        } else {
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        }

        val newImage = ContentValues().apply {
            put(
                MediaStore.Images.Media.DISPLAY_NAME,
                "camera-${System.currentTimeMillis()}.${Bitmap.CompressFormat.JPEG}"
            )
        }
        return context.contentResolver.insert(imageCollection, newImage)
    }


    @TargetApi(Build.VERSION_CODES.KITKAT)
    @SuppressLint("NewApi")
    fun getPath(context: Context, uri: Uri): String? {

        /**
         * DocumentProvider
         */
        if (DocumentsContract.isDocumentUri(context, uri)) {

            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                val docId = DocumentsContract.getDocumentId(uri)
                val split = docId.split(":".toRegex()).toTypedArray()
                val type = split[0]
                if ("primary".equals(type, ignoreCase = true)) {
                    return (Environment.getExternalStorageDirectory().toString() + "/"
                            + split[1])
                }
            } else if (isDownloadsDocument(uri)) {
                val id = DocumentsContract.getDocumentId(uri)
                val contentUri = ContentUris.withAppendedId(
                    Uri.parse("content://downloads/public_downloads"), id.toLong()
                )
                return getDataColumn(context, contentUri, null, null)
            } else if (isMediaDocument(uri)) {
                val docId = DocumentsContract.getDocumentId(uri)
                val split = docId.split(":".toRegex()).toTypedArray()
                val type = split[0]
                var contentUri: Uri? = null
                when (type) {
                    "image" -> {
                        contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                    }
                    "video" -> {
                        contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                    }
                    "audio" -> {
                        contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
                    }
                }
                val selection = "_id=?"
                val selectionArgs = arrayOf(split[1])
                return getDataColumn(
                    context, contentUri, selection,
                    selectionArgs
                )
            }
        } else if ("content".equals(uri.scheme, ignoreCase = true)) {

            // Return the remote address
            return if (isGooglePhotosUri(uri)) uri.lastPathSegment else getDataColumn(
                context,
                uri,
                null,
                null
            )
        } else if ("file".equals(uri.scheme, ignoreCase = true)) {
            return uri.path
        }
        return nopath
    }
    /**
     * Get the value of the data column for this Uri. This is <span id="IL_AD2" class="IL_AD">useful</span> for MediaStore Uris, and other file-based
     * ContentProviders.
     *
     * @param context
     * The context.
     * @param uri
     * The Uri to query.
     * @param selection
     * (Optional) Filter used in the query.
     * @param selectionArgs
     * (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    fun getDataColumn(
        context: Context, uri: Uri?,
        selection: String?, selectionArgs: Array<String>?
    ): String {
        var cursor: Cursor? = null
        val column = "_data"
        val projection = arrayOf(column)
        try {
            cursor = context.contentResolver.query(
                uri!!, projection,
                selection, selectionArgs, null
            )
            if (cursor != null && cursor.moveToFirst()) {
                val index = cursor.getColumnIndexOrThrow(column)
                return cursor.getString(index)
            }
        } finally {
            cursor?.close()
        }
        return nopath
    }
    /**
     * @param uri
     * The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    private fun isExternalStorageDocument(uri: Uri): Boolean {
        return "com.android.externalstorage.documents" == uri
            .authority
    }

    /**
     * @param uri
     * The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    private fun isDownloadsDocument(uri: Uri): Boolean {
        return "com.android.providers.downloads.documents" == uri
            .authority
    }

    /**
     * @param uri
     * The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    private fun isMediaDocument(uri: Uri): Boolean {
        return "com.android.providers.media.documents" == uri
            .authority
    }
    /**
     * @param uri
     * The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    private fun isGooglePhotosUri(uri: Uri): Boolean {
        return "com.google.android.apps.photos.content" == uri
            .authority
    }

    fun imageUriToBitmap(imageUri:Uri,activity: Activity): Bitmap{
        var bitmap: Bitmap? = null
        val contentResolver: ContentResolver = activity.contentResolver
        try {
            bitmap = if (Build.VERSION.SDK_INT < 28) {
                MediaStore.Images.Media.getBitmap(contentResolver, imageUri)
            } else {
                val source: ImageDecoder.Source =
                    ImageDecoder.createSource(contentResolver, imageUri)
                ImageDecoder.decodeBitmap(source)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return  bitmap!!
    }

    fun bitmapToImageUri(context: Context, bitmap: Bitmap): Uri{
        val bytes = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(context.contentResolver, bitmap, "Title", null)
        return Uri.parse(path.toString())
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    fun insertImageForLowAndroidVersions(context: Context, bitmap: Bitmap): Uri{
        val contentValues = ContentValues()
        contentValues.put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/")
        contentValues.put(MediaStore.Images.Media.TITLE, "Title")
        contentValues.put(MediaStore.Images.Media.DISPLAY_NAME, "Title")
        contentValues.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
        contentValues.put(MediaStore.Images.Media.DATE_ADDED, System.currentTimeMillis() / 1000)
        contentValues.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis())
        contentValues.put(MediaStore.Images.Media.IS_PENDING, 1)

        val resolver: ContentResolver = context.contentResolver
        val imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
        val fos: OutputStream? = resolver.openOutputStream(imageUri!!)
        val savedBitmap: Boolean =
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos)
        fos?.flush()
        fos?.close()

        contentValues.clear()
        contentValues.put(MediaStore.Audio.Media.IS_PENDING, 0)
        resolver.update(imageUri, contentValues, null, null)
        return imageUri
    }

    fun downloadImageFile(url: String, context: Context): Long {
        val dir = File(context.getExternalFilesDir(null), filePath)
//        val fileName = "$filePath${DateUtils.convertSystemTimeToCurrentDate()}.${Bitmap.CompressFormat.JPEG}"

        if (!dir.exists()) {
            dir.mkdirs()
        }

        val mgr = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager

        val downloadUri = Uri.parse(url)
        val request = DownloadManager.Request(
            downloadUri
        )

        request.setAllowedNetworkTypes(
            DownloadManager.Request.NETWORK_WIFI or
                    DownloadManager.Request.NETWORK_MOBILE
        )
            .setAllowedOverRoaming(false).setTitle("AkoneyaZay") //Download Manager Title
            .setDescription("Downloading...") //Download Manager description
//            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_ONLY_COMPLETION)
            .setDestinationInExternalPublicDir(
                Environment.DIRECTORY_PICTURES,
                "$filePath${System.currentTimeMillis()}.${Bitmap.CompressFormat.JPEG}" //Your User define(Non Standard Directory name)/File Name
            )

        return mgr.enqueue(request)
    }

}