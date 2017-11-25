package com.gdut.dkmfromcg.ggmusic.ui.activity;


import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.gdut.dkmfromcg.ggmusic.R;
import com.gdut.dkmfromcg.ggmusic.base.BaseToolbarWithTabActivity;

public class MyRadioActivity extends BaseToolbarWithTabActivity {

    public static void startSelf(Context ctx){
        Intent intent=new Intent(ctx,MyRadioActivity.class);
        ctx.startActivity(intent);
    }

    @Override
    protected void showWhichContainer() {
        mWithoutTabContainer.setVisibility(View.VISIBLE);
        mTabVpContainer.setVisibility(View.GONE);
    }


}
