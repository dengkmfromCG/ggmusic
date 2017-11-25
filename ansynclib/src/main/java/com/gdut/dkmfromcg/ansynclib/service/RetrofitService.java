package com.gdut.dkmfromcg.ansynclib.service;

import com.gdut.dkmfromcg.ansynclib.service.entity.GankIoDataBean;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by dkmFromCG on 2017/10/12.
 * function:
 */

public interface RetrofitService {

    //解耦
    class Builder{

        public static RetrofitService getGankIOServer(){
            return RetrofitHttpHelper.getInstance().getGankIOServer(RetrofitService.class);
        }

    }

    /**
     * 分类数据: http://gank.io/api/data/数据类型/请求个数/第几页
     * 数据类型： 福利 | Android | iOS | 休息视频 | 拓展资源 | 前端 | all
     * 请求个数： 数字，大于0
     * 第几页：数字，大于0
     * eg: http://gank.io/api/data/Android/10/1
     */
    @GET("data/{type}/{pre_page}/{page}")
    Observable<GankIoDataBean> getGankIoData(@Path("type") String id, @Path("page") int page, @Path("pre_page") int pre_page);

}
