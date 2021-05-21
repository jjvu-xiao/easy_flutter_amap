import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

class AmapView extends StatelessWidget {

  AmapConfig config;

  MethodChannel _channel = MethodChannel('easy_flutter_amap');

  AmapView({this.config}) {
    if (null == this.config)
      this.config = AmapConfig();
  }

  @override
  Widget build(BuildContext context) {
    return Container(
      child: AndroidView(
        viewType:"cn.jjvu.xiao.easy_flutter_amap/mapview",
        creationParamsCodec: StandardMessageCodec(),
        creationParams: config.toMap(),
      ),
    );
  }

  Future<String> addMarker(MarkerOption options) async {
    return _channel.invokeMethod("addMarkers", options.toMap());
  }
}

class AmapConfig {
  int interval;
  double zoomLevel;

  AmapConfig({this.interval: 1000, this.zoomLevel: 28.0});

  Map toMap() {
    Map map = Map();
    map['interval'] = interval;
    map['zoomLevel'] = zoomLevel;
    return map;
  }
}

class MarkerOption {
  double latitude;
  double longitude;
  String title;

  MarkerOption({this.latitude, this.longitude, this.title});

  Map toMap() {
    Map map = Map();
    map['latitude'] = latitude;
    map['longitude'] = longitude;
    map['title'] = title;
    return map;
  }
}