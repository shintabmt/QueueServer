package com.example.queueserver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.queueserver.managers.thread.HttpResponseThread;
import com.example.queueserver.models.Customer;
import com.example.queueserver.models.QueueInfo;
import com.example.queueserver.views.adapters.QueueAdapter;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView mTextView;
    private RecyclerView mRecyclerView;
    private QueueAdapter mAdapter;
    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String event = intent.getStringExtra("event");
            Customer customer = (Customer) intent.getSerializableExtra("message");
            String uid = intent.getStringExtra("uid");
            if (event.equalsIgnoreCase(HttpResponseThread.SUB_EVENT)){
                handleSub(uid);
            } else {
                handleQueue(customer);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextView = (TextView) findViewById(R.id.textView);
        mTextView.setText(getIpAddress());
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mAdapter = new QueueAdapter();
        mAdapter.setItems(QueueApplication.getInstance().getQueueInfo());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, new IntentFilter(HttpResponseThread.ACTION_EVENT));
    }

    @Override
    protected void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
    }

    private void handleQueue(Customer customer) {
        mAdapter.setItems(QueueApplication.getInstance().getQueueInfo());
    }

    private void handleSub(String uid){
        Toast.makeText(this,"Client with UID:  " + uid + " is connected to system", Toast.LENGTH_SHORT).show();
    }

    private String getIpAddress() {
        String ip = "";
        try {
            Enumeration<NetworkInterface> enumNetworkInterfaces = NetworkInterface
                    .getNetworkInterfaces();
            while (enumNetworkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = enumNetworkInterfaces
                        .nextElement();
                Enumeration<InetAddress> enumInetAddress = networkInterface
                        .getInetAddresses();
                while (enumInetAddress.hasMoreElements()) {
                    InetAddress inetAddress = enumInetAddress.nextElement();

                    if (inetAddress.isSiteLocalAddress()) {
                        ip += "Server Ip Address: "
                                + inetAddress.getHostAddress() + "\n";
                    }

                }

            }

        } catch (SocketException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            ip += "Something Wrong! " + e.toString() + "\n";
        }
        return ip;
    }
}
