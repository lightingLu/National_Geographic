package com.light.national_geographic.ui.activity

import android.animation.Animator
import android.animation.ObjectAnimator
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.view.animation.DecelerateInterpolator
import com.light.national_geographic.R
import com.light.national_geographic.databinding.ActivitySplashBinding
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : BaseActiviy<ActivitySplashBinding>() {
    override val layoutId: Int
        get() = R.layout.activity_splash

    override fun initView(savedInstanceState: Bundle?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
            window.statusBarColor = Color.TRANSPARENT
        }
        var anim = ObjectAnimator.ofFloat(fl_splash, "alpha", 1f, 0f)
        anim.duration = 3000
        anim.interpolator = DecelerateInterpolator()
        anim.startDelay = 1000
        anim.start()
        anim.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator?) {
            }

            override fun onAnimationRepeat(animation: Animator?) {
            }

            override fun onAnimationEnd(animation: Animator?) {
                val intent:Intent= Intent(this@SplashActivity,MainActivity::class.java)
                startActivity(intent)
                this@SplashActivity.finish()
            }

            override fun onAnimationCancel(animation: Animator?) {
            }
        })

    }

}
