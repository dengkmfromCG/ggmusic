package com.gdut.dkmfromcg.ggmusic.base.recyclerview;


import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;

/**
 * author: imilk
 * https://github.com/Solartisan/TurboRecyclerViewHelper
 */
public class BaseViewHolder extends RecyclerView.ViewHolder {

    /**
     * Views indexed with their IDs
     */
    private final SparseArray<View> mViews;


    public BaseViewHolder(View view) {
        super(view);
        this.mViews = new SparseArray<>();
    }

    @SuppressWarnings("unchecked")
    public  <T extends View> T findViewById(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

}

