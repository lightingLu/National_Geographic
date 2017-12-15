package com.light.national_geographic.data

import com.light.national_geographic.data.model.Detail
import com.light.national_geographic.data.model.Item
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * 创建日期：2017/12/15 on 8:47
 * @author ludaguang
 * @version 1.0
 * 类说明：
 */
interface WebService {
    //获取图片列表
    @GET("main/p{index}.html")
    fun getItem(@Path("index") index: Int): Call<Item>

    //获取图片详细信息
    @GET("albums/a{id}.html")
    fun getDetail(@Path("id") id: String): Call<Detail>

}