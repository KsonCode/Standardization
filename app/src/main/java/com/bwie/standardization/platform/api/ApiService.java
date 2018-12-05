package com.bwie.standardization.platform.api;

import com.bwie.standardization.platform.common.Constants;
import com.bwie.standardization.platform.entity.BaseResponseEnitty;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Author:kson
 * E-mail:19655910@qq.com
 * Time:2018/05/05
 * Description:
 */
public interface ApiService {
//    @GET("data/content.json")
//    Observable<BaseResponseEnitty> downloadFile();
    @GET("data/content.json")
    Observable<ResponseBody> downloadFile();
}
