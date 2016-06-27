package com.example.queueserver.managers.thread;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.StringTokenizer;

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
        PrintWriter os;
        try{
//
            BufferedReader is = new BufferedReader(new InputStreamReader(mSocket.getInputStream()));
            String currentLine = is.readLine();
            StringTokenizer tokenizer = new StringTokenizer(currentLine);
            String method = tokenizer.nextToken();
            String query = tokenizer.nextToken();
            Log.d("HttpResponseThread", "method: " +method + "  query: " + query);

        }catch (Exception e){
            e.printStackTrace();
        }

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
                    if (request instanceof HttpEntityEnclosingRequest){
                        conn.receiveRequestEntity((HttpEntityEnclosingRequest)request);
                        HttpEntity entity = ((HttpEntityEnclosingRequest)request).getEntity();
                        Log.d("HttpResponseThread", EntityUtils.toString(entity));
                    }

                } catch (HttpException e) {
                    e.printStackTrace();
                } catch (Exception e){
                    e.printStackTrace();
                }

//                is = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//                request = is.readLine();
//                while((request = is.readLine()).length() !=0){
//                    Log.d("HttpResponseThread", request);
//                }
                os = new PrintWriter(mSocket.getOutputStream(), true);

                JSONObject object = new JSONObject();
                try {
                    object.put("abc", 1);
                    object.put("456","man");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
//                String response =
//                        "<html><head></head>" +
//                                "<body>" +
//                                "<h1>" + "TESTTTTTTTTTTTTTTTTTTTT" + "</h1>" +
//                                "</body></html>";
//
//                os.print("HTTP/1.0 200" + "\r\n");
//                os.print("Content type: text/html" + "\r\n");
//                os.print("Content length: " + response.length() + "\r\n");
//                os.print("\r\n");
//                os.print(response + "\r\n");
                os.print(object.toString());
                os.flush();
                mSocket.close();

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return;
    }

}
