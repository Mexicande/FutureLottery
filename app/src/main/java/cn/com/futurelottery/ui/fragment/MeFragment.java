package cn.com.futurelottery.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.com.futurelottery.R;
import cn.com.futurelottery.base.BaseFragment;

/**
 * Created by tantan on 2018/4/9.
 * 我的
 */

public class MeFragment extends BaseFragment{
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            view = View.inflate(getActivity(), R.layout.me_fragment, null);
        return view;
    }
}
