import 'dart:convert';

import 'package:easy_flutter_amap/src/map/location/enum/my_location_style.dart';
import 'package:easy_flutter_amap/src/map/location/enum/my_location_type.dart';
import 'package:flutter/services.dart';

class AmapViewController {
  late final MethodChannel _mapChannel;
  late final EventChannel _markerClickedEventChannel;
  late final EventChannel _mapClickedEventChannel;

  AmapViewController() {
    // _mapChannel = MethodChannel('cn.jjvu.xiaop/map');
    _mapChannel = MethodChannel('easy_flutter_amap');
    // _markerClickedEventChannel = EventChannel('cn.jjvu.xiaop/marker_clicked');
    // _mapClickedEventChannel = EventChannel('cn.jjvu.xiaop/map_clicked');
  }

  void dispose() {}

  /// 设置我的位置
  Future setMyLocationStyle(MyLocationStyle style) {
    final _styleJson =
    jsonEncode(style?.toJson() ?? MyLocationStyle().toJson());

    return _mapChannel.invokeMethod(
      'setMyLocationStyle',
      _styleJson,
    );
  }

  /// 设置我的位置
  Future setMyLocationType(MyLocationType myLocationType) {
    return _mapChannel.invokeMethod(
      'setMyLocationType',
      myLocationType.val,
    );
  }
}