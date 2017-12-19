package com.light.national_geographic.ui.view

import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.MotionEvent

/**
 * 创建日期：2017/12/19 on 23:15
 * @author ludaguang
 * @version 1.0
 * 类说明：
 */
class HackyViewPager:ViewPager {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        return super.onInterceptTouchEvent(ev)
        try {
            return super.onInterceptTouchEvent(ev)
        }catch (e:IllegalArgumentException){
            e.printStackTrace()
            return false
        }
    }
}