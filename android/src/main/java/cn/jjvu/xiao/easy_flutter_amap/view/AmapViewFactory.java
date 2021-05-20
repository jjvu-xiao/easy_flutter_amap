package cn.jjvu.xiao.easy_flutter_amap.view;

import android.app.Activity;
import android.content.Context;

import java.util.Map;

import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.StandardMessageCodec;
import io.flutter.plugin.platform.PlatformView;
import io.flutter.plugin.platform.PlatformViewFactory;

public class AmapViewFactory extends PlatformViewFactory {

    private BinaryMessenger messenger;

    private Activity activity;

    public AmapViewFactory(BinaryMessenger messenge, Activity activity) {
        super(StandardMessageCodec.INSTANCE);
        this.activity = activity;
        this.messenger = messenge;
    }

    @Override
    public PlatformView create(Context context, int id, Object args) {
        Map<String, Object> params = (Map<String, Object>) args;
        return new AmapView(context, messenger, id, params);
    }
}
