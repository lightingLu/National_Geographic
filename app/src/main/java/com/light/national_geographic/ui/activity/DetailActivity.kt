package com.light.national_geographic.ui.activity

import android.os.Bundle
import com.light.national_geographic.R
import com.light.national_geographic.data.Collects
import com.light.national_geographic.data.model.Detail
import com.light.national_geographic.data.model.Picture
import com.light.national_geographic.databinding.ActivityDetailBinding
import com.light.national_geographic.ui.adapter.DetailPagerAdapter

class DetailActivity : BaseActiviy<ActivityDetailBinding>() {
    var picturesList: List<Picture>? = null
    var mCurrentPosition: Int = 0

    override val layoutId: Int
        get() = R.layout.activity_detail

    override fun initView(savedInstanceState: Bundle?) {
       Collects.getCollect(this)
        val detailPagerAdapter = DetailPagerAdapter()
        val data: Detail = this.intent.extras.getSerializable("DETAIL") as Detail;
        detailPagerAdapter.setData(data)
        picturesList = data.picture
        getBinding().saveProgressVisible = false
        getBinding().data = picturesList!!.get(0)
        getBinding().total = data.counttotal
        getBinding().pos = 1
        getBinding().titleAndContentVisible = true
        getBinding().isConnect = false
    }

}
