package cn.jjvu.xiao.easy_flutter_amap.view;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import androidx.annotation.NonNull;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;

import java.util.HashMap;
import java.util.Map;

import cn.jjvu.xiao.easy_flutter_amap.R;
import io.flutter.Log;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.platform.PlatformView;

public class AmapView implements PlatformView, MethodChannel.MethodCallHandler {

    MapView mapView;

    AMap aMap;

    LocationSource.OnLocationChangedListener mListener;

    private MethodChannel methodChannel;

    private Context context;

    public AmapView(Context context, BinaryMessenger messenger, int id, Map<String, Object> params) {
        methodChannel = new MethodChannel(messenger, "easy_flutter_amap");
        methodChannel.setMethodCallHandler(this);
        createMap(context);
        initMapOptions();
        mapView.onResume();
        this.context = context;
    }

    @Override
    public View getView() {
        return mapView;
    }

    @Override
    public void dispose() {
        mapView.onDestroy();
    }

    private void createMap(Context context) {
        mapView = new MapView(context);
        mapView.onCreate(new Bundle());
        aMap = mapView.getMap();
    }

    private void initMapOptions() {
        CameraUpdateFactory.zoomTo(31);
        aMap.getUiSettings().setMyLocationButtonEnabled(true);
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        myLocationStyle.strokeWidth(1f);
        myLocationStyle.strokeColor(Color.parseColor("#8052A3FF"));
        myLocationStyle.radiusFillColor(Color.parseColor("#3052A3FF"));
        myLocationStyle.showMyLocation(true);
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory.fromResource(R.drawable.mime));
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);
        myLocationStyle.interval(1000);
        aMap.setMyLocationStyle(myLocationStyle);
        aMap.setMyLocationEnabled(true);
    }

    @Override
    public void onMethodCall(@NonNull MethodCall call, @NonNull MethodChannel.Result result) {
        Map<String, Object> args = (Map<String, Object>) call.arguments;
        if (call.method.equals("sendData")) {
            String name = (String) args.get("data");
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(new LatLng(34.341568, 108.940174));
            markerOptions.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.mime)));
            markerOptions.setFlat(true);
            markerOptions.snippet("DefaultMarker");
            //设置覆盖物比例
            markerOptions.anchor(0.5f, 0.5f);
            Log.d("xiao", "初始化mark");
            Marker marker = aMap.addMarker(markerOptions);
            Log.d("xiao", "添加mark");
            Map<String, String> r = new HashMap<>();
            r.put("result", "成功");
            result.success(r);
        }
        else {
            result.notImplemented();
        }
    }
}
