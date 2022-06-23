
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
    this.myLocationType = MyLocationType.MAP_ROTATE_NO_CENTER,
    this.locationInterval = 2000,
    this.showZoom = true,
    this.showCompass = false,
    this.showScale = false,
    this.allGesturesEnabled,
    this.zoomGesturesEnabled = true,
    this.rotateGesturesEnabled = true,
    this.scrollGesturesEnabled = true,
    this.tiltGesturesEnabled = true,
    this.initialZoomLevel = 12.0,
    this.maxZoomLevel = 20.0,
    this.minZoomLevel = 3.0,
    this.customMapStyleId,
    this.onAMapViewCreated,
    required this.marker
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

  /// 定位类型
  final MyLocationType myLocationType;

  /// 是否显示缩放控件
  ///
  /// * 只针对Android
  final bool showZoom;

  /// 是否显示指南针
  final bool showCompass;

  /// 是否显示比例尺控件
  final bool showScale;

  /// 所有手势是否可用
  ///
  /// 为null或true时其余手势可以单独控制
  final bool? allGesturesEnabled;

  /// 缩放手势是否可用
  final bool zoomGesturesEnabled;

  /// 旋转手势是否可用
  final bool rotateGesturesEnabled;

  /// 拖拽手势是否可用
  final bool scrollGesturesEnabled;

  /// 倾斜手势是否可用
  final bool tiltGesturesEnabled;

  /// 初始缩放等级
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

  final Widget marker;


  @override
  Widget build(BuildContext context) {
    Map<String, dynamic> creationParams = <String, dynamic>{
      'autoLocateAfterInit': autoLocateAfterInit,
      'mapType': mapType.val,
      'mapLanguage': mapLanguage.val,
      'myLocationType': myLocationType.val,
      'locationInterval': locationInterval,
      'showZoom': showZoom,
      'showCompass': showCompass,
      'showScale': showScale,
      'allGesturesEnabled': allGesturesEnabled,
      'zoomGesturesEnabled': zoomGesturesEnabled,
      'rotateGesturesEnabled': rotateGesturesEnabled,
      'scrollGesturesEnabled': scrollGesturesEnabled,
      'tiltGesturesEnabled': tiltGesturesEnabled,
      'initialZoomLevel': initialZoomLevel,
      'maxZoomLevel': maxZoomLevel,
      'minZoomLevel': minZoomLevel,
      'customMapStyleId': customMapStyleId,
      'marker': marker,
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
      default:
        return Center(
          child: Text("不支持的平台"),
        );
    }
  }

  void _onViewCreated(int id) {
    final controller = AmapViewController();
    if (this.onAMapViewCreated != null) {
      this.onAMapViewCreated!(controller);
    }
  }
}
