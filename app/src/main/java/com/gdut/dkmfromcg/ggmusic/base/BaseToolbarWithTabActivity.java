package com.gdut.dkmfromcg.ggmusic.base;


import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.RelativeLayout;

import com.gdut.dkmfromcg.ggmusic.R;
import com.gdut.dkmfromcg.ggmusic.adapter.MyFragmentPagerAdapter;

import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Created by dkmFromCG on 2017/10/23.
 * function:
 */

public abstract class BaseToolbarWithTabActivity extends BaseActivity {
    private static final String TAG = "BaseToolbarWithTab";

    protected AppBarLayout mAppBar;
    protected Toolbar mToolbar;
    protected RelativeLayout mWithoutTabContainer;
    protected RelativeLayout mTabVpContainer;
    protected TabLayout mTabLayout;
    protected ViewPager mViewPager;

    //如果显示mWithoutTabContainer时,mWithoutTabContainer子类的布局
    protected View rootView;

    private boolean mIsHidden = false;

    //决定显示哪个Container
    protected abstract void showWhichContainer();

    //当显示mWithoutTabContainer时,需要重写这个方法,为mWithoutTabContainer提供子布局
    protected int provideWithoutTabContentId() {return -1;}
    protected void findWithoutTabViews(View rootView) {}

    //当显示 mTabVpContainer 时需要重写 这两个方法
    protected  List<Fragment> setFragmentList(){return null;}
    protected  List<String> setTitleList(){return null;}

    //当要显示左边的返回按钮时,返回true..如果要不显示,则重写该方法返回false
    protected boolean canBack() {
        return true;
    }

    //当要设置 toolbar 的点击事件的时候,需要重写这个方法.比如双击recyclerView回到顶部,之类的
    protected void onToolbarClick() {}

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_toorbar_tab);
        mAppBar = findViewById(R.id.app_bar_layout);
        mToolbar =findViewById(R.id.toolbar);
        mWithoutTabContainer=findViewById(R.id.without_tab_container);
        mTabVpContainer=findViewById(R.id.tab_vp_container);
        mTabLayout=findViewById(R.id.tab_layout);
        mViewPager=findViewById(R.id.vp_under_tab);
        setToolbar();
        showWhichContainer();
        if (mWithoutTabContainer.getVisibility()==View.VISIBLE && mTabVpContainer.getVisibility()==View.GONE
                && provideWithoutTabContentId()!=-1){
            initWithoutTabContainer();
        }else if (mTabVpContainer.getVisibility()==View.VISIBLE && mWithoutTabContainer.getVisibility()==View.GONE){
            initTabVpContainer();
        }
    }

    private void initWithoutTabContainer() {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        rootView= LayoutInflater.from(this).inflate(provideWithoutTabContentId(),null);
        rootView.setLayoutParams(params);
        mWithoutTabContainer.addView(rootView);
        findWithoutTabViews(rootView);
    }

    private void initTabVpContainer() {
        List<String> titleList=setTitleList();
        List<Fragment> fragmentList=setFragmentList();
        if (titleList==null || fragmentList==null){
            Log.d(TAG, ": titleList or fragmentList is null");
        }
        MyFragmentPagerAdapter adapter=new MyFragmentPagerAdapter(getSupportFragmentManager(),fragmentList,titleList);
        mViewPager.setOffscreenPageLimit(2);
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    private void setToolbar() {
        mToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onToolbarClick();
            }
        });
        setSupportActionBar(mToolbar);

        if (canBack()) {
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) actionBar.setDisplayHomeAsUpEnabled(true);
        }
        if (Build.VERSION.SDK_INT >= 21) {
            mAppBar.setElevation(10.6f);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }


    protected void setAppBarAlpha(float alpha) {
        mAppBar.setAlpha(alpha);
    }

    protected void hideOrShowToolbar() {
        mAppBar.animate()
                .translationY(mIsHidden ? 0 : -mAppBar.getHeight())
                .setInterpolator(new DecelerateInterpolator(2))
                .start();
        mIsHidden = !mIsHidden;
    }
}
