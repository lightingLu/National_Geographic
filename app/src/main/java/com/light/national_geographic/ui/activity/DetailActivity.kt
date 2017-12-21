package com.light.national_geographic.ui.activity

import android.graphics.Picture
import android.os.Bundle
import com.light.national_geographic.R
import com.light.national_geographic.data.Collects
import com.light.national_geographic.databinding.ActivityDetailBinding

class DetailActivity : BaseActiviy<ActivityDetailBinding>() {
    var picturesList: List<Picture>? = null
    var mCurrentPosition: Int = 0

    override val layoutId: Int
        get() = R.layout.activity_detail

    override fun initView(savedInstanceState: Bundle?) {
       Collects.getCollect(this)

    }

}
