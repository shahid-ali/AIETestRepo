package com.shahid.aietest;

import android.app.Application;
import android.content.Intent;

import com.shahid.aietest.service.ConnectivityService;


public class AIEApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        startService(new Intent(getApplicationContext(), ConnectivityService.class));
    }
}
