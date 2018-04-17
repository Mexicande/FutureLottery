package cn.com.futurelottery.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.support.annotation.Nullable;
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

public class SuperLottoCommonFragment extends BaseFragment {


    @BindView(R.id.shake_choose_iv)
    ImageView shakeChooseIv;
    @BindView(R.id.red_gv)
    GridView redGv;
    @BindView(R.id.blue_gv)
    GridView blueGv;
    @BindView(R.id.bottom_result_clear_tv)
    TextView bottomResultClearTv;
    @BindView(R.id.bottom_result_choose_tv)
    TextView bottomResultChooseTv;
    @BindView(R.id.bottom_result_count_tv)
    TextView bottomResultCountTv;
    @BindView(R.id.tv_period)
    TextView tvPeriod;
    @BindView(R.id.bottom_result_money_tv)
    TextView bottomResultMoneyTv;
    @BindView(R.id.bottom_result_next_btn)
    Button bottomResultNextBtn;
    Unbinder unbinder;
    private Vibrator mVibrator;
    private ShakeListener mShakeListener;
    private DoubleBallRedAdapter redBallAdapter;
    private DoubleBallBlueAdapter blueBallAdapter;
    //注数
    private long zhushu;
    //选择的球集合
    private ArrayList<String> chooseRedBall = new ArrayList<>(), chooseblueBall = new ArrayList<>();
    //选择的球数
    private int selectRedNumber, selectBlueNumber;
    //是否显示遗漏1显示0不显示
    private int isShow;
    //遗漏数
    private ArrayList<String> omitsRed =new ArrayList<>();
    private ArrayList<String> omitsBlue =new ArrayList<>();

    public SuperLottoCommonFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        setListener();
    }

    @Override
    public int getLayoutResource() {
        return R.layout.fragment_super_lotto_common;
    }



    @OnClick({R.id.shake_choose_iv, R.id.bottom_result_clear_tv, R.id.bottom_result_choose_tv, R.id.bottom_result_next_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.shake_choose_iv:
                break;
            case R.id.bottom_result_clear_tv:
                break;
            case R.id.bottom_result_choose_tv:
                break;
            case R.id.bottom_result_next_btn:
                break;
        }
    }

    private void setListener() {
        // 对红球GridView进行监听，获得红球选中的数。
        redGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 在每次获取点击的item时将对应的checkbox状态改变，同时修改map的值。
                DoubleBallRedAdapter.redGridViewHolder vHolder = (DoubleBallRedAdapter.redGridViewHolder) view.getTag();
                vHolder.chkRed.toggle();
                redBallAdapter.getSelected().put(position, vHolder.chkRed.isChecked());
                int tempRed = 0;
                String hq = "";
                chooseRedBall.clear();
                for (int i = 0; i < redGv.getCount(); i++) {
                    DoubleBallRedAdapter.redGridViewHolder vHolder_red = (DoubleBallRedAdapter.redGridViewHolder) redGv.getChildAt(i).getTag();
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
        blueGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DoubleBallBlueAdapter.LanGridViewHolder vHollder = (DoubleBallBlueAdapter.LanGridViewHolder) view.getTag();
                vHollder.chkBlue.toggle();
                blueBallAdapter.getSelected().put(position, vHollder.chkBlue.isChecked());
                int tempBlue = 0;
                String lq = "";
                chooseblueBall.clear();
                for (int i = 0; i < blueGv.getCount(); i++) {
                    DoubleBallBlueAdapter.LanGridViewHolder vHolder_Blue = (DoubleBallBlueAdapter.LanGridViewHolder) blueGv.getChildAt(i).getTag();
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
            @Override
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
                mVibrator.cancel();
                mShakeListener.start();
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

        redGv.setAdapter(redBallAdapter);
        blueGv.setAdapter(blueBallAdapter);

        //重新设置高度
        ViewSetHinghUtil.resetGridViewHight7(redGv);
        ViewSetHinghUtil.resetGridViewHight7(blueGv);

        //底部显示机选
        bottomResultChooseTv.setVisibility(View.VISIBLE);
        bottomResultClearTv.setVisibility(View.GONE);
    }

    //随机选球
    private void randomSelect() {
        getBallNumber();
        redBallAdapter.updateData(isShow,chooseRedBall,omitsRed);
        blueBallAdapter.updateData(isShow,chooseblueBall,omitsBlue);
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

    //显示遗漏
    public void showOmit(ArrayList<String> omitsRed,ArrayList<String> omitsBlue) {
        isShow=1;
        this.omitsRed=omitsRed;
        this.omitsBlue=omitsBlue;
        redBallAdapter.updateData(isShow,chooseRedBall,omitsRed);
        blueBallAdapter.updateData(isShow,chooseblueBall,omitsBlue);
        //重新设置高度
        ViewSetHinghUtil.resetGridViewHight7(redGv);
        ViewSetHinghUtil.resetGridViewHight7(blueGv);
    }
    //显示遗漏
    public void unShowOmit() {
        isShow=2;
        omitsRed.clear();
        omitsBlue.clear();
        redBallAdapter.updateData(isShow,chooseRedBall,omitsRed);
        blueBallAdapter.updateData(isShow,chooseblueBall,omitsBlue);
        //重新设置高度
        ViewSetHinghUtil.resetGridViewHight7(redGv);
        ViewSetHinghUtil.resetGridViewHight7(blueGv);
    }


    @Override
    public void onResume() {
        super.onResume();
        if(mShakeListener!=null){
            mShakeListener.start();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if(mShakeListener!=null){
            mShakeListener.stop();
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(mShakeListener!=null){
            if(hidden){
                mShakeListener.stop();
            }else {
                mShakeListener.start();
            }

        }
    }

}
