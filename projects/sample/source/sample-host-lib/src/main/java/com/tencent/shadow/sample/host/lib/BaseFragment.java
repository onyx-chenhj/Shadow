package com.tencent.shadow.sample.host.lib;

import android.content.Context;
import android.content.res.Resources;
import androidx.fragment.app.Fragment;

public class BaseFragment extends Fragment {

    private Context pluginContext;
    private Resources pluginResources;

    public BaseFragment() {
        super();
    }

    public void setPluginContext(Context pluginContext) {
        getFragmentManager();
        this.pluginContext = pluginContext;
    }

    public Context getPluginContext() {
        return pluginContext;
    }
}
