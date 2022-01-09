//
//  EasyFlutterAmapFactory.swift
//  easy_flutter_amap
//
//  Created by xiao on 2022/1/9.
//

import Foundation

class EasyFlutterAmapFactory : NSObject,FlutterPlatformViewFactory{

    
    var messenger: FlutterBinaryMessenger!
    
    func create(withFrame frame: CGRect, viewIdentifier viewId: Int64, arguments args: Any?) -> FlutterPlatformView {
        return EasyAmapView(messenger: messenger, params: args);
    }
    
//    init(messenger: (NSObject & FlutterBinaryMessenger)?) {
    init(messenger: FlutterBinaryMessenger) {
        super.init()
        self.messenger = messenger
    }
}
