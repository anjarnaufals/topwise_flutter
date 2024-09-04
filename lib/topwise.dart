import 'topwise_platform_interface.dart';

class Topwise {
  Future<String?> getPlatformVersion() {
    return TopwisePlatform.instance.getPlatformVersion();
  }

  Future<void> requestPermission() {
    return TopwisePlatform.instance.requestPermission();
  }

  Future<String?> swipeCard() {
    return TopwisePlatform.instance.swipeCard();
  }
}
