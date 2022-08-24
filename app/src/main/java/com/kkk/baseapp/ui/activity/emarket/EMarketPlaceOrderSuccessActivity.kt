package com.kkk.baseapp.ui.activity.emarket

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.kkk.baseapp.databinding.ActivityEmarketPlaceOrderSuccessBinding
import com.kkk.baseapp.network.networkresponse.emarket.EMarketShopProductListResponse
import com.kkk.mylibrary.ui.activity.BaseViewBindingActivity

class EMarketPlaceOrderSuccessActivity : BaseViewBindingActivity<ActivityEmarketPlaceOrderSuccessBinding>() {

    companion object{
        fun newIntent(context: Context): Intent {
            val intent = Intent(context,EMarketPlaceOrderSuccessActivity::class.java)
            return intent
        }
    }

    override val bindingInflater: (LayoutInflater) -> ActivityEmarketPlaceOrderSuccessBinding
        get() = ActivityEmarketPlaceOrderSuccessBinding::inflate

    override fun setup() {
    }

    override fun observers() {
    }

    override fun listeners() {
        binding.btnDone.setOnClickListener {
            finish()
            val intent = EMarketHomeActivity.newIntent(this)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
        }
    }

}