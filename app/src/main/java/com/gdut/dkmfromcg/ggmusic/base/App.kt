package com.gdut.dkmfromcg.ggmusic.base

import android.app.Application
import android.content.Context
import com.gdut.dkmfromcg.ansynclib.service.RetrofitHttpHelper
import kotlin.properties.Delegates

/**
 * Created by dkmFromCG on 2017/9/20.
 * function:
 */

class App: Application(){
    companion object {
        var instance:App by Delegates.notNull()
        var appContext: Context by Delegates.notNull()
    }

    override fun onCreate() {
        super.onCreate()
        instance=this;
        appContext=applicationContext
        RetrofitHttpHelper.getInstance().init(this,true)
    }
}