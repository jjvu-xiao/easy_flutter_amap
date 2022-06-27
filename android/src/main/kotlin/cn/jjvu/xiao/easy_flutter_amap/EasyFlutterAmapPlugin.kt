package cn.jjvu.xiao.easy_flutter_amap

import androidx.annotation.NonNull
import com.amap.api.maps.model.LatLng
import io.flutter.Log
import io.flutter.embedding.android.FlutterActivity

import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.embedding.engine.plugins.activity.ActivityAware
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import kotlin.math.log

/** EasyFlutterAmapPlugin */
class EasyFlutterAmapPlugin: FlutterPlugin, MethodCallHandler, ActivityAware {

    private lateinit var _activity: FlutterActivity

    private lateinit var _flutterPluginBinding: FlutterPlugin.FlutterPluginBinding

    private lateinit var _channel: MethodChannel

    private lateinit var _aMapViewFactory: AMapViewFactory

    private val TAG = "easy_flutter_amap"

    override fun onAttachedToEngine(@NonNull flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
        this._flutterPluginBinding = flutterPluginBinding
        _channel = MethodChannel(flutterPluginBinding.binaryMessenger, TAG)
        _channel.setMethodCallHandler(this)
    }

    override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
        _channel.setMethodCallHandler(null)
    }

    override fun onMethodCall(@NonNull call: MethodCall, @NonNull result: Result) {
        when(call.method) {
            "setMyLocationType" -> _aMapViewFactory.aMapView.setLocationType(call.arguments<Int>())

        }
    }

    override fun onAttachedToActivity(binding: ActivityPluginBinding) {
        _activity = binding.activity as FlutterActivity
        _aMapViewFactory = AMapViewFactory(_activity, _flutterPluginBinding.binaryMessenger)
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
