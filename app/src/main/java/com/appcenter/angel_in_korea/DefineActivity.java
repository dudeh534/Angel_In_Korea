package com.appcenter.angel_in_korea;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by Youngdo on 2016-08-05.
 */
public class DefineActivity extends AppCompatActivity {
    Button Requester, Responser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_define);
        Requester = (Button) findViewById(R.id.REQUESTER);
        Responser = (Button) findViewById(R.id.RESPONSER);
        Requester.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Mainintent = new Intent(DefineActivity.this, MainActivity.class);
                startActivity(Mainintent);
                finish();
            }
        });
        Responser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Mainintent = new Intent(DefineActivity.this, MainActivity.class);
                startActivity(Mainintent);
                finish();
            }
        });
    }
}
