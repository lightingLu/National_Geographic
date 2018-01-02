package com.light.national_geographic.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.light.national_geographic.R
import com.light.national_geographic.data.Collects
import com.light.national_geographic.data.model.Detail
import com.light.national_geographic.databinding.ActivityDetailBinding
import com.light.national_geographic.ui.adapter.CollectionAdapter
import kotlinx.android.synthetic.main.activity_collection.*

class CollectionActivity : BaseActiviy<ActivityDetailBinding>(), CollectionAdapter.OnItemClickListener, CollectionAdapter.OnCollectionClickListener {
    var mAdapter: CollectionAdapter = CollectionAdapter(this)
    var mData: Detail? = null
    private var REQUEST_INFO = 100

    override fun onItemClick(adapter: CollectionAdapter, position: Int, view: View, collectionViewHolder: CollectionAdapter.CollectionViewHolder, data: Detail) {
        var intent: Intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("DETAIL", Collects.getDetail())
        intent.putExtra("POS", position)
        startActivity(intent)
    }

    override fun onItemClick(adapter: CollectionAdapter, position: Int, view: View, collectionAdapter: CollectionAdapter, data: Detail) {
        Collects.deleteItem(data.picture?.get(position)?.id)
        mAdapter.setData(Collects.getDetail())
        if (mAdapter.itemCount == 0) {
            finish()
        }
    }

    override val layoutId: Int
        get() = R.layout.activity_collection

    override fun initView(savedInstanceState: Bundle?) {
        mData = this.intent.extras.getSerializable("DETAIL") as Detail
        if (mData != null) {
            mAdapter.setData(mData!!)
        }
        recycler_collection.adapter = mAdapter
        recycler_collection.layoutManager = LinearLayoutManager(this)
        mAdapter.setOnCollectionClickListener(this)
        mAdapter.setOnItemClickListener(this)
        back_collection.setOnClickListener({
            onBackPressed()
        })

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (REQUEST_INFO == requestCode && resultCode == Activity.RESULT_OK) {
            mAdapter.setData(Collects.getDetail())
        }
        if (mAdapter.itemCount == 0) {
            finish()
        }
    }


}
