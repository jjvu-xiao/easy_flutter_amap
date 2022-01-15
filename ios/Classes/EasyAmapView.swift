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
    
//    private var locationInterval: Int
    
//    private var showZoom: Bool
    
//    private var showCompass: Bool
    
//    private var showScale: Bool
    
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
        
        let r = MAUserLocationRepresentation()
        r.showsHeadingIndicator = true
        r.showsAccuracyRing = false
        mapView.update(r)
        
        self.mapView.mapType = setMapType(val: params["mapType"] as! Int) ?? MAMapType.standard
        self.mapView.showsScale = params["showScale"] as! Bool
        self.mapView.showsCompass = params["showCompass"] as! Bool
    }
    
    func setMapType(val mapType: Int) -> MAMapType? {
        return MAMapType(rawValue: mapType - 1)
    }

}

extension EasyAmapView: MAMapViewDelegate {
}

