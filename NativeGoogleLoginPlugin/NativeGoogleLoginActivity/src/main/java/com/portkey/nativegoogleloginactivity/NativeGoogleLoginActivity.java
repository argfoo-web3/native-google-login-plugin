package com.portkey.nativegoogleloginactivity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class NativeGoogleLoginActivity extends AppCompatActivity {

    public interface Callback {
        void onResult(String authToken);
        void onError(Exception e);
    }

    private static GoogleSignInClient googleSignInClient;
    public static Callback callback;
    public static String clientId;

    public static void SetCallback(Callback cb)
    {
        callback = cb;
    }

    private AnimationDrawable loadingAnimation;

    public static void Call(Activity activity)
    {
        // Creating an intent with the current activity and the activity we wish to start
        Intent myIntent = new Intent(activity, NativeGoogleLoginActivity.class);
        activity.startActivity(myIntent);
    }


    @Override
    protected void onStart() {
        super.onStart();

        ImageView rocketImage = (ImageView) findViewById(R.id.imageView);
        rocketImage.setBackgroundResource(R.drawable.loading);
        loadingAnimation = (AnimationDrawable) rocketImage.getBackground();
        loadingAnimation.start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_native_google_login);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestServerAuthCode(clientId)
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        googleSignInClient = GoogleSignIn.getClient(this, gso);

        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, 1000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == 1000) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            String authToken = account.getServerAuthCode();
            // Signed in successfully, show authenticated UI.
            this.callback.onResult(authToken);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.e("NativeGoogleLogin", e.getStackTrace().toString());
            this.callback.onError(e);
        }
        finish();
        googleSignInClient.signOut();
    }
}