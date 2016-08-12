package com.appcenter.angel_in_korea;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Youngdo on 2016-08-05.
 */
public class ChattingActivity extends AppCompatActivity {
    private Socket mSocket;
    private int numUsers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_chatting);
        ChatApplication app = (ChatApplication) getApplication();
        mSocket = app.getSocket();
        mSocket.on("login", onLogin);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSocket.off("login", onLogin);
    }

    private Emitter.Listener onLogin = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            JSONObject data = (JSONObject) args[0];


            try {
                numUsers = data.getInt("numUsers");
            } catch (JSONException e) {
                return;
            }
        }
    };
}
