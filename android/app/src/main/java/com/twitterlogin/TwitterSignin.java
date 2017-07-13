package com.twitterlogin;


import android.app.Activity;
import android.content.Intent;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeMap;
import com.twitter.sdk.android.core.DefaultLogger;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterConfig;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

/**
 * Created by victoraliaga on 7/12/17.
 */

public class TwitterSignin extends ReactContextBaseJavaModule {

    public TwitterLoginButton loginButton;
    private Callback callback = null;
    private static final int REQUEST_CODE = 112112;
    private final ReactApplicationContext reactContext;

    public TwitterSignin(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
    }

    @Override
    public String getName() {
        return "TwitterLoginAndroid";
    }

    public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {
        onActivityResult(requestCode, resultCode, data);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                sendCallback(true, false, false);
            } else if (resultCode == Activity.RESULT_CANCELED) {
                sendCallback(false, true, false);
            }
        }
        if (loginButton != null) {
            loginButton.onActivityResult(requestCode, resultCode, data);
            loginButton = null;
        }
    }

    public void onNewIntent(Intent intent) {
    }

    public void sendCallback(Boolean completed, Boolean cancelled, Boolean error) {
        if (callback != null) {
            callback.invoke(completed, cancelled, error);
            callback = null;
        }
    }


    @ReactMethod
    public void login(final Callback callback) {

        TwitterConfig config = new TwitterConfig.Builder(getReactApplicationContext())
                .logger(new DefaultLogger(Log.DEBUG))
                .twitterAuthConfig(new TwitterAuthConfig("0AYFJqHqbhWr2ivUx22G9OEQ2", "9L57HAEeEikrHjLSGxiWsUfqc3r7VpoXaSq5mlTSOZpYMambAm"))
                .debug(true)
                .build();
        Twitter.initialize(config);

        loginButton = new TwitterLoginButton(getCurrentActivity());
        Log.d("asd","initial");
        loginButton.setCallback(new com.twitter.sdk.android.core.Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> sessionResult) {
                WritableMap result = new WritableNativeMap();
                result.putString("authToken", sessionResult.data.getAuthToken().token);
                result.putString("authTokenSecret",sessionResult.data.getAuthToken().secret);
                result.putString("userID", sessionResult.data.getUserId()+"");
                result.putString("userName", sessionResult.data.getUserName());
                Log.d("asd","success");
                callback.invoke(result);
            }

            @Override
            public void failure(TwitterException exception) {
                Log.d("asd","error");
                exception.printStackTrace();
                callback.invoke(exception.getMessage());
            }
        });

        loginButton.performClick();
    }
}
