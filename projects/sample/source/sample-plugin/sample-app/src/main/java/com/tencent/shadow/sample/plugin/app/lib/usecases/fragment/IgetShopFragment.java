package com.tencent.shadow.sample.plugin.app.lib.usecases.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/*import androidx.fragment.app.Fragment;
*/
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.tencent.shadow.sample.host.lib.BaseFragment;
import com.tencent.shadow.sample.plugin.app.lib.R;
import com.tencent.shadow.sample.plugin.app.lib.UseCaseApplication;

//import com.luojilab.bookcitysdk.CitySdk;

public class IgetShopFragment extends BaseFragment {

    public static IgetShopFragment newInstance(Bundle args) {
        IgetShopFragment fragment = new IgetShopFragment();
        if (args != null) {
            fragment.setArguments(args);
        }
        return fragment;
    }

    public IgetShopFragment() {
        Log.e("chenhj", "IgetShopFragment()");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.e("chenhj", "IgetShopFragment->onCreateView： ");
        View view = LayoutInflater.from(UseCaseApplication.getInstance()).inflate(R.layout.fragment_iget_shop, null, false);
        //View view = inflater.inflate(R.layout.fragment_iget_shop, container, false);
        addIgetFragment();
        return view;
    }

    private static boolean hasAddIgetShopFragment = false;
    private void addIgetFragment() {
        hasAddIgetShopFragment = true;
        //Fragment igetFragment = CitySdk.getMainFragment();
        Fragment igetFragment = TestFragment.newInstance(null);
        Log.e("chenhj", "load iget shop Fragment -> " + igetFragment);
        //FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        if (fragmentTransaction == null) {
            Log.e("chenhj", "iget shop fragment add failed, fragmentTransaction is null.");
            return;
        }
        fragmentTransaction.replace(R.id.layout_container, igetFragment);
        fragmentTransaction.commit();
    }
}
