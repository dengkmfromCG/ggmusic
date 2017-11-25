package com.gdut.dkmfromcg.ggmusic.ui.fragment


import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.gdut.dkmfromcg.ggmusic.R
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView
import android.widget.Toast
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.gdut.dkmfromcg.ggmusic.base.ActivityCollector
import com.gdut.dkmfromcg.ggmusic.ui.activity.LoginActivity
import com.gdut.dkmfromcg.ggmusic.ui.activity.SettingActivity
import com.qmuiteam.qmui.util.QMUISpanHelper


/**
 * A simple [Fragment] subclass.
 */
class NavigationFragment : Fragment() {

    private lateinit var mView:View
    private lateinit var mGroupListView: QMUIGroupListView

    private lateinit var tvSetting:TextView
    private lateinit var tvQuit:TextView

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        mView=inflater!!.inflate(R.layout.fragment_navigation, container, false)
        mView.findViewById<ImageView>(R.id.imageView).setOnClickListener {
            LoginActivity.startSelf(activity)
        }
        mGroupListView=mView.findViewById(R.id.groupListView)
        tvSetting=mView.findViewById(R.id.tv_whole_setting)
        tvQuit=mView.findViewById(R.id.tv_whole_quit)
        val settingIcon=activity.resources.getDrawable(R.drawable.song_menu_setting)
        val quitIcon=activity.resources.getDrawable(R.drawable.quit)
        tvSetting.text = QMUISpanHelper.generateSideIconText(true,20,"设置",settingIcon)
        tvQuit.text=QMUISpanHelper.generateSideIconText(true,20,"退出",quitIcon)
        tvSetting.setOnClickListener {
            SettingActivity.startSelf(activity)
        }
        tvQuit.setOnClickListener {
            ActivityCollector.finishAll()
        }
        initGroupListView()
        return mView
    }

    private fun initGroupListView() {

        val thisGit = mGroupListView.createItemView("项目主页")
        thisGit.orientation = QMUICommonListItemView.VERTICAL

        val myMessage = mGroupListView.createItemView("我的消息")
        myMessage.orientation = QMUICommonListItemView.VERTICAL

        val problemFeedback = mGroupListView.createItemView("问题反馈")
        problemFeedback.orientation = QMUICommonListItemView.VERTICAL

        val scanDownload = mGroupListView.createItemView("扫码下载/传送")
        scanDownload.orientation = QMUICommonListItemView.VERTICAL

        val musicAlarmClock = mGroupListView.createItemView("音乐闹钟")
        musicAlarmClock.orientation = QMUICommonListItemView.VERTICAL

        //右边可以设置成自定义的View
        val timingStop = mGroupListView.createItemView("定时停止播放")
        timingStop.orientation = QMUICommonListItemView.VERTICAL




        val nightMode = mGroupListView.createItemView("夜间模式")
        nightMode.accessoryType = QMUICommonListItemView.ACCESSORY_TYPE_SWITCH
        nightMode.switch.setOnCheckedChangeListener{
            _, isChecked -> Toast.makeText(activity, "夜间模式" + isChecked, Toast.LENGTH_SHORT).show() }

        val changeSkin = mGroupListView.createItemView("个性换肤")
        changeSkin.setDetailText("官方蓝")

        val draw : Drawable=activity.resources.getDrawable(R.drawable.loading1)
        val listenKnowSong = mGroupListView.createItemView(draw,"听歌识曲","", LinearLayout.VERTICAL,QMUICommonListItemView.ALIGN_PARENT_LEFT)
        listenKnowSong.orientation = QMUICommonListItemView.VERTICAL

        val onClickListener0 = View.OnClickListener { v ->
            if (v is QMUICommonListItemView) {
                val text = v.text
                Toast.makeText(activity, "假的, "+text.toString() + " 其实还实现", Toast.LENGTH_SHORT).show()
            }
        }

        val onClickListener1 = View.OnClickListener { v ->
            if (v is QMUICommonListItemView) {
                val text = v.text
                Toast.makeText(activity, "真的, "+text.toString() + " 真的没实现", Toast.LENGTH_SHORT).show()
            }
        }


        QMUIGroupListView.newSection(activity)
                .setTitle("已实现功能")
                .addItemView(thisGit,onClickListener0)
                .addItemView(myMessage,onClickListener0)
                .addItemView(problemFeedback,onClickListener0)
                .addItemView(scanDownload,onClickListener0)
                .addItemView(musicAlarmClock,onClickListener0)
                .addItemView(timingStop,onClickListener0)
                .addTo(mGroupListView)



        QMUIGroupListView.newSection(activity)
                .setTitle("未实现功能")
                .addItemView(nightMode,onClickListener1)
                .addItemView(changeSkin,onClickListener1)
                .addItemView(listenKnowSong,onClickListener1)
                .addTo(mGroupListView)
    }

}// Required empty public constructor
