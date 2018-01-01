package com.light.national_geographic.ui.activity

import android.Manifest
import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.view.animation.DecelerateInterpolator
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.light.national_geographic.R
import com.light.national_geographic.data.Collects
import com.light.national_geographic.data.model.Detail
import com.light.national_geographic.data.model.Picture
import com.light.national_geographic.databinding.ActivityDetailBinding
import com.light.national_geographic.ui.adapter.DetailPagerAdapter
import kotlinx.android.synthetic.main.activity_detail.*
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.lang.Exception

class DetailActivity : BaseActiviy<ActivityDetailBinding>() {
    var picturesList: List<Picture>? = null
    var mCurrentPosition: Int = 0
    private val permissions = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
    private val REQUEST_PERMISSIONS = 1

    override val layoutId: Int
        get() = R.layout.activity_detail

    override fun initView(savedInstanceState: Bundle?) {
        Collects.getCollect(this)
        val detailPagerAdapter = DetailPagerAdapter()
        val data: Detail = this.intent.extras.getSerializable("DETAIL") as Detail;
        detailPagerAdapter.setData(data)
        picturesList = data.picture
        getBinding().saveProgressVisible = false
        getBinding().data = picturesList!!.get(0)
        getBinding().total = data.counttotal
        getBinding().pos = 1
        getBinding().titleAndContentVisible = true
        getBinding().isCollect = false
        if (Collects.isCollect(picturesList!!.get(0))) {
            getBinding().isCollect = true
        }
        view_pager.adapter = detailPagerAdapter
        view_pager.setCurrentItem(0)

        //收藏activity需要做的
        val firstpos = this.intent.extras.getInt("POS")
        if (firstpos != 0) {
            view_pager.currentItem = firstpos
            getBinding().pos = firstpos + 1
            mCurrentPosition = firstpos
        }


        view_pager.setOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                if (picturesList != null) {
                    getBinding().data = picturesList!!.get(position)
                    getBinding().pos = position + 1
                    mCurrentPosition = position
                    getBinding().isCollect = false
                    if (Collects.isCollect(picturesList!!.get(position))) {
                        getBinding().isCollect = true
                    }
                }
            }
        })

        detailPagerAdapter.setonPageClickListener(object : DetailPagerAdapter.OnPageClickListener {
            override fun onClick() {

                if (getBinding().titleAndContentVisible == true) {
                    val animatorSet = AnimatorSet()
                    val scaleX = ObjectAnimator.ofFloat(tool_bar, "alpha", 0f)
                    val scaleY = ObjectAnimator.ofFloat(ll_des, "alpha", 0f)
                    animatorSet.duration = 600
                    animatorSet.interpolator = DecelerateInterpolator()
                    animatorSet.play(scaleX).with(scaleY)
                    animatorSet.start()
                    animatorSet.addListener(object : Animator.AnimatorListener {
                        override fun onAnimationRepeat(animation: Animator?) {
                        }

                        override fun onAnimationEnd(animation: Animator?) {
                            getBinding().titleAndContentVisible = false

                        }

                        override fun onAnimationCancel(animation: Animator?) {
                        }

                        override fun onAnimationStart(animation: Animator?) {
                        }
                    })
                } else {
                    tool_bar.animate().alpha(1f).duration = 400
                    ll_des.animate().alpha(1f).duration = 400
                    getBinding().titleAndContentVisible = true
                }

            }

        })
        initClick()
        setResult(Activity.RESULT_OK)
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_PERMISSIONS -> {
                run {
                    if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        savePhoto()
                    }
                }
                return
            }
        }
    }

    private fun initClick() {
        detail_back.setOnClickListener({
            this.finish()
        })
        detail_collect.setOnClickListener({
            if (Collects.isCollect(picturesList!!.get(mCurrentPosition))) {
                Collects.deleteItem(picturesList!!.get(mCurrentPosition).id)
                getBinding().isCollect = false
                Toast.makeText(this, "取消收藏成功", Toast.LENGTH_SHORT).show()
            } else {
                Collects.collectItem(picturesList!!.get(mCurrentPosition))
                getBinding().isCollect = true
                Toast.makeText(this, "收藏成功", Toast.LENGTH_SHORT).show()
            }

        })

        detail_share.setOnClickListener({
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_SUBJECT, "分享")
            intent.putExtra(Intent.EXTRA_TEXT, picturesList?.get(mCurrentPosition)?.url + "-来自国家地理")
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(Intent.createChooser(intent, "分享"))
        })

        detail_save.setOnClickListener({
            checksPermission()
        })

    }

    fun checksPermission() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, permissions, REQUEST_PERMISSIONS)
        } else {
            savePhoto()
        }
    }

    /**
     * 保存图片
     */
    private fun savePhoto() {
        val handler: Handler = Handler()
        getBinding().saveProgressVisible = true
        Glide.with(this).load(picturesList!!.get(mCurrentPosition).url).asBitmap().listener(object : RequestListener<String?, Bitmap?> {
            override fun onException(e: Exception?, model: String?, target: Target<Bitmap?>?, isFirstResource: Boolean): Boolean {
                handler.post({
                    Toast.makeText(this@DetailActivity, "下载失败，请检查网络重试", Toast.LENGTH_SHORT).show()
                    getBinding().saveProgressVisible = false
                })
                return true
            }

            override fun onResourceReady(resource: Bitmap?, model: String?, target: Target<Bitmap?>?, isFromMemoryCache: Boolean, isFirstResource: Boolean): Boolean {
                val fileDirePath: File = File(Environment.getExternalStorageDirectory().path + "/National_geographic/" + "/image/")
                if (!fileDirePath.exists()) {
                    fileDirePath.mkdirs()
                }
                val fileName = picturesList!!.get(mCurrentPosition).id + ".jpeg"
                val filePath = File(fileDirePath, fileName)
                if (filePath.exists()) {
                    handler.post({
                        Toast.makeText(this@DetailActivity, "此照片已保存过", Toast.LENGTH_SHORT).show()
                        getBinding().saveProgressVisible = false
                    })
                } else {
                    try {
                        var fileOutputStream: FileOutputStream = FileOutputStream(filePath)
                        resource?.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream)
                        fileOutputStream.flush()
                        fileOutputStream.close()
                        handler.post({
                            Toast.makeText(this@DetailActivity, "保存成功", Toast.LENGTH_SHORT).show()
                            getBinding().saveProgressVisible = false
                        })
                    } catch (e: IOException) {
                        handler.post({
                            Toast.makeText(this@DetailActivity, "保存失败,请检查网络及权限", Toast.LENGTH_SHORT).show()
                            getBinding().saveProgressVisible = false
                        })
                    }
                    return true;
                }


                return true
            }

        }).into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)

    }

}
