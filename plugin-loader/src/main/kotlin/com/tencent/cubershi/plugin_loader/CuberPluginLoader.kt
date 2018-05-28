package com.tencent.cubershi.plugin_loader

import android.content.Context
import android.content.res.Resources
import com.tencent.cubershi.plugin_loader.blocs.CreateApplicationBloc
import com.tencent.cubershi.plugin_loader.blocs.CreateResourceBloc
import com.tencent.cubershi.plugin_loader.blocs.LoadApkBloc
import com.tencent.cubershi.plugin_loader.blocs.ParsePluginApkBloc
import com.tencent.cubershi.plugin_loader.delegates.DefaultHostActivityDelegate
import com.tencent.cubershi.plugin_loader.test.FakeRunningPlugin
import com.tencent.hydevteam.common.progress.ProgressFuture
import com.tencent.hydevteam.common.progress.ProgressFutureImpl
import com.tencent.hydevteam.pluginframework.installedplugin.InstalledPlugin
import com.tencent.hydevteam.pluginframework.plugincontainer.*
import com.tencent.hydevteam.pluginframework.pluginloader.LoadPluginException
import com.tencent.hydevteam.pluginframework.pluginloader.PluginLoader
import com.tencent.hydevteam.pluginframework.pluginloader.RunningPlugin
import dalvik.system.DexClassLoader
import org.slf4j.LoggerFactory
import java.util.concurrent.Callable
import java.util.concurrent.Executors

class CuberPluginLoader : PluginLoader, DelegateProvider {

    private val mExecutorService = Executors.newSingleThreadExecutor()

    private lateinit var mPluginClassLoader: DexClassLoader

    private lateinit var mPluginResources: Resources

    @Throws(LoadPluginException::class)
    override fun loadPlugin(hostAppContext: Context, installedPlugin: InstalledPlugin): ProgressFuture<RunningPlugin> {
        if (mLogger.isInfoEnabled) {
            mLogger.info("loadPlugin installedPlugin=={}", installedPlugin)
        }
        if (installedPlugin.pluginFile != null && installedPlugin.pluginFile.exists()) {
            val submit = mExecutorService.submit(Callable<RunningPlugin> {
                val (applicationClassName) = ParsePluginApkBloc.parse(installedPlugin.pluginFile)
                val pluginClassLoader = LoadApkBloc.loadPlugin(installedPlugin.pluginFile)
                mPluginClassLoader = pluginClassLoader
                val resources = CreateResourceBloc.create(installedPlugin.pluginFile.absolutePath, hostAppContext)
                mPluginResources = resources
                val mockApplication = CreateApplicationBloc.callPluginApplicationOnCreate(pluginClassLoader, applicationClassName, resources)
                mockApplication.hostApplicationContext = hostAppContext
                FakeRunningPlugin(mockApplication, installedPlugin)
            })
            return ProgressFutureImpl(submit, null)
        } else if (installedPlugin.pluginFile != null)
            throw LoadPluginException("插件文件不存在.pluginFile==" + installedPlugin.pluginFile.absolutePath)
        else
            throw LoadPluginException("pluginFile==null")

    }

    override fun setPluginDisabled(installedPlugin: InstalledPlugin): Boolean {
        return false
    }

    override fun getHostActivityDelegate(aClass: Class<out HostActivityDelegator>): HostActivityDelegate {
        return DefaultHostActivityDelegate(mPluginClassLoader, mPluginResources)
    }

    override fun getHostServiceDelegate(aClass: Class<out HostServiceDelegator>): HostServiceDelegate? {
        return null
    }

    companion object {
        private val mLogger = LoggerFactory.getLogger(CuberPluginLoader::class.java)
    }
}
