package com.example.queueserver.managers.thread;

import com.example.queueserver.Constants;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by shintabmt on 6/27/2016.
 */
public class HttpServerThread extends Thread implements Constants {
    ServerSocket httpServerSocket;
    @Override
    public void run() {
        Socket socket = null;
        try {
            httpServerSocket = new ServerSocket(SERVER_PORT);
            while (true){
                socket = httpServerSocket.accept();
                HttpResponseThread responseThread = new HttpResponseThread(socket);
                responseThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
