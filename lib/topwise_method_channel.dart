import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';

import 'topwise_platform_interface.dart';

/// An implementation of [TopwisePlatform] that uses method channels.
class MethodChannelTopwise extends TopwisePlatform {
  /// The method channel used to interact with the native platform.
  @visibleForTesting
  final methodChannel = const MethodChannel('topwise');

  @override
  Future<String?> getPlatformVersion() async {
    final version =
        await methodChannel.invokeMethod<String>('getPlatformVersion');
    return version;
  }

  @override
  Future<void> requestPermission() async {
    await methodChannel.invokeMethod<void>('requestPermission');
  }

  @override
  Future<String?> swipeCard() async {
    return await methodChannel.invokeMethod<String>('swipeCard');
  }

  void setCallback(Function callback) {
    methodChannel.setMethodCallHandler((call) async {
      if (call.method == 'callbackMethod') {
        callback(call.arguments);
      }
    });
  }
}
