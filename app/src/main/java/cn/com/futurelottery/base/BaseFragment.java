package cn.com.futurelottery.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.com.futurelottery.R;
import cn.com.futurelottery.utils.StatusBarUtil;

/**
 * Created by apple on 2018/4/8.
 */

public abstract class BaseFragment extends Fragment {



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setTitle();
    }

    protected  void setTitle(){

    }


}
