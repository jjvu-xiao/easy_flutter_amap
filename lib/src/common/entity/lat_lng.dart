/// 坐标点
class LatLng {

  /// 纬度
  late double latitude;

  /// 经度
  late double longitude;

  LatLng({
    required this.latitude,
    required this.longitude,
  }) :  assert(latitude >= -90 && latitude <= 90, '纬度范围为[-90, 90]!'),
        assert(longitude >= -180 && longitude <= 180, '经度范围为[-180, 180]!');

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = Map<String, dynamic>();
    data['latitude'] = latitude;
    data['longitude'] = longitude;
    return data;
  }

  LatLng.fromJson(Map<String, dynamic> json) {
    latitude = double.parse(json['latitude']);
    longitude = double.parse(json['longitude']);
  }
}
