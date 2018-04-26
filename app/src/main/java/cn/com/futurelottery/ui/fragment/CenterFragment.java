package cn.com.futurelottery.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.futurelottery.R;
import cn.com.futurelottery.base.Api;
import cn.com.futurelottery.base.ApiService;
import cn.com.futurelottery.base.BaseApplication;
import cn.com.futurelottery.base.BaseFragment;
import cn.com.futurelottery.base.Contacts;
import cn.com.futurelottery.inter.OnRequestDataListener;
import cn.com.futurelottery.pay.wechat.Share;
import cn.com.futurelottery.ui.activity.LoginActivity;
import cn.com.futurelottery.ui.activity.OrderActivity;
import cn.com.futurelottery.ui.activity.PersonalInformationActivity;
import cn.com.futurelottery.ui.activity.RechargeActivity;
import cn.com.futurelottery.ui.activity.SetActivity;
import cn.com.futurelottery.ui.activity.WithdrawActivity;
import cn.com.futurelottery.utils.ActivityUtils;
import cn.com.futurelottery.utils.ToastUtils;

/**
 * Created by tantan on 2018/4/9.
 * 我的
 */

public class CenterFragment extends BaseFragment {
    @BindView(R.id.center_fragment_iv)
    ImageView centerFragmentIv;
    private final int LOGIN_REQUEST_CODE = 1000;
    private final int WITHDRAW_REQUEST_CODE = 1001;
    private final int RESULT_CODE = -1;
    @BindView(R.id.center_fragment_number_tv)
    TextView centerFragmentNumberTv;
    @BindView(R.id.center_fragment_money_tv1)
    TextView centerFragmentMoneyTv1;
    @BindView(R.id.center_fragment_money_ll1)
    LinearLayout centerFragmentMoneyLl1;
    @BindView(R.id.center_fragment_money_ll2)
    LinearLayout centerFragmentMoneyLl2;
    @BindView(R.id.center_fragment_money_ll3)
    LinearLayout centerFragmentMoneyLl3;
    @BindView(R.id.center_fragment_order_ll1)
    LinearLayout centerFragmentOrderLl1;
    @BindView(R.id.center_fragment_order_ll2)
    LinearLayout centerFragmentOrderLl2;
    @BindView(R.id.center_fragment_order_ll3)
    LinearLayout centerFragmentOrderLl3;
    @BindView(R.id.center_fragment_order_ll4)
    LinearLayout centerFragmentOrderLl4;
    @BindView(R.id.center_fragment_rl1)
    RelativeLayout centerFragmentRl1;
    @BindView(R.id.center_fragment_rl2)
    RelativeLayout centerFragmentRl2;


    @Override
    public int getLayoutResource() {
        return R.layout.center_fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

    private void initView() {

        if (!TextUtils.isEmpty(BaseApplication.getInstance().token)){
            setLoginView();
        }
    }

    private void setLoginView() {
        centerFragmentIv.setImageResource(R.mipmap.me_fragment_head2);
        centerFragmentNumberTv.setText(BaseApplication.getInstance().userName);
        centerFragmentMoneyTv1.setText(BaseApplication.getInstance().amount);
    }

    @Override
    protected void setTitle() {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CODE) {
            switch (requestCode) {
                case LOGIN_REQUEST_CODE:
                    String amount = data.getStringExtra(Contacts.AMOUNT);
                    String nick = data.getStringExtra(Contacts.NICK);
                    centerFragmentNumberTv.setText(nick);
                    centerFragmentMoneyTv1.setText(amount);
                    break;
                case WITHDRAW_REQUEST_CODE:
                    getBalance();
                    break;
                default:
                    break;
            }
        }
    }


    @OnClick({R.id.center_fragment_money_ll1, R.id.center_fragment_money_ll2, R.id.center_fragment_money_ll3, R.id.center_fragment_order_ll1, R.id.center_fragment_order_ll2, R.id.center_fragment_order_ll3, R.id.center_fragment_order_ll4, R.id.center_fragment_rl1, R.id.center_fragment_rl2,
            R.id.center_fragment_iv, R.id.center_fragment_number_tv, R.id.me_fragment_rl3})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.center_fragment_iv:
                Intent intent1 = new Intent(getActivity(), LoginActivity.class);
                startActivityForResult(intent1, LOGIN_REQUEST_CODE);
                break;
            case R.id.center_fragment_number_tv:
                Intent intent2 = new Intent(getActivity(), LoginActivity.class);
                startActivityForResult(intent2, LOGIN_REQUEST_CODE);
                break;
            case R.id.center_fragment_money_ll1:
                ActivityUtils.startActivity(RechargeActivity.class);
                break;
            case R.id.center_fragment_money_ll2:
                break;
            case R.id.center_fragment_money_ll3:
                getWithdrawStatus();
                break;
            case R.id.center_fragment_order_ll1:
                Intent intent3 = new Intent(getActivity(), OrderActivity.class);
                intent3.putExtra("intentType","1");
                startActivity(intent3);
                break;
            case R.id.center_fragment_order_ll2:
                Intent intent4 = new Intent(getActivity(), OrderActivity.class);
                intent4.putExtra("intentType","2");
                startActivity(intent4);
                break;
            case R.id.center_fragment_order_ll3:
                Intent intent5 = new Intent(getActivity(), OrderActivity.class);
                intent5.putExtra("intentType","3");
                startActivity(intent5);
                break;
            case R.id.center_fragment_order_ll4:
                Intent intent6 = new Intent(getActivity(), OrderActivity.class);
                intent6.putExtra("intentType","4");
                startActivity(intent6);
                break;
            case R.id.center_fragment_rl1:
                //红包，暂时不做
                break;
            case R.id.center_fragment_rl2:
                Share share=new Share(getContext());
                share.show();
                break;
            case R.id.me_fragment_rl3:
                Intent intent8 = new Intent(getActivity(), SetActivity.class);
                startActivity(intent8);
                break;
        }
    }

    /**
     * 获取用户绑定银行卡状态
     */
    private void getWithdrawStatus() {
        JSONObject jsonObject = new JSONObject();
        ApiService.GET_SERVICE(Api.Withdraw.binding, getContext(), jsonObject, new OnRequestDataListener() {
            @Override
            public void requestSuccess(int code, JSONObject data) {
                try {
                    if (0==code){
                        String status = data.getJSONObject("data").getString("status");
                        if ("0".equals(status)){
                            Intent intent = new Intent(getActivity(), WithdrawActivity.class);
                            startActivityForResult(intent, WITHDRAW_REQUEST_CODE);
                        }else {
                            Intent intent = new Intent(getActivity(), PersonalInformationActivity.class);
                            startActivityForResult(intent, WITHDRAW_REQUEST_CODE);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void requestFailure(int code, String msg) {
                ToastUtils.showToast(msg);
            }
        });
    }

    /**
     * 获取余额
     */
    private void getBalance() {
        JSONObject jsonObject = new JSONObject();
        ApiService.GET_SERVICE(Api.Login.balance, getContext(), jsonObject, new OnRequestDataListener() {
            @Override
            public void requestSuccess(int code, JSONObject data) {
                try {
                    if (0==code){
                        BaseApplication.getInstance().amount=data.getJSONObject("data").getString("amount");
                        centerFragmentMoneyTv1.setText(BaseApplication.getInstance().amount);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void requestFailure(int code, String msg) {
                ToastUtils.showToast(msg);
            }
        });
    }


}
