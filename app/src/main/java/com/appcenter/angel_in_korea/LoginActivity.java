package com.appcenter.angel_in_korea;

import android.content.Intent;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login);
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
                                    Intent MainIntent = new Intent(LoginActivity.this, DefineActivity.class);
                                    startActivity(MainIntent);
                                    finish();
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
    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

}
