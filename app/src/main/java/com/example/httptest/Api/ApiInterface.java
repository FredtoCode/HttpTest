package com.example.httptest.Api;

import com.example.httptest.bean.refhttpBean;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiInterface {

    @GET("/nvm/displayhttp/{id}")
    Call<String> getResult(@Path("id") String id);

    @FormUrlEncoded
    @POST("/nvm/adduser")
    Call<refhttpBean> postResult(@Field("username") String username, @Field("password") String password);
}
