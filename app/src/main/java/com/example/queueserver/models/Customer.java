package com.example.queueserver.models;

import java.io.Serializable;

/**
 * Created by shintabmt on 7/4/2016.
 */
public class Customer implements Serializable{
    private String name;
    private String uid;
    private QueueType queueType;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public QueueType getQueueType() {
        return queueType;
    }

    public void setQueueType(QueueType queueType) {
        this.queueType = queueType;
    }
}
