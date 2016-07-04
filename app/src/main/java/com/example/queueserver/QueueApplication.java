package com.example.queueserver;

import android.app.Application;

import com.example.queueserver.managers.ServerManager;
import com.example.queueserver.models.Customer;
import com.example.queueserver.models.QueueInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shintabmt on 6/27/2016.
 */
public class QueueApplication extends Application{
    private static QueueApplication sInstance;
    private List<Customer> subscribeList = new ArrayList<>();
    public static QueueApplication getInstance(){
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

    public void setSubscribeList(List<Customer> subscribeList) {
        this.subscribeList = subscribeList;
    }

    public List<QueueInfo> getQueueInfo(){
        List<QueueInfo> queueInfos = new ArrayList<>();
        for (int i = 0; i <subscribeList.size(); i++){
            QueueInfo queueInfo = new QueueInfo(i, subscribeList.get(i));
            queueInfos.add(queueInfo);
        }
        return queueInfos;
    }
}
