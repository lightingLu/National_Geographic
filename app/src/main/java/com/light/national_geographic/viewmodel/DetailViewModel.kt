package com.light.national_geographic.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.light.national_geographic.data.Resource
import com.light.national_geographic.data.WebRepository
import com.light.national_geographic.data.model.Detail

/**
 * 创建日期：2017/12/18 on 22:23
 * @author ludaguang
 * @version 1.0
 * 类说明：
 */
class DetailViewModel():ViewModel() {
    private var mWebRes: WebRepository = WebRepository()

    constructor(repository: WebRepository) : this() {
        mWebRes = repository
    }
fun getDetail(id:String):MutableLiveData<Resource<Detail?>>?{
    return mWebRes?.getDetail(id)

}

}