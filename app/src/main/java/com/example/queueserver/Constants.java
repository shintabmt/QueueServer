package com.example.queueserver;

/**
 * Created by shintabmt on 6/27/2016.
 */
public interface Constants {
    int SERVER_PORT = 8888;
     String POST = "POST";
    String GET = "GET";
    String SUBSCRIBE_REQUEST_PATH = "/customer/subscribe";
    String QUEUE_REQUEST_PATH = "/customer/queue";
    String CONTENT_LENGTH_HEADER = "Content-Length: ";
    int PRIORITY_MAX = 10;
}
