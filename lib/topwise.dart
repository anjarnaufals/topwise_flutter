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

  Future<String?> cancelSwipe() {
    return TopwisePlatform.instance.cancelSwipe();
  }

  Future<String?> openICCard() {
    return TopwisePlatform.instance.openICCard();
  }

  Future<String?> closeICCard() {
    return TopwisePlatform.instance.closeICCard();
  }

  Future<String?> isICCardExist() {
    return TopwisePlatform.instance.isICCardExist();
  }

  Future<String?> resetCard() {
    return TopwisePlatform.instance.resetCard();
  }

  Future<String?> sendApduCom() {
    return TopwisePlatform.instance.sendApduCom();
  }

  Future<String?> sendCustomApduCom(String hexApdu) {
    return TopwisePlatform.instance.sendCustomApduCom(hexApdu);
  }

  Future<String?> openRFCard() {
    return TopwisePlatform.instance.openRFCard();
  }

  Future<String?> closeRFCard() {
    return TopwisePlatform.instance.closeRFCard();
  }

  Future<String?> isRFCardExists() {
    return TopwisePlatform.instance.isRFCardExists();
  }

  Future<String?> getUidRFCard() {
    return TopwisePlatform.instance.getUidRFCard();
  }

  Future<String?> readRFCardType() {
    return TopwisePlatform.instance.readRFCardType();
  }

  Future<String?> openQRScanner() {
    return TopwisePlatform.instance.openQRScanner();
  }

  Future<String?> stopQRScanner() {
    return TopwisePlatform.instance.stopQRScanner();
  }

  Future<String?> getPrintState() {
    return TopwisePlatform.instance.getPrintState();
  }

  Future<String?> printTickertape() {
    return TopwisePlatform.instance.printTickertape();
  }

  Future<String?> getHardwareSN() {
    return TopwisePlatform.instance.getHardwareSN();
  }

  void universalCallback(Function callback) =>
      TopwisePlatform.instance.universalCallback(callback);
}
