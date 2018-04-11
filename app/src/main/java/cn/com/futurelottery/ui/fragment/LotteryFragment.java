package cn.com.futurelottery.ui.fragment;


import android.os.Bundle;
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
    Unbinder unbinder;

    public LotteryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lottery, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected void setTitle() {
        tvTitle.setText(R.string.lottery_title);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
