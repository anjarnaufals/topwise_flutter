package com.example.topwise;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.topwise.cloudpos.aidl.camera.AidlCameraScanCode;
import com.topwise.cloudpos.aidl.iccard.AidlICCard;
import com.topwise.cloudpos.aidl.magcard.AidlMagCard;
import com.topwise.cloudpos.aidl.printer.AidlPrinter;
import com.topwise.cloudpos.aidl.rfcard.AidlRFCard;
import com.topwise.cloudpos.aidl.shellmonitor.AidlShellMonitor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.embedding.engine.plugins.activity.ActivityAware;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry;

/** TopwisePlugin */
public class TopwisePlugin implements FlutterPlugin,
        MethodCallHandler,
        PluginRegistry.RequestPermissionsResultListener,
        PluginRegistry.ActivityResultListener,
        ActivityAware {
  /// The MethodChannel that will the communication between Flutter and native Android
  ///
  /// This local reference serves to register the plugin with the Flutter Engine and unregister it
  /// when the Flutter Engine is detached from the Activity
  static String dartChannel = "topwise";
  static String universaDartChannellCallback = "universalCallback";

  private MethodChannel channel;
  private DynamicPermissionTool permissionTool;
  private FlutterPluginBinding pluginBinding;
  private ActivityPluginBinding activityBinding;
  private Context context;
  private Activity activity;
  private final int    REQUEST_CODE1          = 0;

    private interface OperationOnPermission {
    void op(boolean granted, String permission);
  }

  private void performTaskWithCallback(MyCallback callback, String data) {

    callback.onTaskCompleted(data);
  }

  interface MyCallback {
    void onTaskCompleted(String data);
  }

  @Override
  public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
    Log.d("onAttachedToEngine", "onAttachedToEngine: Topwise Plugin Attached To Engine");

    pluginBinding = flutterPluginBinding;

    this.context = (Application) pluginBinding.getApplicationContext();

    DeviceServiceManager.getInstance().bindDeviceService(this.context);
    permissionTool = new DynamicPermissionTool(this.context);
    
    channel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), dartChannel);
    channel.setMethodCallHandler(this);

  }

  // TODO : construct every data needed to every feature
  // TODO : construct value to Magnetic Stripe (Swipe) data
  // TODO : construct value to IC Card data
  // TODO : construct value to RF Card (NFC Contacless) data
  // TODO : construct value to Scanner Camera (QR Scanner) data
  // TODO : construct value to printer data

  @Override
  public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
    if (call.method.equals("getPlatformVersion")) {
      result.success("Android " + android.os.Build.VERSION.RELEASE);

      return;
    }

    if (call.method.equals("requestPermission")) {
      requestPermission();

      return;
    }

    /*
      Magnetic Stripe Swipe
     */

    if (call.method.equals("swipeCard")) {
      AidlMagCard magCardDev = DeviceServiceManager.getInstance().getMagCardReader();

      new SwipeCardActivity(magCardDev).getTrackData(new SwipeCardActivity.SwipeCardCallback() {
        @Override
        public void onEventFinish(String value) {
          new MethodChannel(pluginBinding.getBinaryMessenger(), dartChannel)
                  .invokeMethod(universaDartChannellCallback, value);
        }
      });

      result.success(null);
      return;
    }

    if (call.method.equals("cancelSwipe")){
      AidlMagCard magCardDev = DeviceServiceManager.getInstance().getMagCardReader();

      new SwipeCardActivity(magCardDev).cancelSwipe(new SwipeCardActivity.SwipeCardCallback() {
        @Override
        public void onEventFinish(String value) {
          new MethodChannel(pluginBinding.getBinaryMessenger(), dartChannel)
                  .invokeMethod(universaDartChannellCallback, value);
        }
      });

      result.success(null);
      return;
    }

    /*
      End Magnetic Stripe Swipe
     */

    /*
      IC Card
     */

    if (call.method.equals("openICCard")){
      AidlICCard icCard = DeviceServiceManager.getInstance().getICCardReader();

      new ICCardActivity(icCard).open(new ICCardActivity.ICCardCallback() {
        @Override
        public void onEventFinish(String value) {
          new MethodChannel(pluginBinding.getBinaryMessenger(), dartChannel)
                  .invokeMethod(universaDartChannellCallback, value);
        }
      });

      result.success(null);
      return;
    }

    if (call.method.equals("closeICCard")){
      AidlICCard icCard = DeviceServiceManager.getInstance().getICCardReader();

      new ICCardActivity(icCard).close(new ICCardActivity.ICCardCallback() {
        @Override
        public void onEventFinish(String value) {
          new MethodChannel(pluginBinding.getBinaryMessenger(), dartChannel)
                  .invokeMethod(universaDartChannellCallback, value);
        }
      });

      result.success(null);
      return;
    }

    if (call.method.equals("isICCardExist")){
      AidlICCard icCard = DeviceServiceManager.getInstance().getICCardReader();

      new ICCardActivity(icCard).isExists(new ICCardActivity.ICCardCallback() {
        @Override
        public void onEventFinish(String value) {
          new MethodChannel(pluginBinding.getBinaryMessenger(), dartChannel)
                  .invokeMethod(universaDartChannellCallback, value);
        }
      });

      result.success(null);
      return;
    }

    if (call.method.equals("resetCard")){
      AidlICCard icCard = DeviceServiceManager.getInstance().getICCardReader();

      new ICCardActivity(icCard).cardReset(new ICCardActivity.ICCardCallback() {
        @Override
        public void onEventFinish(String value) {
          new MethodChannel(pluginBinding.getBinaryMessenger(), dartChannel)
                  .invokeMethod(universaDartChannellCallback, value);
        }
      });

      result.success(null);
      return;
    }

    if (call.method.equals("sendApduCom")){
      AidlICCard icCard = DeviceServiceManager.getInstance().getICCardReader();

      new ICCardActivity(icCard).apduComm(new ICCardActivity.ICCardCallback() {
        @Override
        public void onEventFinish(String value) {
          new MethodChannel(pluginBinding.getBinaryMessenger(), dartChannel)
                  .invokeMethod(universaDartChannellCallback, value);
        }
      });

      result.success(null);
      return;
    }

    if (call.method.equals("sendCustomApduCom")){
      AidlICCard icCard = DeviceServiceManager.getInstance().getICCardReader();

      String apduComCustom = call.argument("hexApdu");

      new ICCardActivity(icCard).apduCommCustom(new ICCardActivity.ICCardCallback() {
        @Override
        public void onEventFinish(String value) {
          new MethodChannel(pluginBinding.getBinaryMessenger(), dartChannel)
                  .invokeMethod(universaDartChannellCallback, value);
        }
      }, apduComCustom);

      result.success(null);
      return;
    }

    /*
      End IC Card
     */

    /*
      RF Card
     */

    if (call.method.equals("openRFCard")){
      AidlRFCard rfcard = DeviceServiceManager.getInstance().getRfCardReader();

      new RFCardActivity(rfcard).open(new RFCardActivity.RFCardCallback() {
        @Override
        public void onEventFinish(String value) {
          new MethodChannel(pluginBinding.getBinaryMessenger(), dartChannel)
                  .invokeMethod(universaDartChannellCallback, value);
        }
      });

      result.success(null);
      return;
    }

    if (call.method.equals("closeRFCard")){
      AidlRFCard rfcard = DeviceServiceManager.getInstance().getRfCardReader();

      new RFCardActivity(rfcard).close(new RFCardActivity.RFCardCallback() {
        @Override
        public void onEventFinish(String value) {
          new MethodChannel(pluginBinding.getBinaryMessenger(), dartChannel)
                  .invokeMethod(universaDartChannellCallback, value);
        }
      });

      result.success(null);
      return;
    }

    if (call.method.equals("isRFCardExists")){
      AidlRFCard rfcard = DeviceServiceManager.getInstance().getRfCardReader();

      new RFCardActivity(rfcard).isExists(new RFCardActivity.RFCardCallback() {
        @Override
        public void onEventFinish(String value) {
          new MethodChannel(pluginBinding.getBinaryMessenger(), dartChannel)
                  .invokeMethod(universaDartChannellCallback, value);
        }
      });

      result.success(null);
      return;
    }

    if (call.method.equals("getUidRFCard")){
      AidlRFCard rfcard = DeviceServiceManager.getInstance().getRfCardReader();

      new RFCardActivity(rfcard).getUid(new RFCardActivity.RFCardCallback() {
        @Override
        public void onEventFinish(String value) {
          new MethodChannel(pluginBinding.getBinaryMessenger(), dartChannel)
                  .invokeMethod(universaDartChannellCallback, value);
        }
      });

      result.success(null);
      return;
    }

    if (call.method.equals("readRFCardType")){
      AidlRFCard rfcard = DeviceServiceManager.getInstance().getRfCardReader();

      new RFCardActivity(rfcard).readCardType(new RFCardActivity.RFCardCallback() {
        @Override
        public void onEventFinish(String value) {
          new MethodChannel(pluginBinding.getBinaryMessenger(), dartChannel)
                  .invokeMethod(universaDartChannellCallback, value);
        }
      });

      result.success(null);
      return;
    }

    /*
      End RF Card
     */

    /*
      QR Code
     */
    if (call.method.equals("openQRScanner")){
      AidlCameraScanCode cameraScanCode =DeviceServiceManager.getInstance().getCameraManager();

      new QrCodeScannerActivity(cameraScanCode).backScan(new QrCodeScannerActivity.QrCodeScannerCallback() {
        @Override
        public void onEventFinish(String value) {
          new MethodChannel(pluginBinding.getBinaryMessenger(), dartChannel)
                  .invokeMethod(universaDartChannellCallback, value);
        }
      });

      result.success(null);
      return;
    }

    if (call.method.equals("stopQRScanner")){
      AidlCameraScanCode cameraScanCode =DeviceServiceManager.getInstance().getCameraManager();

      new QrCodeScannerActivity(cameraScanCode).stopScan(new QrCodeScannerActivity.QrCodeScannerCallback() {
        @Override
        public void onEventFinish(String value) {
          new MethodChannel(pluginBinding.getBinaryMessenger(), dartChannel)
                  .invokeMethod(universaDartChannellCallback, value);
        }
      });

      result.success(null);
      return;
    }
    /*
      End QR Code
    */

    /*
      Printer
    */
    if (call.method.equals("getPrintState")){
      AidlPrinter aidlPrinter =DeviceServiceManager.getInstance().getPrintManager();

      new PrintDevActivity(aidlPrinter, this.context).getPrintState(new PrintDevActivity.PrintDevCallBack() {
        @Override
        public void onEventFinish(String value) {
          new MethodChannel(pluginBinding.getBinaryMessenger(), dartChannel)
                  .invokeMethod(universaDartChannellCallback, value);
        }
      });

      result.success(null);
      return;
    }

    if (call.method.equals("printTickertape")){
      AidlPrinter aidlPrinter =DeviceServiceManager.getInstance().getPrintManager();

      new PrintDevActivity(aidlPrinter, this.context).printTickertape(new PrintDevActivity.PrintDevCallBack() {
        @Override
        public void onEventFinish(String value) {
          new MethodChannel(pluginBinding.getBinaryMessenger(), dartChannel)
                  .invokeMethod(universaDartChannellCallback, value);
        }
      }, this.context);

      result.success(null);
      return;
    }
    /*
      End Printer
    */

    /*
      Shell CMD
     */
      if(call.method.equals("getHardwareSN")){
        AidlShellMonitor aidlShellMonitor = DeviceServiceManager.getInstance().getShellMonitor();

        new ShellCmdActivity(aidlShellMonitor).getHardwareSNPlaintext(new ShellCmdActivity.ShellCmdCallback() {
          @Override
          public void onEventFinish(String value) {
            new MethodChannel(pluginBinding.getBinaryMessenger(), dartChannel)
                    .invokeMethod(universaDartChannellCallback, value);
          }
        });

        result.success(null);
        return;
      }
    /*
      End Shell CMD
     */
  }


  @Override
  public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
    DeviceServiceManager.getInstance().unBindDeviceService();
    channel.setMethodCallHandler(null);
  }


  @Override
  public boolean onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    return false;
  }

  @Override
  public boolean onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

    boolean isAllGranted = permissionTool.isAllPermissionGranted(grantResults);
    if (requestCode == REQUEST_CODE1){

      if (!isAllGranted) {
        return false;
      }else {
        return  true;
      }
    }else{
     return  false;
    }

  }

  @Override
  public void onAttachedToActivity(@NonNull ActivityPluginBinding binding) {
    Log.d("onAttachedToActivity", "onAttachedToActivity");
    activityBinding = binding;
    activity = binding.getActivity();
    activityBinding.addRequestPermissionsResultListener(this);
    activityBinding.addActivityResultListener(this);
  }

  @Override
  public void onDetachedFromActivityForConfigChanges() {

  }

  @Override
  public void onReattachedToActivityForConfigChanges(@NonNull ActivityPluginBinding binding) {

  }

  @Override
  public void onDetachedFromActivity() {

  }

  private void requestPermission() {
    boolean isAllGranted = permissionTool.isAllPermissionGranted(permissionTool.permissions);

    if (!isAllGranted) {
      String[] deniedPermissions = permissionTool.getDeniedPermissions(permissionTool.permissions);
        permissionTool.requestNecessaryPermissions(activity, deniedPermissions, REQUEST_CODE1);
    }
  }
}
