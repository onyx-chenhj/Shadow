package com.tencent.shadow.sample.host.lib;

import android.app.Fragment;
import android.view.View;


public interface HostAddPluginViewContainer {
    void addView(View view);
    void addFragment(Fragment fragment);
}
