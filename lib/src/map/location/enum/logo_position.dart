/// Logo位置
///
/// 只针对Android
enum LogoPosition {
  /// 左下
  BOTTOM_LEFT,

  /// 右下
  BOTTOM_RIGHT,

  /// 下居中
  BOTTOM_CENTER,
}

extension LogoPositionExtension on LogoPosition {
  String get name {
    switch (this) {
      case LogoPosition.BOTTOM_LEFT:
        return 'BOTTOM_LEFT';
      case LogoPosition.BOTTOM_RIGHT:
        return 'BOTTOM_RIGHT';
      case LogoPosition.BOTTOM_CENTER:
        return 'BOTTOM_CENTER';
      default:
        return '';
    }
  }
}
