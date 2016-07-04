package com.example.queueserver.models;


public class ConnectionStatus {
    private String status;
    private String error;

    public ConnectionStatus(String status, String error) {
        this.status = status;
        this.error = error;
    }
}
