package com.light.national_geographic.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
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
    private var mContext:Context?=null
    init {
        mContext=context
    }

    fun setData(data:Detail){
        mData=data
        notifyDataSetChanged()
    }


    fun removeItem(position: Int){
        if (mData!=null){
            mData!!.picture!!.removeAt(position)
        }
        notifyDataSetChanged()
    }


    override fun onBindViewHolder(holder: CollectionViewHolder?, position: Int) {

    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): CollectionViewHolder {
    }

    override fun getItemCount(): Int {
        if (mData!=null ){
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
}