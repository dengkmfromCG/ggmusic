package com.gdut.dkmfromcg.ggmusic.ui.activity

import android.Manifest
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v4.view.ViewPager
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import com.gdut.dkmfromcg.ggmusic.R
import com.gdut.dkmfromcg.ggmusic.adapter.ViewPagerAdapter
import com.gdut.dkmfromcg.ggmusic.base.BaseActivity
import com.gdut.dkmfromcg.ggmusic.ui.fragment.ganhuo.DiscoverFragment
import com.gdut.dkmfromcg.ggmusic.ui.fragment.friend.FriendsFragment
import com.gdut.dkmfromcg.ggmusic.ui.fragment.localmusic.LocalMusicFragment
import com.gdut.dkmfromcg.ggmusic.util.RxPermissionsTool
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.app_bar_home.*
import kotlinx.android.synthetic.main.toolbar_home.*


class HomeActivity : BaseActivity()/*, NavigationView.OnNavigationItemSelectedListener*/ {


    var tabs= mutableListOf<ImageView>()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setSupportActionBar(toolbar)


        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        /*nav_view.setNavigationItemSelectedListener(this)*/

        initViewPager()
        RxPermissionsTool.
                with(this).
                addPermission(Manifest.permission.READ_EXTERNAL_STORAGE).
                addPermission(Manifest.permission.CAMERA).
                addPermission(Manifest.permission.CALL_PHONE).
                initPermission()

    }


    private fun initViewPager() {

        tabs.add(ivLocalMusic)
        tabs.add(ivNetRes)
        tabs.add(ivFriends)

        val pagers= mutableListOf<Fragment>(
                LocalMusicFragment(),
                DiscoverFragment(),
                FriendsFragment()
        )


        /*val pagerAdapter =object :QMUIPagerAdapter(){

            override fun getPageTitle(position: Int): CharSequence {
                return "ITEM$position"
            }

            override fun populate(container: ViewGroup?, item: Any?, position: Int) {
                container?.addView(pagers[position] as View)
            }

            override fun destroy(container: ViewGroup?, position: Int, `object`: Any?) {
                //销毁
                container?.removeView(`object` as View)
            }

            override fun hydrate(container: ViewGroup?, position: Int): Any = pagers[position]

            override fun isViewFromObject(view: View?, `object`: Any?): Boolean = view==`object`

            override fun getCount(): Int =pagers.size
        }*/

        val pagerAdapter= ViewPagerAdapter(supportFragmentManager,pagers)
        vpHome.adapter=pagerAdapter

        vpHome.currentItem=1
        ivNetRes.isSelected=true
        vpHome.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                switchTabs(position)
            }

        })

        ivLocalMusic.setOnClickListener{vpHome.currentItem=0}
        ivNetRes.setOnClickListener{vpHome.currentItem=1}
        ivFriends.setOnClickListener{vpHome.currentItem=2}
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.home, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

   /* override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_camera -> {
                // Handle the camera action
            }
            R.id.nav_gallery -> {

            }
            R.id.nav_slideshow -> {

            }
            R.id.nav_manage -> {

            }
            R.id.nav_share -> {

            }
            R.id.nav_send -> {

            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }*/

    private fun  switchTabs(position: Int) {
        for (i in 0..tabs.size - 1) {
            tabs[i].isSelected = position == i
        }
    }
}
