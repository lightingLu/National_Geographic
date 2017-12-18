package com.light.national_geographic.data

import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.light.national_geographic.data.model.Detail
import com.light.national_geographic.data.model.Item
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


/**
 * 创建日期：2017/12/15 on 8:41
 * @author ludaguang
 * @version 1.0
 * 类说明：
 */
class WebRepository {
    var mWebService: WebService? = null
    val url = "http://dili.bdatu.com/jiekou/"
    fun getItem(index: Int): MutableLiveData<Resource<Item?>>? {
        var data: MutableLiveData<Resource<Item?>> = MutableLiveData<Resource<Item?>>()
        val retrofit = Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create()).build()
        mWebService = retrofit.create(WebService::class.java)
        mWebService?.getItem(index)?.enqueue(object : retrofit2.Callback<Item?> {
            override fun onResponse(call: Call<Item?>?, response: Response<Item?>?) {
                data.value = Resource.success(response!!.body())
            }

            override fun onFailure(call: Call<Item?>?, t: Throwable?) {
                Log.d("National_Geographic", "onFailure")
                data.value = Resource.error(t?.message)
            }

        })
        return data
    }


    fun getDetail(id: String): MutableLiveData<Resource<Detail?>>? {
        var data: MutableLiveData<Resource<Detail?>> = MutableLiveData<Resource<Detail?>>()
        var retrofit = Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create()).build()
        mWebService = retrofit.create(WebService::class.java)
        mWebService?.getDetail(id)?.enqueue(object : Callback<Detail?> {
            override fun onFailure(call: Call<Detail?>?, t: Throwable?) {
                data.value = Resource.error(t?.message)

            }

            override fun onResponse(call: Call<Detail?>?, response: Response<Detail?>?) {
                data.value = Resource.success(response?.body())
            }

        })
        return data
    }


}