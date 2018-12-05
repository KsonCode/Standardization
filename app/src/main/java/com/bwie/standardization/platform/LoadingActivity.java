package com.bwie.standardization.platform;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bwie.standardization.platform.utils.AndroidBug54971Workaround;

public class LoadingActivity extends AppCompatActivity {

    private Handler mHandler;
    private final int FLAG = 1;
    private final int TIME = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        AndroidBug54971Workaround.assistActivity(findViewById(android.R.id.content));
        init();
    }

    private void init() {
        mHandler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {

                startActivity(new Intent(LoadingActivity.this,MainActivity.class));
                LoadingActivity.this.finish();
                return true;
            }
        });
        mHandler.sendEmptyMessageDelayed(FLAG,TIME);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }
}
