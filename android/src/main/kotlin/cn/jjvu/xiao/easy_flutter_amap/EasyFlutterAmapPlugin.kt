package cn.jjvu.xiao.easy_flutter_amap

import androidx.annotation.NonNull
import com.amap.api.maps.model.LatLng
import io.flutter.embedding.android.FlutterActivity

import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.embedding.engine.plugins.activity.ActivityAware
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result

/** EasyFlutterAmapPlugin */
class EasyFlutterAmapPlugin: FlutterPlugin, MethodCallHandler, ActivityAware {

    private lateinit var _activity: FlutterActivity

    private lateinit var _flutterPluginBinding: FlutterPlugin.FlutterPluginBinding

    private lateinit var _channel: MethodChannel

    private lateinit var _aMapViewFactory: AMapViewFactory

    override fun onAttachedToEngine(@NonNull flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
        this._flutterPluginBinding = flutterPluginBinding
        _channel = MethodChannel(flutterPluginBinding.binaryMessenger, "easy_flutter_amap")
        _channel.setMethodCallHandler(this)
    }

    override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
        _channel.setMethodCallHandler(null)
    }

    override fun onMethodCall(@NonNull call: MethodCall, @NonNull result: Result) {
        when (call.method) {
            "setZoomLevel" -> _aMapViewFactory.aMapView.setZoomLevel(call.arguments<Double>())
            "getZoomLevel" -> _aMapViewFactory.aMapView.getZoomLevel(result)
            "zoomIn" -> _aMapViewFactory.aMapView.zoomIn()
            "zoomOut" -> _aMapViewFactory.aMapView.zoomOut()
            "setMaxZoomLevel" -> _aMapViewFactory.aMapView.setMaxZoomLevel(call.arguments<Double>())
            "setMinZoomLevel" -> _aMapViewFactory.aMapView.setMinZoomLevel(call.arguments<Double>())
            "getMaxZoomLevel" -> _aMapViewFactory.aMapView.getMaxZoomLevel(result)
            "getMinZoomLevel" -> _aMapViewFactory.aMapView.getMinZoomLevel(result)
            "setMapType" -> _aMapViewFactory.aMapView.setMapType(call.arguments<String>())
            "getMapType" -> _aMapViewFactory.aMapView.getMapType(result)
            "turnOnTraffic" -> _aMapViewFactory.aMapView.turnOnTraffic(call.arguments<Boolean>())
            "isTrafficOn" -> _aMapViewFactory.aMapView.isTrafficOn(result)
            "turnOnBuildings" -> _aMapViewFactory.aMapView.turnOnBuildings(call.arguments<Boolean>())
            "isBuildingsOn" -> _aMapViewFactory.aMapView.isBuildingsOn(result)
            "turnOnMapText" -> _aMapViewFactory.aMapView.turnOnMapText(call.arguments<Boolean>())
            "setMapLanguage" -> _aMapViewFactory.aMapView.setMapLanguage(call.arguments<String>())
            "setLocationType" -> _aMapViewFactory.aMapView.setLocationType(call.arguments<String>())
            "getLocationType" -> _aMapViewFactory.aMapView.getLocationType(result)
            "setLocationInterval" -> _aMapViewFactory.aMapView.setLocationInterval(call.arguments<Int>())
            "getLocationInterval" -> _aMapViewFactory.aMapView.getLocationInterval(result)
            "turnOnZoomControl" -> _aMapViewFactory.aMapView.turnOnZoomControl(call.arguments<Boolean>())
            "isZoomControlOn" -> _aMapViewFactory.aMapView.isZoomControlOn(result)
            "turnOnCompass" -> _aMapViewFactory.aMapView.turnOnCompass(call.arguments<Boolean>())
            "isCompassOn" -> _aMapViewFactory.aMapView.isCompassOn(result)
            "turnOnLocationButton" -> _aMapViewFactory.aMapView.turnOnLocationButton(call.arguments<Boolean>())
            "isLocationButtonOn" -> _aMapViewFactory.aMapView.isLocationButtonOn(result)
            "turnOnScaleControl" -> _aMapViewFactory.aMapView.turnOnScaleControl(call.arguments<Boolean>())
            "isScaleControlOn" -> _aMapViewFactory.aMapView.isScaleControlOn(result)
            "enableAllGesture" -> _aMapViewFactory.aMapView.enableAllGesture(call.arguments<Boolean>())
            "isAllGestureEnable" -> _aMapViewFactory.aMapView.isAllGestureEnable(result)
            "enableZoomGesture" -> _aMapViewFactory.aMapView.enableZoomGesture(call.arguments<Boolean>())
            "isZoomGestureEnable" -> _aMapViewFactory.aMapView.isZoomGestureEnable(result)
            "enableRotateGesture" -> _aMapViewFactory.aMapView.enableRotateGesture(call.arguments<Boolean>())
            "isRotateGestureEnable" -> _aMapViewFactory.aMapView.isRotateGestureEnable(result)
            "enableScrollGesture" -> _aMapViewFactory.aMapView.enableScrollGesture(call.arguments<Boolean>())
            "isScrollGestureEnable" -> _aMapViewFactory.aMapView.isScrollGestureEnable(result)
            "enableTiltGesture" -> _aMapViewFactory.aMapView.enableTiltGesture(call.arguments<Boolean>())
            "isTiltGestureEnable" -> _aMapViewFactory.aMapView.isTiltGestureEnable(result)
            "setLogoPosition" -> _aMapViewFactory.aMapView.setLogoPosition(call.arguments<String>())
            "getLogoPosition" -> _aMapViewFactory.aMapView.getLogoPosition(result)
            "setLogoMargin" -> _aMapViewFactory.aMapView.setLogoMargin(call.arguments<HashMap<String, Int>>())
            "setZoomPosition" -> _aMapViewFactory.aMapView.setZoomPosition(call.arguments<String>())
            "getZoomPosition" -> _aMapViewFactory.aMapView.getZoomPosition(result)
            "setIsGestureScaleByMapCenterPosition" -> _aMapViewFactory.aMapView.setIsGestureScaleByMapCenterPosition(
                call.arguments<Boolean>()
            )
            "getIsGestureScaleByMapCenterPosition" -> _aMapViewFactory.aMapView.getIsGestureScaleByMapCenterPosition(
                result
            )
            "setCustomMapStyleId" -> _aMapViewFactory.aMapView.setCustomMapStyleId(call.arguments<String>())
            "disableCustomMapStyle" -> _aMapViewFactory.aMapView.disableCustomMapStyle()
            "setMapCenter" -> {
                val latLngMap = call.arguments<HashMap<String, Double>>()
                val latLng = LatLng(latLngMap["latitude"]!!, latLngMap["longitude"]!!)
                _aMapViewFactory.aMapView.setMapCenter(latLng)
            }
            "getMapCenter" -> _aMapViewFactory.aMapView.getMapCenter(result)
            "setBound" -> {
                _aMapViewFactory.aMapView.setBound(call.arguments<List<HashMap<String, Double>>>())
            }
            "screenShot" -> _aMapViewFactory.aMapView.screenShot(result)
            else -> result.notImplemented()
        }
    }

    override fun onAttachedToActivity(binding: ActivityPluginBinding) {
        _activity = binding.activity as FlutterActivity
        _aMapViewFactory = AMapViewFactory(_activity)
        _flutterPluginBinding.platformViewRegistry.registerViewFactory(
            "AMapView",
            _aMapViewFactory
        )
    }

    override fun onDetachedFromActivityForConfigChanges() {

    }

    override fun onReattachedToActivityForConfigChanges(binding: ActivityPluginBinding) {

    }

    override fun onDetachedFromActivity() {

    }
}
