package com.tencent.shadow.sample.plugin.app.lib.usecases.service;

import android.app.Fragment;
import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

//import androidx.fragment.app.Fragment;

import com.tencent.shadow.sample.host.lib.HostAddPluginViewContainer;
import com.tencent.shadow.sample.host.lib.HostAddPluginViewContainerHolder;
import com.tencent.shadow.sample.plugin.app.lib.R;
import com.tencent.shadow.sample.plugin.app.lib.usecases.fragment.IgetTestFragment;
import com.tencent.shadow.sample.plugin.app.lib.usecases.fragment.TestFragment;

public class HostAddPluginViewService extends IntentService {
    private final Handler uiHandler = new Handler(Looper.getMainLooper());

    public HostAddPluginViewService() {
        super("HostAddPluginViewService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        int id = intent.getIntExtra("id", 0);
        HostAddPluginViewContainer viewContainer
                = HostAddPluginViewContainerHolder.instances.remove(id);

        uiHandler.post(() -> {
            View view = LayoutInflater.from(this).inflate(
                    R.layout.layout_host_add_plugin_view, null, false);
            //viewContainer.addView(view);

            Log.e("chenhj", "HostAddPluginViewService->pluginResources:" + HostAddPluginViewContainerHolder.pluginResources);

            String msg = "chenh这是一个动态添加的fragment";
            Bundle bundle = new Bundle();
            bundle.putString("msg", msg);
            Fragment testFragment = TestFragment.newInstance(this,bundle);
            //Fragment testFragment = IgetTestFragment.newInstance(bundle);
            viewContainer.addFragment(testFragment);

            //viewContainer.addFragment(null);

        });
    }
}
