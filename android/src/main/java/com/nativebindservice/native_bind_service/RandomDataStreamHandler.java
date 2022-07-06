package com.nativebindservice.native_bind_service;

import android.os.Handler;

import java.util.Random;

import io.flutter.plugin.common.EventChannel;

class RandomDataStreamHandler implements EventChannel.StreamHandler {

    private static RandomDataStreamHandler instance;

    public static void initInstance() {
        if (instance == null) {
            instance = new RandomDataStreamHandler();
        }
    }

    public static RandomDataStreamHandler getInstance() {
        return instance;
    }

    static EventChannel.EventSink sink = null;

    @Override
    public void onListen(Object arguments, EventChannel.EventSink events) {
        sink = events;
    }

    @Override
    public void onCancel(Object arguments) {
        sink = null;

    }

}
