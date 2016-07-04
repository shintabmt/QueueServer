package com.example.queueserver.models;

import java.util.List;

/**
 * Created by shintabmt@gmai.com on 7/5/2016.
 */
public class QueueStatus extends ConnectionStatus {
    private List<QueueInfo> queueInfoList;

    public QueueStatus() {
    }

    public QueueStatus(String status, String error) {
        super(status, error);
    }

    public QueueStatus(String status, String error, List<QueueInfo> queueInfoList) {
        super(status, error);
        this.queueInfoList = queueInfoList;
    }

    public List<QueueInfo> getQueueInfoList() {
        return queueInfoList;
    }

    public void setQueueInfoList(List<QueueInfo> queueInfoList) {
        this.queueInfoList = queueInfoList;
    }
}
