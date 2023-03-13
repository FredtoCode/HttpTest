package com.example.httptest.http;

import android.util.Log;

import com.example.httptest.Api.ApiInterface;
import com.example.httptest.bean.refhttpBean;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Myrefhttp {

    private ApiInterface apiInter = null;

    public Myrefhttp(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://172.20.10.3:8080") // 设置 网络请求 Url
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
                .build();

        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        apiInter = apiInterface;
    }

    public void doGet(){
        Call<String> call = apiInter.getResult("11");
        //步骤6:发送网络请求(异步)
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful()){//回调的方法执行在子线程。
                    String result = response.body();
                    Log.d("refhttp: " , result  + "\n" + response.headers());
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e( "refhttp错误信息: " , t.getMessage() );
            }
        });
    }

    public void doPost(){
        Call<refhttpBean> call = apiInter.postResult("哈哈哈哈", "123456");
        //步骤6:发送网络请求(异步)
        call.enqueue(new Callback<refhttpBean>() {
            @Override
            public void onResponse(Call<refhttpBean> call, Response<refhttpBean> response) {
                if(response.isSuccessful()){//回调的方法执行在子线程。
                    refhttpBean result = response.body();
                    Log.d("refhttp: " , result.usernameReturn + " " + result.passwordReturn + "\n" + response.headers());
                }
            }

            @Override
            public void onFailure(Call<refhttpBean> call, Throwable t) {

            }
        });
    }

}
