package com.kkk.mylibrary.ui.activity

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.view.LayoutInflater
import android.view.View
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.kkk.mylibrary.R
import com.kkk.mylibrary.databinding.ActivityViewPhotoDetailBinding
import com.kkk.mylibrary.ui.adapter.DelegateAdapter
import com.kkk.mylibrary.ui.adapter.displayer.ItemDisplayer
import com.kkk.mylibrary.ui.adapter.displayer.ViewPhotoDetailImageDisplayer
import com.kkk.mylibrary.ui.other.CustomBottomSheet
import com.kkk.mylibrary.utils.ImageUtil
import com.kkk.mylibrary.utils.PermissionUtils
import com.kkk.mylibrary.utils.TakePhotoOptionList
import com.kkk.mylibrary.utils.constants.RequestConstants
import com.kkk.mylibrary.utils.extensions.showToast
import com.kkk.mylibrary.utils.listeners.OnSnapPositionChangeListener
import com.kkk.mylibrary.utils.listeners.RequestPermissionCallback

class ViewPhotoDetailActivity :   BaseViewBindingActivity<ActivityViewPhotoDetailBinding>(), View.OnClickListener,
    OnSnapPositionChangeListener, RequestPermissionCallback {
    private var images:ArrayList<String> = ArrayList()
    private var photoListAdapter: DelegateAdapter = DelegateAdapter()
    private val photoOptionList by lazy { TakePhotoOptionList.getSaveImageData() }
    private val photoBottomSheetFragment by lazy {
        CustomBottomSheet(
            photoOptionList,
            ::onClickSaveButton
        )
    }
    var snapPosition = 0
    var downloadId:Long = 0

    companion object {
        private const val IE_IMAGE_LIST = "IE_IMAGE_LIST"
        private const val IE_CURRENT_POSITION = "IE_CURRENT_POSITION"
        fun newIntent(
            context: Context,
            data: java.util.ArrayList<String>,
            position: Int? = 0,
        ): Intent {
            val intent = Intent(context, ViewPhotoDetailActivity::class.java)
            intent.putStringArrayListExtra(IE_IMAGE_LIST,data)
            intent.putExtra(IE_CURRENT_POSITION,position)
            return intent
        }
    }

    override val bindingInflater: (LayoutInflater) -> ActivityViewPhotoDetailBinding
        get() = ActivityViewPhotoDetailBinding::inflate

    override fun setup() {
        setUpRecyclerView()
        prepareData()
        registerReceiver(onDownloadComplete,IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))
    }

    override fun observers() {

    }

    private val onDownloadComplete: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(
            context: Context?,
            intent: Intent
        ) {
            //Fetching the download id received with the broadcast
            val id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
            val status = intent.getIntExtra(DownloadManager.COLUMN_STATUS, -1)
            //Checking if the received broadcast is for our enqueued download by matching download id
            if (downloadId == id) { // && status == DownloadManager.STATUS_SUCCESSFUL
                showToast(getString(R.string.txt_download_completed))
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(onDownloadComplete)
    }

    private fun setUpRecyclerView() {
        //Image Carousel
        val controller =
            AnimationUtils.loadLayoutAnimation(binding.root.context, R.anim.list_tem_animation)
        val linerLayoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        linerLayoutManager.isItemPrefetchEnabled = true
        linerLayoutManager.isSmoothScrollbarEnabled = true

        binding.rvPhotoDetail.apply {
            layoutManager = linerLayoutManager
            adapter = photoListAdapter
            layoutAnimation = controller
        }

        val snapHelper: LinearSnapHelper = object : LinearSnapHelper() {
            override fun findTargetSnapPosition(
                layoutManager: RecyclerView.LayoutManager,
                velocityX: Int,
                velocityY: Int
            ): Int {
                val centerView = findSnapView(layoutManager)
                    ?: return RecyclerView.NO_POSITION
                val position = layoutManager.getPosition(centerView)
                var targetPosition = -1
                if (layoutManager.canScrollHorizontally()) {
                    targetPosition = if (velocityX < 0) {
                        position - 1
                    } else {
                        position + 1
                    }
                }
                if (layoutManager.canScrollVertically()) {
                    targetPosition = if (velocityY < 0) {
                        position - 1
                    } else {
                        position + 1
                    }
                }
                val firstItem = 0
                val lastItem = layoutManager.itemCount - 1
                targetPosition = lastItem.coerceAtMost(targetPosition.coerceAtLeast(firstItem))
                changePhotoCount(targetPosition)
                return targetPosition
            }
        }
        snapHelper.attachToRecyclerView(binding.rvPhotoDetail)

    }

    override fun listeners() {
        binding.ibMore.setOnClickListener(this)
        binding.ibClose.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.ib_more -> {
                if (photoOptionList.isNotEmpty()) {
                    showBottomSheet()
                }
            }
            R.id.ib_close -> finish()
        }
    }

    private fun changePhotoCount(position: Int) {
        if (position > -1) {
            snapPosition = position
            binding.tvPhotoCount.text =
                getString(
                    R.string.txt_photo_position_detail,
                    position + 1,
                    photoListAdapter.getCount()
                )
        }
    }

    private fun prepareData() {
        val imageDisplayerList = mutableListOf<ItemDisplayer>()
        intent.getStringArrayListExtra(IE_IMAGE_LIST)?.let {
            images = it
        }
        images.mapIndexed { index, image ->
            imageDisplayerList.add(ViewPhotoDetailImageDisplayer(index, image))
        }
        photoListAdapter.setData(imageDisplayerList)
        intent.getIntExtra(IE_CURRENT_POSITION, -1).let {
            changePhotoCount(it)
            binding.rvPhotoDetail.scrollToPosition(it)
        }
    }

    private fun showBottomSheet() {
        photoBottomSheetFragment.allowEnterTransitionOverlap = false
        photoBottomSheetFragment.show(
            supportFragmentManager,
            photoBottomSheetFragment.tag
        )
    }

    override fun onSnapPositionChange(position: Int) {
        changePhotoCount(position)
    }

    override fun onPermissionResult(requestCode: Int) {
        when (requestCode) {
            RequestConstants.MY_READ_PERMISSION_REQUEST_CODE -> {
                downloadId = ImageUtil.downloadImageFile(
                    images[snapPosition],
                    this@ViewPhotoDetailActivity
                )
            }
            else -> showToast(getString(R.string.txt_permission_already_denied))
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permission: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permission, grantResults)
        if (PermissionUtils.checkPermissionGranted(grantResults)) {
            when (requestCode) {
                RequestConstants.MY_READ_PERMISSION_REQUEST_CODE -> {
                    downloadId = ImageUtil.downloadImageFile(
                        images[snapPosition],
                        this@ViewPhotoDetailActivity
                    )
                }
                else -> showToast(getString(R.string.txt_permission_already_denied))
            }
        } else {
            showToast(getString(R.string.txt_permission_already_denied))
        }
    }

    private fun onClickSaveButton(position: Int){
        photoBottomSheetFragment.dismiss()
        PermissionUtils.checkReadWritePermission(
            this@ViewPhotoDetailActivity,
            this@ViewPhotoDetailActivity,
            this@ViewPhotoDetailActivity
        )
    }

}