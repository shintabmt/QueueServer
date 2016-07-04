package com.example.queueserver.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by shintabmt on 7/4/2016.
 */
public enum QueueType {
    @SerializedName("0")
    STANDARD,
    @SerializedName("1")
    PRIORITY;
}
