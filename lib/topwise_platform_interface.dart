import 'package:plugin_platform_interface/plugin_platform_interface.dart';

import 'topwise_method_channel.dart';

abstract class TopwisePlatform extends PlatformInterface {
  /// Constructs a TopwisePlatform.
  TopwisePlatform() : super(token: _token);

  static final Object _token = Object();

  static TopwisePlatform _instance = MethodChannelTopwise();

  /// The default instance of [TopwisePlatform] to use.
  ///
  /// Defaults to [MethodChannelTopwise].
  static TopwisePlatform get instance => _instance;

  /// Platform-specific implementations should set this with their own
  /// platform-specific class that extends [TopwisePlatform] when
  /// they register themselves.
  static set instance(TopwisePlatform instance) {
    PlatformInterface.verifyToken(instance, _token);
    _instance = instance;
  }

  Future<String?> getPlatformVersion() {
    throw UnimplementedError('platformVersion() has not been implemented.');
  }

  Future<void> requestPermission() {
    throw UnimplementedError('requestPermission() has not been implemented.');
  }

  Future<String?> swipeCard() {
    throw UnimplementedError('swipeCard() has not been implemented.');
  }
}
