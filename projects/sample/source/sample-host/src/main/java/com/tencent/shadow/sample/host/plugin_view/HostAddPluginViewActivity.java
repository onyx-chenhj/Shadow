package com.tencent.shadow.sample.host.plugin_view;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

/*import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;*/

import com.tencent.shadow.sample.host.HostApplication;
import com.tencent.shadow.sample.host.R;
import com.tencent.shadow.sample.host.lib.BaseFragment;
import com.tencent.shadow.sample.host.lib.HostAddPluginViewContainer;
import com.tencent.shadow.sample.host.lib.HostAddPluginViewContainerHolder;

public class HostAddPluginViewActivity extends Activity implements HostAddPluginViewContainer {
    private ViewGroup mPluginViewContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout activityContentView = new LinearLayout(this);
        activityContentView.setOrientation(LinearLayout.VERTICAL);

        LinearLayout.LayoutParams wrapContent = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );

        TextView note = new TextView(this);
        note.setLayoutParams(wrapContent);
        note.setText("需要先启动插件sample-plugin-app后，才能点下面的加载插件View");

        Button loadButton = new Button(this);
        loadButton.setText("加载插件View");
        loadButton.setOnClickListener(this::loadPluginView);
        loadButton.setLayoutParams(wrapContent);

        Button loadFragmentButton = new Button(this);
        loadFragmentButton.setText("加载插件Fragment");
        loadFragmentButton.setOnClickListener(this::loadPluginFragment);
        loadFragmentButton.setLayoutParams(wrapContent);

        ViewGroup pluginViewContainer = new LinearLayout(this);
        pluginViewContainer.setLayoutParams(wrapContent);
        pluginViewContainer.setId(R.id.id_fragment);
        mPluginViewContainer = pluginViewContainer;

        View[] views = {
                note,
                loadButton,
                loadFragmentButton,
                pluginViewContainer
        };
        for (View view : views) {
            activityContentView.addView(view);
        }
        setContentView(activityContentView);
    }

    private void loadPluginView(View view) {
        //简化逻辑，只允许点一次
        view.setEnabled(false);

        //因为当前Activity和插件都在:plugin进程，不能直接操作主进程的manager对象，所以通过一个广播调用manager。
        Intent intent = new Intent();
        intent.setPackage(getPackageName());
        intent.setAction("sample_host.manager.startPluginService");

        final int id = System.identityHashCode(this);
        HostAddPluginViewContainerHolder.instances.put(id, this);
        intent.putExtra("id", id);

        sendBroadcast(intent);
    }

    @Override
    public void addView(View view) {
        Log.e("chenhj", "HostAddPluginViewActivity:addView");
        mPluginViewContainer.addView(view);
    }

    private void loadPluginFragment(View view) {
        //简化逻辑，只允许点一次
        view.setEnabled(false);

        //因为当前Activity和插件都在:plugin进程，不能直接操作主进程的manager对象，所以通过一个广播调用manager。
        Intent intent = new Intent();
        intent.setPackage(getPackageName());
        intent.setAction("sample_host.manager.startPluginService");

        final int id = System.identityHashCode(this);
        HostAddPluginViewContainerHolder.instances.put(id, this);
        intent.putExtra("id", id);

        sendBroadcast(intent);
    }

    @Override
    public void addFragment(Fragment fragment) {
        Log.e("chenhj", "HostAddPluginViewActivity:addFragment::" + fragment);
        addFragmentTest1(fragment);
        //addFragmentTest2();
    }

    public void addFragmentTest1(Fragment fragment) {
        Log.e("chenhj", "HostAddPluginViewActivity:addFragment::" + fragment);
        try {
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            Log.e("chenhj", "HostAddPluginViewActivity:mPluginViewContainer id::" + mPluginViewContainer.getId());
            fragmentTransaction.replace(mPluginViewContainer.getId(), fragment);
            fragmentTransaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addFragmentTest2() {
        Fragment fragment2 = null;
        try {
            fragment2 = HostAddPluginViewContainerHolder.pluginClassLoader.loadClass("com.tencent.shadow.sample.plugin.app.lib.usecases.fragment.IgetTestFragment").asSubclass(Fragment.class).newInstance();
            //fragment2 = getClassLoader().loadClass("com.tencent.shadow.sample.plugin.app.lib.usecases.fragment.IgetTestFragment").asSubclass(Fragment.class).newInstance();
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            Log.e("chenhj", "HostAddPluginViewActivity:mPluginViewContainer id::" + mPluginViewContainer.getId());
            Log.e("chenhj", "HostAddPluginViewActivity:fragment2::" + fragment2);
            fragmentTransaction.replace(mPluginViewContainer.getId(), fragment2);
            fragmentTransaction.commit();

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
