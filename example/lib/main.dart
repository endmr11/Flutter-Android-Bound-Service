import 'dart:async';

import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:native_bind_service/native_bind_service.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatefulWidget {
  const MyApp({Key? key}) : super(key: key);

  @override
  State<MyApp> createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  String _platformVersion = 'Unknown';

  @override
  void initState() {
    super.initState();
    initPlatformState();
  }

  Future<void> initPlatformState() async {
    String platformVersion;
    try {
      platformVersion = await NativeBindService.platformVersion ?? 'Unknown platform version';
    } on PlatformException {
      platformVersion = 'Failed to get platform version.';
    }

    if (!mounted) return;

    setState(() {
      _platformVersion = platformVersion;
    });
  }

  Future<void> startBoundService() async {
    try {
      await NativeBindService.startBoundService;
    } on PlatformException {
      debugPrint("");
    }
  }

  Future<void> stopBoundService() async {
    try {
      await NativeBindService.stopBoundService;
    } on PlatformException {
      debugPrint("");
    }
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        body: Column(
          children: [
            Text('Running on: $_platformVersion\n'),
            StreamBuilder<dynamic>(
              stream: NativeBindService.getRandomDataStream,
              builder: (BuildContext context, AsyncSnapshot<dynamic> snapshot) {
                if (snapshot.hasData) {
                  return Text("Veri: ${snapshot.data}");
                } else {
                  return const Text("Yükleniyor...");
                }
              },
            ),
            ElevatedButton(onPressed: startBoundService, child: const Text("START")),
            ElevatedButton(onPressed: stopBoundService, child: const Text("STOP")),
          ],
        ),
      ),
    );
  }
}
