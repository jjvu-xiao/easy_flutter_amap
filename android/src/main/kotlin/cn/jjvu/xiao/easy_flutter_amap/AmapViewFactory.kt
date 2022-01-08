package cn.jjvu.xiao.easy_flutter_amap

import EasyAmapView
import android.content.Context
import io.flutter.embedding.android.FlutterActivity
import io.flutter.plugin.common.BinaryMessenger
import io.flutter.plugin.common.StandardMessageCodec
import io.flutter.plugin.platform.PlatformView
import io.flutter.plugin.platform.PlatformViewFactory

class AMapViewFactory(val activity: FlutterActivity, val messenger: BinaryMessenger) :

    PlatformViewFactory(StandardMessageCodec.INSTANCE) {

    lateinit var aMapView: EasyAmapView

    override fun create(context: Context?, viewId: Int, args: Any?): PlatformView {
        val creationParams = args as Map<String, Any?>
        aMapView = EasyAmapView(context!!, messenger, viewId, creationParams)
        return aMapView
    }
}