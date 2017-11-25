package com.gdut.dkmfromcg.ggmusic.base.recyclerview;

import android.support.v7.widget.RecyclerView;

/**
 * author: imilk
 * https://github.com/Solartisan/TurboRecyclerViewHelper
 */
public abstract class OnItemClickListener {

    public void onItemLongClick(RecyclerView.ViewHolder vh,int position){}
    abstract public void onItemClick(RecyclerView.ViewHolder vh,int position);
}
