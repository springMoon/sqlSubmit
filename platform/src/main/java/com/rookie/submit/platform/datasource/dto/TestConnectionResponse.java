package com.rookie.submit.platform.datasource.dto;

public class TestConnectionResponse {

    private boolean connected;
    private String message;

    public TestConnectionResponse() {
    }

    public TestConnectionResponse(boolean connected, String message) {
        this.connected = connected;
        this.message = message;
    }

    public boolean isConnected() {
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
