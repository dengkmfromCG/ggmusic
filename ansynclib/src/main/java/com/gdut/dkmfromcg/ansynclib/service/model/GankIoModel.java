package com.gdut.dkmfromcg.ansynclib.service.model;

import com.gdut.dkmfromcg.ansynclib.service.RequestCallback;
import com.gdut.dkmfromcg.ansynclib.service.RetrofitService;
import com.gdut.dkmfromcg.ansynclib.service.entity.GankIoDataBean;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by dkmFromCG on 2017/10/13.
 * function: 统一请求数据接口 ,不用每个地方都写请求的接口，更换接口方便。
 * 其实代码量也没有减少多少，但维护起来方便。
 *
 * 分类数据: http://gank.io/api/data/数据类型/请求个数/第几页
 */

public class GankIoModel {

    private String id;
    private int page;
    private int per_page;

    public void setData(String id, int page, int per_page) {
        this.id = id;
        this.page = page;
        this.per_page = per_page;
    }

    public void getGankIoData(final RequestCallback listener){
        Subscription subscription= RetrofitService.Builder.getGankIOServer().getGankIoData(id,page,per_page)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GankIoDataBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.loadFailed();
                    }

                    @Override
                    public void onNext(GankIoDataBean gankIoDataBean) {
                        listener.loadSuccess(gankIoDataBean);
                    }
                });
        listener.addSubscription(subscription);
    }
}
