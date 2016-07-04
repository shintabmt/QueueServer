package com.example.queueserver.managers;

import com.example.queueserver.managers.thread.HttpServerThread;

/**
 * Created by shintabmt on 6/27/2016.
 */
public class ServerManager {
    static  ServerManager sInstance;
    HttpServerThread serverThread;
    public static ServerManager getInstance(){
        if (sInstance == null){
            sInstance = new ServerManager();
        }
        return sInstance;
    }
    public void start(){
        if (serverThread == null){
            serverThread = new HttpServerThread();
        }
        serverThread.start();
    }
}
