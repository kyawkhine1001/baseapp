package com.kkk.baseapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kkk.sharedbase.utils.SharedUtils

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        SharedUtils.showToast("This is first toast message",this)
    }
}