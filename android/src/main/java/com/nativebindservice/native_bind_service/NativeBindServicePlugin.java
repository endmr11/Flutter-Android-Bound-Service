package com.nativebindservice.native_bind_service;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;

import androidx.annotation.NonNull;

import java.util.Random;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.embedding.engine.plugins.activity.ActivityAware;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.EventChannel;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry;

public class NativeBindServicePlugin implements FlutterPlugin, ActivityAware, PluginRegistry.RequestPermissionsResultListener,
        PluginRegistry.ActivityResultListener, MethodCallHandler {
    private MethodChannel channel;
    private EventChannel randomDataEventChannel;
    private FlutterPluginBinding flutterPluginBinding;
    private ActivityPluginBinding activityPluginBinding;
    Activity activity;
    Context context;


    @Override
    public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
        this.flutterPluginBinding = flutterPluginBinding;
        context = flutterPluginBinding.getApplicationContext();
    }

    public static void registerWith(PluginRegistry.Registrar registrar) {
        final NativeBindServicePlugin instance = new NativeBindServicePlugin();
        instance.setupChannels(registrar.messenger(), registrar.activity());
        registrar.addActivityResultListener(instance);
        registrar.addRequestPermissionsResultListener(instance);
    }

    @Override
    public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
        Intent intent = new Intent(context, MyService.class);
        if (call.method.equals("getPlatformVersion")) {
            result.success("Android " + android.os.Build.VERSION.RELEASE);
        } else if (call.method.equals("startBoundService")) {
            context.startService(intent);
            result.success("startBoundService");
        } else if (call.method.equals("stopBoundService")) {
            context.stopService(intent);
            result.success("stopBoundService");
        } else {
            result.notImplemented();
        }
    }

    @Override
    public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
        channel.setMethodCallHandler(null);
    }

    @Override
    public void onAttachedToActivity(@NonNull ActivityPluginBinding binding) {
        this.activityPluginBinding = binding;
        setupChannels(flutterPluginBinding.getBinaryMessenger(), binding.getActivity());

    }

    @Override
    public void onDetachedFromActivityForConfigChanges() {

    }

    @Override
    public void onReattachedToActivityForConfigChanges(@NonNull ActivityPluginBinding binding) {
        onAttachedToActivity(binding);
    }

    @Override
    public void onDetachedFromActivity() {

    }

    @Override
    public boolean onActivityResult(int requestCode, int resultCode, Intent data) {
        return false;
    }

    @Override
    public boolean onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        return false;
    }

    private void setupChannels(BinaryMessenger messenger, Activity activity) {
        if (activityPluginBinding != null) {
            activityPluginBinding.addActivityResultListener(this);
            activityPluginBinding.addRequestPermissionsResultListener(this);
        }
        this.activity = activity;
        channel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), "native_bind_service");
        channel.setMethodCallHandler(this);
        randomDataEventChannel = new EventChannel(flutterPluginBinding.getBinaryMessenger(), "example_event_channel");
        randomDataEventChannel.setStreamHandler(new RandomDataStreamHandler());
    }
}

