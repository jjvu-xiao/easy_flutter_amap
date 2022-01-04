import 'dart:convert';

import 'package:easy_flutter_amap/src/map/location/enum/my_location_style.dart';
import 'package:flutter/services.dart';

class AmapViewController {
  final MethodChannel _mapChannel;
  final EventChannel _markerClickedEventChannel;
  final EventChannel _mapClickedEventChannel;

  AmapViewController.withId(int id)
      : _mapChannel = MethodChannel('cn.jjvu.xiaop/map$id'),
        _markerClickedEventChannel = EventChannel('cn.jjvu.xiaop/marker_clicked$id'),
        _mapClickedEventChannel = EventChannel('cn.jjvu.xiaop/map_clicked$id');

  void dispose() {}

  /// 设置我的位置
  Future setMyLocationStyle(MyLocationStyle  style) {
    final _styleJson =
    jsonEncode(style?.toJson() ?? MyLocationStyle().toJson());

    return _mapChannel.invokeMethod(
      'map#setMyLocationStyle',
      {'myLocationStyle': _styleJson},
    );
  }
}