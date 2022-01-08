/// 地图图层类型
enum MapType {
  /// 正常
  NORMAL,

  /// 卫星
  SATELLITE,

  /// 夜间
  NIGHT,

  /// 导航
  NAVI,

  /// 公交
  BUS,

}

extension MapTypeExtension on MapType {
  int get val {
    switch (this) {
      case MapType.NORMAL:
        return 1;
      case MapType.SATELLITE:
        return 2;
      case MapType.NIGHT:
        return 3;
      case MapType.NAVI:
        return 4;
      case MapType.BUS:
        return 5;
      default:
        return 1;
    }
  }

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