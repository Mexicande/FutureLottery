package cn.com.futurelottery.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.com.futurelottery.R;
import cn.com.futurelottery.base.BaseFragment;
import cn.com.futurelottery.ui.adapter.DoubleBallBlueAdapter;
import cn.com.futurelottery.ui.adapter.DoubleBallRedAdapter;
import cn.com.futurelottery.utils.Calculator;
import cn.com.futurelottery.utils.RandomMadeBall;
import cn.com.futurelottery.utils.ShakeListener;
import cn.com.futurelottery.utils.ViewSetHinghUtil;

import static android.content.Context.VIBRATOR_SERVICE;


/**
 * 双色球正常选
 */
public class DoubleBallCommonFragment extends BaseFragment {

    @BindView(R.id.shake_choose_iv)
    ImageView shakeChooseIv;
    @BindView(R.id.doubleBall_red_gv)
    GridView doubleBallRedGv;
    @BindView(R.id.doubleBall_blue_gv)
    GridView doubleBallBlueGv;
    Unbinder unbinder;
    @BindView(R.id.bottom_result_clear_tv)
    TextView bottomResultClearTv;
    @BindView(R.id.bottom_result_choose_tv)
    TextView bottomResultChooseTv;
    @BindView(R.id.bottom_result_count_tv)
    TextView bottomResultCountTv;
    @BindView(R.id.bottom_result_money_tv)
    TextView bottomResultMoneyTv;
    @BindView(R.id.bottom_result_next_btn)
    Button bottomResultNextBtn;
    private View view;
    private Vibrator mVibrator;
    private ShakeListener mShakeListener;
    private DoubleBallRedAdapter redBallAdapter;
    private DoubleBallBlueAdapter blueBallAdapter;
    private ArrayList<String> chooseRedBall = new ArrayList<>(), chooseblueBall = new ArrayList<>();
    private int selectRedNumber, selectBlueNumber;
    private long zhushu;
    //是否显示遗漏
    private int isShow;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_double_ball_common, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        setListener();
        return view;
    }


    private void setListener() {
        // 对红球GridView进行监听，获得红球选中的数。
        doubleBallRedGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 在每次获取点击的item时将对应的checkbox状态改变，同时修改map的值。
                DoubleBallRedAdapter.redGridViewHolder vHolder = (DoubleBallRedAdapter.redGridViewHolder) view.getTag();
                vHolder.chkRed.toggle();
                redBallAdapter.getSelected().put(position, vHolder.chkRed.isChecked());
                int tempRed = 0;
                String hq = "";
                chooseRedBall.clear();
                for (int i = 0; i < doubleBallRedGv.getCount(); i++) {
                    DoubleBallRedAdapter.redGridViewHolder vHolder_red = (DoubleBallRedAdapter.redGridViewHolder) doubleBallRedGv.getChildAt(i).getTag();
                    if (redBallAdapter.hisSelected.get(i)) {
                        ++tempRed;
                        selectRedNumber = tempRed;
                        vHolder_red.chkRed.setTextColor(ContextCompat.getColor(getContext(), android.R.color.white));
                    } else {
                        vHolder_red.chkRed.setTextColor(ContextCompat.getColor(getContext(), R.color.red_ball));
                    }
                    if (redBallAdapter.getSelected().get(i)) {
                        hq = hq + (i + 1) + "  ";
                        chooseRedBall.add(i + "");
                    }
                }

                //计算
                calculatorResult();
                //是否选择了求
                isChooseBall();
            }
        });

        // 对篮球GridView进行监听，获取选球状态。
        doubleBallBlueGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DoubleBallBlueAdapter.LanGridViewHolder vHollder = (DoubleBallBlueAdapter.LanGridViewHolder) view.getTag();
                vHollder.chkBlue.toggle();
                blueBallAdapter.getSelected().put(position, vHollder.chkBlue.isChecked());
                int tempBlue = 0;
                String lq = "";
                chooseblueBall.clear();
                for (int i = 0; i < doubleBallBlueGv.getCount(); i++) {
                    DoubleBallBlueAdapter.LanGridViewHolder vHolder_Blue = (DoubleBallBlueAdapter.LanGridViewHolder) doubleBallBlueGv.getChildAt(i).getTag();
                    if (blueBallAdapter.lisSelected.get(i)) {
                        ++tempBlue;
                        selectBlueNumber = tempBlue;
                        vHolder_Blue.chkBlue.setTextColor(getResources().getColor(android.R.color.white));
                    } else {
                        vHolder_Blue.chkBlue.setTextColor(getResources().getColor(R.color.blue_ball));
                    }

                    if (blueBallAdapter.getSelected().get(i)) {
                        lq = lq + (i + 1) + "  ";
                        chooseblueBall.add(i+"");
                    }
                }

                //计算
                calculatorResult();
                //是否选择了求
                isChooseBall();
            }
        });

        // 当手机摇晃时
        mShakeListener.setOnShakeListener(new ShakeListener.OnShakeListener() {
            public void onShake() {
                //机选
                randomChoose();
            }
        });
    }

    //机选
    private void randomChoose() {
        mShakeListener.stop();
        startVibrato(); // 开始 震动
        randomSelect();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mShakeListener.start();
                mVibrator.cancel();
            }
        }, 500);
    }

    // 定义震动
    public void startVibrato() {
        mVibrator.vibrate(new long[] { 0, 400, 0, 0 }, -1);
        // 第一个｛｝里面是节奏数组，第一个参数为等待指定时间后开始震动，震动时间为第二个参数。后边的参数依次为等待震动和震动的时间
        // 第二个参数是重复次数，-1为不重复，0为一直震动，非-1则从pattern的指定下标开始重复
    }

    /**
     * 检查是否选择了球，以切换清空与机选按钮
     */
    private void isChooseBall() {
        if (chooseblueBall.size()==0&&chooseRedBall.size()==0){
            bottomResultChooseTv.setVisibility(View.VISIBLE);
            bottomResultClearTv.setVisibility(View.GONE);
        }else {
            bottomResultChooseTv.setVisibility(View.GONE);
            bottomResultClearTv.setVisibility(View.VISIBLE);
        }
    }

    //计算注数并显示
    private void calculatorResult() {
        zhushu = Calculator.calculateBetNum(selectRedNumber, selectBlueNumber);
        bottomResultCountTv.setText(String.valueOf(zhushu));
        bottomResultMoneyTv.setText(String.valueOf(zhushu * 2));
    }

    private void initView() {
        // 获得振动器服务
        mVibrator = (Vibrator) getActivity().getApplication().getSystemService(VIBRATOR_SERVICE);
        // 实例化加速度传感器检测类
        mShakeListener = new ShakeListener(getContext());

        redBallAdapter = new DoubleBallRedAdapter(getContext(), chooseRedBall,isShow,33);
        blueBallAdapter = new DoubleBallBlueAdapter(getContext(), chooseblueBall,isShow,16);

        doubleBallRedGv.setAdapter(redBallAdapter);
        doubleBallBlueGv.setAdapter(blueBallAdapter);

        //重新设置高度
        ViewSetHinghUtil.resetGridViewHight7(doubleBallRedGv);
        ViewSetHinghUtil.resetGridViewHight7(doubleBallBlueGv);

        //底部显示机选
        bottomResultChooseTv.setVisibility(View.VISIBLE);
        bottomResultClearTv.setVisibility(View.GONE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.shake_choose_iv, R.id.bottom_result_clear_tv, R.id.bottom_result_choose_tv, R.id.bottom_result_next_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.shake_choose_iv:
                randomChoose();
                break;
            case R.id.bottom_result_clear_tv:
                chooseRedBall.clear();
                chooseblueBall.clear();
                selectRedNumber = 0;
                selectBlueNumber = 0;
                //计算
                calculatorResult();
                //是否选择了求
                isChooseBall();
                //清除选中的球
                redBallAdapter.clearData();
                blueBallAdapter.clearData();
                break;
            case R.id.bottom_result_choose_tv:
                break;
            case R.id.bottom_result_next_btn:
                break;
        }
    }

    //随机选球
    private void randomSelect() {
        getBallNumber();
        redBallAdapter.updateData(isShow,chooseRedBall);
        blueBallAdapter.updateData(isShow,chooseblueBall);
        selectRedNumber = 6;
        selectBlueNumber = 1;
//        String hq = "";
//        for (int i = 0; i < arrRandomRed.size(); i++) {
//            hq = hq + (Integer.parseInt(arrRandomRed.get(i)) + 1) + "  ";
//        }
//        txtShowRedBall.setText(hq);
//
//        String lq = "";
//        for (int i = 0; i < arrRandomBlue.size(); i++) {
//            lq = lq + (Integer.parseInt(arrRandomBlue.get(i)) + 1) + "  ";
//        }
//        txtShowBlueBall.setText(lq);

        //计算
        calculatorResult();
        //是否选择了求
        isChooseBall();
    }

    //获取随机数据
    private void getBallNumber() {
        chooseRedBall.clear();
        chooseRedBall.addAll(RandomMadeBall.getManyBall(33,6));
        chooseblueBall.clear();
        chooseblueBall.addAll(RandomMadeBall.getOneBall(16));
    }

    @Override
    public void onPause() {
        super.onPause();
        mShakeListener.stop();
    }

    @Override
    public void onResume() {
        super.onResume();
        mShakeListener.start();
    }
}
