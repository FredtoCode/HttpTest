package com.example.httptest.http;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Myokhttp {

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
//    public static final MediaType JSON = MediaType.parse("application/x-www-form-urlencoded");

    public static void doGet(String url){
        //创建一个Request
        Request request = new Request.Builder()
                .url(url)
                .build();
        execute(request);
    }
    public static void doPost(String url){
        FormBody body = new FormBody.Builder()
                .add("username", "post")
                .add("password", "123456")
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        enqueue(request);
    }

    public static void doPut(String url){
        try {
            JSONObject args = new JSONObject();
            args.putOpt("username", "put");
            args.putOpt("password", "123456");
            RequestBody body = RequestBody.create(JSON, args.toString());
            Request request = new Request.Builder()
                    .url(url)
                    .put(body)
                    .build();
            enqueue(request);
        }catch (Exception e){
            Log.e("doPut: ", e.getMessage());
        }
    }

    public static void doDelete(String url){

        //FormBody
//        FormBody body = new FormBody.Builder()
//                .add("username", "delete")
//                .add("password", "123456")
//                .build();
//        JSONObject args = new JSONObject();
//        args.putOpt("username", "put");
//        args.putOpt("password", "123456");
//        RequestBody body = RequestBody.create(JSON, args.toString());
//        Request request = new Request.Builder()
//                .url(url)
//                .delete(body)
//                .build();
//        enqueue(request);

        //RequestBody
//        try {
//            JSONObject args = new JSONObject();
//            args.putOpt("username", "delete");
//            args.putOpt("password", "123456");
//            RequestBody body = RequestBody.create(JSON, args.toString());
//            Request request = new Request.Builder()
//                    .url(url)
//                    .delete(body)
//                    .build();
//            enqueue(request);
//        }catch (Exception e){
//            Log.e("doPut: ", e.getMessage());
//        }

        //MultipartBody
        File file = new File("/sdcard/Download/123.jpg");

        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"),file); //所有图片类型

        MultipartBody multipartBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file",file.getName(),requestBody).build();

        Request request= new Request.Builder()
                .url(url)
                .delete(multipartBody)
                .build();
        enqueue(request);

    }

    public static void enqueue(Request request){
        try {
            OkHttpClient client = new OkHttpClient();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.e( "okhttp & enqueue 错误信息: " , e.getMessage() );
                }
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if(response.isSuccessful()){//回调的方法执行在子线程。
                        String result = response.body().string();
                        Log.d("okhttp & enqueue: " , result  + "\n" + response.headers());
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void execute(Request request){
        try {
            OkHttpClient client = new OkHttpClient();
            Response response = client.newCall(request).execute();
            Log.d("okhttp & execute:  ", response.body().string());
        }catch (Exception e){
            Log.e("okhttp & execute:  ", e.getMessage());
        }
    }

}
