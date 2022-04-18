package com.tencent.shadow.sample.host.lib;

import android.content.Context;
import android.content.res.Resources;

import java.util.HashMap;
import java.util.Map;

public class HostAddPluginViewContainerHolder {
    public final static Map<Integer, HostAddPluginViewContainer> instances = new HashMap<>();
    public static Context pluginContext;
    public static Resources pluginResources;
    public static ClassLoader pluginClassLoader;
}
