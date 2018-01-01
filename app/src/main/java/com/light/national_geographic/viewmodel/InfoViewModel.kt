package com.light.national_geographic.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.Context
import com.light.national_geographic.utils.GlideCacheUtil

/**
 * 创建日期：2017/12/21 on 22:35
 * @author ludaguang
 * @version 1.0
 * 类说明：缓存
 */
class InfoViewModel :ViewModel() {
    var mImageCache : MutableLiveData<String> = MutableLiveData<String>()
    fun getImageCache(context: Context):MutableLiveData<String>{
        mImageCache.value = GlideCacheUtil().getInstance().getCacheSize(context)
        return mImageCache
    }


}