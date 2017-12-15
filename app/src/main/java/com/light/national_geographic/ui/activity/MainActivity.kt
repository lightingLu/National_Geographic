package com.light.national_geographic.ui.activity

import android.app.ProgressDialog
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import com.light.national_geographic.R
import com.light.national_geographic.data.Resource
import com.light.national_geographic.data.model.Album
import com.light.national_geographic.data.model.Item
import com.light.national_geographic.databinding.ActivityMainBinding
import com.light.national_geographic.ui.adapter.ItemAdapter
import com.light.national_geographic.viewmodel.ItemViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActiviy<ActivityMainBinding>(), SwipeRefreshLayout.OnRefreshListener, ItemAdapter.OnItemClickListener {
    override fun onRefresh() {
       swipe_refresh.isRefreshing=true
        mItemViewModel?.getItem(1)?.observe(this,object :Observer<Resource<Item?>?>{
            override fun onChanged(t: Resource<Item?>?) {
                if (t!!.mStatus==SUCCESS){
                    view_error.visibility=View.GONE
                    swipe_refresh.isRefreshing=false
                    adapter?.setData(t!!.mData!!.album)
                }
                if (t!!.mStatus==ERROR){
                   swipe_refresh.isRefreshing=false
                    if (adapter?.getRealCount()==0){
                        view_error.visibility=View.VISIBLE
                        view_error.setOnClickListener(View.OnClickListener {
                            onRefresh()
                        })
                        Toast.makeText(this@MainActivity, " 加载失败，请检查网络重试", Toast.LENGTH_SHORT).show()
                    }
                }
            }

        })

    }

    override fun onClick(adapter: ItemAdapter, position: Int, view: View, itemViewHolder: ItemAdapter.ItemViewHolder, data: MutableList<Album>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    var clickTime: Long = 0
    var adapter: ItemAdapter? = ItemAdapter(this)
    var progressDialog: ProgressDialog? = null
    var mItemViewModel: ItemViewModel? = null
    private val SUCCESS: Int = 1
    private val ERROR: Int = 2

    override fun initView(savedInstanceState: Bundle?) {
        progressDialog = ProgressDialog(this)
        progressDialog?.setMessage("正在加载")
        progressDialog?.setCanceledOnTouchOutside(false)
        recycler.adapter = adapter
        adapter?.setOnclickListener(this)
        val layoutManager: LinearLayoutManager = LinearLayoutManager(this)
        recycler.layoutManager=layoutManager
        mItemViewModel= ViewModelProviders.of(this).get(ItemViewModel::class.java)

        swipe_refresh.setOnRefreshListener(this)

        title_main.setOnClickListener(View.OnClickListener {
            if (System.currentTimeMillis() - clickTime >= 2000) {
                Toast.makeText(this, "再按一次回到顶部", Toast.LENGTH_LONG).show()
                clickTime = System.currentTimeMillis()
            } else {
                recycler.smoothScrollToPosition(0)
            }
        })

        ic_more.setOnClickListener(View.OnClickListener {
            val intent: Intent = Intent(this, InfoActivity::class.java)
            startActivity(intent)
        })

    }

    override val layoutId: Int
        get() = R.layout.activity_main


}
