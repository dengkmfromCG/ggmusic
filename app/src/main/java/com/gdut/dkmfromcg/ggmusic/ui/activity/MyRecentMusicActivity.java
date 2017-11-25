package com.gdut.dkmfromcg.ggmusic.ui.activity;


import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.View;

import com.gdut.dkmfromcg.ggmusic.R;
import com.gdut.dkmfromcg.ggmusic.base.BaseToolbarWithTabActivity;
import com.gdut.dkmfromcg.ggmusic.ui.fragment.localmusic.ListMultipleFragment;

import java.util.ArrayList;
import java.util.List;

public class MyRecentMusicActivity extends BaseToolbarWithTabActivity {

    public static void startSelf(Context ctx){
        Intent intent=new Intent(ctx,MyRecentMusicActivity.class);
        ctx.startActivity(intent);
    }

    @Override
    protected void showWhichContainer() {
        mWithoutTabContainer.setVisibility(View.VISIBLE);
        mTabVpContainer.setVisibility(View.GONE);
    }

}
