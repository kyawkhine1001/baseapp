package com.kkk.baseapp.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.recyclerview.widget.GridLayoutManager
import com.kkk.baseapp.R
import com.kkk.baseapp.databinding.ActivityMainBinding
import com.kkk.baseapp.ui.activity.emarket.EMarketHomeActivity
import com.kkk.baseapp.ui.adapter.PageTypeAdapter
import com.kkk.mylibrary.ui.activity.BaseActivity
import com.kkk.mylibrary.ui.activity.BaseViewBindingActivity
import com.kkk.mylibrary.utils.SharedUtils

class MainActivity : BaseViewBindingActivity<ActivityMainBinding>() {

    private val pageTypeAdapter:PageTypeAdapter by lazy { PageTypeAdapter{
        when(it){
            PageTitle.MOVIE_REVIEW.title-> {
                val intent = MovieReviewActivity.newIntent(this,it)
                startActivity(intent)
            }
            PageTitle.LOGIN.title-> {
                val intent = LoginActivity.newIntent(this,it)
                startActivity(intent)
            }
            PageTitle.REGISTER.title-> {
                val intent = RegisterActivity.newIntent(this,it)
                startActivity(intent)
            }
            PageTitle.TRAVEL.title-> {
                val intent = TravelActivity.newIntent(this,it)
                startActivity(intent)
            }
            PageTitle.E_MARKET.title-> {
                val intent = EMarketHomeActivity.newIntent(this)
                startActivity(intent)
            }
        }
    } }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SharedUtils.showToast("This is first toast message",this)
        pageTypeAdapter.addData(PageTitle.MOVIE_REVIEW.title)
        pageTypeAdapter.addData(PageTitle.LOGIN.title)
        pageTypeAdapter.addData(PageTitle.REGISTER.title)
        pageTypeAdapter.addData(PageTitle.TRAVEL.title)
        pageTypeAdapter.addData(PageTitle.E_MARKET.title)
        binding.recyclerViewPageType.apply {
            layoutManager = GridLayoutManager(applicationContext, 1)
            adapter = pageTypeAdapter
        }
    }

    override val bindingInflater: (LayoutInflater) -> ActivityMainBinding
        get() = ActivityMainBinding::inflate

    override fun setup() {
    }

    override fun observers() {
    }

    override fun listeners() {
    }
}
enum class PageTitle(val title:String){
    MOVIE_REVIEW("Movie Review"),
    LOGIN("Login"),
    REGISTER("Register"),
    TRAVEL("Travel"),
    E_MARKET("E-Market"),
}