package com.example.queueserver.managers.thread;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.example.queueserver.Constants;
import com.example.queueserver.QueueApplication;
import com.example.queueserver.models.ConnectionStatus;
import com.example.queueserver.models.Customer;
import com.example.queueserver.models.QueueInfo;
import com.example.queueserver.models.QueueStatus;
import com.example.queueserver.models.QueueType;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;


/**
 * Created by shintabmt on 6/27/2016.
 */
public class HttpResponseThread extends Thread implements Constants {
    private Socket mSocket;
    public static final String ACTION_EVENT = "com.example.ACTION_EVENT";
    public static final String SUB_EVENT = "com.example.SUB_EVENT";
    public static final String QUEUE_EVENT = "com.example.QUEUE_EVENT";

    public HttpResponseThread(Socket socket) {
        this.mSocket = socket;
    }

    @Override
    public void run() {
        try {
            BufferedReader is = new BufferedReader(new InputStreamReader(mSocket.getInputStream()));
            String currentLine = is.readLine();
            StringTokenizer tokenizer = new StringTokenizer(currentLine);
            String method = tokenizer.nextToken();
            String query = tokenizer.nextToken();

            switch (method.toUpperCase()) {
                case POST:
                    int contentLength = 0;
                    while ((currentLine = is.readLine()).length() != 0) {
                        Log.d("HttpResponseThread", currentLine);
                        if (currentLine.startsWith(CONTENT_LENGTH_HEADER)) {
                            contentLength = Integer.parseInt(currentLine.substring((CONTENT_LENGTH_HEADER.length())));
                        }
                    }
                    StringBuilder body = new StringBuilder();
                    for (int i = 0; i < contentLength; i++) {
                        int c = is.read();
                        body.append((char) c);
                    }
                    Log.d("HttpResponseThread", "method: " + method + "  query: " + query + " body : " + body);
                    handlePostRequest(is, query, body.toString());
                    break;
                case GET:
                    // not handle this case
                    break;
                default:
                    break;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handlePostRequest(BufferedReader is, String query, String body) {
        PrintWriter os;
        String response = "";
        Gson gson = new GsonBuilder().disableHtmlEscaping().serializeNulls().create();
        try {
            os = new PrintWriter(mSocket.getOutputStream(), true);
            switch (query) {
                case SUBSCRIBE_REQUEST_PATH:
                    ConnectionStatus connectionStatus = new ConnectionStatus("ok", "");
                    response = gson.toJson(connectionStatus);
                    String uid = body.replace("uid=", "");
                    sendBroadCast(SUB_EVENT, null, uid);
                    break;
                case QUEUE_REQUEST_PATH:
                    Customer customer = gson.fromJson(body, Customer.class);
                    response = gson.toJson(handleQueueResponse(customer));
                    break;
                default:
                    break;
            }
            String statusLine = "HTTP/1.1 200 OK" + "\r\n";
            String contentType = "Content Type: application/json" + "\r\n";
            os.print(statusLine);
            os.print(contentType);
            os.print("\r\n");
            os.print(response + "\r\n");
            os.flush();
            os.close();
            is.close();
            mSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int positionInQueue(Customer customer) {
        List<Customer> customerList = QueueApplication.getInstance().getSubscribeList();
        if (customerList.isEmpty()) return -1;
        for (int i = 0; i < customerList.size(); i++) {
            Customer cus = customerList.get(i);
            if (cus.getUid().equalsIgnoreCase(customer.getUid())) {
                return i;
            }
        }
        return -1;
    }

    private QueueStatus handleQueueResponse(Customer customer) {
        QueueStatus queueStatus = new QueueStatus();
        int position = positionInQueue(customer);
        if (position == -1) {
            QueueApplication.getInstance().getSubscribeList().add(customer);
            sendBroadCast(QUEUE_EVENT, customer, null);
        }
        if (customer.getQueueType() == QueueType.PRIORITY) {
            if (QueueApplication.getInstance().isMaxPriorityQueue()) {
                queueStatus.setStatus("fail");
                queueStatus.setError("Priority queue is maxed, please wait");
            } else {
                queueStatus.setStatus("ok");
            }
        } else {
            queueStatus.setStatus("ok");
        }
        queueStatus.setQueueInfoList(QueueApplication.getInstance().getQueueInfo());
        return queueStatus;
    }

    private void sendBroadCast(String event, Customer message, String uid) {
        Intent intent = new Intent();
        intent.setAction(ACTION_EVENT);
        intent.putExtra("event", event);
        intent.putExtra("message", message);
        intent.putExtra("uid", uid);
        LocalBroadcastManager.getInstance(QueueApplication.getInstance()).sendBroadcast(intent);
    }
}
