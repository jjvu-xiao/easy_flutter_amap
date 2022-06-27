import android.content.Context
import android.os.Bundle
import android.view.View
import com.amap.api.maps.*
import com.amap.api.maps.model.MyLocationStyle
import io.flutter.plugin.common.BinaryMessenger
import io.flutter.plugin.platform.PlatformView
import android.graphics.BitmapFactory
import cn.jjvu.xiao.easy_flutter_amap.R

import com.amap.api.maps.model.BitmapDescriptorFactory
import com.amap.api.maps.model.LatLng

import com.amap.api.maps.model.MarkerOptions

class EasyAmapView(
    private val context: Context,
    private val messenger: BinaryMessenger,
    private val id: Int,
    private var params : Map<String, Any?>) : PlatformView {

    private lateinit var aMap : AMap

    private lateinit var aMapView: MapView

    // 地图底图类型
    private val mapType: Int = params["mapType"] as Int

    // 语言, 中文-zh_cn 英文-en
    private val mapLanguage: String = params["mapLanguage"] as String

    // 显示我的位置
    private val myLocationStyle = MyLocationStyle()

    // 是否显示我的位置
    private var showMyLocationType: Int = params["myLocationType"] as Int

    // 定位间隔
    private var locationInterval: Int = params["locationInterval"] as Int

    // 是否显示缩放按钮
    private var showZoom: Boolean = params["showZoom"] as Boolean

    // 是否显示指南针
    private var showCompass: Boolean = params["showCompass"] as Boolean

    // 是否显示比例尺
    private var showScale: Boolean = params["showScale"] as Boolean

    // logo显示位置
    private var logoPosition: Int = params["logoPosition"] as Int

    // 是否允许缩放手势
    private var zoomGesturesEnabled: Boolean = params["zoomGesturesEnabled"] as Boolean

    // 是否允许滑动手势
    private var scrollGesturesEnabled: Boolean = params["scrollGesturesEnabled"] as Boolean

    // 是否允许旋转手势
    private var rotateGesturesEnabled: Boolean = params["rotateGesturesEnabled"] as Boolean

    // 是否允许倾斜手势
    private var tiltGesturesEnabled: Boolean = params["tiltGesturesEnabled"] as Boolean

    // 是否允许所有手势
    private var allGesturesEnabled: Boolean = params["allGesturesEnabled"] as Boolean


    init {
//        methodChannel = MethodChannel(messenger,"easy_flutter_amap")
//        methodChannel.setMethodCallHandler(this)
        createMap(context);
    }

    private fun createMap(context: Context) {
        this.aMapView = MapView(context)
        this.aMapView.onCreate(Bundle())
        this.aMap = this.aMapView.getMap()

        aMap.mapType = mapType
        aMap.setMapLanguage(mapLanguage)
        aMap.moveCamera(CameraUpdateFactory.zoomTo(1.0f))
        aMap.uiSettings.isMyLocationButtonEnabled = true

        myLocationStyle.let {
            it.myLocationType(showMyLocationType)
            it.interval(locationInterval.toLong())
            it.showMyLocation(true)
        }

        val uiSetting: UiSettings = aMap.uiSettings
        uiSetting.isCompassEnabled = showCompass
        uiSetting.isScaleControlsEnabled = showScale
        uiSetting.isZoomControlsEnabled = showZoom
//        uiSetting.logoPosition = logoPosition
        uiSetting.isZoomGesturesEnabled = zoomGesturesEnabled
        uiSetting.isScrollGesturesEnabled = scrollGesturesEnabled
        uiSetting.isRotateGesturesEnabled = rotateGesturesEnabled
        uiSetting.isTiltGesturesEnabled = tiltGesturesEnabled
        uiSetting.setAllGesturesEnabled(allGesturesEnabled)

        val markerOption = MarkerOptions()
        markerOption.position(LatLng(34.341568, 108.940174))
        markerOption.title("西安市").snippet("西安市：34.341568, 108.940174")

        markerOption.draggable(true) //设置Marker可拖动

        markerOption.icon(
            BitmapDescriptorFactory.fromBitmap(
                BitmapFactory
                    .decodeResource(context.getResources(), R.drawable.loc)
            )
        )
        // 将Marker设置为贴地显示，可以双指下拉地图查看效果
        // 将Marker设置为贴地显示，可以双指下拉地图查看效果
        markerOption.isFlat = true //设置marker平贴地图效果

        aMap.addMarker(markerOption)

        val c = params["marker"]

//        UiSettingsisZoomGesturesEnabled = true

//        val myLocationStyle = MyLocationStyle();
//        myLocationStyle.interval(1000);
//        myLocationStyle.strokeWidth(1f);
//        myLocationStyle.strokeColor(Color.parseColor("#8052A3FF"));
//        myLocationStyle.radiusFillColor(Color.parseColor("#3052A3FF"));
//        myLocationStyle.showMyLocation(true);
//        myLocationStyle.myLocationIcon(BitmapDescriptorFactory.fromResource(R.drawable.pic3));
//        myLocationStyle.myLocationType()
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE_NO_CENTER);

        aMap.myLocationStyle = myLocationStyle
        aMap.isMyLocationEnabled = true
    }

    override fun getView(): View {
        return this.aMapView as View
    }

    override fun dispose() {
        this.aMapView.onDestroy()
    }

    // 设置定位类型
    fun setLocationType(type: Int) {
        myLocationStyle.myLocationType(type)
        aMap.myLocationStyle = myLocationStyle
    }

}