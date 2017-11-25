package com.gdut.dkmfromcg.ggmusic.ui.fragment


import android.animation.ObjectAnimator
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.animation.LinearInterpolator
import android.widget.FrameLayout

import com.gdut.dkmfromcg.ggmusic.R
import android.animation.ValueAnimator
import com.gdut.dkmfromcg.ggmusic.util.MyAnimatorUpdateListener


/**
 * A simple [Fragment] subclass.
 */
class RoundPicFragment : Fragment() {

    private lateinit var animatorUpdateListener:MyAnimatorUpdateListener
    private lateinit var animation :ObjectAnimator

    private lateinit var mView:View
    private lateinit var rotateFrm:FrameLayout


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        mView=inflater!!.inflate(R.layout.fragment_round_pic, container, false)
        rotateFrm=mView.findViewById(R.id.rotateFrm)

        animation=ObjectAnimator.ofFloat(rotateFrm,"rotation",0F,360F,0F)
        animation.repeatCount = -1 //-1为一直重复
        animation.repeatMode=ObjectAnimator.RESTART
        animation.interpolator = LinearInterpolator()
        animatorUpdateListener=MyAnimatorUpdateListener(animation)
        animation.addUpdateListener(animatorUpdateListener)
        animation.duration = 10000

        rotatePic()
        return mView
    }

    fun pauseRotatePic(){
        animatorUpdateListener.pause()
    }

    fun rotatePic(){
        //如果已经暂停，是继续播放
        if (animatorUpdateListener.isPause) animatorUpdateListener.play()
        //否则就是从头开始播放
        else animation.start()
    }


}// Required empty public constructor
