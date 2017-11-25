package com.gdut.dkmfromcg.ggmusic.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.gdut.dkmfromcg.ggmusic.R;
import com.gdut.dkmfromcg.ggmusic.base.recyclerview.BaseViewHolder;
import com.gdut.dkmfromcg.ggmusic.data.SongBean;
import com.gdut.dkmfromcg.ggmusic.ui.activity.PlayingMusicActivity;

import java.util.List;

/**
 * Created by dkmFromCG on 2017/10/27.
 * function:
 */

public class ListMultipleAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    Context mContext;
    List<SongBean> mDatas;
    private LayoutInflater mInflater;

    final private static int SONG_ITEM= 0;
    final private static int PINNED_ITEM=1;

    public ListMultipleAdapter(Context ctx,List<SongBean> datas){
        this.mContext=ctx;
/*        for (int i=0;i<datas.size()-1;i++){
            String thisPinYin=RxDataTool.getPYFirstLetter(datas.get(i).getSongName());
            String nextPinYin=RxDataTool.getPYFirstLetter(datas.get(i+1).getSongName());
            if (! thisPinYin.equals(nextPinYin)){
                mDatas.add(datas.get(i));
                mDatas.add(new SongBean(-1,thisPinYin.substring(0,1),null,null,null,null
                ,null,null,null));

            }else {
                mDatas.add(datas.get(i));
            }
        }*/
        this.mDatas=datas;
        mInflater=LayoutInflater.from(ctx);
    }


    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType==SONG_ITEM){
            return new SongHolder( mInflater.inflate(R.layout.item_song,parent,false));
        }else {
            return new PinnedHolder( mInflater.inflate(R.layout.item_pinned_header,parent,false));
        }
    }


    @SuppressWarnings("unchecked")
    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        SongBean bean=mDatas.get(position);

        if (holder instanceof ListMultipleAdapter.PinnedHolder){
            ((PinnedHolder) holder).songTip.setText("sss");

        }else if (holder instanceof ListMultipleAdapter.SongHolder){
            SongHolder vh= (SongHolder) holder;
            vh.songTitle.setText(bean.getSongName());
            vh.singer.setText(bean.getSinger());
            vh.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PlayingMusicActivity.Companion.startActivity(mContext,null);
                }
            });
            vh.btnMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public int getLetterPosition(String letter){
        for (int i = 0 ; i < mDatas.size(); i++){
            if(mDatas.get(i).getType() ==0 && mDatas.get(i).getPinYin().equals(letter)){
                return i;
            }
        }
        return -1;
    }

    class PinnedHolder extends BaseViewHolder {

        TextView songTip;

        public PinnedHolder(View view) {
            super(view);
            songTip = findViewById(R.id.song_tip);
        }
    }

    class SongHolder extends BaseViewHolder{

        TextView songTitle;
        TextView singer;
        ImageView hasDownLoad;
        Button btnMore;

        public SongHolder(View view) {
            super(view);
            songTitle=findViewById(R.id.tv_song_name);
            singer=findViewById(R.id.tv_singer);
            hasDownLoad=findViewById(R.id.iv_has_download);
            btnMore=findViewById(R.id.btn_more);
        }
    }
}
