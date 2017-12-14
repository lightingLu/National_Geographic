package com.light.national_geographic.ui.activity

import android.arch.lifecycle.LifecycleActivity
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View

/**
 * 创建日期：2017/12/9 on 16:22
 * @author ludaguang
 * @version 1.0
 * 类说明：基础类
 */
abstract class BaseActiviy<T:ViewDataBinding> : LifecycleActivity() {
    private var mainView:View?=null
    private var binding:ViewDataBinding?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        val layoutId=layoutId
        super.onCreate(savedInstanceState)
        try {
            binding= DataBindingUtil.setContentView(this,layoutId)
            if (binding!=null){
                mainView=binding!!.root
            }else{
                mainView=LayoutInflater.from(this).inflate(layoutId,null)
                setContentView(mainView)
            }
        }catch (e:NoClassDefFoundError){
            mainView=LayoutInflater.from(this).inflate(layoutId,null)
            setContentView(mainView)
        }
        initView(savedInstanceState)

    }
    abstract val layoutId:Int
    abstract fun initView(savedInstanceState: Bundle?)
    fun getBinding():T{
        return binding as T
    }
}