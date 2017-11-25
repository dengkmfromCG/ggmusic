package com.gdut.dkmfromcg.ggmusic.ui.activity;


import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.View;

import com.gdut.dkmfromcg.ggmusic.base.BaseToolbarWithTabActivity;
import com.gdut.dkmfromcg.ggmusic.ui.fragment.localmusic.ListMultipleFragment;

import java.util.ArrayList;
import java.util.List;

public class ScanMusicActivity extends BaseToolbarWithTabActivity {

    public static void startSelf(Context ctx){
        Intent intent=new Intent(ctx,ScanMusicActivity.class);
        ctx.startActivity(intent);
    }

    @Override
    protected void showWhichContainer() {
        mWithoutTabContainer.setVisibility(View.GONE);
        mTabVpContainer.setVisibility(View.VISIBLE);
    }


    @Override
    protected List<Fragment> setFragmentList() {
        List<Fragment> fragmentList=new ArrayList<>();
        fragmentList.add(new ListMultipleFragment());
        fragmentList.add(new ListMultipleFragment());
        fragmentList.add(new ListMultipleFragment());
        fragmentList.add(new ListMultipleFragment());
        return fragmentList;
    }

    @Override
    protected List<String> setTitleList() {
        List<String> titleList=new ArrayList<>();
        titleList.add("单曲");
        titleList.add("歌手");
        titleList.add("专辑");
        titleList.add("文件夹");
        return titleList;
    }
}
