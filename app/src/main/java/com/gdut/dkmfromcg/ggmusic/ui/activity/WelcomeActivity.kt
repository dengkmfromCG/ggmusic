package com.gdut.dkmfromcg.ggmusic.ui.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.gdut.dkmfromcg.ggmusic.R
import android.content.Intent
import android.os.Handler
import android.support.v4.app.FragmentActivity
import android.util.Log
import android.view.View
import android.view.animation.Animation
import com.bumptech.glide.Glide
import com.gdut.dkmfromcg.ansynclib.service.RequestCallback
import com.gdut.dkmfromcg.ansynclib.service.entity.GankIoDataBean
import com.gdut.dkmfromcg.ansynclib.service.model.GankIoModel
import com.gdut.dkmfromcg.ggmusic.base.BaseActivity
import kotlinx.android.synthetic.main.activity_welcome.*
import org.jetbrains.anko.toast
import rx.Subscription


class WelcomeActivity : BaseActivity() {

    private val TAG="WelcomeActivity"
    private var isIn=false
    private var animationEnd=false

    private lateinit var mContext: FragmentActivity

    private val mGankIoModel:GankIoModel by lazy {
        GankIoModel()
    }

    //实现监听跳转效果
    private val animationListener = object : Animation.AnimationListener {
        override fun onAnimationEnd(animation: Animation) {
            animationEnd()
        }

        override fun onAnimationStart(animation: Animation) {}

        override fun onAnimationRepeat(animation: Animation) {}
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        mContext=this
        PicUrlFactory().showSinglePicUrlPic()


        Handler().postDelayed(Runnable { iv_defult_pic.visibility= View.GONE },1500)

        Handler().postDelayed(Runnable { toHomeActivity() },3500)

        tv_jump.setOnClickListener {
            toHomeActivity()
        }


    }

    private fun animationEnd() {
        synchronized (HomeActivity::class.java) {
            if (!animationEnd) {
                animationEnd = true
                iv_pic.clearAnimation()
                toHomeActivity()
            }
        }
    }

    private fun toHomeActivity(){
        if (isIn) {
            return
        }
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.screen_zoom_in, R.anim.screen_zoom_out)
        finish()
        isIn = true
    }

    inner class PicUrlFactory{
        fun showSinglePicUrlPic(){
            var picUrl:String=""
            mGankIoModel.setData("福利",1,10)
            mGankIoModel.getGankIoData(object :RequestCallback{

                override fun loadSuccess(`object`: Any?) {

                    val gankIoDataBean :GankIoDataBean ?=`object` as GankIoDataBean

                    if (gankIoDataBean!=null && gankIoDataBean.results!=null
                            && gankIoDataBean.results.size>0){
                        picUrl=gankIoDataBean.results[0].url
                        Log.d(TAG, picUrl)
                        Glide.with(mContext)
                                /*.load("http://ojyz0c8un.bkt.clouddn.com/home_six_1.png")*/
                                .load(picUrl)
                                .placeholder(R.drawable.login_bg)
                                .error(R.drawable.login_bg)
                                .into(iv_pic)
                    }
                }

                override fun loadFailed() {
                    toast("数据没有加载成功")
                }


                override fun addSubscription(subscription: Subscription?) {

                }

            })
        }
    }
}
