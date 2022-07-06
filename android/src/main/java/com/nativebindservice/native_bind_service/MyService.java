package com.nativebindservice.native_bind_service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;

import androidx.annotation.Nullable;

import java.util.Random;

public class MyService extends Service {
    boolean isStarting = false;
    Handler handler = null;
    Runnable runnable = () -> senNewRandomData();

    public MyService() {

    }

    void senNewRandomData() {
        int randomData = new Random().nextInt(9);
        RandomDataStreamHandler.sink.success("Eren:  " + randomData);
        handler.postDelayed(runnable, 1000);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        System.out.println("MyService onCreate ");
        handler = new Handler();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        System.out.println("MyService onStartCommand ");
        isStarting = true;
        handler.post(runnable);
        return START_STICKY;

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.out.println("MyService onDestroy ");
        isStarting = false;
        handler.removeCallbacks(runnable);
    }
}
