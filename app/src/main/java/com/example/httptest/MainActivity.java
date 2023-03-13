package com.example.httptest;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import android.view.View;

import com.example.httptest.http.Myhttp;
import com.example.httptest.http.Myokhttp;
import com.example.httptest.http.Myrefhttp;

public class MainActivity extends AppCompatActivity {

    private Myhttp myhttp;
    private Myokhttp myokhttp;
    private Myrefhttp myrefhttp;
    private String url = "http://172.20.10.3:8080/nvm/adduser";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myhttp = new Myhttp();
        myokhttp = new Myokhttp();
        myrefhttp = new Myrefhttp();
        requestPermission();
    }

    public void httpGet(View view){
        Log.e("==================", "HttpGet请求");
        myhttp.doGet(url);
    }

    public void httpPut(View view){
        Log.e("==================", "HttpPost请求");
        myhttp.doPost(url);
    }

    public void httpPost(View view){

    }

    public void httpDelete(View view){

    }

    public void okhttpGet(View view){
        Log.e("==================", "OKHttpGet请求");
        new Thread(){
            @Override
            public void run() {
                Log.e("同步: ", "步骤1");
                myokhttp.doGet(url);
                Log.e("同步: ", "步骤2");
            }
        }.start();
        Log.e("同步: ", "步骤3");
    }

    public void okhttpPut(View view){
        Log.e("==================", "OKHttpPut请求");
        Log.e("异步: ", "步骤1");
        myokhttp.doPut(url);
        Log.e("异步: ", "步骤2");
    }

    public void okhttpPost(View view){
        Log.e("==================", "OKHttpPost请求");
        myokhttp.doPost(url);
    }

    public void okhttpDelete(View view){
        Log.e("==================", "OKHttpDelete请求");
        myokhttp.doDelete(url);
    }

    public void refhttpGet(View view){
        Log.e("==================", "RefHttpGet请求");
        myrefhttp.doGet();
    }

    public void refhttpPost(View view){
        Log.e("==================", "RefHttpPost请求");
        myrefhttp.doPost();
    }


//==========================================================================..........................................................
    //android 11 申请权限
    private int REQUEST_CODE = 0;
    /**
     * 文件权限
     */
    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            // 先判断有没有权限
            if (Environment.isExternalStorageManager()) {
                writeFile();
            } else {
                Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                intent.setData(Uri.parse("package:" + getApplication().getPackageName()));
                startActivityForResult(intent, REQUEST_CODE);
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // 先判断有没有权限
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                writeFile();
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE);
            }
        } else {
            writeFile();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                writeFile();
            } else {
//                log.d("存储权限获取失败");
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (Environment.isExternalStorageManager()) {
                writeFile();
            } else {
//                LogUtils.d("存储权限获取失败");
            }
        }
    }

    /**
     * 模拟文件写入
     */
    private void writeFile() {
//        LogUtils.d("存储权限获取成功");
    }

}