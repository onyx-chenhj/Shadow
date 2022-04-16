package com.tencent.shadow.sample.host.plugin_view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.tencent.shadow.sample.constant.Constant;
import com.tencent.shadow.sample.host.HostApplication;

public class MainProcessManagerReceiver extends BroadcastReceiver {

    public static final String ACTION_LOAD_VIEW_TO_HOST = "ACTION_LOAD_VIEW_TO_HOST";
    public static final String ACTION_LOAD_FRAGMENT_TO_HOST = "ACTION_LOAD_FRAGMENT_TO_HOST";

    @Override
    public void onReceive(Context context, Intent intent) {
        HostApplication.getApp().getPluginManager()
                .enter(context, Constant.FROM_ID_LOAD_VIEW_TO_HOST, intent.getExtras(), null);
    }
}
