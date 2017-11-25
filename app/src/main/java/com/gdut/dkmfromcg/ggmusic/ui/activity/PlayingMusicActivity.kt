package com.gdut.dkmfromcg.ggmusic.ui.activity

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.View
import android.view.animation.Interpolator
import android.view.animation.LinearInterpolator
import kotlinx.android.synthetic.main.activity_playing_music.*
import kotlin.properties.Delegates
import android.widget.Scroller
import android.widget.SeekBar
import com.gdut.dkmfromcg.ggmusic.R
import com.gdut.dkmfromcg.ggmusic.adapter.ViewPagerAdapter
import com.gdut.dkmfromcg.ggmusic.base.BaseActivity
import com.gdut.dkmfromcg.ggmusic.data.ConstantString
import com.gdut.dkmfromcg.ggmusic.lyric.LrcView
import com.gdut.dkmfromcg.ggmusic.service.MusicPlayerService
import android.media.AudioManager
import android.os.Build
import android.support.v4.view.ViewCompat
import com.gdut.dkmfromcg.ggmusic.ui.fragment.RoundPicFragment
import org.jetbrains.anko.toast
import android.widget.PopupWindow
import android.widget.Toast
import android.widget.AdapterView
import com.qmuiteam.qmui.util.QMUIDisplayHelper
import com.qmuiteam.qmui.widget.popup.QMUIPopup
import com.qmuiteam.qmui.widget.popup.QMUIListPopup
import android.widget.ArrayAdapter


class PlayingMusicActivity : BaseActivity() {

    private val mNeedleAnim:ObjectAnimator by lazy {
        val temp=ObjectAnimator.ofFloat(needle, "rotation", -25F, 0F)
        temp.duration = 200
        temp.repeatMode=0
        temp.interpolator = LinearInterpolator()
        temp
    }

    private var mAnimationSet:AnimatorSet by Delegates.notNull()
    private var mAdapter: ViewPagerAdapter?=null

    private val musicPlayCallbackReceiver by lazy { MusicPlayCallbackReceiver() }

    private val onSeekToListener by lazy { LrcView.OnSeekToListener { progress ->
        playBinder.seekToPlay(progress)
    } }

    private val onLrcClickListener by lazy {
        LrcView.OnLrcClickListener{
            if (lyricViewContainer.visibility==View.VISIBLE){
                lyricViewContainer.visibility=View.INVISIBLE
                diskViewContainer.visibility=View.VISIBLE
                musicToolLy.visibility=View.VISIBLE
            }
        }
    }

    //用于记录ViewPager是向左滑还是向右滑
    private var lastValue:Int=-1
    private var isLeft:Boolean=true

    companion object {
        val VIEWPAGER_SCROLL_TIME=390

        fun startActivity( context: Context ,bundle: Bundle?){
            val intent=Intent(context,PlayingMusicActivity::class.java)
            context.startActivity(intent,bundle)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_playing_music)
        registerPlayMusicCallback()
        init()
    }

    private fun registerPlayMusicCallback() {
        val intentFilter=IntentFilter()
        intentFilter.addAction(ConstantString.currentPlaySecond)
        intentFilter.addAction(ConstantString.newSongDuration)
        localBroadcastManager.registerReceiver(musicPlayCallbackReceiver,intentFilter)
    }

    override fun onDestroy() {
        super.onDestroy()
        localBroadcastManager.unregisterReceiver(musicPlayCallbackReceiver)
    }

    private fun init() {
        setToolbar()
        setDiskView()
        setLrcView()
        setSeekBar()
        setControlTool()
    }

    private fun setToolbar() {
        setSupportActionBar(toolbarPlayingMusic)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setHomeAsUpIndicator(R.drawable.actionbar_back)
        }
        toolbarPlayingMusic.setNavigationOnClickListener{_ -> onBackPressed() }
    }

    private fun setDiskView() {
        diskViewContainer.setOnClickListener {
            diskViewContainer.visibility=View.INVISIBLE
            lyricViewContainer.visibility= View.VISIBLE
        }
        setViewPager()
        setMusicTool()
    }

    private fun setLrcView() {
        lyricView.setOnLrcClickListener(onLrcClickListener)
        lyricView.setOnSeekToListener(onSeekToListener)
        vpPlayMusic.setOnClickListener {  _ ->
            if (diskViewContainer.visibility==View.VISIBLE){
                diskViewContainer.visibility=View.INVISIBLE
                musicToolLy.visibility=View.INVISIBLE
                lyricViewContainer.visibility=View.VISIBLE
            }
        }
        lyricViewContainer.setOnClickListener { _ ->
            if (lyricViewContainer.visibility==View.VISIBLE){
                lyricViewContainer.visibility=View.INVISIBLE
                diskViewContainer.visibility=View.VISIBLE
                musicToolLy.visibility=View.VISIBLE
            }
        }

        tvTargetLyric.setOnClickListener { _ ->
            //异步获取歌词信息,rxjava实现

            toast("正在获取信息")
        }


        //volumeSeek调节音量大小
        val audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
        val v = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)
        val mMaxVol = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
        volumeSeek.max = mMaxVol
        volumeSeek.progress = v
        volumeSeek.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, AudioManager.ADJUST_SAME)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {

            }
        })


    }

    private fun setSeekBar() {
        playSeek.isIndeterminate=false
        playSeek.progress=1
        playSeek.max=1000
        var progress=0
        var fromSeekByUser:Boolean=false
        playSeek?.setOnSeekBarChangeListener(object :SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                progress=(p1*playBinder.getCurrentSongDuration())/1000
                fromSeekByUser=p2
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
                lyricView.seekTo(progress,true,fromSeekByUser)
                playBinder.seekToPlay(progress)
            }
        })
    }

    private fun setControlTool() {
        playingMode.setOnClickListener { _ ->
            mListPopup.setAnimStyle(QMUIPopup.ANIM_GROW_FROM_CENTER)
            mListPopup.setPreferredDirection(QMUIPopup.DIRECTION_TOP)
            mListPopup.show(playingMode)
        }
        playingPrev.setOnClickListener { _ -> playBinder.previousPlay() }
        playingPlayOrPause.setOnClickListener { _ ->
            if (MusicPlayerService.isPlaying()){
                playBinder.stopPlay()
                //专辑图片停止旋转

                mNeedleAnim.reverse()
                mNeedleAnim.end()
            }else{
                playBinder.musicPlay()
                //专辑图片开始旋转

                mNeedleAnim.start()
            }
        }
        playingNext.setOnClickListener { _ ->  playBinder.nextPlay() }

    }

    private fun setViewPager() {
        vpPlayMusic.setOnClickListener{
            if(diskViewContainer.visibility== View.VISIBLE){
                diskViewContainer.visibility=View.INVISIBLE
                lyricViewContainer.visibility= View.VISIBLE
            }
        }

        //设置应该保留在两边的页面的数量,默认为1
        vpPlayMusic.offscreenPageLimit=2
        val transformer= PlayBarPagerTransformer()
        val mm= mutableListOf<Fragment>(
                RoundPicFragment(),
                RoundPicFragment(),
                RoundPicFragment(),
                RoundPicFragment(),
                RoundPicFragment()
        )
        mAdapter= ViewPagerAdapter(supportFragmentManager,mm)

        val canUseHardware :Boolean= Build.VERSION.SDK_INT >= 21
        val pageLayerType:Int=if (canUseHardware) ViewCompat.LAYER_TYPE_HARDWARE else ViewCompat.LAYER_TYPE_SOFTWARE
        vpPlayMusic.setPageTransformer(false, transformer,pageLayerType)
        vpPlayMusic.infiniteRatio=500
        vpPlayMusic.isEnableLoop=true
        vpPlayMusic.adapter=mAdapter

        //改变ViewPager动画时间
        val mField = ViewPager::class.java.getDeclaredField("mScroller")
        mField.isAccessible=true
        val mScroller=MyScroller(vpPlayMusic.context.applicationContext,LinearInterpolator())
        mField.set(vpPlayMusic,mScroller)

        vpPlayMusic.addOnPageChangeListener(object :ViewPager.OnPageChangeListener{

            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                if (positionOffset != 0F) {
                    if (lastValue >= positionOffsetPixels) {
                        //右滑
                        isLeft = false
                    } else if (lastValue < positionOffsetPixels) {
                        //左滑
                        isLeft = true
                    }
                    if (MusicPlayerService.isPlaying()){
                        //专辑停止旋转

                        //
                        mNeedleAnim.reverse()
                        mNeedleAnim.end()
                    }
                }
                lastValue = positionOffsetPixels
            }

            //切换了页面之后,才会调用
            override fun onPageSelected(position: Int) {
                if (isLeft ){
                    //先切到上一首歌曲,但是还不播放


                    if (MusicPlayerService.isPlaying()){
                        playBinder.previousPlay()
                    }
                }else{
                    //先切到下一首歌曲,但是还不播放


                    if (MusicPlayerService.isPlaying()){
                        playBinder.nextPlay()
                    }
                }

                if (MusicPlayerService.isPlaying()){
                    mNeedleAnim.start()
                }
            }
        })
    }

    private fun setMusicTool() {
        playingOpenPlayList.setOnClickListener { _ ->  }
        playingMore.setOnClickListener { _ ->  }
        playingFav.setOnClickListener { _ -> }
        playingCmt.setOnClickListener { _ ->  }
    }


    inner class PlayBarPagerTransformer : ViewPager.PageTransformer  {

        override fun transformPage(page: View?, position: Float) {
            val pos=position.toInt()
            if (pos<-2 || pos>2){

            }else{
                if (pos<0){

                }else{

                }
            }

        }
    }

    inner class MyScroller (context: Context, interpolator: Interpolator?): Scroller(context,interpolator) {
        private var animTime = VIEWPAGER_SCROLL_TIME

        override fun startScroll(startX: Int, startY: Int, dx: Int, dy: Int, duration: Int) {
            super.startScroll(startX, startY, dx, dy, animTime)
        }

        override fun startScroll(startX: Int, startY: Int, dx: Int, dy: Int) {
            super.startScroll(startX, startY, dx, dy, animTime)
        }

        fun setMDuration(animTime: Int) {
            this.animTime = animTime
        }
    }

    inner class MusicPlayCallbackReceiver :BroadcastReceiver(){
        override fun onReceive(p0: Context?, p1: Intent?) {
            if (p0==null || p1 == null) return
            val action:String=p1.action
            when(action){
                ConstantString.currentPlaySecond -> { updateSeekBarAndLyric()}
                ConstantString.newSongDuration -> { tvSongTotalLength.setText(p1.getIntExtra("duration",-1)) }
            }
        }
    }

    private fun updateSeekBarAndLyric() {

    }

    private  val mListPopup :QMUIListPopup by lazy {
        val data = mutableListOf<String>("顺序播放", "循环播放", "随机播放", "单曲循环")
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, data)

        val tempListPopup :QMUIListPopup= QMUIListPopup(this, QMUIPopup.DIRECTION_NONE, adapter)
        tempListPopup.create(QMUIDisplayHelper.dp2px(this, 100), QMUIDisplayHelper.dp2px(this, 200), AdapterView.OnItemClickListener { adapterView, view, i, l ->
            Toast.makeText(this, "Item " + (i + 1), Toast.LENGTH_SHORT).show()
            when(i){
                0 -> { playBinder.changePlayMode(MusicPlayerService.listPlay)
                    playingMode.setImageResource(R.drawable.order_loop)}
                1 -> { playBinder.changePlayMode(MusicPlayerService.listPlayLoop)
                playingMode.setImageResource(R.drawable.play_icn_loop_prs)}
                2 -> { playBinder.changePlayMode(MusicPlayerService.listPlayRandom)
                playingMode.setImageResource(R.drawable.random_play)}
                3 -> { playBinder.changePlayMode(MusicPlayerService.singlePlayLoop)
                playingMode.setImageResource(R.drawable.single_loop)}
            }
            tempListPopup.dismiss()
        })
        tempListPopup.setOnDismissListener(PopupWindow.OnDismissListener {  })
        tempListPopup
    }
}
