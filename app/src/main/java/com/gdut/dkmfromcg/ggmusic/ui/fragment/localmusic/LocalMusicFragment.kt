package com.gdut.dkmfromcg.ggmusic.ui.fragment.localmusic

import android.content.Context
import android.content.DialogInterface
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.cocosw.bottomsheet.BottomSheet

import com.gdut.dkmfromcg.ggmusic.R
import com.gdut.dkmfromcg.ggmusic.adapter.LocalMusicAdapter
import com.gdut.dkmfromcg.ggmusic.data.SongListInfo
import com.gdut.dkmfromcg.ggmusic.data.SongMenu
import com.gdut.dkmfromcg.ggmusic.ui.activity.PlayingMusicActivity
import com.gdut.dkmfromcg.ggmusic.util.T


class LocalMusicFragment : Fragment() {


    private lateinit var mView:View
    private lateinit var recyclerView:RecyclerView

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        mView =inflater!!.inflate(R.layout.fragment_local_music, container, false)
        recyclerView=mView.findViewById(R.id.recv_localmusic);
        recyclerView.layoutManager=LinearLayoutManager(activity)
        val songListInfo= mutableListOf<SongListInfo>(
                SongListInfo(R.drawable.music_icn_local,"本地音乐",0),
                SongListInfo(R.drawable.music_icn_recent,"最近播放",0),
                SongListInfo(R.drawable.music_icn_dld,"下载管理",0),
                SongListInfo(R.drawable.music_icn_artist,"我的歌手",0)
        )
        val songMenuList= mutableListOf<SongMenu>(
                SongMenu("我喜欢的音乐","10",R.mipmap.ic_launcher_round)
        )
        val adapter=LocalMusicAdapter(activity,songListInfo,songMenuList)
        recyclerView.adapter=adapter

        return mView
    }

}
