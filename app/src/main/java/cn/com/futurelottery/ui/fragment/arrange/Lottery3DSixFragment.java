package cn.com.futurelottery.ui.fragment.arrange;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.com.futurelottery.R;
import cn.com.futurelottery.base.Api;
import cn.com.futurelottery.base.ApiService;
import cn.com.futurelottery.base.BaseFragment;
import cn.com.futurelottery.base.Contacts;
import cn.com.futurelottery.listener.OnRequestDataListener;
import cn.com.futurelottery.model.Arrange;
import cn.com.futurelottery.model.AwardPeriod;
import cn.com.futurelottery.ui.activity.arrange.ArrangePayActivity;
import cn.com.futurelottery.ui.adapter.PastLotteryAdapter;
import cn.com.futurelottery.ui.adapter.arrange.ArrangeAdapter;
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
 * 3D组六
 */
public class Lottery3DSixFragment extends BaseFragment {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout toolbarLayout;
    @BindView(R.id.tv_award)
    TextView tvAward;
    @BindView(R.id.limite_date)
    TextView limiteDate;
    @BindView(R.id.tv_expand)
    TextView tvExpand;
    @BindView(R.id.iv_expand)
    ImageView ivExpand;
    @BindView(R.id.layout_expand)
    LinearLayout layoutExpand;
    @BindView(R.id.appBar)
    AppBarLayout appBar;
    @BindView(R.id.shake_choose_iv)
    ImageView shakeChooseIv;
    @BindView(R.id.unit_gv)
    GridView unitGv;
    @BindView(R.id.tip_tv)
    TextView tipTv;
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

    private boolean flag = false;
    private int isShow = 2;
    private ArrayList<String> omits = new ArrayList<>();
    private int intentType;
    private String phase;
    private ArrayList<AwardPeriod> jsonArray;
    private Vibrator mVibrator;
    private ShakeListener mShakeListener;
    private ArrayList<String> units = new ArrayList<>();
    private ArrangeAdapter unitAdapter;
    private PastLotteryAdapter pastLotteryAdapter;
    private EasyPopup mCirclePop;
    private View tvOne;
    private View tvFive;
    private View tvTen;
    private int selectUnitNumber;
    private long zhushu;
    private boolean IsXpand = false;

    @Override
    public int getLayoutResource() {
        return R.layout.fragment_lottery3d_six;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getData();
        initView();
        initDate();
        setListener();

        //遗漏
        if (null!=unitAdapter){
            unitAdapter.updateData(isShow, units, omits);
            //重新设置高度
            ViewSetHinghUtil.resetGridViewHight(unitGv,5);
        }
    }
    private void getData() {
        Intent intent = getActivity().getIntent();
        intentType = intent.getIntExtra("data", 0);
    }

    private void initView() {
        // 获得振动器服务
        mVibrator = (Vibrator) getActivity().getApplication().getSystemService(VIBRATOR_SERVICE);
        // 实例化加速度传感器检测类
        mShakeListener = new ShakeListener(getContext());

        unitAdapter = new ArrangeAdapter(getContext(), units, isShow, 10);

        unitGv.setAdapter(unitAdapter);
        pastLotteryAdapter = new PastLotteryAdapter(null);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(pastLotteryAdapter);
        //重新设置高度
        ViewSetHinghUtil.resetGridViewHight(unitGv, 5);

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

    private void initDate() {
        if (!DeviceUtil.IsNetWork(getContext())) {
            ToastUtils.showToast("网络异常，请检查网络");
            return;
        }
        //往期中奖
        ApiService.GET_SERVICE(Api.Lottery3D.drop, getActivity(), new JSONObject(), new OnRequestDataListener() {
            @Override
            public void requestSuccess(int code, JSONObject data) {
                try {
                    JSONArray bannerArray = data.getJSONArray("data");
                    Gson gson = new Gson();
                    Type bannerType = new TypeToken<ArrayList<AwardPeriod>>() {
                    }.getType();
                    jsonArray = gson.fromJson(bannerArray.toString(), bannerType);
                    for (int i = 0; i < jsonArray.size(); i++) {
                        AwardPeriod ja = jsonArray.get(i);
                        jsonArray.remove(i);
                        ja.setType(3);
                        jsonArray.add(i, ja);
                    }
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
        ApiService.GET_SERVICE(Api.Lottery3D.purchase, getActivity(), new JSONObject(), new OnRequestDataListener() {
            @Override
            public void requestSuccess(int code, JSONObject data) {
                try {
                    JSONObject jo = data.getJSONArray("data").getJSONObject(0);
                    phase = jo.getString(Contacts.PHASE);
                    tvAward.setText("第" + phase + "期");
                    limiteDate.setText(jo.getString(Contacts.END_TIME) + " 截止");
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

        // 个位
        unitGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 在每次获取点击的item时将对应的checkbox状态改变，同时修改map的值。
                ArrangeAdapter.redGridViewHolder vHolder = (ArrangeAdapter.redGridViewHolder) view.getTag();
                vHolder.chkRed.toggle();
                unitAdapter.getSelected().put(position, vHolder.chkRed.isChecked());
                String hq = "";
                units.clear();
                for (int i = 0; i < unitGv.getCount(); i++) {
                    ArrangeAdapter.redGridViewHolder vHolder_red = (ArrangeAdapter.redGridViewHolder) unitGv.getChildAt(i).getTag();
                    if (unitAdapter.hisSelected.get(i)) {
                        vHolder_red.chkRed.setTextColor(ContextCompat.getColor(getContext(), android.R.color.white));
                    } else {
                        vHolder_red.chkRed.setTextColor(ContextCompat.getColor(getContext(), R.color.red_ball));
                    }
                    if (unitAdapter.getSelected().get(i)) {
                        hq = hq + (i + 1) + "  ";
                        units.add(i + "");
                    }
                }
                selectUnitNumber = units.size();
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
                if (state == AppBarStateChangeListener.State.EXPANDED) {
                    IsXpand = true;
                    tvExpand.setText(R.string.expand);
                    RoteteUtils.rotateArrow(ivExpand, !IsXpand);
                    //展开状态
                } else if (state == State.COLLAPSED) {
                    IsXpand = false;
                    tvExpand.setText(R.string.un_expand);
                    RoteteUtils.rotateArrow(ivExpand, !IsXpand);
                    //折叠状态
                } else {
                    pastLotteryAdapter.setNewData(jsonArray);
                    //中间状态
                }
            }
        });

    }

    //获取多个随机注
    private void getRandomCount(int count) {
        ArrayList<Arrange> balls = new ArrayList<>();
        for (int j = 0; j < count; j++) {
            Arrange arrange = new Arrange();
            ArrayList<String> random = RandomMadeBall.getManyBall(10, 3);
            arrange.setSix(random.get(0) + ","+random.get(1)+","+random.get(2));

            arrange.setType(4);
            arrange.setNotes(1);
            arrange.setMoney(2);

            balls.add(arrange);
        }
        intent(balls);
    }

    //跳转
    private void intent(ArrayList<Arrange> balls) {
        if (intentType == 0) {
            Intent intent = new Intent(getContext(), ArrangePayActivity.class);
            intent.putExtra("balls", (Serializable) balls);
            intent.putExtra("phase", phase);
            intent.putExtra("kind", Contacts.Lottery.D3name);
            startActivity(intent);
        } else {
            Intent intent = new Intent();
            intent.putExtra("balls", (Serializable) balls);
            intent.putExtra("phase", phase);
            intent.putExtra("kind", Contacts.Lottery.D3name);
            getActivity().setResult(-1, intent);
        }
        getActivity().finish();
    }

    //计算注数并显示
    private void calculatorResult() {
        zhushu = Calculator.arrange3Six(selectUnitNumber);
        bottomResultCountTv.setText(String.valueOf(zhushu));
        bottomResultMoneyTv.setText(String.valueOf(zhushu * 2));
        if (zhushu == 0) {
            tipTv.setVisibility(View.GONE);
        } else {
            tipTv.setVisibility(View.VISIBLE);
            tipTv.setText("若中奖：奖金173元，盈利" + (173 - zhushu * 2) + "元");
        }
    }

    /**
     * 检查是否选择了球，以切换清空与机选按钮
     */
    private void isChooseBall() {
        if (units.size() == 0) {
            bottomResultChooseTv.setVisibility(View.VISIBLE);
            bottomResultClearTv.setVisibility(View.GONE);
        } else {
            bottomResultChooseTv.setVisibility(View.GONE);
            bottomResultClearTv.setVisibility(View.VISIBLE);
        }
    }

    @OnClick({R.id.shake_choose_iv, R.id.bottom_result_clear_tv, R.id.bottom_result_next_btn, R.id.layout_expand})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.shake_choose_iv:
                randomChoose();
                break;
            case R.id.bottom_result_clear_tv:
                units.clear();
                selectUnitNumber = 0;
                //计算
                calculatorResult();
                //是否选择了求
                isChooseBall();
                //清除选中的球
                unitAdapter.clearData();
                break;
            case R.id.bottom_result_next_btn:
                if (units.size() == 0) {
                    randomChoose();
                    return;
                }
                if (zhushu == 0) {
                    ToastUtils.showToast("至少选择一注");
                    return;
                }
                next();
                break;
            case R.id.layout_expand:
                if (!IsXpand) {
                    appBar.setExpanded(true);
                } else {
                    appBar.setExpanded(false);
                }
                break;
        }
    }

    private void next() {
        Arrange arrange = new Arrange();
        String individual = "";
        for (int i = 0; i < units.size(); i++) {
            if (i==0){
                individual = individual + units.get(i);
            }else {
                individual = individual + ","+units.get(i);
            }
        }
        arrange.setSix(individual);

        arrange.setType(4);
        arrange.setNotes(zhushu);
        arrange.setMoney(zhushu * 2);

        ArrayList<Arrange> balls = new ArrayList<>();
        balls.add(arrange);

        intent(balls);
    }

    // 定义震动
    public void startVibrato() {
        mVibrator.vibrate(new long[]{0, 400, 0, 0}, -1);
        // 第一个｛｝里面是节奏数组，第一个参数为等待指定时间后开始震动，震动时间为第二个参数。后边的参数依次为等待震动和震动的时间
        // 第二个参数是重复次数，-1为不重复，0为一直震动，非-1则从pattern的指定下标开始重复
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

    //随机选球
    private void randomSelect() {
        getBallNumber();
        unitAdapter.updateData(isShow, units, omits);
        selectUnitNumber = 3;
        //计算
        calculatorResult();
        //是否选择了求
        isChooseBall();
    }

    //获取随机数据
    private void getBallNumber() {
        units.clear();
        units.addAll(RandomMadeBall.getManyBall(10, 3));
    }


    //显示遗漏
    public void showOmit(ArrayList<String> omits) {
        isShow = 1;
        this.omits = omits;
        if (null!=unitAdapter){
            unitAdapter.updateData(isShow, units, omits);
            //重新设置高度
            ViewSetHinghUtil.resetGridViewHight(unitGv, 5);
        }
    }

    //显示遗漏
    public void unShowOmit() {
        isShow = 2;
        omits.clear();
        if (null!=unitAdapter){
            unitAdapter.updateData(isShow, units, omits);
            //重新设置高度
            ViewSetHinghUtil.resetGridViewHight(unitGv, 5);
        }

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
}
