package com.light.national_geographic.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.light.national_geographic.data.WebRepository
import com.light.national_geographic.data.model.Item

/**
 * 创建日期：2017/12/15 on 8:39
 * @author ludaguang
 * @version 1.0
 * 类说明：
 */
class ItemViewModel() : ViewModel() {
    private var mWebRepository: WebRepository = WebRepository()
    constructor(repository: WebRepository):this(){
        mWebRepository=repository
    }

    fun getItem(index:Int): MutableLiveData<com.light.national_geographic.data.Resource<Item?>>? {
        return mWebRepository?.getItem(index)
    }

}