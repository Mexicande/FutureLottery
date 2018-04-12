package cn.com.futurelottery.ui.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.com.futurelottery.R;
import cn.com.futurelottery.base.BaseFragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class LotteryFragment extends BaseFragment {


    @BindView(R.id.tv_title)
    TextView tvTitle;

    public LotteryFragment() {
        // Required empty public constructor
    }



    @Override
    public int getLayoutResource() {
        return R.layout.fragment_lottery;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    protected void setTitle() {
        tvTitle.setText(R.string.lottery_title);
    }

}
