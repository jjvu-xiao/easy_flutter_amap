import 'package:flutter/material.dart';

import 'my_location_type.dart';

/// 我的位置选项
@immutable
class MyLocationOption {
  MyLocationOption({
    this.show = true,
    this.myLocationType = MyLocationType.LOCATE,
    this.interval = Duration.zero,
    this.strokeColor = Colors.transparent,
    this.strokeWidth = 0,
    this.fillColor = Colors.transparent,
    this.iconProvider,
    this.anchorU,
    this.anchorV,
  });

  /// 是否显示
  final bool show;

  /// 定位类型
  late final MyLocationType myLocationType;

  /// 定位间隔
  final Duration interval;

  /// 边框颜色
  final Color strokeColor;

  /// 边框宽度
  final double strokeWidth;

  /// 填充颜色
  final Color fillColor;

  /// 图标
  ///
  /// 资源图片则使用[AssetImage], 网络图片则使用[NetworkImage], 文件图片则使用[FileImage]
  late final ImageProvider? iconProvider;

  /// 锚点
  late final double? anchorU, anchorV;

  @override
  String toString() {
    return 'MyLocationOption{show: $show, myLocationType: $myLocationType, interval: $interval, strokeColor: $strokeColor, strokeWidth: $strokeWidth, fillColor: $fillColor, iconProvider: $iconProvider, anchorU: $anchorU, anchorV: $anchorV}';
  }
}