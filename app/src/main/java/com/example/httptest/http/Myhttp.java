package com.example.httptest.http;

import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class Myhttp {
  //  public static String urlPath="https://console-mock.apipost.cn/app/mock/project/a717252c-9043-40ce-94e4-9e12259b2628/get2.php";
  //    public static String urlPath="http://192.168.0.101:8080/nvm/display";//get请求
  //    public static String urlPath2="http://192.168.0.101:8080/nvm/adduser";//post请求

    public void doGet(String urlPath){
        new Thread() {
            @Override
            public void run() {
                String result="";
                BufferedReader reader=null;
                try {
                    //1.建立连接
                    HttpURLConnection httpURLConnection=null;
                    String url=urlPath;
                    URL requestURL=new URL(url);
                    httpURLConnection=(HttpURLConnection) requestURL.openConnection();
                    httpURLConnection.setRequestMethod("GET");
                    httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
//                    httpURLConnection.setRequestProperty("Content-Type", "multipart/form-data");
                    httpURLConnection.connect();

//                    DataOutputStream out = new DataOutputStream(httpURLConnection
//                            .getOutputStream());
//                    JSONObject jsonObject = new JSONObject();
//                    jsonObject.putOpt("username", "getBody");
//                    jsonObject.putOpt("password", "123456");
//                    out.writeBytes(jsonObject.toString());
//                    out.flush();
//                    out.close();

                    //2.获取二进制流
                    InputStream inputStream =httpURLConnection.getInputStream();
                    //3.将二进制流包装
                    reader=new BufferedReader(new InputStreamReader(inputStream));

                    //4.从BufferedReader中一行行读取字符串。使用StringBuilder接受。
                    String line;
                    StringBuilder builder=new StringBuilder();

                    while ((line=reader.readLine())!=null)
                    {
                        builder.append(line);
                        builder.append("\n");
                    }
                    if(builder.length()==0){
                        Log.d("http: " , "返回数据为空");
                    }

                    //5.返回json对应的String数据。
                    result=builder.toString();
                    Log.d("http: " , result + " ");
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("http: " , e.getMessage());
                }
            }
        }.start();
    }

    //发送JSON字符串 如果成功则返回成功标识。
    public void doPost(String urlPath) {
        new Thread(){
            @Override
            public void run() {
                // HttpClient 6.0被抛弃了
                String result = "";
                BufferedReader reader = null;
                try {
                    URL postUrl = new URL(urlPath);
                    // 打开连接
                    HttpURLConnection connection = (HttpURLConnection) postUrl.openConnection();

                    // 设置是否向connection输出，因为这个是post请求，参数要放在
                    // http正文内，因此需要设为true
                    connection.setDoOutput(true);
                    // Read from the connection. Default is true.
                    connection.setDoInput(true);
                    // 默认是 GET方式
                    connection.setRequestMethod("POST");

                    // Post 请求不能使用缓存
                    connection.setUseCaches(false);

                    connection.setInstanceFollowRedirects(true);

                    // 配置本次连接的Content-type，配置为application/x-www-form-urlencoded的
                    // 意思是正文是urlencoded编码过的form参数，下面我们可以看到我们对正文内容使用URLEncoder.encode
                    // 进行编码
                    connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                    // 连接，从postUrl.openConnection()至此的配置必须要在connect之前完成，
                    // 要注意的是connection.getOutputStream会隐含的进行connect。
                    connection.connect();

                    DataOutputStream out = new DataOutputStream(connection
                            .getOutputStream());
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.putOpt("username", "post");
                    jsonObject.putOpt("password", "123456");
                    out.writeBytes(jsonObject.toString());
                    out.flush();
                    out.close();

                    InputStream inputStream =connection.getInputStream();
                    reader = new BufferedReader(new InputStreamReader(inputStream));

                    String line;
                    StringBuilder builder=new StringBuilder();

                    while ((line=reader.readLine())!=null)
                    {
                        builder.append(line);
                    }
                    if(builder.length()==0){
                        Log.d("http: " , "返回数据为空");
                    }
                    result=builder.toString();
                    System.out.println(result);
                    reader.close();
                    connection.disconnect();
                    Log.d("http: " , result);
                } catch (Exception e) {
                    Log.e("http: " , e.getMessage());
                }
            }
        }.start();
    }

}
