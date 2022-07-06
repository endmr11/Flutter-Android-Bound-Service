import 'dart:async';

import 'package:flutter/services.dart';

class NativeBindService {
  static const MethodChannel _channel = MethodChannel('native_bind_service');
  static const EventChannel _randomDataChannel = EventChannel('example_event_channel');

  static Future<String?> get platformVersion async {
    final String? version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }

  static Stream<dynamic> get getRandomDataStream {
    return _randomDataChannel.receiveBroadcastStream().cast();
  }

  static Future<void> get startBoundService async {
    await _channel.invokeMethod('startBoundService');
  }

  static Future<void> get stopBoundService async {
    await _channel.invokeMethod('stopBoundService');
  }
}
