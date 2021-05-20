import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

class AmapView extends StatefulWidget {

  // static const MethodChannel _channel = const MethodChannel('easy_flutter_amap');

  @override
  _AmapViewState createState() => _AmapViewState();

  // static Future<Map> addMarker(String data) async {
  //   return await _channel.invokeMethod("sendData", {'data': data});
  // }
}

class _AmapViewState extends State<AmapView> {

  @override
  Widget build(BuildContext context) {
    return Container(
      child: AndroidView(
        viewType:"cn.jjvu.xiao.easy_flutter_amap/mapview",
        // creationParams: {'data': 'xiao'},
        creationParamsCodec: StandardMessageCodec(),
      ),
    );
  }



}