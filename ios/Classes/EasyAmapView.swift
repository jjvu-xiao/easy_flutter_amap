//
//  File.swift
//  easy_flutter_amap
//
//  Created by xiao on 2022/1/9.
//

import Foundation
import Flutter


class EasyAmapView : NSObject, FlutterPlatformView {

    let params: Dictionary<String, Any?>
    
    let messenger: FlutterBinaryMessenger
    
    var mapView: MAMapView
    
    private var _view: UIView
    
    init(messenger: FlutterBinaryMessenger, params: Dictionary<String, Any?>) {
        _view = UIView()
//        self.params = params
        self.messenger = messenger
        self.mapView = MAMapView()
        self.params = params
        mapView = MAMapView(frame: _view.bounds)
        super.init()
        initMapView()
        _view.addSubview(mapView)
    }
    
    func view() -> UIView {
        return self._view
    }
    
    func initMapView() {
        self.mapView.delegate = self
        AMapServices.shared().enableHTTPS = true
        self.mapView.showsUserLocation = true
        self.mapView.userTrackingMode = .followWithHeading
        self.mapView.mapType = setMapType(val: params["mapType"] as! Int) ?? MAMapType.standard
    }
    
    func setMapType(val mapType: Int) -> MAMapType? {
        return MAMapType(rawValue: mapType - 1)
    }

}

extension EasyAmapView: MAMapViewDelegate {
}

