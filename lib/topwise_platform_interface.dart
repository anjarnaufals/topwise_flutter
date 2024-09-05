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

  // Magnetic Stripe Feature
  Future<String?> swipeCard() {
    throw UnimplementedError('swipeCard() has not been implemented.');
  }

  Future<String?> cancelSwipe() {
    throw UnimplementedError('cancelSwipe() has not been implemented.');
  }
  // end Magnetic Stripe Feature

  // IC Card Feature
  Future<String?> openICCard() {
    throw UnimplementedError('openICCard() has not been implemented.');
  }

  Future<String?> closeICCard() {
    throw UnimplementedError('closeICCard() has not been implemented.');
  }

  Future<String?> isICCardExist() {
    throw UnimplementedError('isICCardExist() has not been implemented.');
  }
  // End IC Card Feature

  // RF Card Feature
  Future<String?> openRFCard() {
    throw UnimplementedError('openRFCard() has not been implemented.');
  }

  Future<String?> closeRFCard() {
    throw UnimplementedError('closeRFCard() has not been implemented.');
  }

  Future<String?> isRFCardExists() {
    throw UnimplementedError('isRFCardExists() has not been implemented.');
  }

  Future<String?> getUidRFCard() {
    throw UnimplementedError('getUidRFCard() has not been implemented.');
  }

  Future<String?> readRFCardType() {
    throw UnimplementedError('readRFCardType() has not been implemented.');
  }
  // End RF Card Feature

  // QR Scanner Feature
  Future<String?> openQRScanner() {
    throw UnimplementedError('openQRScanner() has not been implemented.');
  }

  Future<String?> stopQRScanner() {
    throw UnimplementedError('stopQRScanner() has not been implemented.');
  }
  // End QR Scanner Feature

  // Printer Feature
  Future<String?> getPrintState() {
    throw UnimplementedError('getPrintState() has not been implemented.');
  }

  Future<String?> printTickertape() {
    throw UnimplementedError('printTickertape() has not been implemented.');
  }
  // End Printer Feature
}
