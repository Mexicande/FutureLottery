package cn.com.futurelottery.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.com.futurelottery.R;
import cn.com.futurelottery.base.BaseFragment;
import cn.com.futurelottery.ui.activity.LoginActivity;
import cn.com.futurelottery.utils.ActivityUtils;

/**
 * Created by tantan on 2018/4/9.
 * 我的
 */

public class CenterFragment extends BaseFragment {
    @BindView(R.id.center_fragment_iv)
    ImageView centerFragmentIv;


    @Override
    public int getLayoutResource() {
        return R.layout.center_fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        centerFragmentIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtils.startActivity(LoginActivity.class);
            }
        });
    }

    @Override
    protected void setTitle() {

    }

}
