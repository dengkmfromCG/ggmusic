package com.gdut.dkmfromcg.ggmusic.ui.fragment.ganhuo;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.gdut.dkmfromcg.ansynclib.service.RequestCallback;
import com.gdut.dkmfromcg.ansynclib.service.entity.GankIoDataBean;
import com.gdut.dkmfromcg.ansynclib.service.model.GankIoModel;
import com.gdut.dkmfromcg.ggmusic.R;
import com.gdut.dkmfromcg.ggmusic.adapter.GankIoAdapter;
import com.gdut.dkmfromcg.ggmusic.base.BaseLoadingFragment;
import com.gdut.dkmfromcg.ggmusic.cache.ACache;
import com.gdut.dkmfromcg.ggmusic.data.ConstantString;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import rx.Subscription;

/**
 * A simple {@link Fragment} subclass.
 */
public  class CustomFragment extends BaseLoadingFragment {

    private static final String TAG = "CustomFragment";
    private static final String TYPE = "mType";

    private boolean mIsPrepared=false;
    private boolean mIsFirst = true;


    private ACache mACache;
    private GankIoDataBean mGankIoDataBean;
    private GankIoModel mGankIoModel;

    private String mType="all";
    private int mPage = 1;

    private XRecyclerView xRecyclerView;
    private GankIoAdapter gankIoAdapter;

    public CustomFragment() {
        // Required empty public constructor
    }

    public static CustomFragment newFragment(String type){
        CustomFragment fragment=new CustomFragment();
        Bundle args = new Bundle();
        args.putString(TYPE, type);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected int provideContentId() {
        return R.layout.fragment_custom;
    }

    @Override
    protected void findRootViewChildren(View rootView) {
        xRecyclerView=rootView.findViewById(R.id.xrecv_custom);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments()!=null){
            mType=getArguments().getString(TYPE);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mACache = ACache.get(getContext());
        mGankIoModel = new GankIoModel();

        //禁止下拉刷新
        /*xRecyclerView.setPullRefreshEnabled(false);*/
        xRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        xRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.SquareSpin);
        gankIoAdapter=new GankIoAdapter(getActivity());
        xRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                //下拉刷新
                mPage=1;
                loadCustomData();
            }

            @Override
            public void onLoadMore() {
                //上拉加载
                mPage++;
                loadCustomData();
            }
        });
        // 准备就绪
        mIsPrepared = true;
        if (mIsVisible){
            loadData();
        }
    }

    @Override
    protected void loadData() {
        if (!mIsPrepared || !mIsVisible || !mIsFirst) {
            return;
        }

        if (mGankIoDataBean != null
                && mGankIoDataBean.getResults() != null
                && mGankIoDataBean.getResults().size() > 0) {
            showContentView();
            mGankIoDataBean= (GankIoDataBean) mACache.getAsObject("gank_custom");
            setAdapter(mGankIoDataBean);
        } else {
            loadCustomData();
        }

    }

    private void setAdapter(GankIoDataBean mGankIoDataBean) {
        gankIoAdapter.clear();
        gankIoAdapter.addAll(mGankIoDataBean.getResults());
        xRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        xRecyclerView.setAdapter(gankIoAdapter);
        gankIoAdapter.notifyDataSetChanged();
        xRecyclerView.refreshComplete();

        mIsFirst=false;
    }

    @Override
    protected void onRefresh() {
        loadCustomData();
    }

    private void loadCustomData() {
        mGankIoModel.setData(mType, mPage,20 );
        mGankIoModel.getGankIoData(new RequestCallback() {
            @Override
            public void loadSuccess(Object object) {
                showContentView();
                GankIoDataBean gankIoDataBean = (GankIoDataBean) object;

                if (mPage == 1) {
                    if (gankIoDataBean != null && gankIoDataBean.getResults() != null && gankIoDataBean.getResults().size() > 0) {
                        setAdapter(gankIoDataBean);

                        mACache.remove(ConstantString.INSTANCE.getGANK_CUSTOM());
                        // 缓存50分钟
                        mACache.put(ConstantString.INSTANCE.getGANK_CUSTOM(), gankIoDataBean, 30000);
                    }
                } else {
                    if (gankIoDataBean != null && gankIoDataBean.getResults() != null && gankIoDataBean.getResults().size() > 0) {
                        //不是第一页的数据时，需要更新Recycler View的数据
                        xRecyclerView.refreshComplete();
                        gankIoAdapter.addAll(gankIoDataBean.getResults());
                        gankIoAdapter.notifyDataSetChanged();
                    } else {
                        //不是第一页的数据，但是 请求的数据为空时
                        xRecyclerView.loadMoreComplete();

                    }
                }
            }

            @Override
            public void loadFailed() {
                xRecyclerView.refreshComplete();
                if (gankIoAdapter.getItemCount()==0){
                    showError();
                }

                if (mPage>1){
                    mPage--;
                }
            }

            @Override
            public void addSubscription(Subscription subscription) {
                CustomFragment.this.addSubscription(subscription);
            }
        });

    }

}
