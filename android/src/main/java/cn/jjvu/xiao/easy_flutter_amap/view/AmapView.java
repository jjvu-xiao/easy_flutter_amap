package cn.jjvu.xiao.easy_flutter_amap.view;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;

import java.util.Map;

import cn.jjvu.xiao.easy_flutter_amap.R;
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

    private static final String TAG = "AmapView";

    private Map<String, Object> initParams;

    public AmapView(Context context, BinaryMessenger messenger, int id, Map<String, Object> params) {
        Log.d(TAG, params.toString());
        methodChannel = new MethodChannel(messenger, "easy_flutter_amap");
        methodChannel.setMethodCallHandler(this);
        initParams = params;
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
        Log.d(TAG, initParams.toString());
        aMap.moveCamera(CameraUpdateFactory.zoomTo(Float.parseFloat(initParams.get("zoomLevel").toString())));
        aMap.getUiSettings().setMyLocationButtonEnabled(true);
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        myLocationStyle.interval(Long.parseLong(initParams.get("interval").toString()));
        myLocationStyle.strokeWidth(1f);
        myLocationStyle.strokeColor(Color.parseColor("#8052A3FF"));
        myLocationStyle.radiusFillColor(Color.parseColor("#3052A3FF"));
        myLocationStyle.showMyLocation(true);
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory.fromResource(R.drawable.mime));
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);
        aMap.setMyLocationStyle(myLocationStyle);
        aMap.setMyLocationEnabled(true);
    }

    @Override
    public void onMethodCall(@NonNull MethodCall call, @NonNull MethodChannel.Result result) {
        if (call.method.equals("addMarkers")) {
            if (call.arguments != null) {
                Map<String, Object> datas = (Map<String, Object>) call.arguments;
                showMarker(datas);
                result.success("suc");
            }
        }
    }

    private void showMarker(Map<String, Object> data) {
        MarkerOptions markerOption = new MarkerOptions();
        markerOption.position(new LatLng((double) data.get("latitude"), (double) data.get("longitude")));
        markerOption.title((String) data.get("title"));
        markerOption.draggable(false);
        markerOption.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
            .decodeResource(this.context.getResources(), R.drawable.location_marker)));
        markerOption.setFlat(true);
        aMap.addMarker(markerOption);
    }
}
