package com.light.national_geographic.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.light.national_geographic.R
import com.light.national_geographic.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActiviy<ActivityMainBinding>() {
    var clickTime: Long = 0
    override fun initView(savedInstanceState: Bundle?) {


        title_main.setOnClickListener(View.OnClickListener {
            if (System.currentTimeMillis() - clickTime >= 2000) {
                Toast.makeText(this, "再按一次回到顶部", Toast.LENGTH_LONG).show()
                clickTime=System.currentTimeMillis()
            }else{
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
