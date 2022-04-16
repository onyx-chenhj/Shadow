/*
 * Tencent is pleased to support the open source community by making Tencent Shadow available.
 * Copyright (C) 2019 THL A29 Limited, a Tencent company.  All rights reserved.
 *
 * Licensed under the BSD 3-Clause License (the "License"); you may not use
 * this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 *     https://opensource.org/licenses/BSD-3-Clause
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.tencent.shadow.sample.plugin.app.lib.usecases.fragment;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.tencent.shadow.sample.host.lib.BaseFragment;
import com.tencent.shadow.sample.host.lib.HostAddPluginViewContainerHolder;
import com.tencent.shadow.sample.plugin.app.lib.R;
import com.tencent.shadow.sample.plugin.app.lib.UseCaseApplication;
import com.tencent.shadow.sample.plugin.app.lib.usecases.activity.TestActivityOnCreate;

public class TestFragment extends BaseFragment {

    public static TestFragment newInstance(Bundle bundle) {
        TestFragment testFragment = new TestFragment();
        testFragment.setArguments(bundle);
        return testFragment;
    }

    public static TestFragment newInstance(Context context, Bundle bundle) {
        TestFragment testFragment = new TestFragment();
        testFragment.setArguments(bundle);
        testFragment.setPluginContext(context);
        return testFragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        //View view = inflater.inflate(R.layout.layout_fragment_test, null, false);
        //View view = inflater.inflate(HostAddPluginViewContainerHolder.pluginResources.getLayout(R.layout.layout_fragment_test), null, false);
        View view = LayoutInflater.from(getPluginContext()).inflate(R.layout.layout_fragment_test, null, false);
        TextView textView = view.findViewById(R.id.tv_msg);
        Bundle bundle = getArguments();
        if (bundle != null) {
            String msg = bundle.getString("msg");
            if (!TextUtils.isEmpty(msg)) {
                textView.setText(msg);
            }
        }

        Button button1 = (Button) view.findViewById(R.id.btn_test1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("chenhj", "在插件中启动activity");
                /*Intent intent = new Intent(getPluginContext(), TestActivityOnCreate.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getPluginContext().startActivity(intent);*/

                Intent intent = new Intent(UseCaseApplication.getInstance(), TestActivityOnCreate.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                UseCaseApplication.getInstance().startActivity(intent);

            }
        });

        IgetTestFragment igetTestFragment = IgetTestFragment.newInstance(null);

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.layout_container, igetTestFragment);
        fragmentTransaction.commit();

        return view;
    }
}
