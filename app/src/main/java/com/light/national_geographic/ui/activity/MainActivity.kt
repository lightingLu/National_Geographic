package com.light.national_geographic.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.light.national_geographic.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        DataBindingUtil.setContentView(this,R.layout.activity_main)
    }

}