package com.gdut.dkmfromcg.ggmusic.data

/**
 * Created by dkmFromCG on 2017/10/11.
 * function:
 */

data class SongMenu(val menuName:String ,val songCount:String,val menuPic:Int)

//type :0代表SongHolder,-1代表PinnedHolder
data class SongBean(val type:Int,val pinYin:String,val id:Long,val songUrl:String,
                    val songName:String,
                    val singer:String,
                    val album:String,val size:Long,val duration:Int
                    )


data class SongListInfo(val pic:Int,val title:String,val count:Int)
