import 'package:flutter/services.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:native_bind_service/native_bind_service.dart';

void main() {
  const MethodChannel channel = MethodChannel('native_bind_service');

  TestWidgetsFlutterBinding.ensureInitialized();

  setUp(() {
    channel.setMockMethodCallHandler((MethodCall methodCall) async {
      return '42';
    });
  });

  tearDown(() {
    channel.setMockMethodCallHandler(null);
  });

  test('getPlatformVersion', () async {
    expect(await NativeBindService.platformVersion, '42');
  });
}
