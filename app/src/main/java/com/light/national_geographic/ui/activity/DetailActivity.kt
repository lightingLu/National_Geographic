package com.light.national_geographic.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.light.national_geographic.R

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        val extra = intent.getSerializableExtra("DETAIL")
        if (extra!=null){
            Toast.makeText(this,"获取数据不为空",Toast.LENGTH_SHORT).show()
        }
    }
}
