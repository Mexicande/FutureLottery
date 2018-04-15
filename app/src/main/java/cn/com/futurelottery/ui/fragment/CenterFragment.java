package cn.com.futurelottery.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.com.futurelottery.R;
import cn.com.futurelottery.base.BaseFragment;
import cn.com.futurelottery.base.Contacts;
import cn.com.futurelottery.ui.activity.LoginActivity;

/**
 * Created by tantan on 2018/4/9.
 * 我的
 */

public class CenterFragment extends BaseFragment {
    @BindView(R.id.center_fragment_iv)
    ImageView centerFragmentIv;
    private final int REQUEST_CODE = 1000;
    private final int RESULT_CODE = 100;
    @BindView(R.id.center_fragment_number_tv)
    TextView centerFragmentNumberTv;
    @BindView(R.id.center_fragment_money_tv1)
    TextView centerFragmentMoneyTv1;

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
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
    }

    @Override
    protected void setTitle() {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            switch (resultCode) {
                case RESULT_CODE:
                    String amount = data.getStringExtra(Contacts.AMOUNT);
                    String nick = data.getStringExtra(Contacts.NICK);
                    centerFragmentNumberTv.setText(nick);
                    centerFragmentMoneyTv1.setText(amount);
                    break;
                default:
                    break;
            }
        }
    }


}
