package com.appcenter.angel_in_korea;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

/**
 * Created by Youngdo on 2016-08-05.
 */
public class LoginActivity extends AppCompatActivity {
    CallbackManager callbackManager;
    LoginButton facebook_LoginButton;
    AccessToken accessToken;
    private Socket mSocket;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private int numUsers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login);
        ChatApplication app = (ChatApplication) getApplication();
        mSocket = app.getSocket();
        pref = getSharedPreferences("NAME", MODE_PRIVATE);
        editor = pref.edit();
        callbackManager = CallbackManager.Factory.create();
        facebook_LoginButton = (LoginButton) findViewById(R.id.login_button);
        facebook_LoginButton.setReadPermissions(Arrays.asList("user_location"));
        facebook_LoginButton.setReadPermissions(Arrays.asList("user_birthday"));
        facebook_LoginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                GraphRequest graphRequest = GraphRequest.newMeRequest(accessToken = AccessToken.getCurrentAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                try {
                                    Log.e("name", object.getString("name") + " " + object.getString("age_range") + " " + object.getString("location") + " " + object.getString("birthday"));
                                    editor.putString("name", object.getString("name"));
                                    editor.commit();
                                    mSocket.emit("add user", pref.getString("name", ""));
                                } catch (JSONException e) {

                                }
                            }
                        }
                );
                Bundle parameters = new Bundle();
                parameters.putString("fields", "name,age_range,location,birthday");
                graphRequest.setParameters(parameters);
                graphRequest.executeAsync();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
        mSocket.on("login", onLogin);
        if(!pref.getString("name","").equals("")){
            mSocket.emit("add user", pref.getString("name", ""));
            Intent intent = new Intent();
            intent.putExtra("username", pref.getString("name",""));
            intent.putExtra("numUsers", numUsers);
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSocket.off("login", onLogin);
    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
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

            Intent intent = new Intent();
            intent.putExtra("username", pref.getString("name",""));
            intent.putExtra("numUsers", numUsers);
            setResult(RESULT_OK, intent);
            finish();
        }
    };

}
