package com.gdut.dkmfromcg.ggmusic.ui.fragment


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView

import com.gdut.dkmfromcg.ggmusic.R
import com.gdut.dkmfromcg.ggmusic.ui.activity.PlayingMusicActivity
import com.gdut.dkmfromcg.ggmusic.util.T
import com.qmuiteam.qmui.widget.QMUIViewPager


/**
 * A simple [Fragment] subclass.
 */
class BottomControlFragment : Fragment() {


    private lateinit var vpNextLastMusic: QMUIViewPager
    private lateinit var ivPlayOrStopMusic: ImageView
    private lateinit var ivOpenPlayList: ImageView
    private lateinit var mView: View

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        mView= inflater!!.inflate(R.layout.fragment_bottom_control, container, true)
        vpNextLastMusic=mView.findViewById(R.id.vpNextLastMusic)
        ivPlayOrStopMusic=mView.findViewById(R.id.ivPlayOrStopMusic)
        ivOpenPlayList=mView.findViewById(R.id.ivOpenPlayList)
        setViewPager()
        initEvent()
        return mView
    }

    private fun initEvent() {
        vpNextLastMusic.setOnClickListener{
            PlayingMusicActivity.startActivity(activity,null)
        }
        ivPlayOrStopMusic.setOnClickListener {
            T.show("aaaaaa")
        }
        ivOpenPlayList.setOnClickListener {
            T.show("bbbbb")
        }
    }

    private fun setViewPager() {

    }

}// Required empty public constructor
