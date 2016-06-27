package com.example.queueserver.managers.thread;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpEntityEnclosingRequest;
import cz.msebera.android.httpclient.HttpException;
import cz.msebera.android.httpclient.HttpRequest;
import cz.msebera.android.httpclient.impl.DefaultHttpServerConnection;
import cz.msebera.android.httpclient.params.BasicHttpParams;
import cz.msebera.android.httpclient.util.EntityUtils;

/**
 * Created by shintabmt on 6/27/2016.
 */
public class HttpResponseThread extends Thread {
    private Socket mSocket;
    public HttpResponseThread(Socket socket) {
        this.mSocket = socket;
    }

    @Override
    public void run() {
            BufferedReader is;
            PrintWriter os;
//            String request;
            try {
                DefaultHttpServerConnection conn = new DefaultHttpServerConnection();
                conn.bind(mSocket, new BasicHttpParams());
                HttpRequest request = null;
                try {
                    request = conn.receiveRequestHeader();
                } catch (HttpException e) {
                    e.printStackTrace();
                }
                try {
                    conn.receiveRequestEntity((HttpEntityEnclosingRequest)request);
                } catch (HttpException e) {
                    e.printStackTrace();
                }
                HttpEntity entity = ((HttpEntityEnclosingRequest)request).getEntity();
                Log.d("HttpResponseThread", EntityUtils.toString(entity));
//                is = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//                request = is.readLine();
//                while((request = is.readLine()).length() !=0){
//                    Log.d("HttpResponseThread", request);
//                }
                os = new PrintWriter(mSocket.getOutputStream(), true);

                String response =
                        "<html><head></head>" +
                                "<body>" +
                                "<h1>" + "TESTTTTTTTTTTTTTTTTTTTT" + "</h1>" +
                                "</body></html>";

                os.print("HTTP/1.0 200" + "\r\n");
                os.print("Content type: text/html" + "\r\n");
                os.print("Content length: " + response.length() + "\r\n");
                os.print("\r\n");
                os.print(response + "\r\n");
                os.flush();
                mSocket.close();

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return;
    }

}
