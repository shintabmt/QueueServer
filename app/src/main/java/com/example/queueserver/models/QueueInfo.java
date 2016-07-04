package com.example.queueserver.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by shintabmt on 7/4/2016.
 */
public class QueueInfo {
    private int queuePosition;
    private Customer customer;

    public QueueInfo(int queuePosition, Customer customer) {
        this.queuePosition = queuePosition;
        this.customer = customer;
    }

    public int getQueuePosition() {
        return queuePosition;
    }

    public void setQueuePosition(int queuePosition) {
        this.queuePosition = queuePosition;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
