package com.appcenter.angel_in_korea;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.github.nkzawa.socketio.client.Socket;

/**
 * Created by Youngdo on 2016-07-29.
 */
public class RoominAcivity extends AppCompatActivity {
    Button room1, room2, room3;
    private Socket mSocket;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.room_activity);
        room1 = (Button) findViewById(R.id.button);
        room2 = (Button) findViewById(R.id.button2);
        room3 = (Button) findViewById(R.id.button3);
        ChatApplication app = (ChatApplication) this.getApplication();
        mSocket = app.getSocket();
        room1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSocket.emit("room in", "room1");
                finish();
            }
        });
        room2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSocket.emit("room in", "room2");
                finish();
            }
        });

        room3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSocket.emit("room in", "room3");
                finish();
            }
        });
    }
}
