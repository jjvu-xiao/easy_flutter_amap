import 'dart:convert';

import 'package:easy_flutter_amap/src/common/entity/entity_api.dart';

import '../location/enum/enum_api.dart';
import 'package:flutter/services.dart';

class AmapViewController {
  
  late final MethodChannel _mapChannel = MethodChannel('easy_flutter_amap');
  late final EventChannel _markerClickedEventChannel;
  late final EventChannel _mapClickedEventChannel;

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
  
  /// [zoomLevel] - 缩放等级
  Future<void> setZoomLevel(double zoomLevel) async {
    await _mapChannel.invokeMethod('setZoomLevel', zoomLevel);
  }

  /// 获取当前缩放等级
  Future<double> getZoomLevel() async {
    return await _mapChannel.invokeMethod('getZoomLevel');
  }

  /// 放大缩放等级
  Future<void> zoomIn() async {
    await _mapChannel.invokeMethod('zoomIn');
  }

  /// 缩小缩放等级
  Future<void> zoomOut() async {
    await _mapChannel.invokeMethod('zoomOut');
  }

  /// 设置最大缩放等级
  Future<void> setMaxZoomLevel(double level) async {
    await _mapChannel.invokeMethod('setMaxZoomLevel', level);
  }

  /// 设置最小缩放等级
  Future<void> setMinZoomLevel(double level) async {
    await _mapChannel.invokeMethod('setMinZoomLevel', level);
  }

  /// 获取最大缩放等级
  Future<double> getMaxZoomLevel() async {
    return await _mapChannel.invokeMethod('getMaxZoomLevel');
  }

  /// 获取最小缩放等级
  Future<double> getMinZoomLevel() async {
    return await _mapChannel.invokeMethod('getMinZoomLevel');
  }

  /// 设置地图图层类型
  ///
  /// [type] - 地图图层类型
  Future<void> setMapType(MapType type) async {
    await _mapChannel.invokeMethod('setMapType', type.name);
  }

  /// 获取地图图层类型
  Future<MapType> getMapType() async {
    String typeString = await _mapChannel.invokeMethod('getMapType');
    switch (typeString) {
      case 'NORMAL':
        return MapType.NORMAL;
      case 'NIGHT':
        return MapType.NIGHT;
      case 'NAVI':
        return MapType.NAVI;
      case 'BUS':
        return MapType.BUS;
      case 'SATELLITE':
        return MapType.SATELLITE;
      default:
        return MapType.NORMAL;
    }
  }

  /// 打开/关闭实时路况
  ///
  /// [on] - 打开/关闭实时路况
  Future<void> turnOnTraffic(bool on) async {
    await _mapChannel.invokeMethod('turnOnTraffic', on);
  }

  /// 实时路况是否打开
  Future<bool> isTrafficOn() async {
    return await _mapChannel.invokeMethod('isTrafficOn');
  }

  /// 打开/关闭楼块
  ///
  /// [on] - 打开/关闭楼块
  Future<void> turnOnBuildings(bool on) async {
    await _mapChannel.invokeMethod('turnOnBuildings', on);
  }

  /// 楼块是否打开
  ///
  /// * 只针对iOS
  Future<bool> isBuildingsOn() async {
    return await _mapChannel.invokeMethod('isBuildingsOn');
  }

  /// 打开/关闭地图文字标注
  ///
  /// [on] - 打开/关闭楼块
  ///
  /// * 只针对Android
  Future<void> turnOnMapText(bool on) async {
    await _mapChannel.invokeMethod('turnOnMapText', on);
  }

  /// 设置底图语言
  ///
  /// [language] - 底图语言
  Future<void> setMapLanguage(MapLanguage language) async {
    await _mapChannel.invokeMethod('setMapLanguage', language.name);
  }

  /// 获取底图语言
  ///
  /// * 只针对iOS
  Future<MapLanguage> getMapLanguage() async {
    String languageString = await _mapChannel.invokeMethod('getMapLanguage');
    switch (languageString) {
      case 'CHINESE':
        return MapLanguage.CHINESE;
      case 'ENGLISH':
        return MapLanguage.ENGLISH;
      default:
        return MapLanguage.CHINESE;
    }
  }

  /// 设置定位类型
  ///
  /// [type] - 定位类型
  Future<void> setLocationType(MyLocationType type) async {
    await _mapChannel.invokeMethod('setLocationType', type.val);
  }

  /// 获取定位类型
  Future<MyLocationType> getLocationType() async {
    String typeString = await _mapChannel.invokeMethod('getLocationType');
    switch (typeString) {
      case 'SHOW':
        return MyLocationType.SHOW;
      case 'LOCATE':
        return MyLocationType.LOCATE;
      case 'FOLLOW':
        return MyLocationType.FOLLOW;
      case 'MAP_ROTATE':
        return MyLocationType.MAP_ROTATE;
      case 'LOCATION_ROTATE':
        return MyLocationType.LOCATION_ROTATE;
      case 'LOCATION_ROTATE_NO_CENTER':
        return MyLocationType.LOCATION_ROTATE_NO_CENTER;
      case 'FOLLOW_NO_CENTER':
        return MyLocationType.FOLLOW_NO_CENTER;
      case 'MAP_ROTATE_NO_CENTER':
        return MyLocationType.MAP_ROTATE_NO_CENTER;
      default:
        return MyLocationType.SHOW;
    }
  }

  /// 设置定位间隔
  ///
  /// [interval] - 定位间隔，单位毫秒
  Future<void> setLocationInterval(int interval) async {
    await _mapChannel.invokeMethod('setLocationInterval', interval);
  }

  /// 获取定位间隔
  Future<int> getLocationInterval() async {
    return await _mapChannel.invokeMethod('getLocationInterval');
  }

  /// 设置是否显示缩放按钮
  ///
  /// [on] - 是否显示
  ///
  /// * 只针对Android
  Future<void> turnOnZoomControl(bool on) async {
    await _mapChannel.invokeMethod('turnOnZoomControl', on);
  }

  /// 缩放按钮是否显示
  ///
  /// * 只针对Android
  Future<bool> isZoomControlOn() async {
    return await _mapChannel.invokeMethod('isZoomControlOn');
  }

  /// 设置是否显示指南针
  ///
  /// [on] - 是否显示
  Future<void> turnOnCompass(bool on) async {
    await _mapChannel.invokeMethod('turnOnCompass', on);
  }

  /// 指南针是否显示
  Future<bool> isCompassOn() async {
    return await _mapChannel.invokeMethod('isCompassOn');
  }

  /// 设置是否显示定位按钮
  ///
  /// [on] - 是否显示
  ///
  /// * 只针对Android
  Future<void> turnOnLocationButton(bool on) async {
    await _mapChannel.invokeMethod('turnOnLocationButton', on);
  }

  /// 定位按钮是否显示
  ///
  /// * 只针对Android
  Future<bool> isLocationButtonOn() async {
    return await _mapChannel.invokeMethod('isLocationButtonOn');
  }

  /// 设置是否显示比例尺控件
  ///
  /// [on] - 是否显示
  Future<void> turnOnScaleControl(bool on) async {
    await _mapChannel.invokeMethod('turnOnScaleControl', on);
  }

  /// 比例尺控件是否显示
  Future<bool> isScaleControlOn() async {
    return await _mapChannel.invokeMethod('isScaleControlOn');
  }

  /// 设置是否启用所有手势
  ///
  /// [enable] - 是否启用
  ///
  /// * 只针对Android
  Future<void> enableAllGesture(bool enable) async {
    await _mapChannel.invokeMethod('enableAllGesture', enable);
  }

  /// 所有手势是否启用
  ///
  /// * 只针对Android
  Future<bool> isAllGestureEnable() async {
    return await _mapChannel.invokeMethod('isAllGestureEnable');
  }

  /// 设置是否启用缩放手势
  ///
  /// [enable] - 是否启用
  Future<void> enableZoomGesture(bool enable) async {
    await _mapChannel.invokeMethod('enableZoomGesture', enable);
  }

  /// 缩放手势是否启用
  Future<bool> isZoomGestureEnable() async {
    return await _mapChannel.invokeMethod('isZoomGestureEnable');
  }

  /// 设置是否启用旋转手势
  ///
  /// [enable] - 是否启用
  Future<void> enableRotateGesture(bool enable) async {
    await _mapChannel.invokeMethod('enableRotateGesture', enable);
  }

  /// 旋转手势是否启用
  Future<bool> isRotateGestureEnable() async {
    return await _mapChannel.invokeMethod('isRotateGestureEnable');
  }

  /// 设置是否启用拖拽手势
  ///
  /// [enable] - 是否启用
  Future<void> enableScrollGesture(bool enable) async {
    await _mapChannel.invokeMethod('enableScrollGesture', enable);
  }

  /// 拖拽手势是否启用
  Future<bool> isScrollGestureEnable() async {
    return await _mapChannel.invokeMethod('isScrollGestureEnable');
  }

  /// 设置是否启用倾斜手势
  ///
  /// [enable] - 是否启用
  Future<void> enableTiltGesture(bool enable) async {
    await _mapChannel.invokeMethod('enableTiltGesture', enable);
  }

  /// 倾斜手势是否启用
  Future<bool> isTiltGestureEnable() async {
    return await _mapChannel.invokeMethod('isTiltGestureEnable');
  }

  /// 设置Logo位置
  ///
  /// [logoPosition] - Logo位置
  ///
  /// * 只针对Android
  Future<void> setLogoPosition(LogoPosition logoPosition) async {
    await _mapChannel.invokeMethod('setLogoPosition', logoPosition.name);
  }

  /// 获取Logo位置
  ///
  /// * 只针对Android
  Future<LogoPosition> getLogoPosition() async {
    String logoPosition = await _mapChannel.invokeMethod('getLogoPosition');
    switch (logoPosition) {
      case "BOTTOM_LEFT":
        return LogoPosition.BOTTOM_LEFT;
      case "BOTTOM_CENTER":
        return LogoPosition.BOTTOM_CENTER;
      case "BOTTOM_RIGHT":
        return LogoPosition.BOTTOM_RIGHT;
      default:
        return LogoPosition.BOTTOM_LEFT;
    }
  }

  /// 设置Logo左下边距
  ///
  /// [logoMargin] - 左下边距
  ///
  /// * iOS上为递增效果
  Future<void> setLogoMargin(LogoMargin logoMargin) async {
    await _mapChannel.invokeMethod('setLogoMargin', logoMargin.toJson());
  }

  /// 设置缩放按钮位置
  ///
  /// [zoomPosition] - 缩放按钮位置
  ///
  /// * 只针对Android
  Future<void> setZoomPosition(ZoomPosition zoomPosition) async {
    await _mapChannel.invokeMethod('setZoomPosition', zoomPosition.name);
  }

  /// 获取缩放按钮位置
  ///
  /// * 只针对Android
  Future<ZoomPosition> getZoomPosition() async {
    String zoomPosition = await _mapChannel.invokeMethod('getZoomPosition');
    switch (zoomPosition) {
      case "RIGHT_BOTTOM":
        return ZoomPosition.RIGHT_BOTTOM;
      case "RIGHT_CENTER":
        return ZoomPosition.RIGHT_CENTER;
      default:
        return ZoomPosition.RIGHT_BOTTOM;
    }
  }

  /// 设置指南针右上边距(递增效果)
  ///
  /// [margin] - 右上边距
  ///
  /// * 只针对iOS
  Future<void> setCompassMargin(CompassMargin margin) async {
    await _mapChannel.invokeMethod('setCompassMargin', margin.toJson());
  }

  /// 设置比例尺左下边距(递增效果)
  ///
  /// [margin] - 左下边距
  ///
  /// * 只针对iOS
  Future<void> setScaleMargin(ScaleMargin margin) async {
    await _mapChannel.invokeMethod('setScaleMargin', margin.toJson());
  }

  /// 设置是否以地图中心点缩放
  ///
  /// [flag] - 是否以地图中心点缩放
  Future<void> setIsGestureScaleByMapCenterPosition(bool flag) async {
    await _mapChannel.invokeMethod('setIsGestureScaleByMapCenterPosition', flag);
  }

  /// 获取是否以地图中心点缩放
  Future<bool> getIsGestureScaleByMapCenterPosition() async {
    return await _mapChannel.invokeMethod('getIsGestureScaleByMapCenterPosition');
  }

  /// 设置自定义地图样式
  ///
  /// [id] - 自定义地图样式id
  Future<void> setCustomMapStyleId(String id) async {
    await _mapChannel.invokeMethod('setCustomMapStyleId', id);
  }

  /// 关闭自定义地图样式
  Future<void> disableCustomMapStyle() async {
    await _mapChannel.invokeMethod('disableCustomMapStyle');
  }

  /// 设置地图中心点
  ///
  /// [latLng] - 坐标点
  Future<void> setMapCenter(LatLng latLng) async {
    await _mapChannel.invokeMethod('setMapCenter', latLng.toJson());
  }

  /// 获取地图中心点
  Future<LatLng> getMapCenter() async {
    var latLngMap = await _mapChannel.invokeMethod('getMapCenter');
    return LatLng(
      latitude: latLngMap['latitude'] as double,
      longitude: latLngMap['longitude'] as double,
    );
  }

  /// 设置地图显示范围
  ///
  /// [bound] - 西南角和东北角
  ///
  /// * 只针对Android
  Future<void> setAndroidBound(List<LatLng> bound) async {
    await _mapChannel.invokeMethod(
      'setBound',
      bound.map((e) => e.toJson()).toList(),
    );
  }

  /// 设置地图显示范围
  ///
  /// [bound] - 西南角和东北角
  ///
  /// * 只针对iOS
  Future<void> setIOSBound(IosBound bound) async {
    await _mapChannel.invokeMethod(
      'setBound',
      bound.toJson(),
    );
  }

  /// 地图截屏
  Future<String> screenShot() async {
    return await _mapChannel.invokeMethod('screenShot');
  }
}