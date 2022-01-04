[English](README_ENG.md)
# easy_flutter_amap

Flutter 高德地图插件

## 描述
基于高德地图封装的跨平台 Android, iOS平台的地图插件，适用于各种地图的应用场景，经受住工业级生产环境使用

## 效果如下
![效果动态图](https://img-blog.csdnimg.cn/20210521154534717.gif#pic_center)

## 环境配置

本插件不会做任何的权限申请，如果需要权限申请请使用权限申请插件

### Android
Android的权限申请, 在AndroidManifest.xml文件中填写以下

```xml
    <uses-permission android:name="android.permission.INTERNET" />
    <!--允许程序设置内置sd卡的读写权限-->
    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
    <!--允许程序获取网络状态-->
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
    <!--允许程序访问WiFi网络信息-->
    <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
    <!--允许程序读写手机状态和身份-->
    <uses-permission android:name="android.permission.READ_PHONE_STATE" />
    <!--允许程序访问CellID或WiFi热点来获取粗略的位置-->
    <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
    <!--用于访问GPS定位-->
    <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"/>
    <!--这个权限用于获取wifi的获取权限，wifi信息会用来进行网络定位-->
    <uses-permission android:name="android.permission.CHANGE_WIFI_STATE"/>
    <!--用于申请调用A-GPS模块-->
    <uses-permission android:name="android.permission.ACCESS_LOCATION_EXTRA_COMMANDS"/>
```

### iOS

在iOS目录下Runner目录下的 info.plist文中加入以下配置,权限申请必须说明"因为 xxx功能所以需要xxx权限"，不能写 "需要拍照所以申请拍照权限"这种描述，会被 Appstore 因为“元数据问题”而拒绝上架

``` xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE plist PUBLIC "-//Apple//DTD PLIST 1.0//EN" "http://www.apple.com/DTDs/PropertyList-1.0.dtd">
<plist version="1.0">
<dict>
    ......
	<key>NSLocationAlwaysAndWhenInUseUsageDescription</key>
	<string>需要您提供定位信息，才能使用定位记录您的正确工作轨迹</string>
	<key>NSLocationAlwaysUsageDescription</key>
	<string>需要您提供后台定位信息，才能使用记录您的正确工作轨迹</string>
	<key>NSLocationUsageDescription</key>
	<string>需要您提供定位信息，才能使用记录您的正确工作轨迹</string>
	<key>NSLocationWhenInUseUsageDescription</key>
	<string>需要您提供定位信息，才能使用记录您的正确工作轨迹</string>
	<key>NSMotionUsageDescription</key>
	<string>需要您提供轨迹,才能使用功能</string>
</dict>
```

## 代码使用



```dart

```


## 注意事项



## 使用场景
推荐在main方法中 用该插件先动态申请 一些必要的 权限，比如网络、定位等。


## 注意事项
