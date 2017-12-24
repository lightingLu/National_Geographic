package com.light.national_geographic.ui.adapter

import android.support.v4.view.PagerAdapter
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.bumptech.glide.Glide
import com.github.chrisbanes.photoview.PhotoView
import com.light.national_geographic.data.model.Detail

/**
 * 创建日期：2017/12/20 on 23:59
 * @author ludaguang
 * @version 1.0
 * 类说明：
 */
class DetailPagerAdapter(): PagerAdapter() {
    private var mData: Detail? = null
    private var mOnPageClickListener: OnPageClickListener? = null

    fun setData(data: Detail) {
        mData = data
        notifyDataSetChanged()
    }


    override fun instantiateItem(container: ViewGroup?, position: Int): Any {
        val photoView = PhotoView(container?.getContext())
        Glide.with(container?.getContext())
                .load(mData?.picture!!.get(position)
                        .url).into(photoView)

        var params: LinearLayout.LayoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
        container?.addView(photoView, params)
        photoView.setOnClickListener(View.OnClickListener {
            if (mOnPageClickListener != null) {
                mOnPageClickListener!!.onClick()
            }
        })
        return photoView
    }

    override fun getCount(): Int {
        if (mData != null) {
            return mData?.picture!!.size
        }
        return 0
    }

    override fun destroyItem(container: ViewGroup?, position: Int, `object`: Any?) {
        container!!.removeView(`object` as View)
    }

    override fun isViewFromObject(view: View?, `object`: Any?): Boolean {
        return view == `object`
    }

    public interface OnPageClickListener {
        fun onClick()
    }

    public fun setonPageClickListener(listener: OnPageClickListener) {
        mOnPageClickListener = listener
    }


}