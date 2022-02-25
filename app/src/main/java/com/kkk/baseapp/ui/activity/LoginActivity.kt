package com.kkk.baseapp.ui.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kkk.baseapp.R
import com.kkk.mylibrary.ui.activity.BaseActivity

class LoginActivity : BaseActivity() {
    override val layoutId: Int
        get() = R.layout.activity_login
    override val progressBarStyle: Int
        get() = com.kkk.mylibrary.R.style.ThemeLoadingDialog


    companion object{
        private const val IE_NAME = "IE_NAME"
        fun newIntent(context: Context, name:String): Intent {
            val intent = Intent(context,LoginActivity::class.java)
            intent.putExtra(IE_NAME,name)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = intent.getStringExtra(IE_NAME)
    }
}