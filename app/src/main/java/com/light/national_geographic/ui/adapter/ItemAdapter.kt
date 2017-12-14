package com.light.national_geographic.ui.adapter

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.light.national_geographic.R
import com.light.national_geographic.data.Album
import com.light.national_geographic.databinding.ItemImgBinding
import com.light.national_geographic.databinding.ViewEmptyBinding
import com.light.national_geographic.databinding.ViewRecyclerviewLoadingBinding

/**
 * 创建日期：2017/12/14 on 22:30
 * @author ludaguang
 * @version 1.0
 * 类说明：
 */
class ItemAdapter(context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val TYPE_ITEM = 1
    private val TYPE_FOOTER = 2
    //空的layout是为了防止getItemCount()+1后没有数据的时候返回一个空的itemView
    private val TYPE_EMPTY = 3
    private var mContext: Context? = null
    var mDataList: MutableList<Album> = ArrayList<Album>()

    init {
        mContext = context
    }

    fun getRealCount(): Int {
        return mDataList.size
    }

    fun setData(data: MutableList<Album>) {
        mDataList = data
        notifyDataSetChanged()
    }

    fun addData(data: MutableList<Album>) {
        mDataList.addAll(data)
        notifyDataSetChanged()
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        if (holder is ItemViewHolder) {
            if (mDataList.size != 0) {
                holder.binding!!.title = mDataList?.get(position)?.title
                Glide.with(mContext).load(mDataList?.get(position)?.url).crossFade().into(holder.binding?.imgItem)
                holder.binding!!.itemDaily.setOnClickListener { view ->
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener!!.onClick(this, position, view, holder, mDataList)
                    }
                }
            }
        }
    }

    private var mOnItemClickListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onClick(adapter: ItemAdapter, position: Int, view: View, itemViewHolder: ItemViewHolder, data: MutableList<Album>)
    }

    fun setOnclickListener(onItemClickListener: OnItemClickListener) {
        mOnItemClickListener = onItemClickListener
    }

    override fun getItemCount(): Int {
        return mDataList.size + 1
    }


    override fun getItemViewType(position: Int): Int {
        if (mDataList!!.size > 0) {
            if (position + 1 == itemCount) {
                return TYPE_FOOTER
            } else if (mDataList!!.size > 0) {
                return TYPE_ITEM
            }
        }
        return TYPE_EMPTY
    }

    var footerViewHolder: FootViewHolder? = null
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == TYPE_ITEM) {
            val binding = DataBindingUtil.inflate<ItemImgBinding>(LayoutInflater.from(parent?.context), R.layout.item_img, parent, false)
            return ItemViewHolder(binding)
        } else if (viewType == TYPE_FOOTER) {
            val binding = DataBindingUtil.inflate<ViewRecyclerviewLoadingBinding>(LayoutInflater.from(parent?.context), R.layout.view_recyclerview_loading, parent, false)
            footerViewHolder = FootViewHolder(binding)
            return FootViewHolder(binding)
        }
        val binding = DataBindingUtil.inflate<ViewEmptyBinding>(LayoutInflater.from(parent?.context), R.layout.view_empty, parent, false)
        return EmptyViewHolder(binding)
    }

    class ItemViewHolder(binding: ItemImgBinding) : RecyclerView.ViewHolder(binding.root) {
        var binding: ItemImgBinding? = null

        init {
            this.binding = binding
        }
    }

    class FootViewHolder(binding: ViewRecyclerviewLoadingBinding) : RecyclerView.ViewHolder(binding.root) {
        var binding: ViewRecyclerviewLoadingBinding? = null

        init {
            this.binding = binding
        }
    }

    class EmptyViewHolder(binding: ViewEmptyBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    //网络问题重新加载时点击回调
    private var mOnReloadClickListener: OnReloadClickListener? = null

    interface OnReloadClickListener {
        fun onClick()
    }

    fun setOnReloadClickListener(onReloadClickListener: OnReloadClickListener) {
        mOnReloadClickListener = onReloadClickListener
    }

    fun setIsLoading() {
        footerViewHolder?.binding?.textLoading?.setText("正在加载更多...")
        footerViewHolder?.binding?.progressLoading?.setVisibility(View.INVISIBLE)
    }

    fun setOnNoLoadMore() {
        footerViewHolder?.binding?.textLoading?.setText("没有更多了")
        footerViewHolder?.binding?.progressLoading?.setVisibility(View.GONE)
    }

    fun setNetError() {
        footerViewHolder?.binding?.textLoading?.setText("加载失败，点击重试")
        footerViewHolder?.binding?.viewLoading?.setOnClickListener(View.OnClickListener {
            if (mOnReloadClickListener != null) {
                mOnReloadClickListener!!.onClick()
            }
        })

        footerViewHolder?.binding?.progressLoading?.setVisibility(View.GONE)
    }


}