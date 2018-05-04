package cn.com.futurelottery.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.futurelottery.R;
import cn.com.futurelottery.base.Api;
import cn.com.futurelottery.base.ApiService;
import cn.com.futurelottery.base.BaseFragment;
import cn.com.futurelottery.base.Contacts;
import cn.com.futurelottery.listener.OnRequestDataListener;
import cn.com.futurelottery.model.AwardPeriod;
import cn.com.futurelottery.model.DoubleBall;
import cn.com.futurelottery.ui.activity.ChooseBallPaymentActivity;
import cn.com.futurelottery.ui.adapter.AwardPeriodAdapter;
import cn.com.futurelottery.ui.adapter.DoubleBallBlueAdapter;
import cn.com.futurelottery.ui.adapter.DoubleBallRedAdapter;
import cn.com.futurelottery.utils.AppBarStateChangeListener;
import cn.com.futurelottery.utils.Calculator;
import cn.com.futurelottery.utils.DeviceUtil;
import cn.com.futurelottery.utils.RandomMadeBall;
import cn.com.futurelottery.utils.RoteteUtils;
import cn.com.futurelottery.utils.ShakeListener;
import cn.com.futurelottery.utils.ToastUtils;
import cn.com.futurelottery.utils.ViewSetHinghUtil;
import cn.com.futurelottery.view.popup.EasyPopup;
import cn.com.futurelottery.view.popup.HorizontalGravity;
import cn.com.futurelottery.view.popup.VerticalGravity;

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
    @BindView(R.id.lion)
    LinearLayout lion;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.tv_award)
    TextView tvAward;
    @BindView(R.id.limite_date)
    TextView limiteDate;
    @BindView(R.id.tv_expand)
    TextView tv_expand;
    @BindView(R.id.iv_expand)
    ImageView ivExpand;
    @BindView(R.id.appBar)
    AppBarLayout appBar;

    private Vibrator mVibrator;
    private ShakeListener mShakeListener;
    public DoubleBallRedAdapter redBallAdapter;
    private DoubleBallBlueAdapter blueBallAdapter;
    private ArrayList<String> chooseRedBall = new ArrayList<>(), chooseblueBall = new ArrayList<>();
    private ArrayList<String> omitsRed = new ArrayList<>();
    private ArrayList<String> omitsBlue = new ArrayList<>();
    private int selectRedNumber, selectBlueNumber;
    private long zhushu;
    //是否显示遗漏1显示0不显示
    private int isShow;
    private EasyPopup mCirclePop;
    private AwardPeriodAdapter mAwardPeriodAdapter;
    private boolean IsXpand=false;
    private ArrayList<AwardPeriod> jsonArray;

    private String phase = "2018032";
    private int intentType;


    @Override
    public int getLayoutResource() {
        return R.layout.fragment_double_ball_common;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getData();
        initView();
        initDate();
        setListener();
    }

    private void getData() {
        Intent intent = getActivity().getIntent();
        intentType=intent.getIntExtra("data",0);
    }

    private void initDate() {
        if (!DeviceUtil.IsNetWork(getContext())){
            ToastUtils.showToast("网络异常，请检查网络");
            return;
        }
        //往期中奖
        ApiService.GET_SERVICE(Api.Double_Ball.GET_DROP, getActivity(), new JSONObject(), new OnRequestDataListener() {
            @Override
            public void requestSuccess(int code, JSONObject data) {
                try {
                    JSONArray bannerArray = data.getJSONArray("data");
                    Gson gson = new Gson();
                    Type bannerType = new TypeToken<ArrayList<AwardPeriod>>() {
                    }.getType();
                    jsonArray = gson.fromJson(bannerArray.toString(), bannerType);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void requestFailure(int code, String msg) {
                ToastUtils.showToast(msg);
            }
        });
        //期
        ApiService.GET_SERVICE(Api.Double_Ball.GET_BYTIME, getActivity(), new JSONObject(), new OnRequestDataListener() {
            @Override
            public void requestSuccess(int code, JSONObject data) {
                try {
                    JSONObject jo = data.getJSONArray("data").getJSONObject(0);
                    phase=jo.getString(Contacts.PHASE);
                    tvAward.setText("第"+phase+"期");
                    limiteDate.setText(jo.getString(Contacts.END_TIME)+" 截止");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void requestFailure(int code, String msg) {
                ToastUtils.showToast(msg);
            }
        });


    }


    private void setListener() {
        // 对红球GridView进行监听，获得红球选中的数。
        doubleBallRedGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //限制球数
                if (limit(20, position, chooseRedBall)) {
                    ToastUtils.showToast("最多选择20个红球");
                    return;
                }

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
            @Override
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
                        chooseblueBall.add(i + "");
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
        //机选多注

        bottomResultChooseTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCirclePop.showAtAnchorView(bottomResultChooseTv, VerticalGravity.ABOVE, HorizontalGravity.ALIGN_LEFT);


            }
        });

        tvOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getRandomCount(1);
                mCirclePop.dismiss();
            }
        });
        tvFive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getRandomCount(5);
                mCirclePop.dismiss();

            }
        });
        tvTen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getRandomCount(10);
                mCirclePop.dismiss();
            }
        });
        appBar.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                if( state == State.EXPANDED ) {
                    IsXpand=true;
                    tv_expand.setText(R.string.expand);
                    RoteteUtils.rotateArrow(ivExpand, !IsXpand);
                    //展开状态
                }else if(state == State.COLLAPSED){
                    IsXpand=false;
                    tv_expand.setText(R.string.un_expand);
                    RoteteUtils.rotateArrow(ivExpand, !IsXpand);
                    //折叠状态
                }else {
                    mAwardPeriodAdapter.setNewData(jsonArray);
                    //中间状态
                }
            }
        });
    }

    //获取多个随机注
    private void getRandomCount(int count) {
        ArrayList<DoubleBall> balls = new ArrayList<>();
        for (int j=0;j<count;j++){
            ArrayList<String> chooseRedBalls = RandomMadeBall.getManyBall(33, 6);
            ArrayList<String> chooseblueBalls = RandomMadeBall.getOneBall(16);
            DoubleBall db = new DoubleBall();
            String red = "";
            String blue = "";
            for (int i = 0; i < chooseRedBalls.size(); i++) {
                int number = Integer.parseInt(chooseRedBalls.get(i)) + 1;
                if (i == 0) {
                    red = red + ((number < 10) ? ("0" + number) : number);
                } else {
                    red = red + "," + ((number < 10) ? ("0" + number) : number);
                }
            }
            for (int i = 0; i < chooseblueBalls.size(); i++) {
                int number = Integer.parseInt(chooseblueBalls.get(i)) + 1;
                if (i == 0) {
                    blue = blue + ((number < 10) ? ("0" + number) : number);
                } else {
                    blue = blue + "," + ((number < 10) ? ("0" + number) : number);
                }
            }

            db.setType(0);

            db.setRed(red);
            db.setBlu(blue);
            db.setZhushu(1);
            db.setMoney(2);

            balls.add(db);
        }

        intent(balls);
    }

    //跳转
    private void intent(ArrayList<DoubleBall> balls) {
        if (intentType==0){
            Intent intent = new Intent(getContext(), ChooseBallPaymentActivity.class);
            intent.putExtra("balls", (Serializable) balls);
            intent.putExtra("phase", phase);
            startActivity(intent);
        }else {
            Intent intent = new Intent();
            intent.putExtra("balls", (Serializable) balls);
            intent.putExtra("phase", phase);
            getActivity().setResult(-1,intent);
        }
        getActivity().finish();
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
        mVibrator.vibrate(new long[]{0, 400, 0, 0}, -1);
        // 第一个｛｝里面是节奏数组，第一个参数为等待指定时间后开始震动，震动时间为第二个参数。后边的参数依次为等待震动和震动的时间
        // 第二个参数是重复次数，-1为不重复，0为一直震动，非-1则从pattern的指定下标开始重复
    }

    /**
     * 检查是否选择了球，以切换清空与机选按钮
     */
    private void isChooseBall() {
        if (chooseblueBall.size() == 0 && chooseRedBall.size() == 0) {
            bottomResultChooseTv.setVisibility(View.VISIBLE);
            bottomResultClearTv.setVisibility(View.GONE);
        } else {
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

    private TextView tvOne;
    private TextView tvFive;
    private TextView tvTen;

    private void initView() {
        // 获得振动器服务
        mVibrator = (Vibrator) getActivity().getApplication().getSystemService(VIBRATOR_SERVICE);
        // 实例化加速度传感器检测类
        mShakeListener = new ShakeListener(getContext());

        redBallAdapter = new DoubleBallRedAdapter(getContext(), chooseRedBall, isShow, 33);
        blueBallAdapter = new DoubleBallBlueAdapter(getContext(), chooseblueBall, isShow, 16);

        doubleBallRedGv.setAdapter(redBallAdapter);
        doubleBallBlueGv.setAdapter(blueBallAdapter);
        mAwardPeriodAdapter = new AwardPeriodAdapter(null);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(mAwardPeriodAdapter);
        //重新设置高度
        ViewSetHinghUtil.resetGridViewHight7(doubleBallRedGv);
        ViewSetHinghUtil.resetGridViewHight7(doubleBallBlueGv);

        //底部显示机选
        bottomResultChooseTv.setVisibility(View.VISIBLE);
        bottomResultClearTv.setVisibility(View.GONE);

        mCirclePop = new EasyPopup(getActivity())
                .setContentView(R.layout.auto_select_layout)
                .setBackgroundDimEnable(true)
                .setDimValue(0.4f)
                //是否允许点击PopupWindow之外的地方消失
                .setFocusAndOutsideEnable(true)
                .createPopup();
        tvOne = mCirclePop.getView(R.id.one);
        tvFive = mCirclePop.getView(R.id.five);
        tvTen = mCirclePop.getView(R.id.ten);
    }


    @OnClick({R.id.shake_choose_iv, R.id.bottom_result_clear_tv,
            R.id.bottom_result_next_btn,R.id.layout_expand})
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
            case R.id.bottom_result_next_btn:
                if (chooseRedBall.size() == 0 && chooseblueBall.size() == 0) {
                    randomChoose();
                    return;
                }
                if (zhushu == 0) {
                    ToastUtils.showToast("至少选择一注");
                    return;
                }
                DoubleBall db = new DoubleBall();
                String red = "";
                String blue = "";
                for (int i = 0; i < chooseRedBall.size(); i++) {
                    int number = Integer.parseInt(chooseRedBall.get(i)) + 1;
                    if (i == 0) {
                        red = red + ((number < 10) ? ("0" + number) : number);
                    } else {
                        red = red + "," + ((number < 10) ? ("0" + number) : number);
                    }
                }
                for (int i = 0; i < chooseblueBall.size(); i++) {
                    int number = Integer.parseInt(chooseblueBall.get(i)) + 1;
                    if (i == 0) {
                        blue = blue + ((number < 10) ? ("0" + number) : number);
                    } else {
                        blue = blue + "," + ((number < 10) ? ("0" + number) : number);
                    }
                }
                //判断单复式
                if (chooseRedBall.size() > 6 || chooseblueBall.size() > 1) {
                    db.setType(1);
                } else {
                    db.setType(0);
                }

                db.setRed(red);
                db.setBlu(blue);
                db.setZhushu(zhushu);
                db.setMoney(zhushu * 2);

                ArrayList<DoubleBall> balls = new ArrayList<>();
                balls.add(db);

                intent(balls);
                break;
            case R.id.layout_expand:
                if(!IsXpand){
                    appBar.setExpanded(true);
                }else {
                    appBar.setExpanded(false);
                }
                break;
            default:
                break;
        }
    }

    //随机选球
    private void randomSelect() {
        getBallNumber();
        redBallAdapter.updateData(isShow, chooseRedBall, omitsRed);
        blueBallAdapter.updateData(isShow, chooseblueBall, omitsBlue);
        selectRedNumber = 6;
        selectBlueNumber = 1;
        //计算
        calculatorResult();
        //是否选择了求
        isChooseBall();
    }

    //获取随机数据
    private void getBallNumber() {
        chooseRedBall.clear();
        chooseRedBall.addAll(RandomMadeBall.getManyBall(33, 6));
        chooseblueBall.clear();
        chooseblueBall.addAll(RandomMadeBall.getOneBall(16));
    }

    //显示遗漏
    public void showOmit(ArrayList<String> omitsRed, ArrayList<String> omitsBlue) {
        isShow = 1;
        this.omitsRed = omitsRed;
        this.omitsBlue = omitsBlue;
        redBallAdapter.updateData(isShow, chooseRedBall, omitsRed);
        blueBallAdapter.updateData(isShow, chooseblueBall, omitsBlue);
        //重新设置高度
        ViewSetHinghUtil.resetGridViewHight7(doubleBallRedGv);
        ViewSetHinghUtil.resetGridViewHight7(doubleBallBlueGv);
    }

    //显示遗漏
    public void unShowOmit() {
        isShow = 2;
        omitsRed.clear();
        omitsBlue.clear();
        redBallAdapter.updateData(isShow, chooseRedBall, omitsRed);
        blueBallAdapter.updateData(isShow, chooseblueBall, omitsBlue);
        //重新设置高度
        ViewSetHinghUtil.resetGridViewHight7(doubleBallRedGv);
        ViewSetHinghUtil.resetGridViewHight7(doubleBallBlueGv);
    }


    @Override
    public void onResume() {
        super.onResume();
        if (mShakeListener != null) {
            mShakeListener.start();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mShakeListener != null) {
            mShakeListener.stop();
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (mShakeListener != null) {
            if (hidden) {
                mShakeListener.stop();
                appBar.setExpanded(false);
            } else {
                mShakeListener.start();
            }

        }
    }

    //限制球的个数，小于等于count返回true
    private boolean limit(int count, int position, ArrayList<String> balls) {
        boolean isLimit = true;
        if (balls.size() >= count) {
            for (int i = 0; i < balls.size(); i++) {
                if (balls.get(i).equals(position + "")) {
                    isLimit = false;
                }
            }
        } else {
            isLimit = false;
        }
        return isLimit;
    }

}
