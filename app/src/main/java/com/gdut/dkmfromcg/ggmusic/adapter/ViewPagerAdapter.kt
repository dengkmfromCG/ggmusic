package com.gdut.dkmfromcg.ggmusic.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter

/**
 * Created by dkmFromCG on 2017/9/20.
 * function:
 */
class ViewPagerAdapter(private val fragmentManager: FragmentManager, private var fragmentList:List<Fragment>)
    : FragmentStatePagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment = fragmentList[position]

    override fun getCount(): Int = fragmentList.size
}