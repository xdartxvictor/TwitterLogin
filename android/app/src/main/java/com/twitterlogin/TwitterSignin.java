package com.twitterlogin;


import android.app.Activity;

import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;

/**
 * Created by victoraliaga on 7/12/17.
 */

public class TwitterSignin extends ReactContextBaseJavaModule {

    public TwitterSignin(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @Override
    public String getName() {
        return "TwitterLoginAndroid";
    }

    @ReactMethod
    public void login(ReadableMap config, Callback successCallback, Callback cancelCallback) {
        Activity currentActivity = getCurrentActivity();

        if (currentActivity == null) {
            cancelCallback.invoke("Activity doesn't exist");
            return;
        }else{
            successCallback.invoke("Activity exist");
        }
    }
}
