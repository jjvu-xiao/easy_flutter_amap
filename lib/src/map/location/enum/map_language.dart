/// 底图语言
enum MapLanguage {
  /// 中文
  CHINESE,

  /// 英文
  ENGLISH,
}

extension MapLanguageExtension on MapLanguage {
  String get name {
    switch (this) {
      case MapLanguage.CHINESE:
        return 'CHINESE';
      case MapLanguage.ENGLISH:
        return 'ENGLISH';
      default:
        return 'CHINESE';
    }
  }
  String get val {
    switch (this) {
      case MapLanguage.CHINESE:
        return 'zh_cn';
      case MapLanguage.ENGLISH:
        return 'en';
      default:
        return 'zh_cn';
    }
  }
}
