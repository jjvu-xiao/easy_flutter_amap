/// 地图类型
enum MapType {
  /// 标准视图
  Standard,

  /// 卫星视图
  Satellite,

  /// 黑夜视图
  Night,

  /// 导航视图
  Navi,

  /// 公交视图
  Bus,
}

extension MapTypeExtension on MapType {
  String get name {
    switch (this) {
      case MapType.Standard:
        return "Standard";
      case MapType.Satellite:
        return "Satellite";
    }
  }
}