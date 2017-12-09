package com.light.national_geographic.ui.activity

import android.arch.lifecycle.LifecycleActivity
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.os.PersistableBundle
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

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        val layoutId=layoutId
        super.onCreate(savedInstanceState, persistentState)
        binding= DataBindingUtil.setContentView(this,layoutId)
//        binding?.setVariable(layoutId,null)

        try {
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