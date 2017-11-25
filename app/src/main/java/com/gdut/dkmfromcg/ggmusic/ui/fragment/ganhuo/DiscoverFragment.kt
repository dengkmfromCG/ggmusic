package com.gdut.dkmfromcg.ggmusic.ui.fragment.ganhuo


import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.gdut.dkmfromcg.ggmusic.R
import com.gdut.dkmfromcg.ggmusic.adapter.MyFragmentPagerAdapter
import com.gdut.dkmfromcg.ggmusic.ui.fragment.ganhuo.*


/**
 * A simple [Fragment] subclass.
 */
class DiscoverFragment : Fragment() {

    private lateinit var mView:View
    private lateinit var tabLayout:TabLayout
    private lateinit var vpDiscover:ViewPager

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        mView=inflater!!.inflate(R.layout.fragment_discover, container, false)
        tabLayout=mView.findViewById(R.id.tabDiscover)
        vpDiscover=mView.findViewById(R.id.vpDiscover)
        val titleList= mutableListOf<String>("安卓","软件","IOS","前端","瞎推荐")
        val fragmentList= mutableListOf<Fragment>(
                CustomFragment.newFragment("Android"),
                CustomFragment.newFragment("App"),
                CustomFragment.newFragment("IOS"),
                CustomFragment.newFragment("前端"),
                CustomFragment.newFragment("瞎推荐")
        )
        val vpAdapter=MyFragmentPagerAdapter(childFragmentManager,fragmentList,titleList)
        vpDiscover.offscreenPageLimit=3
        vpDiscover.adapter=vpAdapter
        tabLayout.setupWithViewPager(vpDiscover)
        return mView
    }

}// Required empty public constructor
