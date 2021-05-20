import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

class AmapView extends StatefulWidget {

  @override
  _AmapViewState createState() => _AmapViewState();
}

class _AmapViewState extends State<AmapView> {

  @override
  Widget build(BuildContext context) {
    return Container(
      child: AndroidView(
        viewType:"cn.jjvu.xiao.easy_flutter_amap/mapview",
        creationParamsCodec: StandardMessageCodec(),
      ),
    );
  }

}