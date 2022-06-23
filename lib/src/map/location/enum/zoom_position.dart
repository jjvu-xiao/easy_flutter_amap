/// 缩放控件位置
///
/// 只针对Android
enum ZoomPosition {
  /// 右下
  RIGHT_BOTTOM,

  /// 下居中
  RIGHT_CENTER,
}

extension ZoomPositionExtension on ZoomPosition {
  String get name {
    switch (this) {
      case ZoomPosition.RIGHT_BOTTOM:
        return 'RIGHT_BOTTOM';
      case ZoomPosition.RIGHT_CENTER:
        return 'RIGHT_CENTER';
      default:
        return 'RIGHT_BOTTOM';
    }
  }
}
