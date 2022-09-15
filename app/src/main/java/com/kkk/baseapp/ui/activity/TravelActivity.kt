package com.kkk.baseapp.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.recyclerview.widget.GridLayoutManager
import com.aemerse.slider.model.CarouselItem
import com.kkk.baseapp.R
import com.kkk.baseapp.databinding.ActivityTravelBinding
import com.kkk.baseapp.ui.adapter.displayer.CarouselDisplayer
import com.kkk.baseapp.ui.adapter.displayer.TitleDisplayer
import com.kkk.baseapp.viewmodel.MainViewModel
import com.kkk.mylibrary.ui.activity.BaseActivity
import com.kkk.mylibrary.ui.activity.BaseViewBindingActivity
import com.kkk.mylibrary.ui.adapter.DelegateAdapter
import com.kkk.mylibrary.ui.adapter.displayer.ItemDisplayer
import org.koin.androidx.viewmodel.ext.android.viewModel

class TravelActivity: BaseViewBindingActivity<ActivityTravelBinding>() {

    //    private val mViewModel: MainViewModel by lazy {
//        ViewModelProviders.of(this, MainViewModelFactory(Injection.provideMainRepository(this),
//            AndroidSchedulerProvider()
//        ))
//            .get(MainViewModel::class.java)
//    }
    private val mViewModel: MainViewModel by viewModel()

    private var mItemList = mutableListOf<ItemDisplayer>()
    private val mAdapter: DelegateAdapter = DelegateAdapter()

    companion object{
        private const val IE_NAME = "IE_NAME"
        fun newIntent(context: Context, name:String): Intent {
            val intent = Intent(context,TravelActivity::class.java)
            intent.putExtra(IE_NAME,name)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = intent.getStringExtra(IE_NAME)
        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(context, 1)
            adapter = mAdapter
        }
//        mItemList.add(MovieFavouriteItemDisplayer(it.mappingToPopular()){ movie->
//                onClickItem(movie)
//        })
        mItemList.add(TitleDisplayer("Let's go!"))
        mItemList.add(CarouselDisplayer(listOf("Testing 1","Testing 2","Testing 3"),::onClickItem,lifecycle))
        mAdapter.setData(mItemList)

        binding.carousel.registerLifecycle(lifecycle)

        val list = mutableListOf<CarouselItem>()

        list.add(
            CarouselItem(
                imageDrawable = android.R.drawable.alert_dark_frame
            )
        )
        list.add(
                CarouselItem(
                    imageDrawable = android.R.drawable.ic_menu_day
                )
                )
        binding.carousel.setData(list)
    }

    private fun onClickItem(data: String){

    }

    override val bindingInflater: (LayoutInflater) -> ActivityTravelBinding
        get() = ActivityTravelBinding::inflate

    override fun setup() {
    }

    override fun observers() {
    }

    override fun listeners() {
    }

}