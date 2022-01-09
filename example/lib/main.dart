import 'dart:io';

import 'package:easy_permission/easy_permission.dart';
import 'package:flutter/material.dart';
import 'dart:async';

import 'package:flutter/services.dart';
import 'package:easy_flutter_amap/easy_flutter_amap.dart';

Future<void> main() async {
  WidgetsFlutterBinding.ensureInitialized();
  await requestPermissions();
  runApp(MyApp());
}

// 申请权限
Future<void> requestPermissions() async {
  if (Platform.isAndroid) {
    List<PermissionType> types = [];
    // 申请位置权限
    types.add(PermissionType.LOCATION);
    // 申请相机权限
    types.add(PermissionType.CAMERA);
    // 申请存储权限
    types.add(PermissionType.STORAGE);
    // 申请麦克风权限
    types.add(PermissionType.MICROPHONE);
    // 申请日历权限
    types.add(PermissionType.CALENDAR);
    await EasyPermission.requestPermissions(types);
  }
}

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {

  AmapViewController controller;

  @override
  void initState() {
    super.initState();
    controller = AmapViewController();
    controller.setMyLocationStyle(MyLocationStyle(myLocationType: MyLocationType.MAP_ROTATE));
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: GestureDetector(
              child: const Text('Plugin example app'),
              onTap: () {
                // controller.setMyLocationStyle(MyLocationStyle(myLocationType: MyLocationType.MAP_ROTATE));
                controller.setMyLocationType(MyLocationType.MAP_ROTATE_NO_CENTER);
              },
          ),
        ),
        body: Center(
          child: AMapView(
            myLocationType: MyLocationType.MAP_ROTATE_NO_CENTER,
            locationInterval: 2000,
            controller: controller,
          )
        ),
        // floatingActionButton: FloatingActionButton(
        //   child: Button(
        //     child: Text("设置"),
        //     onPressed: () {
        //     },
        //   ),
        // ),
      ),
    );
  }
}
