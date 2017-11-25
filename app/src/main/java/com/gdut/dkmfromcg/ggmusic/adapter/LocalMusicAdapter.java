package com.gdut.dkmfromcg.ggmusic.adapter;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cocosw.bottomsheet.BottomSheet;
import com.gdut.dkmfromcg.ggmusic.R;
import com.gdut.dkmfromcg.ggmusic.data.SongListInfo;
import com.gdut.dkmfromcg.ggmusic.data.SongMenu;
import com.gdut.dkmfromcg.ggmusic.ui.activity.MyCollectionActivity;
import com.gdut.dkmfromcg.ggmusic.ui.activity.MyDownloadMusicActivity;
import com.gdut.dkmfromcg.ggmusic.ui.activity.MyRecentMusicActivity;
import com.gdut.dkmfromcg.ggmusic.ui.activity.ScanMusicActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dkmFromCG on 2017/10/22.
 * function:
 */

public class LocalMusicAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static boolean isExpand=false;
    private List<SongListInfo> songListInfos;
    private List<SongMenu> songMenuList=new ArrayList<>();
    private final List<SongMenu> tempSongMenuList=new ArrayList<>();

    private static final int NORMAL_LIST=0;
    private static final int EXPANED_FATHER=1;
    private static final int EXPANED_CHILD=2;

    private Context mContext;
    private LayoutInflater mInflater;


    public LocalMusicAdapter(Context context,List<SongListInfo> songListInfos,List<SongMenu> songMenuList){
        this.songListInfos=songListInfos;
        tempSongMenuList.addAll(songMenuList);

        mContext=context;
        mInflater=LayoutInflater.from(mContext);
    }

    private void removeList(List<SongMenu> songMenuList){
        this.songMenuList.removeAll(songMenuList);
        this.notifyDataSetChanged();
    }

    private void addList(List<SongMenu> songMenuList){
        this.songMenuList.addAll(songMenuList);
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (position<=3 && position>=0){
            return NORMAL_LIST;
        }else if (position == 4){
            return EXPANED_FATHER;
        }else{
            return EXPANED_CHILD;
        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view ;
        if (mContext == null) {
            mContext = parent.getContext();
        }
        if (viewType == NORMAL_LIST){
            view=mInflater.inflate(R.layout.local_normal_item,parent,false);
            return new NormalViewHolder(view);

        }else if (viewType==EXPANED_FATHER){
            view=mInflater.inflate(R.layout.local_expaned_item_father,parent,false);
            return new ExpanedFatherViewHolder(view);

        }else if (viewType==EXPANED_CHILD){
            view=mInflater.inflate(R.layout.local_expaned_item_child,parent,false);
            return new ExpanedChildViewHolder(view);

        }
        return null;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof NormalViewHolder){
            SongListInfo songListInfo=songListInfos.get(position);
            NormalViewHolder vh= (NormalViewHolder) holder;
            vh.normalListPic.setImageResource(songListInfo.getPic());
            vh.normalListTitle.setText(songListInfo.getTitle());
            vh.normalListCount.setText("("+songListInfo.getCount()+")");
            switch (songListInfo.getTitle()){
                case "本地音乐":
                    vh.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ScanMusicActivity.startSelf(mContext);
                        }
                    });
                    break;
                case "最近播放":
                    vh.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            MyRecentMusicActivity.startSelf(mContext);
                        }
                    });

                    break;
                case "下载管理":
                    vh.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            MyDownloadMusicActivity.startSelf(mContext);
                        }
                    });

                    break;
                case "我的歌手":
                    vh.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            MyCollectionActivity.startSelf(mContext);
                        }
                    });

                    break;
            }
        }else if (holder instanceof ExpanedFatherViewHolder){
            ExpanedFatherViewHolder vh= (ExpanedFatherViewHolder) holder;
            vh.cardTitle.setText("创建的歌单("+tempSongMenuList.size()+")");

            final ObjectAnimator anim = ObjectAnimator.ofFloat(vh.expandIcon, "rotation", 0,90);
            anim.setDuration(100);
            anim.setRepeatCount(0);
            anim.setInterpolator(new LinearInterpolator());

            vh.container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!isExpand){
                        isExpand=true;
                        anim.start();
                        addList(tempSongMenuList);
                    }else {
                        isExpand=false;
                        anim.reverse();
                        removeList(tempSongMenuList);
                    }
                }
            });
            vh.ivBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new BottomSheet.Builder(mContext, R.style.BottomSheet_StyleDialog)
                            .sheet(R.menu.home)
                            .listener(new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    switch (i){

                                    }
                                }
                            }).show();
                }
            });

        }else if (holder instanceof ExpanedChildViewHolder){

        }
    }

    @Override
    public int getItemCount() {
        return songListInfos.size()+1+songMenuList.size();
    }

    static class NormalViewHolder extends RecyclerView.ViewHolder{
        ImageView normalListPic;
        TextView normalListTitle;
        TextView normalListCount;

        public NormalViewHolder(View itemView) {
            super(itemView);
            normalListPic=itemView.findViewById(R.id.normalList_img);
            normalListTitle=itemView.findViewById(R.id.normalList_title);
            normalListCount=itemView.findViewById(R.id.normalList_count);
        }
    }

    static class ExpanedFatherViewHolder extends RecyclerView.ViewHolder{
        RelativeLayout container;
        ImageView expandIcon;
        TextView cardTitle;
        ImageView ivBtn;

        public ExpanedFatherViewHolder(View itemView) {
            super(itemView);
            container=itemView.findViewById(R.id.title_container);
            expandIcon=itemView.findViewById(R.id.expand_icon);
            cardTitle=itemView.findViewById(R.id.card_title);
            ivBtn=itemView.findViewById(R.id.ivBtnSetting);
        }
    }

    static class ExpanedChildViewHolder extends RecyclerView.ViewHolder{
        ImageView songMenuPic;
        TextView songMenuTitle;
        TextView songMenuCount;
        ImageView songMenuMore;

        public ExpanedChildViewHolder(View itemView) {
            super(itemView);
            songMenuPic=itemView.findViewById(R.id.songMenu_img);
            songMenuTitle=itemView.findViewById(R.id.sonMenu_title);
            songMenuCount=itemView.findViewById(R.id.songMenu_count);
            songMenuMore=itemView.findViewById(R.id.songMenu_more);
        }
    }
}
