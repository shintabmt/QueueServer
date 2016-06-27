package com.example.queueserver;

import android.app.Application;

import com.example.queueserver.managers.ServerManager;

/**
 * Created by shintabmt on 6/27/2016.
 */
public class QueueApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        ServerManager.getInstance().start();
    }

}
