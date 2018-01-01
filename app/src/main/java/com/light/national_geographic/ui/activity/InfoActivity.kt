package com.light.national_geographic.ui.activity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.widget.Toast
import com.light.national_geographic.R
import com.light.national_geographic.data.Collects
import com.light.national_geographic.databinding.ActivityInfoBinding
import com.light.national_geographic.utils.GlideCacheUtil
import com.light.national_geographic.viewmodel.InfoViewModel
import kotlinx.android.synthetic.main.activity_info.*

class InfoActivity : BaseActiviy<ActivityInfoBinding>() {
    override val layoutId: Int
        get() = R.layout.activity_info


    var infoViewModel: InfoViewModel? = null

    override fun initView(savedInstanceState: Bundle?) {
        infoViewModel = ViewModelProviders.of(this).get(InfoViewModel::class.java)
        infoViewModel?.getImageCache(this)!!.observe(this, object : Observer<String?> {
            override fun onChanged(t: String?) {
                getBinding().cache = t
            }

        })
        initClick()
        Collects.getCollect(this)
    }

    private fun initClick() {

        info_back.setOnClickListener({
            onBackPressed()
        })


        info_collect.setOnClickListener({
            if (!Collects.getDetail().counttotal.equals("0")) {
                val intent: Intent = Intent(this, CollectionActivity::class.java)
                intent.putExtra("DETAIL", Collects.getDetail())
                startActivity(intent)
            } else {
                Toast.makeText(this, "收藏为空", Toast.LENGTH_SHORT).show()
            }
        })
        info_cache.setOnClickListener({
            val gl: GlideCacheUtil = GlideCacheUtil()
            gl.clearImageAllCache(this)
            getBinding().cache = "0KB"
            Toast.makeText(this, "清除成功", Toast.LENGTH_SHORT).show()
        })

        info_feedback.setOnClickListener({
            val intent: Intent = Intent(this, WebActivity::class.java)
            intent.putExtra("URL", "https://github.com/threelu/National_Geographic")
            startActivity(intent)
        })
        info_checkupdate.setOnClickListener({
            val intent: Intent = Intent(this, AboutActivity::class.java)
            intent.putExtra("URL", "https://github.com/threelu/National_Geographic")
            startActivity(intent)
        })

        info_disclaimer.setOnClickListener({
            val dialog: AlertDialog? = AlertDialog.Builder(this)
                    .setTitle("免责声明")
                    .setMessage("应用使用GPL3.0作为开源许可协议，并且应用中的所有数据以及资源来源于网络，所有内容和商标的版权归原创者或所有方所有，应用仅作学习交流之用，严禁用于商业用途，违反申明所引发的一切问题由使用者承担")
                    .setPositiveButton("确定", null)
                    .show()
        })
        info_share.setOnClickListener({
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_SUBJECT, "分享国家地理")
            intent.putExtra(Intent.EXTRA_TEXT, "https://github.com/threelu/National_Geographic" + " -国家地理")
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(Intent.createChooser(intent, "分享国家地理"))
        })

        info_about.setOnClickListener({
            val intent: Intent = Intent(this, AboutActivity::class.java)
            startActivity(intent)
        })

    }


}
