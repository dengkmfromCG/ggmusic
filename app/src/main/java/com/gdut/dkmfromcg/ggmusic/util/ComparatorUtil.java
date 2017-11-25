package com.gdut.dkmfromcg.ggmusic.util;

import com.gdut.dkmfromcg.ggmusic.data.SongBean;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by dkmFromCG on 2017/10/27.
 * function:
 */

public class ComparatorUtil {

    public static  List<SongBean> compareByDate(List<SongBean> list){
        Collections.sort(list,new ComparatorDate());
        return list;
    }

    public static  List<SongBean> compareByPinyin(List<SongBean> list){
        Collections.sort(list,new ComparatorPinyin());
        return list;
    }

    static class ComparatorPinyin implements Comparator<SongBean>{

        @Override
        public int compare(SongBean t0, SongBean t1) {
            if (t0==null || t1==null){
                return 0;
            }
            String toLetter=RxDataTool.getPYFirstLetter(t0.getSinger());
            String t1Letter=RxDataTool.getPYFirstLetter(t1.getSinger());
            if (toLetter==null || t1Letter==null){
                return 0;
            }
            return t0.getSinger().compareTo(t1.getSinger());
        }
    }

    static class ComparatorDate implements Comparator<SongBean>{

        @Override
        public int compare(SongBean t0, SongBean t1) {
            if (t0==null || t1==null){
                return 0;
            }
            return 0;
        }
    }

}
