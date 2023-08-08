package com.portkey.nativegoogleloginplugin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.portkey.nativegoogleloginactivity.NativeGoogleLoginActivity;

public class MainActivity extends AppCompatActivity {

    public class LoginCallback implements NativeGoogleLoginActivity.Callback {
        public void onResult(String authToken) {

        }
        public void onError(Exception e) {

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NativeGoogleLoginActivity.callback = new LoginCallback();
        NativeGoogleLoginActivity.clientId = "931335042992-ousd4tdbui5n2msmqj94ppp632a27ofv.apps.googleusercontent.com";
        NativeGoogleLoginActivity.Call(this);
    }
}