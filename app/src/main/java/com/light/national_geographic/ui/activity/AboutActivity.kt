package com.light.national_geographic.ui.activity

import android.os.Bundle
import com.light.national_geographic.R
import com.light.national_geographic.databinding.ActivityAboutBinding
import kotlinx.android.synthetic.main.activity_about.*

class AboutActivity : BaseActiviy<ActivityAboutBinding>() {
    override val layoutId: Int
        get() = R.layout.activity_about

    override fun initView(savedInstanceState: Bundle?) {
        about_back.setOnClickListener({
            onBackPressed()
        })
    }
}
