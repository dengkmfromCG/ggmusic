package com.gdut.dkmfromcg.ggmusic.service

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import android.util.Log
import com.gdut.dkmfromcg.ggmusic.data.SongBean
import com.gdut.dkmfromcg.ggmusic.util.MusicLoader
import android.content.BroadcastReceiver
import android.content.Context
import android.content.IntentFilter
import android.os.Binder
import android.os.Handler
import com.gdut.dkmfromcg.ggmusic.data.ConstantString


/**
 * Created by dkmFromCG on 2017/10/15.
 * function:
 */
class MusicPlayerService: Service() {



    companion object {
        val TAG="MusicPlayerService"

        val listPlay=0  //列表播放
        val listPlayLoop=1 //列表循环
        val listPlayRandom=2 //列表随机
        val singlePlayLoop=3   //单曲循环

        private var isPlay=false
        fun isPlaying():Boolean{
            return isPlay
        }

    }


    private var currentPlayMode=listPlay //播放模式
    private var currentMusicIndex=0 //记录当前正在播放的音乐


    private var path:String=""

    private var currentTime:Int=0
    private var duration:Int=0

    private val mediaPlayer:MediaPlayer by lazy {
        MediaPlayer()
    }

    private val songList:List<SongBean> by lazy {
        MusicLoader.getMusicList()
    }

    override fun onCreate() {
        super.onCreate()

        /**
         * 设置音乐播放完成时的监听器
         */
        mediaPlayer.setOnCompletionListener { mediaPlayer ->
            if (currentPlayMode==listPlay){
                if (currentMusicIndex<songList.size-1){
                    currentMusicIndex++

                    path=songList[currentMusicIndex].songUrl
                    playMusic(0)
                }else{
                    mediaPlayer.seekTo(0)
                    currentMusicIndex=0
                }

            }else if (currentPlayMode==listPlayLoop){
                currentMusicIndex=(currentMusicIndex+1)%songList.size

                path=songList[currentMusicIndex].songUrl
                playMusic(0)
                
            }else if (currentPlayMode==listPlayRandom){
                currentMusicIndex=getRandomIndex(songList.size-1)
                Log.d(TAG, "currentMusicIndex: $currentMusicIndex")

                path=songList[currentMusicIndex].songUrl
                playMusic(0)

            }else if (currentPlayMode==singlePlayLoop){
                mediaPlayer.start()
            }
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        mediaPlayer.let {
            it.stop()
            it.release()
        }
        super.onDestroy()
    }

    private fun  playMusic(currentTime: Int) {
        mediaPlayer.reset()// 把各项参数恢复到初始状态
        mediaPlayer.setDataSource(path)
        mediaPlayer.prepare() // 进行缓冲
        mediaPlayer.setOnPreparedListener(PreparedListener(currentTime));// 注册一个监听器

        val intent=Intent(ConstantString.newSongDuration)
        sendBroadcast(intent)


    }

    private fun getRandomIndex(end:Int):Int= (Math.random()*end).toInt()

    private inner class PreparedListener(val currentTime: Int): MediaPlayer.OnPreparedListener {
        override fun onPrepared(p0: MediaPlayer?) {
            mediaPlayer.start() // 开始播放
            if (currentTime > 0) { // 如果音乐不是从头播放
                mediaPlayer.seekTo(currentTime)
            }

            val intent = Intent(ConstantString.newSongDuration)
            duration = mediaPlayer.duration
            intent.putExtra("duration", duration)  //通过Intent来传递歌曲的总长度,UI界面更改
            sendBroadcast(intent)
        }
    }


    private val mBinder=PlayBinder()
    override fun onBind(p0: Intent?): IBinder=mBinder
    inner class PlayBinder: Binder() {

        fun nextPlay(){
            if (currentPlayMode== listPlay || currentPlayMode== listPlayLoop){
                currentMusicIndex=(currentMusicIndex+1)%songList.size
                path=songList[currentMusicIndex].songUrl
                playMusic(0)
            }else if (currentPlayMode== listPlayRandom || currentPlayMode== singlePlayLoop){
                currentPlayMode=getRandomIndex(songList.size-1)
                path=songList[currentMusicIndex].songUrl
                playMusic(0)
            }
        }

        fun previousPlay(){
            if (currentPlayMode== listPlay || currentPlayMode== listPlayLoop){
                if (currentMusicIndex==0){
                    currentMusicIndex=songList.size-1
                }else{
                    currentMusicIndex--
                }
                path=songList[currentMusicIndex].songUrl
                playMusic(0)
            }else if (currentPlayMode== listPlayRandom || currentPlayMode== singlePlayLoop){
                currentPlayMode=getRandomIndex(songList.size-1)
                path=songList[currentMusicIndex].songUrl
                playMusic(0)
            }
        }

        fun musicPlay(){
            if (!mediaPlayer.isPlaying){
                mediaPlayer.start()
                isPlay=true
            }
        }

        fun stopPlay(){
            if (mediaPlayer.isPlaying){
                mediaPlayer.pause()
                isPlay=false
            }
        }

        fun changePlayMode(playMode:Int){
            currentPlayMode=playMode
        }

        fun seekToPlay(process:Int) {
            mediaPlayer.seekTo(process)
        }

        fun getCurrentSongDuration():Int=songList[currentMusicIndex].duration
    }

}