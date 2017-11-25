package com.gdut.dkmfromcg.ggmusic.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.gdut.dkmfromcg.ggmusic.R;
import com.gdut.dkmfromcg.ggmusic.base.BaseActivity;

public class SettingActivity extends BaseActivity {

    public static void startSelf(Context ctx){
        Intent intent=new Intent(ctx,SettingActivity.class);
        ctx.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
    }
}
