package com.appcenter.angel_in_korea;

import android.content.Intent;
import android.os.*;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Youngdo on 2016-08-05.
 */
public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Handler handler = new Handler(){
            @Override
            public void handleMessage(android.os.Message msg) {
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        };

        handler.sendEmptyMessageDelayed(0, 3000);
    }
}
