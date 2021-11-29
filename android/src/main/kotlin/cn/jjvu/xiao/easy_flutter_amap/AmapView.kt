package cn.jjvu.xiao.easy_flutter_amap

import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import android.graphics.Bitmap.CompressFormat
import android.view.View
import androidx.annotation.NonNull
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.amap.api.maps.*
import com.amap.api.maps.model.*
import io.flutter.Log
import io.flutter.embedding.android.FlutterActivity
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.platform.PlatformView
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class AMapView(
    context: Context,
    private val activity: FlutterActivity,
    id: Int,
    creationParams: Map<String, Any?>
) :
    PlatformView,
    DefaultLifecycleObserver {

    private val _mapView: MapView by lazy {
        if (_defaultCameraPosition != null) {
            val target: HashMap<String, Double> =
                _defaultCameraPosition!!["target"] as HashMap<String, Double>
            val center = LatLng(target["latitude"]!!, target["longitude"]!!)
            val zoom: Float = (_defaultCameraPosition!!["zoom"] as Double).toFloat()
            val tilt: Float = (_defaultCameraPosition!!["tilt"] as Double).toFloat()
            val bearing: Float = (_defaultCameraPosition!!["bearing"] as Double).toFloat()
            val cameraPosition = CameraPosition(center, zoom, tilt, bearing)
            val mapOptions = AMapOptions().apply {
                camera(cameraPosition)
            }
            MapView(context, mapOptions)
        } else {
            MapView(context)
        }
    }

    private val _aMap: AMap by lazy {
        _mapView.map
    }

    private val _uiSettings: UiSettings by lazy {
        _aMap.uiSettings
    }

    private var _locationStyle = MyLocationStyle()

    private var _customMapStyleOptions = CustomMapStyleOptions()

    /**
     * 初始化后是否自动定位
     */
    private var _autoLocateAfterInit: Boolean = creationParams["autoLocateAfterInit"] as Boolean

    /**
     * 地图图层类型
     */
    private var _mapType: String = creationParams["mapType"] as String

    /**
     * 底图语言
     */
    private var _mapLanguage: String = creationParams["mapLanguage"] as String

    /**
     * 定位类型
     */
    private var _locationType: String = creationParams["locationType"] as String

    /**
     * 定位间隔，仅定位类型为连续定位时有效，单位毫秒
     */
    private var _locationInterval: Int = creationParams["locationInterval"] as Int

    /**
     * 是否显示实时路况
     */
    private var _showTraffic: Boolean = creationParams["showTraffic"] as Boolean

    /**
     * 是否显示楼块
     */
    private var _showBuildings: Boolean = creationParams["showBuildings"] as Boolean

    /**
     * 是否显示底图文字标注
     */
    private var _showMapText: Boolean = creationParams["showMapText"] as Boolean

    /**
     * 是否显示缩放控件
     */
    private var _showZoomControl: Boolean = creationParams["showZoomControl"] as Boolean

    /**
     * 是否显示指南针
     */
    private var _showCompass: Boolean = creationParams["showCompass"] as Boolean

    /**
     * 是否显示定位按钮
     */
    private var _showLocationButton: Boolean = creationParams["showLocationButton"] as Boolean

    /**
     * 是否显示比例尺控件
     */
    private var _showScaleControl: Boolean = creationParams["showScaleControl"] as Boolean

    /**
     * 是否显示室内地图
     */
    private var _showIndoorMap: Boolean = creationParams["showIndoorMap"] as Boolean

    /**
     * 是否显示室内地图控件
     */
    private var _showIndoorMapControl: Boolean = creationParams["showIndoorMapControl"] as Boolean

    /**
     * 所有手势是否可用
     */
    private var _allGestureEnable: Boolean? = creationParams["allGestureEnable"] as Boolean?

    /**
     * 缩放手势是否可用
     */
    private var _zoomGestureEnable: Boolean = creationParams["zoomGestureEnable"] as Boolean

    /**
     * 旋转手势是否可用
     */
    private var _rotateGestureEnable: Boolean = creationParams["rotateGestureEnable"] as Boolean

    /**
     * 拖拽手势是否可用
     */
    private var _scrollGestureEnable: Boolean = creationParams["scrollGestureEnable"] as Boolean

    /**
     * 倾斜手势是否可用
     */
    private var _tiltGestureEnable: Boolean = creationParams["tiltGestureEnable"] as Boolean

    /**
     * 是否以地图中心点缩放
     */
    private var _isGestureScaleByMapCenter: Boolean =
        creationParams["isGestureScaleByMapCenter"] as Boolean

    /**
     * 缩放控件位置
     */
    private var _zoomPosition: String = creationParams["zoomPosition"] as String

    /**
     * Logo位置
     */
    private var _logoPosition: String = creationParams["logoPosition"] as String

    /**
     * Logo左下边距
     *
     * marginLeft: 左边距
     *
     * marginBottom: 下边距
     */
    private var _logoMargin: HashMap<String, Int>? =
        creationParams["logoMargin"] as HashMap<String, Int>?

    /**
     * 初始缩放等级，默认[3,19]，有室内地图时[3,20]
     */
    private var _initialZoomLevel: Double = creationParams["initialZoomLevel"] as Double

    /**
     * 最大缩放等级
     */
    private var _maxZoomLevel: Double = creationParams["maxZoomLevel"] as Double

    /**
     * 最小
     */
    private var _minZoomLevel: Double = creationParams["minZoomLevel"] as Double

    /**
     * 自定义地图id
     */
    private var _customMapStyleId: String? = creationParams["customMapStyleId"] as String?

    /**
     * 默认显示视觉位置
     *
     * target: 中心点坐标
     *
     * tilt: 倾斜度
     *
     * zoom: 缩放等级
     *
     * bearing: 指向的方向，以角度为单位，从正北向逆时针方向计算，0-360
     */
    private var _defaultCameraPosition: HashMap<String, Any>? =
        creationParams["defaultCameraPosition"] as HashMap<String, Any>?

    /**
     * 显示范围，西南角和东北角
     */
    private var _bound: List<HashMap<String, Double>>? =
        creationParams["androidBound"] as List<HashMap<String, Double>>?

    init {
        activity.lifecycle.addObserver(this)
    }

    private fun initAMapView() {
        setMaxZoomLevel(_maxZoomLevel)
        setMinZoomLevel(_minZoomLevel)
        if (_defaultCameraPosition == null && _bound == null) {
            _aMap.moveCamera(CameraUpdateFactory.zoomTo(_initialZoomLevel.toFloat()))
        }

        _locationStyle.let {
            it.myLocationType(handleLocationType(_locationType))
            it.interval(_locationInterval.toLong())
            it.showMyLocation(true)
        }
        _aMap.myLocationStyle = _locationStyle

        if (_autoLocateAfterInit && _defaultCameraPosition == null && _bound == null) {
            _aMap.isMyLocationEnabled = true
        }

        if (_bound != null) {
            setBound(_bound!!)
        }

        setMapType(_mapType)
        setMapLanguage(_mapLanguage)
        turnOnTraffic(_showTraffic)
        turnOnBuildings(_showBuildings)
        turnOnMapText(_showMapText)
        showZoomControl(_showZoomControl)
        turnOnCompass(_showCompass)
        turnOnLocationButton(_showLocationButton)
        turnOnScaleControl(_showScaleControl)
        showIndoorMap(_showIndoorMap)
        _uiSettings.isGestureScaleByMapCenter = _isGestureScaleByMapCenter
        enableAllGesture(_allGestureEnable)
        if (_logoMargin != null) {
            _uiSettings.setLogoLeftMargin(_logoMargin!!["marginLeft"] ?: 0)
            _uiSettings.setLogoBottomMargin(_logoMargin!!["marginBottom"] ?: 0)
        } else {
            setLogoPosition(_logoPosition)
        }

        if (_customMapStyleId != null) {
            _aMap.setCustomMapStyle(_customMapStyleOptions.apply {
                isEnable = true
                styleId = _customMapStyleId
            })
        }
    }

    /**
     * 处理定位类型常量
     *
     * @param type 定位类型名称
     */
    private fun handleLocationType(type: String): Int {
        return when (type) {
            "SHOW" -> MyLocationStyle.LOCATION_TYPE_SHOW
            "LOCATE" -> MyLocationStyle.LOCATION_TYPE_LOCATE
            "FOLLOW" -> MyLocationStyle.LOCATION_TYPE_FOLLOW
            "MAP_ROTATE" -> MyLocationStyle.LOCATION_TYPE_MAP_ROTATE
            "LOCATION_ROTATE" -> MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE
            "LOCATION_ROTATE_NO_CENTER" -> MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE_NO_CENTER
            "FOLLOW_NO_CENTER" -> MyLocationStyle.LOCATION_TYPE_FOLLOW_NO_CENTER
            "MAP_ROTATE_NO_CENTER" -> MyLocationStyle.LOCATION_TYPE_MAP_ROTATE_NO_CENTER
            else -> MyLocationStyle.LOCATION_TYPE_LOCATE
        }
    }

    /**
     * 是否显示缩放按钮
     *
     * @param show 是否显示
     */
    private fun showZoomControl(show: Boolean) {
        turnOnZoomControl(show)

        if (show) {
            setZoomPosition(_zoomPosition)
        }
    }

    /**
     * 是否显示室内地图
     *
     * @param show 是否显示
     */
    private fun showIndoorMap(show: Boolean) {
        _aMap.showIndoorMap(show)
        if (show) {
            _uiSettings.isIndoorSwitchEnabled = _showIndoorMapControl
        } else {
            _uiSettings.isIndoorSwitchEnabled = false
        }
    }

    /**
     * 所有手势是否可用
     */
    private fun enableAllGesture(enable: Boolean?) {
        when (enable) {
            true -> {
                _uiSettings.setAllGesturesEnabled(true)
                enableZoomGesture(_zoomGestureEnable)
                enableRotateGesture(_rotateGestureEnable)
                enableScrollGesture(_scrollGestureEnable)
                enableTiltGesture(_tiltGestureEnable)
            }
            false -> {
                _uiSettings.setAllGesturesEnabled(false)
            }
            else -> {
                enableZoomGesture(_zoomGestureEnable)
                enableRotateGesture(_rotateGestureEnable)
                enableScrollGesture(_scrollGestureEnable)
                enableTiltGesture(_tiltGestureEnable)
            }
        }
    }

    /**
     * 设置缩放等级
     *
     * @param zoomLevel 缩放等级
     */
    fun setZoomLevel(zoomLevel: Double) {
        _aMap.animateCamera(CameraUpdateFactory.zoomTo(zoomLevel.toFloat()))
    }

    /**
     * 获取当前缩放等级
     */
    fun getZoomLevel(@NonNull result: MethodChannel.Result) {
        result.success(_aMap.cameraPosition.zoom)
    }

    /**
     * 放大缩放等级
     */
    fun zoomIn() {
        _aMap.animateCamera(CameraUpdateFactory.zoomIn())
    }

    /**
     * 缩小缩放等级
     */
    fun zoomOut() {
        _aMap.animateCamera(CameraUpdateFactory.zoomOut())
    }

    /**
     * 设置最大缩放等级
     *
     * @param level 最大缩放等级
     */
    fun setMaxZoomLevel(level: Double) {
        _aMap.maxZoomLevel = level.toFloat()
    }

    /**
     * 设置最小缩放等级
     *
     * @param level 最小缩放等级
     */
    fun setMinZoomLevel(level: Double) {
        _aMap.minZoomLevel = level.toFloat()
    }

    /**
     * 获取最大缩放等级
     */
    fun getMaxZoomLevel(@NonNull result: MethodChannel.Result) {
        result.success(_aMap.maxZoomLevel)
    }

    /**
     * 获取最小缩放等级
     */
    fun getMinZoomLevel(@NonNull result: MethodChannel.Result) {
        result.success(_aMap.minZoomLevel)
    }

    /**
     * 设置地图图层类型
     *
     * @param type 图层类型名称
     */
    fun setMapType(type: String) {
        if (_mapType != type) {
            _mapType = type
        }
        when (type) {
            "NORMAL" -> _aMap.mapType = AMap.MAP_TYPE_NORMAL
            "NIGHT" -> _aMap.mapType = AMap.MAP_TYPE_NIGHT
            "NAVI" -> _aMap.mapType = AMap.MAP_TYPE_NAVI
            "BUS" -> _aMap.mapType = AMap.MAP_TYPE_BUS
            "SATELLITE" -> _aMap.mapType = AMap.MAP_TYPE_SATELLITE
            else -> _aMap.mapType = AMap.MAP_TYPE_NORMAL
        }
    }

    /**
     * 获取地图图层类型
     */
    fun getMapType(@NonNull result: MethodChannel.Result) {
        result.success(_mapType)
    }

    /**
     * 打开/关闭实时路况
     */
    fun turnOnTraffic(on: Boolean) {
        _aMap.isTrafficEnabled = on
    }

    /**
     * 获取实时路况是否打开
     */
    fun isTrafficOn(@NonNull result: MethodChannel.Result) {
        result.success(_aMap.isTrafficEnabled)
    }

    /**
     * 打开/关闭楼块
     */
    fun turnOnBuildings(on: Boolean) {
        _aMap.showBuildings(on)
    }

    /**
     * 获取楼块是否打开
     */
    fun isBuildingsOn(@NonNull result: MethodChannel.Result) {
        result.success(false)
    }

    /**
     * 打开/关闭地图文字标注
     */
    fun turnOnMapText(on: Boolean) {
        _aMap.showMapText(on)
    }

    /**
     * 设置底图语言
     *
     * @param language 底图语言
     */
    fun setMapLanguage(language: String) {
        _aMap.setMapLanguage(if (language == "CHINESE") AMap.CHINESE else AMap.ENGLISH)
    }

    /**
     * 设置定位类型
     *
     * @param type 定位类型
     */
    fun setLocationType(type: String) {
        _locationStyle.myLocationType(handleLocationType(type))
        _aMap.myLocationStyle = _locationStyle
    }

    /**
     * 获取定位类型
     */
    fun getLocationType(@NonNull result: MethodChannel.Result) {
        val typeString = when (_locationStyle.myLocationType) {
            0 -> "SHOW"
            1 -> "LOCATE"
            2 -> "FOLLOW"
            3 -> "MAP_ROTATE"
            4 -> "LOCATION_ROTATE"
            5 -> "LOCATION_ROTATE_NO_CENTER"
            6 -> "FOLLOW_NO_CENTER"
            7 -> "MAP_ROTATE_NO_CENTER"
            else -> "show"
        }
        result.success(typeString)
    }

    /**
     * 设置定位间隔
     *
     * @param interval 定位间隔，单位毫秒
     */
    fun setLocationInterval(interval: Int) {
        _locationStyle.interval(interval.toLong())
        _aMap.myLocationStyle = _locationStyle
    }

    /**
     * 获取定位间隔
     */
    fun getLocationInterval(@NonNull result: MethodChannel.Result) {
        result.success(_locationStyle.interval)
    }

    /**
     * 设置是否显示缩放按钮
     *
     * @param on 是否显示
     */
    fun turnOnZoomControl(on: Boolean) {
        _uiSettings.isZoomControlsEnabled = on
    }

    /**
     * 缩放按钮是否显示
     */
    fun isZoomControlOn(@NonNull result: MethodChannel.Result) {
        result.success(_uiSettings.isZoomControlsEnabled)
    }

    /**
     * 设置是否显示指南针
     *
     * @param on 是否显示
     */
    fun turnOnCompass(on: Boolean) {
        _uiSettings.isCompassEnabled = on
    }

    /**
     * 指南针是否显示
     */
    fun isCompassOn(@NonNull result: MethodChannel.Result) {
        result.success(_uiSettings.isCompassEnabled)
    }

    /**
     * 设置是否显示定位按钮
     *
     * @param on 是否显示
     */
    fun turnOnLocationButton(on: Boolean) {
        _uiSettings.isMyLocationButtonEnabled = on
    }

    /**
     * 定位按钮是否显示
     */
    fun isLocationButtonOn(@NonNull result: MethodChannel.Result) {
        result.success(_uiSettings.isMyLocationButtonEnabled)
    }

    /**
     * 设置是否显示比例尺控件
     *
     * @param on 是否显示
     */
    fun turnOnScaleControl(on: Boolean) {
        _uiSettings.isScaleControlsEnabled = on
    }

    /**
     * 比例尺控件是否显示
     */
    fun isScaleControlOn(@NonNull result: MethodChannel.Result) {
        result.success(_uiSettings.isScaleControlsEnabled)
    }

    /**
     * 设置是否启用所有手势
     *
     * @param on 是否启用
     */
    fun enableAllGesture(on: Boolean) {
        _uiSettings.setAllGesturesEnabled(on)
    }

    /**
     * 所有手势是否启用
     */
    fun isAllGestureEnable(@NonNull result: MethodChannel.Result) {
        result.success(
            _uiSettings.isZoomGesturesEnabled &&
                    _uiSettings.isRotateGesturesEnabled &&
                    _uiSettings.isScrollGesturesEnabled &&
                    _uiSettings.isTiltGesturesEnabled
        )
    }

    /**
     * 设置是否启用缩放手势
     *
     * @param on 是否启用
     */
    fun enableZoomGesture(on: Boolean) {
        _uiSettings.isZoomGesturesEnabled = on
    }

    /**
     * 缩放手势是否启用
     */
    fun isZoomGestureEnable(@NonNull result: MethodChannel.Result) {
        result.success(_uiSettings.isZoomGesturesEnabled)
    }

    /**
     * 设置是否启用旋转手势
     *
     * @param on 是否启用
     */
    fun enableRotateGesture(on: Boolean) {
        _uiSettings.isRotateGesturesEnabled = on
    }

    /**
     * 旋转手势是否启用
     */
    fun isRotateGestureEnable(@NonNull result: MethodChannel.Result) {
        result.success(_uiSettings.isRotateGesturesEnabled)
    }

    /**
     * 设置是否启用拖拽手势
     *
     * @param on 是否启用
     */
    fun enableScrollGesture(on: Boolean) {
        _uiSettings.isScrollGesturesEnabled = on
    }

    /**
     * 拖拽手势是否启用
     */
    fun isScrollGestureEnable(@NonNull result: MethodChannel.Result) {
        result.success(_uiSettings.isScrollGesturesEnabled)
    }

    /**
     * 设置是否启用倾斜手势
     *
     * @param on 是否启用
     */
    fun enableTiltGesture(on: Boolean) {
        _uiSettings.isTiltGesturesEnabled = on
    }

    /**
     * 倾斜手势是否启用
     */
    fun isTiltGestureEnable(@NonNull result: MethodChannel.Result) {
        result.success(_uiSettings.isTiltGesturesEnabled)
    }

    /**
     * 设置Logo位置
     */
    fun setLogoPosition(position: String) {
        when (position) {
            "BOTTOM_LEFT" ->
                _uiSettings.logoPosition = AMapOptions.LOGO_POSITION_BOTTOM_LEFT
            "BOTTOM_RIGHT" ->
                _uiSettings.logoPosition = AMapOptions.LOGO_POSITION_BOTTOM_RIGHT
            "BOTTOM_CENTER" ->
                _uiSettings.logoPosition = AMapOptions.LOGO_POSITION_BOTTOM_CENTER
            else ->
                _uiSettings.logoPosition = AMapOptions.LOGO_POSITION_BOTTOM_LEFT
        }
    }

    /**
     * 获取Logo位置
     */
    fun getLogoPosition(@NonNull result: MethodChannel.Result) {
        val logoPosition = when (_uiSettings.logoPosition) {
            0 -> "BOTTOM_LEFT"
            1 -> "BOTTOM_CENTER"
            2 -> "BOTTOM_RIGHT"
            else -> "BOTTOM_LEFT"
        }
        result.success(logoPosition)
    }

    /**
     * 设置Logo边距
     */
    fun setLogoMargin(margin: HashMap<String, Int>) {
        _uiSettings.setLogoLeftMargin(margin["marginLeft"] ?: 0)
        _uiSettings.setLogoBottomMargin(margin["marginBottom"] ?: 0)
    }

    /**
     * 设置缩放按钮位置
     */
    fun setZoomPosition(position: String) {
        when (position) {
            "RIGHT_BOTTOM" ->
                _uiSettings.zoomPosition = AMapOptions.ZOOM_POSITION_RIGHT_BUTTOM
            "RIGHT_CENTER" ->
                _uiSettings.zoomPosition = AMapOptions.ZOOM_POSITION_RIGHT_CENTER
            else -> _uiSettings.zoomPosition = AMapOptions.ZOOM_POSITION_RIGHT_BUTTOM
        }
    }

    /**
     * 获取缩放按钮位置
     */
    fun getZoomPosition(@NonNull result: MethodChannel.Result) {
        val zoomPosition = when (_uiSettings.zoomPosition) {
            1 -> "RIGHT_CENTER"
            2 -> "RIGHT_BOTTOM"
            else -> "RIGHT_BOTTOM"
        }
        result.success(zoomPosition)
    }

    /**
     * 设置是否以地图中心点缩放
     *
     * @param flag 是否以地图中心点缩放
     */
    fun setIsGestureScaleByMapCenterPosition(flag: Boolean) {
        _uiSettings.isGestureScaleByMapCenter = flag
    }

    /**
     * 获取是否以地图中心点缩放
     */
    fun getIsGestureScaleByMapCenterPosition(@NonNull result: MethodChannel.Result) {
        result.success(_uiSettings.isGestureScaleByMapCenter)
    }

    /**
     * 设置自定义地图样式
     *
     * @param id: 自定义地图样式id
     */
    fun setCustomMapStyleId(id: String) {
        _aMap.setCustomMapStyle(_customMapStyleOptions.apply {
            isEnable = true
            styleId = id
        })
    }

    /**
     * 关闭自定义地图样式
     */
    fun disableCustomMapStyle() {
        _aMap.setCustomMapStyle(_customMapStyleOptions.apply {
            isEnable = false
        })
    }

    /**
     * 设置地图中心点
     *
     * @param latLng 坐标
     */
    fun setMapCenter(latLng: LatLng) {
        _aMap.animateCamera(CameraUpdateFactory.changeLatLng(latLng))
    }

    /**
     * 获取地图中心点
     */
    fun getMapCenter(@NonNull result: MethodChannel.Result) {
        val target = _aMap.cameraPosition.target
        val latLngMap: HashMap<String, Double> = hashMapOf(
            "latitude" to target.latitude,
            "longitude" to target.longitude
        )
        result.success(latLngMap)
    }

    /**
     * 设置地图显示范围
     *
     * @param bound 西南角、东北角
     */
    fun setBound(bound: List<HashMap<String, Double>>) {
        _aMap.setMapStatusLimits(
            LatLngBounds(
                LatLng(
                    bound[0]["latitude"]!!,
                    bound[0]["longitude"]!!
                ),
                LatLng(
                    bound[1]["latitude"]!!,
                    bound[1]["longitude"]!!
                )
            )
        )
    }

    /**
     * 地图截屏
     */
    fun screenShot(@NonNull result: MethodChannel.Result) {
        _aMap.getMapScreenShot(object : AMap.OnMapScreenShotListener {
            override fun onMapScreenShot(bitmap: Bitmap?) {

            }

            override fun onMapScreenShot(bitmap: Bitmap?, status: Int) {
                val sdf = SimpleDateFormat("yyyyMMddHHmmss")
                if (null == bitmap) {
                    result.error("1001", "Bitmap为空", null)
                }
                try {
                    val contextWrapper = ContextWrapper(activity.applicationContext)
                    val path =
                        contextWrapper.externalCacheDir!!.absolutePath +
                                "/" + sdf.format(Date()) + ".png"
                    val fos = FileOutputStream(path)
                    val b: Boolean = bitmap!!.compress(CompressFormat.PNG, 100, fos)
                    try {
                        fos.flush()
                    } catch (e: IOException) {
                        e.printStackTrace()
                        result.error("1002", "截屏保存失败", null)
                    }
                    try {
                        fos.close()
                    } catch (e: IOException) {
                        e.printStackTrace()
                        result.error("1002", "截屏保存失败", null)
                    }
                    if (b) {
                        result.success(path)
                    } else {
                        result.error("1002", "截屏保存失败", null)
                    }
                    if (status != 0) {
                        Log.d("Screenshot", "地图渲染完成，截屏无网格")
                    } else {
                        Log.d("Screenshot", "地图未渲染完成，截屏有网格")
                    }
                } catch (e: FileNotFoundException) {
                    result.error("1003", "截屏失败", null)
                    e.printStackTrace()
                }
            }
        })
    }

    override fun getView(): View {
        return _mapView
    }

    override fun dispose() {
        activity.lifecycle.removeObserver(this)
    }

    override fun onCreate(owner: LifecycleOwner) {

        _mapView.onCreate(null)

        initAMapView()
    }

    override fun onStart(owner: LifecycleOwner) {
    }

    override fun onResume(owner: LifecycleOwner) {
        _mapView.onResume()
    }

    override fun onPause(owner: LifecycleOwner) {
        _mapView.onPause()
    }

    override fun onStop(owner: LifecycleOwner) {
    }

    override fun onDestroy(owner: LifecycleOwner) {
        _mapView.onDestroy()
    }
}
