// import 'package:flutter/foundation.dart';
// import 'package:flutter/services.dart';
// import 'package:flutter/widgets.dart';
//
//
// /// 高德地图
// class AMapView extends StatelessWidget {
//   const AMapView({
//     Key? key,
//     this.controller,
//     this.autoLocateAfterInit = false,
//     this.mapType = MapType.NORMAL,
//     this.mapLanguage = MapLanguage.CHINESE,
//     this.locationType = LocationType.LOCATE,
//     this.locationInterval = 2000,
//     this.showTraffic = false,
//     this.showBuildings = true,
//     this.showMapText = true,
//     this.showZoomControl = true,
//     this.showCompass = false,
//     this.showLocationButton = false,
//     this.showScaleControl = false,
//     this.showIndoorMap = false,
//     this.showIndoorMapControl = false,
//     this.allGestureEnable,
//     this.zoomGestureEnable = true,
//     this.rotateGestureEnable = true,
//     this.scrollGestureEnable = true,
//     this.tiltGestureEnable = true,
//     this.isGestureScaleByMapCenter = false,
//     this.zoomPosition = ZoomPosition.RIGHT_BOTTOM,
//     this.logoPosition = LogoPosition.BOTTOM_LEFT,
//     this.logoMargin,
//     this.compassMargin,
//     this.scaleMargin,
//     this.initialZoomLevel = 12.0,
//     this.maxZoomLevel = 20.0,
//     this.minZoomLevel = 3.0,
//     this.customMapStyleId,
//     this.defaultCameraPosition,
//     this.androidBound,
//     this.iOSBound,
//   })  : assert(
//             initialZoomLevel >= minZoomLevel &&
//                 initialZoomLevel <= maxZoomLevel,
//             '初始化缩放等级不能小于最小缩放等级，并且不能大于最大缩放等级'),
//         assert(maxZoomLevel <= 20, '最大缩放等级不能大于20'),
//         assert(minZoomLevel >= 3, '最小缩放等级不能小于3'),
//         super(key: key);
//
//   final AMapViewController? controller;
//
//   final String viewType = 'AMapView';
//
//   /// 初始化后是否自动定位
//   final bool autoLocateAfterInit;
//
//   /// 地图图层类型
//   final MapType mapType;
//
//   /// 底图语言
//   final MapLanguage mapLanguage;
//
//   /// 定位类型
//   final LocationType locationType;
//
//   /// 定位间隔，仅定位类型为连续定位时有效，单位毫秒
//   final int locationInterval;
//
//   /// 是否显示实时路况
//   final bool showTraffic;
//
//   /// 是否显示楼块
//   final bool showBuildings;
//
//   /// 是否显示底图文字标注
//   ///
//   /// * 只针对Android
//   final bool showMapText;
//
//   /// 是否显示缩放控件
//   ///
//   /// * 只针对Android
//   final bool showZoomControl;
//
//   /// 是否显示指南针
//   final bool showCompass;
//
//   /// 是否显示定位按钮
//   ///
//   /// * 只针对Android
//   final bool showLocationButton;
//
//   /// 是否显示比例尺控件
//   final bool showScaleControl;
//
//   /// 是否显示室内地图
//   final bool showIndoorMap;
//
//   /// 是否显示室内地图控件
//   final bool showIndoorMapControl;
//
//   /// 所有手势是否可用
//   ///
//   /// 为null或true时其余手势可以单独控制
//   final bool? allGestureEnable;
//
//   /// 缩放手势是否可用
//   final bool zoomGestureEnable;
//
//   /// 旋转手势是否可用
//   final bool rotateGestureEnable;
//
//   /// 拖拽手势是否可用
//   final bool scrollGestureEnable;
//
//   /// 倾斜手势是否可用
//   final bool tiltGestureEnable;
//
//   /// 是否以地图中心点缩放
//   final bool isGestureScaleByMapCenter;
//
//   /// 缩放控件位置
//   ///
//   /// * 只针对Android
//   final ZoomPosition zoomPosition;
//
//   /// Logo位置
//   ///
//   /// * 只针对Android
//   final LogoPosition logoPosition;
//
//   /// Logo相对左下角边距，同时会影响比例尺控件的位置
//   ///
//   /// 不为null时LogoPosition属性无效
//   final LogoMargin? logoMargin;
//
//   /// 指南针相对右上角边距
//   ///
//   /// * 只针对iOS
//   final CompassMargin? compassMargin;
//
//   /// 比例尺相对左下角边距
//   ///
//   /// * 只针对iOS
//   final ScaleMargin? scaleMargin;
//
//   /// 初始缩放等级
//   ///
//   /// 默认[3,19]
//   ///
//   /// 有室内地图时[3,20]
//   final double initialZoomLevel;
//
//   /// 最大缩放等级
//   final double maxZoomLevel;
//
//   /// 最小缩放等级
//   final double minZoomLevel;
//
//   /// 自定义地图id
//   final String? customMapStyleId;
//
//   /// 视觉位置，用于设置默认显示区域
//   final CameraPosition? defaultCameraPosition;
//
//   /// Android显示范围，西南角和东北角
//   ///
//   /// * 只针对Android
//   final List<LatLng>? androidBound;
//
//   /// iOS显示范围，西南角和东北角，经纬度变化比例
//   ///
//   /// * 只针对iOS
//   final IosBound? iOSBound;
//
//   @override
//   Widget build(BuildContext context) {
//     Map<String, dynamic> creationParams = <String, dynamic>{
//       'autoLocateAfterInit': autoLocateAfterInit,
//       'mapType': mapType.name,
//       'mapLanguage': mapLanguage.name,
//       'locationType': locationType.name,
//       'locationInterval': locationInterval,
//       'showTraffic': showTraffic,
//       'showBuildings': showBuildings,
//       'showMapText': showMapText,
//       'showZoomControl': showZoomControl,
//       'showCompass': showCompass,
//       'showLocationButton': showLocationButton,
//       'showScaleControl': showScaleControl,
//       'showIndoorMap': showIndoorMap,
//       'showIndoorMapControl': showIndoorMapControl,
//       'allGestureEnable': allGestureEnable,
//       'zoomGestureEnable': zoomGestureEnable,
//       'rotateGestureEnable': rotateGestureEnable,
//       'scrollGestureEnable': scrollGestureEnable,
//       'tiltGestureEnable': tiltGestureEnable,
//       'isGestureScaleByMapCenter': isGestureScaleByMapCenter,
//       'zoomPosition': zoomPosition.name,
//       'logoPosition': logoPosition.name,
//       'logoMargin': logoMargin?.toJson(),
//       'compassMargin': compassMargin?.toJson(),
//       'scaleMargin': scaleMargin?.toJson(),
//       'initialZoomLevel': initialZoomLevel,
//       'maxZoomLevel': maxZoomLevel,
//       'minZoomLevel': minZoomLevel,
//       'customMapStyleId': customMapStyleId,
//       'defaultCameraPosition': defaultCameraPosition?.toJson(),
//       'androidBound': androidBound?.map((e) => e.toJson()).toList(),
//       'iOSBound': iOSBound?.toJson(),
//     };
//     switch (defaultTargetPlatform) {
//       case TargetPlatform.android:
//         return AndroidView(
//           viewType: viewType,
//           creationParams: creationParams,
//           creationParamsCodec: StandardMessageCodec(),
//         );
//       case TargetPlatform.iOS:
//         return UiKitView(
//           viewType: viewType,
//           creationParams: creationParams,
//           creationParamsCodec: StandardMessageCodec(),
//         );
//       case TargetPlatform.fuchsia:
//         throw UnsupportedError('Unsupported Fuchsia');
//       case TargetPlatform.linux:
//         throw UnsupportedError('Unsupported Linux');
//       case TargetPlatform.macOS:
//         throw UnsupportedError('Unsupported MacOS');
//       case TargetPlatform.windows:
//         throw UnsupportedError('Unsupported Windows');
//     }
//   }
// }
