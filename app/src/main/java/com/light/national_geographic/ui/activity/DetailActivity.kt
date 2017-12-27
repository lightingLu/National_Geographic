package com.light.national_geographic.ui.activity

import android.os.Bundle
import android.support.v4.view.ViewPager
import com.light.national_geographic.R
import com.light.national_geographic.data.Collects
import com.light.national_geographic.data.model.Detail
import com.light.national_geographic.data.model.Picture
import com.light.national_geographic.databinding.ActivityDetailBinding
import com.light.national_geographic.ui.adapter.DetailPagerAdapter
import kotlinx.android.synthetic.main.activity_detail.*

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
        if(Collects.isCollect(picturesList!!.get(0))){
            getBinding().isConnect=true
        }
        view_pager.adapter=detailPagerAdapter
        view_pager.setCurrentItem(0)

        //收藏activity需要做的

        view_pager.setOnPageChangeListener(object :ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {
                if (picturesList!=null ){
                    getBinding().data=picturesList!!.get(state)
                    getBinding().pos=state+1
                    mCurrentPosition=state
                    getBinding().isConnect=false
                    if (Collects.isCollect(picturesList!!.get(state))) {
                        getBinding().isConnect = true
                    }
                }
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onPageSelected(position: Int) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })

    }

}
