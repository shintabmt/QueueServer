package com.example.queueserver;

import android.app.Application;

import com.example.queueserver.managers.ServerManager;
import com.example.queueserver.models.Customer;
import com.example.queueserver.models.QueueInfo;
import com.example.queueserver.models.QueueType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shintabmt on 6/27/2016.
 */
public class QueueApplication extends Application implements Constants {
    private static QueueApplication sInstance;
    private List<Customer> subscribeList = new ArrayList<>();
    public static QueueApplication getInstance() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ServerManager.getInstance().start();
        sInstance = this;
    }

    public List<Customer> getSubscribeList() {
        return subscribeList;
    }

//    public void setSubscribeList(List<Customer> subscribeList) {
//        this.subscribeList = subscribeList;
//    }

    public List<QueueInfo> getQueueInfo() {
        List<QueueInfo> queueInfos = new ArrayList<>();
        List<Customer> priorityList = new ArrayList<>();
        List<Customer> standardList = new ArrayList<>();

        for (Customer customer : subscribeList) {
            if (customer.getQueueType() == QueueType.PRIORITY) {
                priorityList.add(customer);
            } else {
                standardList.add(customer);
            }
        }

        for (int i = 0; i < priorityList.size(); i++) {
            QueueInfo queueInfo = new QueueInfo(i, priorityList.get(i));
            queueInfos.add(queueInfo);
        }

        for (int i = 0; i < standardList.size(); i++) {
            QueueInfo queueInfo = new QueueInfo(priorityList.size() + i, standardList.get(i));
            queueInfos.add(queueInfo);
        }
        return queueInfos;
    }

    public boolean isMaxPriorityQueue() {
        int totalPriority = 0;
        for (Customer customer : subscribeList) {
            if (customer.getQueueType() == QueueType.PRIORITY) {
                totalPriority += 1;
                if (totalPriority >= PRIORITY_MAX) {
                    return true;
                }
            }
        }
        return false;
    }

}
