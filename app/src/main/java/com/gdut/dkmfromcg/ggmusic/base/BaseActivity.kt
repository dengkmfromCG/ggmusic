package com.gdut.dkmfromcg.ggmusic.base


import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.support.v4.content.LocalBroadcastManager
import android.support.v7.app.AppCompatActivity
import com.gdut.dkmfromcg.ggmusic.service.MusicPlayerService


/**
 * Created by dkmFromCG on 2017/9/20.
 * function:
 */
open class BaseActivity : AppCompatActivity(){

    protected val localBroadcastManager by lazy<LocalBroadcastManager> {
        LocalBroadcastManager.getInstance(this)
    }

    protected lateinit var playBinder: MusicPlayerService.PlayBinder
    private val connection = object : ServiceConnection {
        override fun onServiceDisconnected(p0: ComponentName?) {
        }

        override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {
            playBinder = p1 as MusicPlayerService.PlayBinder
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //活动与服务之间通信
        val bindIntent=Intent(this,MusicPlayerService::class.java)
        bindService(bindIntent,connection, BIND_AUTO_CREATE)

        ActivityCollector.addActivity(this)
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        unbindService(connection)
        ActivityCollector.removeActivity(this)
    }

    protected fun sendLocalReceiver(action :String){
        val intent=Intent(action)
        localBroadcastManager.sendBroadcast(intent)
    }

}