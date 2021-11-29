/// 地图图层类型
enum MapType {
  /// 正常
  NORMAL,

  /// 夜间
  NIGHT,

  /// 导航
  NAVI,

  /// 公交
  BUS,

  /// 卫星
  SATELLITE,
}

extension MapTypeExtension on MapType {
  String get name {
    switch (this) {
      case MapType.NORMAL:
        return 'NORMAL';
      case MapType.NIGHT:
        return 'NIGHT';
      case MapType.NAVI:
        return 'NAVI';
      case MapType.BUS:
        return 'BUS';
      case MapType.SATELLITE:
        return 'SATELLITE';
      default:
        return 'NORMAL';
    }
  }
}