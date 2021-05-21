import 'package:flutter/material.dart';

import 'package:easy_flutter_amap/easy_flutter_amap.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {

  AmapView amapView;
  
  @override
  void initState() {
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    amapView =  AmapView(
      config: AmapConfig(zoomLevel: 3),
    );
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        body: Center(
          child: amapView
        ),
        floatingActionButton: FloatingActionButton(
          child: Text('添加marker'),
          onPressed: () async {
            String msg = await amapView.addMarker(MarkerOption(latitude: 34.341568, longitude: 108.940174, title: "标记"));
            debugPrint(msg);
          },
        ),
      ),
    );
  }
}
