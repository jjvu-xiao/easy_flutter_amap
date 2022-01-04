import 'package:easy_flutter_amap/src/map/view/amap_view_controller.dart';
import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';
import 'package:flutter/widgets.dart';

import '../location/enum/map_language.dart';
import '../location/enum/map_type.dart';
import '../location/enum/my_location_type.dart';

typedef void MapCreatedCallback(AmapViewController controller);

/// 高德地图
class AMapView extends StatelessWidget {
  const AMapView({
    Key? key,
    this.controller,
    this.autoLocateAfterInit = false,
    this.mapType = MapType.NORMAL,
    this.mapLanguage = MapLanguage.CHINESE,
    this.locationInterval = 2000,
    this.showTraffic = false,
    this.showBuildings = true,
    this.showMapText = true,
    this.showZoomControl = true,
    this.showCompass = false,
    this.showLocationButton = false,
    this.showScaleControl = false,
    this.showIndoorMap = false,
    this.showIndoorMapControl = false,
    this.allGestureEnable,
    this.zoomGestureEnable = true,
    this.rotateGestureEnable = true,
    this.scrollGestureEnable = true,
    this.tiltGestureEnable = true,
    this.isGestureScaleByMapCenter = false,
    this.initialZoomLevel = 12.0,
    this.maxZoomLevel = 20.0,
    this.minZoomLevel = 3.0,
    this.customMapStyleId,
    this.onAMapViewCreated,
  })  : assert(
            initialZoomLevel >= minZoomLevel &&
                initialZoomLevel <= maxZoomLevel,
            '初始化缩放等级不能小于最小缩放等级，并且不能大于最大缩放等级'),
        assert(maxZoomLevel <= 20, '最大缩放等级不能大于20'),
        assert(minZoomLevel >= 3, '最小缩放等级不能小于3'),
        super(key: key);

  final AmapViewController? controller;

  final MapCreatedCallback? onAMapViewCreated;

  final String viewType = 'AMapView';

  /// 初始化后是否自动定位
  final bool autoLocateAfterInit;

  /// 地图图层类型
  final MapType mapType;

  /// 底图语言
  final MapLanguage mapLanguage;

  /// 定位间隔，仅定位类型为连续定位时有效，单位毫秒
  final int locationInterval;

  /// 是否显示实时路况
  final bool showTraffic;

  /// 是否显示楼块
  final bool showBuildings;

  /// 是否显示底图文字标注
  ///
  /// * 只针对Android
  final bool showMapText;

  /// 是否显示缩放控件
  ///
  /// * 只针对Android
  final bool showZoomControl;

  /// 是否显示指南针
  final bool showCompass;

  /// 是否显示定位按钮
  ///
  /// * 只针对Android
  final bool showLocationButton;

  /// 是否显示比例尺控件
  final bool showScaleControl;

  /// 是否显示室内地图
  final bool showIndoorMap;

  /// 是否显示室内地图控件
  final bool showIndoorMapControl;

  /// 所有手势是否可用
  ///
  /// 为null或true时其余手势可以单独控制
  final bool? allGestureEnable;

  /// 缩放手势是否可用
  final bool zoomGestureEnable;

  /// 旋转手势是否可用
  final bool rotateGestureEnable;

  /// 拖拽手势是否可用
  final bool scrollGestureEnable;

  /// 倾斜手势是否可用
  final bool tiltGestureEnable;

  /// 是否以地图中心点缩放
  final bool isGestureScaleByMapCenter;

  /// 初始缩放等级
  ///
  /// 默认[3,19]
  ///
  /// 有室内地图时[3,20]
  final double initialZoomLevel;

  /// 最大缩放等级
  final double maxZoomLevel;

  /// 最小缩放等级
  final double minZoomLevel;

  /// 自定义地图id
  final String? customMapStyleId;


  @override
  Widget build(BuildContext context) {
    Map<String, dynamic> creationParams = <String, dynamic>{
      'autoLocateAfterInit': autoLocateAfterInit,
      'mapType': mapType.name,
      'mapLanguage': mapLanguage.name,
      'locationInterval': locationInterval,
      'showTraffic': showTraffic,
      'showBuildings': showBuildings,
      'showMapText': showMapText,
      'showZoomControl': showZoomControl,
      'showCompass': showCompass,
      'showLocationButton': showLocationButton,
      'showScaleControl': showScaleControl,
      'showIndoorMap': showIndoorMap,
      'showIndoorMapControl': showIndoorMapControl,
      'allGestureEnable': allGestureEnable,
      'zoomGestureEnable': zoomGestureEnable,
      'rotateGestureEnable': rotateGestureEnable,
      'scrollGestureEnable': scrollGestureEnable,
      'tiltGestureEnable': tiltGestureEnable,
      'isGestureScaleByMapCenter': isGestureScaleByMapCenter,
      'initialZoomLevel': initialZoomLevel,
      'maxZoomLevel': maxZoomLevel,
      'minZoomLevel': minZoomLevel,
      'customMapStyleId': customMapStyleId,
    };
    switch (defaultTargetPlatform) {
      case TargetPlatform.android:
        return AndroidView(
          viewType: viewType,
          creationParams: creationParams,
          creationParamsCodec: StandardMessageCodec(),
          onPlatformViewCreated: _onViewCreated,
        );
      case TargetPlatform.iOS:
        return UiKitView(
          viewType: viewType,
          creationParams: creationParams,
          creationParamsCodec: StandardMessageCodec(),
          onPlatformViewCreated: _onViewCreated,
        );
      case TargetPlatform.fuchsia:
        throw UnsupportedError('不支持 Fuchsia');
      case TargetPlatform.linux:
        throw UnsupportedError('不支持 Linux');
      case TargetPlatform.macOS:
        throw UnsupportedError('不支持 MacOS');
      case TargetPlatform.windows:
        throw UnsupportedError('不支持 Windows');
    }
  }

  void _onViewCreated(int id) {
    final controller = AmapViewController.withId(id);
    if (this.onAMapViewCreated != null) {
      this.onAMapViewCreated!(controller);
    }
  }
}
