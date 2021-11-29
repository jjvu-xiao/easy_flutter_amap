
import 'dart:async';

import 'package:flutter/services.dart';

class EasyFlutterAmap {
  static const MethodChannel _channel =
      const MethodChannel('easy_flutter_amap');

  static Future<String> get platformVersion async {
    final String version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }
}
