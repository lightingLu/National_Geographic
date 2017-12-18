package com.light.national_geographic.data

import java.io.Serializable

/**
 * 创建日期：2017/12/15 on 9:01
 * @author ludaguang
 * @version 1.0
 * 类说明：
 */
class Resource<T>() :Serializable{

    var mData: T? = null
    var mStatus: Int? = null
    var mMessage: String? = null

    private constructor(status: Int, data: T?, message: String?) : this() {
        this.mData = data
        this.mStatus = status
        this.mMessage = mMessage
    }


    companion object {
        private val SUCCESS: Int = 1
        private val ERROR: Int = 2
        private val LOADING: Int = 3

        fun <T> success(data: T): Resource<T>? {
            return Resource(SUCCESS, data, null)
        }

        fun <T> error(msg: String?): Resource<T> {
            return Resource(ERROR, null, msg)
        }

        fun <T> loading(data: T): Resource<T> {
            return Resource(LOADING, data, null)
        }
    }
}