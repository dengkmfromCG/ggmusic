package com.gdut.dkmfromcg.ggmusic.util

import android.util.Log

/**
 * Created by dkmFromCG on 2017/9/20.
 * function:
 */
class L{
    companion object {
        var isNeedLog :Boolean=true;

        fun v(tag :String  ="SB" ,any: Any  = "You log is null"){

            if (isNeedLog){
                Log.v(tag,any.toString())
            }

        }

        fun d(tag :String  ="SB" ,any: Any  = "You log is null"){

            if (isNeedLog){
                Log.d(tag,any.toString())
            }

        }

        fun i(tag :String  ="SB" ,any: Any  = "You log is null"){

            if (isNeedLog){
                Log.i(tag,any.toString())
            }

        }

        fun w(tag :String  ="SB" ,any: Any  = "You log is null"){

            if (isNeedLog){
                Log.w(tag,any.toString())
            }

        }

        fun e(tag :String  ="SB" ,any: Any  = "You log is null"){

            if (isNeedLog){
                Log.e(tag,any.toString())
            }

        }

    }
}