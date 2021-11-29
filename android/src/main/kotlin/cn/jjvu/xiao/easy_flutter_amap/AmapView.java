package cn.jjvu.xiao.easy_flutter_amap;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.CustomMapStyleOptions;
import com.amap.api.maps.model.MyLocationStyle;

import java.util.Map;

import io.flutter.Log;
import io.flutter.embedding.android.FlutterActivity;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.platform.PlatformView;

public class AmapView implements PlatformView, DefaultLifecycleObserver, MethodChannel.MethodCallHandler {

    private Context context;

    private FlutterActivity activity;

    private int id;

    private Map<String, Object> creationParams;

    private MapView mapView;

    private AMap aMap;

    private static final String TAG = "AmapView";

    private MethodChannel methodChannel;

    // 当前位置的样式
    private MyLocationStyle myLocationStyle = new MyLocationStyle();

    private CustomMapStyleOptions customMapStyleOptions = new CustomMapStyleOptions();

    // 初始化后是否自动定位
    private boolean autoLocateAfterInit;

    // 地图类型
    private String mapType;

    // 底图语言
    private String mapLanguage;

    // 定位类型
    private String showMyLocation;

    // 定位间隔
    private Integer locationInterval;

    // 是否显示实时路况
    private boolean showTraffic;

    // 是否显示楼块
    private boolean showBuildings;

    // 是否显示地图文字标注
    private boolean showMapText;

    // 是否显示缩放控件
    private boolean showZoomControl;

    // 是否显示指南针
    private boolean showCompass;

    // 是否显示定位按钮
    private boolean showLocationButton;

    // 是否显示比例尺控件
    private boolean showScaleControl;

    // 缩放等级
    private float zoomLevel;

    private UiSettings uiSettings;

    // 初始化缩放等级, [3, 19];
    private Double initialZoomLevel;

    // 最大缩放等级
    private Double maxZoomLevel;

    // 最小缩放等级
    private Double minZoomLevel;

    // 自定义地图ID
    private String customMapStyleId;


    public AmapView(Context context, BinaryMessenger messenger, int id, Map<String, Object> params) {
        Log.d(TAG, params.toString());
        methodChannel = new MethodChannel(messenger, "easy_flutter_amap");
        methodChannel.setMethodCallHandler(this);
        creationParams = params;
        createMap(context);
//        initMapOptions();
        initAmapView();;
        mapView.onResume();
        this.context = context;
    }

    private void createMap(Context context) {
        mapView = new MapView(context);
        mapView.onCreate(new Bundle());
        aMap = mapView.getMap();
    }

    private void initAmapView() {
        setMapType(this.mapType);
        aMap.moveCamera(CameraUpdateFactory.zoomTo(this.zoomLevel));
    }

    /*
     * 初始化当前的位置
     */
    private void initMyLocationStyle() {
        myLocationStyle.interval(Long.parseLong(creationParams.get("interval").toString()));
        aMap.getUiSettings().setMyLocationButtonEnabled(true);
        myLocationStyle.showMyLocation(true);
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);
        aMap.setMyLocationStyle(myLocationStyle);
        aMap.setMyLocationEnabled(true);
    }


    /**
     * 设置地图的类型
     */
    private void setMapType(String type) {
        if (!this.mapType.equals(type)) {
            this.mapType = type;
        }
        switch (type) {
            case "NORMAL":
                this.aMap.setMapType(AMap.MAP_TYPE_NORMAL);
                break;
            case "NIGHT":
                this.aMap.setMapType(AMap.MAP_TYPE_NIGHT);
                break;
            case "NAVI":
                this.aMap.setMapType(AMap.MAP_TYPE_NAVI);
                break;
            case "BUS":
                this.aMap.setMapType(AMap.MAP_TYPE_BUS);
                break;
            case "SATELLITE":
                this.aMap.setMapType(AMap.MAP_TYPE_SATELLITE);
                break;
            default:
                this.aMap.setMapType(AMap.MAP_TYPE_NORMAL);
        }
    }

    /**
     * 设置缩放等级
     * @param zoomLevel 缩放等级
     */
    private void setZoomLevel(Double zoomLevel) {
        aMap.animateCamera(CameraUpdateFactory.zoomTo(zoomLevel.floatValue()));
    }

    /**
     * 获取当前缩放等级
     * @param result 方法通道
     */
    private void getZoomLevel(MethodChannel.Result result) {
        result.success(this.aMap.getCameraPosition().zoom);
    }

    /**
     * 放大缩放等级
     */
    private void zoomIn() {
        this.aMap.animateCamera(CameraUpdateFactory.zoomIn());
    }

    /**
     * 放大缩放等级
     */
    private void zoomOut() {
        this.aMap.animateCamera(CameraUpdateFactory.zoomOut());
    }

    /**
     * 设置最大缩放等级
     *
     * @param level 最大缩放等级
     */
    private void setMaxZoomLevel(Double level) {
        this.aMap.setMaxZoomLevel(level.floatValue());
    }

    /**
     * 设置最小缩放等级
     *
     * @param level 最小缩放等级
     */
    private void setMinZoomLevel(Double level) {
        this.aMap.setMinZoomLevel(level.floatValue());
    }

    /**
     * 设置定位类型
     *
     * @param type 定位类型
     */
    private void setMyLocationStyle(String type) {
        this.myLocationStyle.myLocationType(handleMyLocationStyle(type));
    }

    /**
     * 设置当前位置的定位类型
     * @param type 定位类型
     * @return 高德地图的定位类型
     */
    private Integer handleMyLocationStyle(String type) {
        Integer myType;
        switch (type) {
            case "SHOW":
                myType = MyLocationStyle.LOCATION_TYPE_SHOW;
                break;
            case "LOCATE":
                myType = MyLocationStyle.LOCATION_TYPE_LOCATE;
                break;
            case "FOLLOW":
                myType = MyLocationStyle.LOCATION_TYPE_FOLLOW;
                break;
            case "MAP_ROTATE":
                myType = MyLocationStyle.LOCATION_TYPE_MAP_ROTATE;
                break;
            case "LOCATION_ROTATE":
                myType = MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE;
                break;
            case "LOCATION_ROTATE_NO_CENTER":
                myType = MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE_NO_CENTER;
                break;
            case "FOLLOW_NO_CENTER":
                myType = MyLocationStyle.LOCATION_TYPE_FOLLOW_NO_CENTER;
                break;
            case "MAP_ROTATE_NO_CENTER":
                myType = MyLocationStyle.LOCATION_TYPE_MAP_ROTATE_NO_CENTER;
                break;
            default:
                myType = MyLocationStyle.LOCATION_TYPE_LOCATE;
        }
        return myType;
    }

    private void turnOnZoomControl(boolean on) {
        this.uiSettings.setZoomControlsEnabled(on);
    }

    void setZoomLevel(boolean isShow) {
//        turnOnZoomLevelContro
    }

    @Override
    public View getView() {
        return this.mapView;
    }

    @Override
    public void dispose() {
        this.mapView.onDestroy();
    }

    @Override
    public void onMethodCall(@NonNull MethodCall call, @NonNull MethodChannel.Result result) {

    }
}
