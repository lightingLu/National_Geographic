package com.light.national_geographic.ui.adapter

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.light.national_geographic.R
import com.light.national_geographic.data.model.Detail
import com.light.national_geographic.databinding.ItemCollectionBinding

/**
 * 创建日期：2017/12/20 on 23:59
 * @author ludaguang
 * @version 1.0
 * 类说明：
 */
class CollectionAdapter(context: Context) : RecyclerView.Adapter<CollectionAdapter.CollectionViewHolder>() {
    var mData: Detail? = null
    private var mContext: Context? = null

    init {
        mContext = context
    }

    fun setData(data: Detail) {
        mData = data
        notifyDataSetChanged()
    }


    fun removeItem(position: Int) {
        if (mData != null) {
            mData!!.picture!!.removeAt(position)
        }
        notifyDataSetChanged()
    }


    override fun onBindViewHolder(holder: CollectionViewHolder?, position: Int) {
        Glide.with(mContext).load(mData?.picture?.get(position)?.url).into(holder?.binding?.imgCollection)
        holder?.binding?.imgCollection?.setOnClickListener({view ->
            if (mOnItemClickLisener!=null){
                mOnItemClickLisener!!.onItemClick(this,position,view,holder!!,mData!!)
            }
        })
        holder?.binding?.collect?.setOnClickListener({view ->
            if (mOnItemClickLisener!=null){
                mOnItemClickLisener!!.onItemClick(this,position,holder!!.binding!!.collect,holder!!,mData!!)
            }
        })

    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): CollectionViewHolder {
        val binding=DataBindingUtil.inflate<ItemCollectionBinding>(LayoutInflater.from(parent?.context), R.layout.item_collection,parent,false)
        return  CollectionAdapter.CollectionViewHolder(binding)
    }

    override fun getItemCount(): Int {
        if (mData != null) {
            return mData!!.picture!!.size
        }
        return 0
    }

    class CollectionViewHolder(binding: ItemCollectionBinding) : RecyclerView.ViewHolder(binding.root) {

        var binding: ItemCollectionBinding? = null

        init {
            this.binding = binding
        }



    }

    private var mOnItemClickLisener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(adapter: CollectionAdapter, position: Int, view: View, collectionViewHolder: CollectionViewHolder, data: Detail)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        mOnItemClickLisener = listener;
    }

    private var mOnCollectiongClickLisener: OnCollectionClickListener? = null

    interface OnCollectionClickListener {
        fun onItemClick(adapter: CollectionAdapter, position: Int, view: View, collectionAdapter: CollectionAdapter, data: Detail)
    }

    fun setOnCollectionClickListener(listener: OnCollectionClickListener) {
        mOnCollectiongClickLisener = listener;
    }
}