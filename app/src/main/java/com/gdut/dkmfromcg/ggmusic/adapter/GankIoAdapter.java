package com.gdut.dkmfromcg.ggmusic.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gdut.dkmfromcg.ansynclib.service.entity.GankIoDataBean;
import com.gdut.dkmfromcg.ggmusic.R;
import com.gdut.dkmfromcg.ggmusic.ui.activity.WebViewActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dkmFromCG on 2017/10/21.
 * function:
 */

public class GankIoAdapter extends RecyclerView.Adapter<GankIoAdapter.ViewHolder>{

    private List<GankIoDataBean.ResultBean> mData=new ArrayList<>();

    Context mContext;
    public GankIoAdapter(Context ctx){
        mContext=ctx;
    }

    public void addAll(List<GankIoDataBean.ResultBean> datas){
        this.mData.addAll(datas);
    }

    public void add(GankIoDataBean.ResultBean data){
        this.mData.add(data);
    }

    public void clear() {
        mData.clear();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.grankio_item,parent,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (mData.size()==0) return;
        final GankIoDataBean.ResultBean bean=mData.get(position);
        holder.newsContent.setText(bean.getDesc());
        holder.newsAuthor.setText(bean.getWho());
        holder.newsType.setText(bean.getType());
        holder.newsDate.setText(bean.getPublishedAt());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WebViewActivity.startSelf(mContext,bean.getUrl());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        TextView newsContent;
        ImageView newsPic;
        TextView newsAuthor;
        TextView newsType;
        TextView newsDate;

        public ViewHolder(View itemView) {
            super(itemView);
            newsContent=itemView.findViewById(R.id.tv_news_content);
            newsPic=itemView.findViewById(R.id.iv_news_pic);
            newsAuthor=itemView.findViewById(R.id.tv_news_who);
            newsType=itemView.findViewById(R.id.tv_content_type);
            newsDate=itemView.findViewById(R.id.tv_news_time);
        }
    }

}
