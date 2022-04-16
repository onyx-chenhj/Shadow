package com.tencent.shadow.sample.host.lib;

import android.app.Fragment;
import android.content.Context;
import android.content.res.Resources;

public class BaseFragment extends Fragment {

    private Context pluginContext;
    private Resources pluginResources;

    public BaseFragment() {
        super();
    }

    public void setPluginContext(Context pluginContext) {
        this.pluginContext = pluginContext;
    }

    public Context getPluginContext() {
        return pluginContext;
    }
}
