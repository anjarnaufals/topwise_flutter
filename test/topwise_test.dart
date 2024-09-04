import 'package:flutter_test/flutter_test.dart';
import 'package:topwise/topwise.dart';
import 'package:topwise/topwise_platform_interface.dart';
import 'package:topwise/topwise_method_channel.dart';
import 'package:plugin_platform_interface/plugin_platform_interface.dart';

class MockTopwisePlatform
    with MockPlatformInterfaceMixin
    implements TopwisePlatform {
  @override
  Future<String?> getPlatformVersion() => Future.value('42');

  @override
  Future<void> requestPermission() {
    // TODO: implement requestPermission
    throw UnimplementedError();
  }

  @override
  Future<String?> swipeCard() {
    // TODO: implement swipeCard
    throw UnimplementedError();
  }

  @override
  void setCallback(Function callback) {
    // TODO: implement setCallback
  }
}

void main() {
  final TopwisePlatform initialPlatform = TopwisePlatform.instance;

  test('$MethodChannelTopwise is the default instance', () {
    expect(initialPlatform, isInstanceOf<MethodChannelTopwise>());
  });

  test('getPlatformVersion', () async {
    Topwise topwisePlugin = Topwise();
    MockTopwisePlatform fakePlatform = MockTopwisePlatform();
    TopwisePlatform.instance = fakePlatform;

    expect(await topwisePlugin.getPlatformVersion(), '42');
  });
}
