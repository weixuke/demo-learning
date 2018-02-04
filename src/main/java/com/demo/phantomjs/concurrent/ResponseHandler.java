package com.demo.phantomjs.concurrent;

public interface ResponseHandler {
    void handleMessage(String key,String result);
}
