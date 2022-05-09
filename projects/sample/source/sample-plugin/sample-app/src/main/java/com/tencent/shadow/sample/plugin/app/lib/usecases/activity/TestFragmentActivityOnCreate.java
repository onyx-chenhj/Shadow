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

package com.tencent.shadow.sample.plugin.app.lib.usecases.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.tencent.shadow.sample.host.lib.BaseFragment;
import com.tencent.shadow.sample.plugin.app.lib.R;
import com.tencent.shadow.sample.plugin.app.lib.gallery.cases.entity.UseCase;
import com.tencent.shadow.sample.plugin.app.lib.gallery.util.ToastUtil;
import com.tencent.shadow.sample.plugin.app.lib.usecases.fragment.TestFragment;

public class TestFragmentActivityOnCreate extends FragmentActivity {

    public static class Case extends UseCase {
        @Override
        public String getName() {
            return "TestFragmentActivityOnCreate生命周期测试";
        }

        @Override
        public String getSummary() {
            return "测试TestFragmentActivityOnCreate的生命周期方法是否正确回调";
        }

        @Override
        public Class getPageClass() {
            return TestFragmentActivityOnCreate.class;
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_fragment_activity_lifecycle);
        ToastUtil.showToast(this, "onCreate");
        getWindow().getDecorView().addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                if (!hasAddFragment) {
                    addFragment();
                }
            }
        });
    }

    private static boolean hasAddFragment = false;
    private void addFragment() {
        hasAddFragment = true;
        //Fragment igetFragment = CitySdk.getMainFragment();
        Fragment igetFragment = TestFragment.newInstance(null);
        Log.e("chenhj", "load iget shop Fragment -> " + igetFragment);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        //FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        if (fragmentTransaction == null) {
            Log.e("chenhj", "iget shop fragment add failed, fragmentTransaction is null.");
            return;
        }
        fragmentTransaction.replace(R.id.layout_container, igetFragment);
        fragmentTransaction.commit();
    }

    @Override
    protected void onStart() {
        super.onStart();
        ToastUtil.showToast(this, "onStart");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        ToastUtil.showToast(this, "onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        ToastUtil.showToast(this, "onResume");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        ToastUtil.showToast(this, "onSaveInstanceState");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        ToastUtil.showToast(this, "onRestoreInstanceState");
    }

    @Override
    protected void onStop() {
        super.onStop();
        ToastUtil.showToast(this, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ToastUtil.showToast(this, "onDestroy");
    }
}
