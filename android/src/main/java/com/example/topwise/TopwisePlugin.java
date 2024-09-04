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

import com.topwise.cloudpos.aidl.magcard.AidlMagCard;

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
    
    channel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), "topwise");
    channel.setMethodCallHandler(this);

  }

  @Override
  public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
    if (call.method.equals("getPlatformVersion")) {
      result.success("Android " + android.os.Build.VERSION.RELEASE);

    } else if (call.method.equals("requestPermission")) {
      requestPermission();

    } else if (call.method.equals("swipeCard")) {
      AidlMagCard magCardDev = DeviceServiceManager.getInstance().getMagCardReader();

      new SwipeCardActivity(magCardDev).getTrackData(new SwipeCardActivity.SwipeCardCallback() {
        @Override
        public void onCardSwiped(String cardData) {
          new MethodChannel(pluginBinding.getBinaryMessenger(), "topwise")
                  .invokeMethod("callbackMethod", cardData);
        }
      });



      result.success(null);

    } else {
      result.notImplemented();
    }
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

//  private void ensurePermissions(List<String> permissions, OperationOnPermission operation)
//  {
//    // only request permission we don't already have
//    List<String> permissionsNeeded = new ArrayList<>();
//    for (String permission : permissions) {
//      if (permission != null && ContextCompat.checkSelfPermission(context, permission)
//              != PackageManager.PERMISSION_GRANTED) {
//        permissionsNeeded.add(permission);
//      }
//    }
//
//    // no work to do?
//    if (permissionsNeeded.isEmpty()) {
//      operation.op(true, null);
//      return;
//    }
//
//    askPermission(permissionsNeeded, operation);
//  }

//  private void askPermission(List<String> permissionsNeeded, OperationOnPermission operation)
//  {
//    // finished asking for permission? call callback
//    if (permissionsNeeded.isEmpty()) {
//      operation.op(true, null);
//      return;
//    }
//
//  }
}
