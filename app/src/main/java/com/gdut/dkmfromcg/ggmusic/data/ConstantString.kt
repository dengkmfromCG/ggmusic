package com.gdut.dkmfromcg.ggmusic.data

/**
 * Created by dkmFromCG on 2017/10/15.
 * function: action常量类
 */
object ConstantString {

    //播放模式
    val listOrderPlay = "listOrderPlay"
    val listLoopPlay = "listLoopPlay"
    val listRandomPlay = "listRandomPlay"
    val singleLoopPlay = "singleLoopPlay"

    //播放,停止,上下曲操作
    val nextPlay="nextPlay"
    val previousPlay="previousPlay"
    val doPlay="doPlay"
    val pausePlay="pausePlay"
    val seekToPlay="seekToPlay"

    val newSongDuration="newSongDuration"//新音乐长度更新动作
    val currentPlaySecond="currentPlaySecond"//当前音乐播放时间更新动作

    //缓存网络数据的key
    val GANK_ANDROID = "gank_android"
    val GANK_CUSTOM = "gank_custom"

}