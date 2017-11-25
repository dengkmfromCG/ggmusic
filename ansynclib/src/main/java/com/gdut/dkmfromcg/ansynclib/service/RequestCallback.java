package com.gdut.dkmfromcg.ansynclib.service;

import rx.Subscription;

/**
 * Created by dkmFromCG on 2017/10/13.
 * function: 用于请求数据的回调
 */

public interface RequestCallback {

    void loadSuccess(Object object);

    void loadFailed();

    void addSubscription(Subscription subscription);

}
