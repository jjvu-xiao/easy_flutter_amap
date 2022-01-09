import Flutter
import UIKit

public class SwiftEasyFlutterAmapPlugin: NSObject, FlutterPlugin{
  public static func register(with registrar: FlutterPluginRegistrar) {
    let channel = FlutterMethodChannel(name: "easy_flutter_amap", binaryMessenger: registrar.messenger())
    let instance = SwiftEasyFlutterAmapPlugin()
    registrar.addMethodCallDelegate(instance, channel: channel)
    let factory = EasyFlutterAmapFactory(messenger: registrar.messenger())
    registrar.register(factory, withId: "AMapView")
  }

  public func handle(_ call: FlutterMethodCall, result: @escaping FlutterResult) {
    result(FlutterMethodNotImplemented)
  }
}
