package com.gdut.dkmfromcg.ggmusic.util

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore.Audio.Media
import com.gdut.dkmfromcg.ggmusic.base.App
import com.gdut.dkmfromcg.ggmusic.data.SongBean

/**
 * Created by dkmFromCG on 2017/10/14.
 * function:
 */
object MusicLoader {

    private val musicList= mutableListOf<SongBean>()

    //Uri，指向external的database
    private val contentUri:Uri=Media.EXTERNAL_CONTENT_URI

    private val projection= arrayOf(
            Media._ID,    //ID
            Media.DATA,   //歌曲文件路径
            Media.TITLE,  //歌名
            Media.ARTIST, //歌手
            Media.ALBUM, //专辑名
            Media.SIZE,  //歌曲文件大小
            Media.DURATION//歌曲的总时长
             )


    fun getMusicList():MutableList<SongBean>{
        val contentResolver: ContentResolver= App.appContext.contentResolver

                /**
         * Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder)
        *Uri：指明要查询的数据库名称加上表的名称，从MediaStore中我们可以找到相应信息的参数。
        *Projection: 指定查询数据库表中的哪几列，返回的游标中将包括相应的信息。Null则返回所有信息。
        *selection: 指定查询条件
        *selectionArgs：参数selection里有 ？这个符号是，这里可以以实际值代替这个问号。如果selection这个没有？的话，那么这个String数组可以为null。
        *SortOrder：指定查询结果的排列顺序
         */
        val cursor:Cursor?= contentResolver.query(contentUri, projection,null,null,Media.DATA)
        cursor?.let{
            it.moveToFirst()
            do {
                val id:Long=it.getLong(it.getColumnIndex(Media._ID))
                val path:String=it.getString(it.getColumnIndex(Media.DATA))
                val songName:String=it.getString(it.getColumnIndex(Media.TITLE))
                val singer:String=it.getString(it.getColumnIndex(Media.ARTIST))
                val album:String=it.getString(it.getColumnIndex(Media.ALBUM))
                val size:Long=it.getLong(it.getColumnIndex(Media.SIZE))
                val duration:Int=it.getInt(it.getColumnIndex(Media.DURATION))

                musicList.add(SongBean(0,"",id,path,songName,singer,album,size,duration))

            }while (it.moveToNext())

            it.close()
        }
        return musicList
    }

}