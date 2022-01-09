import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.View
import cn.jjvu.xiao.easy_flutter_amap.R
//import com.amap.api.location.AMapLocationClient.updatePrivacyAgree
//import com.amap.api.location.AMapLocationClient.updatePrivacyShow
import com.amap.api.maps.AMap
import com.amap.api.maps.CameraUpdateFactory
import com.amap.api.maps.MapView
import com.amap.api.maps.MapsInitializer
import com.amap.api.maps.model.BitmapDescriptorFactory
import com.amap.api.maps.model.MyLocationStyle
import io.flutter.plugin.common.BinaryMessenger
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.platform.PlatformView


class EasyAmapView(
    private val context: Context,
    private val messenger: BinaryMessenger,
    private val id: Int,
    private var params : Map<String, Any?>) : PlatformView {

    private lateinit var aMap : AMap

    private lateinit var amapView: MapView

    // 地图底图类型
    private val mapType: Int = params["mapType"] as Int

    // 语言, 中文-zh_cn 英文-en
    private val mapLanguage: String = params["mapLanguage"] as String

    private val myLocationStyle = MyLocationStyle()

    private var showMyLocationType: Int = params["myLocationType"] as Int

    private var locationInterval: Int = params["locationInterval"] as Int


    init {
//        methodChannel = MethodChannel(messenger,"easy_flutter_amap")
//        methodChannel.setMethodCallHandler(this)
        createMap(context);
    }

    private fun createMap(context: Context) {
        this.amapView = MapView(context)
        this.amapView.onCreate(Bundle())
        this.aMap = this.amapView.getMap()

        aMap.mapType = mapType
        aMap.setMapLanguage(mapLanguage)
        aMap.moveCamera(CameraUpdateFactory.zoomTo(1.0f))
        aMap.uiSettings.isMyLocationButtonEnabled = true

        myLocationStyle.let {
            it.myLocationType(showMyLocationType)
            it.interval(locationInterval.toLong())
            it.showMyLocation(true)
        }

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
        return this.amapView as View
    }

    override fun dispose() {
        this.amapView.onDestroy()
    }

    // 设置定位类型
    fun setLocationType(type: Int) {
        myLocationStyle.myLocationType(type)
        aMap.myLocationStyle = myLocationStyle
    }

}