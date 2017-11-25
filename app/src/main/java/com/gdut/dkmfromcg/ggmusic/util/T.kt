package com.gdut.dkmfromcg.ggmusic.util

import android.widget.Toast
import com.gdut.dkmfromcg.ggmusic.base.App

/**
 * Created by dkmFromCG on 2017/9/20.
 * function:
 */
class T{
    companion object {
        //普通的toast,弹多次
        fun show(message :String,toastLength:Int= Toast.LENGTH_LONG){
            Toast.makeText(App.appContext,message,toastLength).show();
        }

        //改进的toast,弹一次
        var toast :Toast ?=null
        fun showOnce(message: String, toastLength: Int=Toast.LENGTH_LONG){
            if (toast==null){
                toast= Toast.makeText(App.appContext,message,toastLength)
            }else{
                toast!!.setText(message)
            }
            toast!!.show()
        }
    }

}