
import 'lat_lng.dart';

/// iOS显示范围
///
/// * 只针对iOS
class IosBound {
  /// iOS显示范围
  ///
  /// [latLng] - 经纬度坐标
  ///
  /// [latitudeDelta] - 纬度变化比例
  ///
  /// [longitudeDelta] - 经度变化比例
  IosBound({
    this.latLng,
    this.latitudeDelta,
    this.longitudeDelta,
  });

  IosBound.fromJson(Map<String, dynamic> json) {
    latLng = (json["latLng"] != null && (json["latLng"] is Map))
        ? LatLng.fromJson(json["latLng"])
        : null;
    latitudeDelta = double.tryParse(json["latitudeDelta"]?.toString() ?? '');
    longitudeDelta = double.tryParse(json["longitudeDelta"]?.toString() ?? '');
  }

  /// 经纬度坐标
  LatLng? latLng;

  /// 纬度变化比例
  double? latitudeDelta;

  /// 经度变化比例
  double? longitudeDelta;

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = Map<String, dynamic>();
    if (latLng != null) {
      data["latLng"] = latLng!.toJson();
    }
    data["latitudeDelta"] = latitudeDelta;
    data["longitudeDelta"] = longitudeDelta;
    return data;
  }
}
