package cn.com.futurelottery.ui.activity.arrange;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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
import cn.com.futurelottery.base.BaseActivity;
import cn.com.futurelottery.base.Contacts;
import cn.com.futurelottery.listener.OnRequestDataListener;
import cn.com.futurelottery.model.Arrange;
import cn.com.futurelottery.model.AwardPeriod;
import cn.com.futurelottery.ui.activity.LotteryInformationActivity;
import cn.com.futurelottery.ui.activity.WebViewActivity;
import cn.com.futurelottery.ui.adapter.arrange.ArrangeAdapter;
import cn.com.futurelottery.ui.adapter.PastLotteryAdapter;
import cn.com.futurelottery.utils.AppBarStateChangeListener;
import cn.com.futurelottery.utils.CommonUtil;
import cn.com.futurelottery.utils.DeviceUtil;
import cn.com.futurelottery.utils.RoteteUtils;
import cn.com.futurelottery.utils.ShakeListener;
import cn.com.futurelottery.utils.ToastUtils;
import cn.com.futurelottery.utils.ViewSetHinghUtil;
import cn.com.futurelottery.view.popup.EasyPopup;
import cn.com.futurelottery.view.popup.HorizontalGravity;
import cn.com.futurelottery.view.popup.VerticalGravity;
import cn.com.futurelottery.view.topRightMenu.MenuItem;
import cn.com.futurelottery.view.topRightMenu.OnTopRightMenuItemClickListener;
import cn.com.futurelottery.view.topRightMenu.TopRightMenu;

public class Line5Activity extends BaseActivity {

    @BindView(R.id.layout_top_back)
    ImageView layoutTopBack;
    @BindView(R.id.iv_menu)
    ImageView ivMenu;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_arrow)
    ImageView ivArrow;
    @BindView(R.id.layout_title)
    LinearLayout layoutTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
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
    @BindView(R.id.ten_thousand_gv)
    GridView tenThousandGv;
    @BindView(R.id.thousand_gv)
    GridView thousandGv;
    @BindView(R.id.hundred_gv)
    GridView hundredGv;
    @BindView(R.id.ten_gv)
    GridView tenGv;
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
    @BindView(R.id.unit_gv)
    GridView unitGv;
    private boolean flag = false;
    private TopRightMenu mtopRightMenu;
    private int isShow=2;
    ArrayList<MenuItem> list = new ArrayList<>();
    private ArrayList<String> omitsTenThousand = new ArrayList<>();
    private ArrayList<String> omitsThousand = new ArrayList<>();
    private ArrayList<String> omitsHundred = new ArrayList<>();
    private ArrayList<String> omitsTen = new ArrayList<>();
    private ArrayList<String> omitsUnit = new ArrayList<>();
    private int intentType;
    private String phase;
    private ArrayList<AwardPeriod> jsonArray;
    private Vibrator mVibrator;
    private ShakeListener mShakeListener;
    private ArrayList<String> tenThousands = new ArrayList<>(), thousands = new ArrayList<>(), hundreds = new ArrayList<>(), tens = new ArrayList<>(), units = new ArrayList<>();
    private ArrangeAdapter tenThousandAdapter;
    private ArrangeAdapter thousandAdapter;
    private ArrangeAdapter hundredAdapter;
    private ArrangeAdapter tenAdapter;
    private ArrangeAdapter unitAdapter;
    private PastLotteryAdapter pastLotteryAdapter;
    private EasyPopup mCirclePop;
    private View tvOne;
    private View tvFive;
    private View tvTen;
    private int selectTenThousandNumber,selectThousandNumber,selectHundredNumber,selectTenNumber,selectUnitNumber;
    private long zhushu;
    private boolean IsXpand=false;


    @Override
    protected void setTitle() {
        tvTitle.setText("排列5");
    }

    @Override
    public int getLayoutResource() {
        return R.layout.activity_line5;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTopTitlePop();
        initView();
        setListener();
        getData();
    }

    @OnClick({R.id.shake_choose_iv,R.id.bottom_result_clear_tv,R.id.layout_top_back, R.id.bottom_result_next_btn, R.id.iv_menu,R.id.layout_expand})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.shake_choose_iv:
                randomChoose();
                break;
            case R.id.bottom_result_clear_tv:
                tenThousands.clear();
                thousands.clear();
                hundreds.clear();
                tens.clear();
                units.clear();
                selectTenThousandNumber = 0;
                selectThousandNumber = 0;
                selectHundredNumber = 0;
                selectTenNumber = 0;
                selectUnitNumber = 0;
                //计算
                calculatorResult();
                //是否选择了求
                isChooseBall();
                //清除选中的球
                tenThousandAdapter.clearData();
                thousandAdapter.clearData();
                hundredAdapter.clearData();
                tenAdapter.clearData();
                unitAdapter.clearData();
                break;
            case R.id.iv_menu:
                mtopRightMenu.showAsDropDown(ivMenu, -50, 0);
                break;
            case R.id.layout_top_back:
                finish();
                break;
            case R.id.bottom_result_next_btn:
                if (tenThousands.size() == 0 && thousands.size() == 0&& hundreds.size() == 0&& tens.size() == 0&& units.size() == 0) {
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
                if(!IsXpand){
                    appBar.setExpanded(true);
                }else {
                    appBar.setExpanded(false);
                }
                break;
        }
    }

    private void next() {
        Arrange arrange=new Arrange();
        String absolutely = "";
        String thousand = "";
        String hundred = "";
        String ten = "";
        String individual = "";
        for (int i = 0; i < tenThousands.size(); i++) {
            absolutely = absolutely + tenThousands.get(i);
        }
        arrange.setAbsolutely(absolutely);
        for (int i = 0; i < thousands.size(); i++) {
            thousand = thousand + thousands.get(i);
        }
        arrange.setThousand(thousand);
        for (int i = 0; i < hundreds.size(); i++) {
            hundred = hundred + hundreds.get(i);
        }
        arrange.setHundred(hundred);
        for (int i = 0; i < tens.size(); i++) {
            ten = ten + tens.get(i);
        }
        arrange.setTen(ten);
        for (int i = 0; i < units.size(); i++) {
            individual = individual + units.get(i);
        }
        arrange.setIndividual(individual);

        arrange.setNotes(zhushu);
        arrange.setMoney(zhushu * 2);

        ArrayList<Arrange> balls = new ArrayList<>();
        balls.add(arrange);

        intent(balls);
    }

    //跳转
    private void intent(ArrayList<Arrange> balls) {
        if (intentType==0){
            Intent intent = new Intent(this, ArrangePayActivity.class);
            intent.putExtra("balls", (Serializable) balls);
            intent.putExtra("phase", phase);
            intent.putExtra("kind", Contacts.Lottery.P5name);
            startActivity(intent);
        }else {
            Intent intent = new Intent();
            intent.putExtra("balls", (Serializable) balls);
            intent.putExtra("phase", phase);
            intent.putExtra("kind", Contacts.Lottery.P5name);
            setResult(-1,intent);
        }
        finish();
    }

    //获取遗漏数据
    private void getData() {
        if (!DeviceUtil.IsNetWork(this)) {
            ToastUtils.showToast("网络异常，请检查网络");
            return;
        }

        JSONObject jo = new JSONObject();
        try {
            jo.put("type","5");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //遗漏
        ApiService.GET_SERVICE(Api.Line.miss, this, jo, new OnRequestDataListener() {
            @Override
            public void requestSuccess(int code, JSONObject data) {
                try {
                    JSONObject jo = data.getJSONObject("data");

                    JSONArray ja1 = jo.getJSONArray(Contacts.Arrange.absolutely);
                    for(int i=0;i<ja1.length();i++){
                        omitsTenThousand.add(ja1.getString(i));
                    }
                    JSONArray ja2 = jo.getJSONArray(Contacts.Arrange.thousand);
                    for(int i=0;i<ja2.length();i++){
                        omitsThousand.add(ja2.getString(i));
                    }
                    JSONArray ja3 = jo.getJSONArray(Contacts.Arrange.hundred);
                    for(int i=0;i<ja3.length();i++){
                        omitsHundred.add(ja3.getString(i));
                    }
                    JSONArray ja4 = jo.getJSONArray(Contacts.Arrange.ten);
                    for(int i=0;i<ja4.length();i++){
                        omitsTen.add(ja4.getString(i));
                    }
                    JSONArray ja5 = jo.getJSONArray(Contacts.Arrange.individual);
                    for(int i=0;i<ja5.length();i++){
                        omitsUnit.add(ja5.getString(i));
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

        //往期中奖
        ApiService.GET_SERVICE(Api.Line.drop, this, jo, new OnRequestDataListener() {
            @Override
            public void requestSuccess(int code, JSONObject data) {
                try {
                    JSONArray bannerArray = data.getJSONArray("data");
                    Gson gson = new Gson();
                    Type bannerType = new TypeToken<ArrayList<AwardPeriod>>() {
                    }.getType();
                    jsonArray = gson.fromJson(bannerArray.toString(), bannerType);
                    for (int i=0;i<jsonArray.size();i++){
                        AwardPeriod ja = jsonArray.get(i);
                        jsonArray.remove(i);
                        ja.setType(2);
                        jsonArray.add(i,ja);
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
        ApiService.GET_SERVICE(Api.Line.purchase, this, jo, new OnRequestDataListener() {
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


    private void initView() {
        Intent intent = getIntent();
        intentType = intent.getIntExtra("data", 0);

        //顶部不显示下拉箭头
        ivArrow.setVisibility(View.GONE);
        // 获得振动器服务
        mVibrator = (Vibrator) getApplication().getSystemService(VIBRATOR_SERVICE);
        // 实例化加速度传感器检测类
        mShakeListener = new ShakeListener(this);

        tenThousandAdapter = new ArrangeAdapter(this, tenThousands, isShow, 10);
        thousandAdapter = new ArrangeAdapter(this, thousands, isShow, 10);
        hundredAdapter = new ArrangeAdapter(this, hundreds, isShow, 10);
        tenAdapter = new ArrangeAdapter(this, tens, isShow, 10);
        unitAdapter = new ArrangeAdapter(this, units, isShow, 10);

        tenThousandGv.setAdapter(tenThousandAdapter);
        thousandGv.setAdapter(thousandAdapter);
        hundredGv.setAdapter(hundredAdapter);
        tenGv.setAdapter(tenAdapter);
        unitGv.setAdapter(unitAdapter);
        pastLotteryAdapter = new PastLotteryAdapter(null);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(pastLotteryAdapter);
        //重新设置高度
        ViewSetHinghUtil.resetGridViewHight(tenThousandGv,5);
        ViewSetHinghUtil.resetGridViewHight(thousandGv,5);
        ViewSetHinghUtil.resetGridViewHight(hundredGv,5);
        ViewSetHinghUtil.resetGridViewHight(tenGv,5);
        ViewSetHinghUtil.resetGridViewHight(unitGv,5);

        //底部显示机选
        bottomResultChooseTv.setVisibility(View.VISIBLE);
        bottomResultClearTv.setVisibility(View.GONE);

        mCirclePop = new EasyPopup(this)
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

    private void setTopTitlePop() {
        //右上菜单
        list.add(new MenuItem(0, "走势图"));
        list.add(new MenuItem(0, "显示遗漏"));
        list.add(new MenuItem(0, "近期开奖"));
        list.add(new MenuItem(0, "玩法说明"));
        mtopRightMenu = new TopRightMenu(this);
        mtopRightMenu.addMenuItems(list);
        mtopRightMenu.setWidth(230)
                .setHeight(350)
                .setShowIcon(false)
                .setShowAnimationStyle(true)
                .setAnimationStyle(R.style.TopRightMenu_Anim)
                .setShowBackground(true)
                .setArrowPosition(CommonUtil.dip2px(55f));
    }

    //显示遗漏
    public void showOmit() {
        tenThousandAdapter.updateData(isShow, tenThousands, omitsTenThousand);
        thousandAdapter.updateData(isShow, thousands, omitsThousand);
        hundredAdapter.updateData(isShow, hundreds, omitsHundred);
        tenAdapter.updateData(isShow, tens, omitsTen);
        unitAdapter.updateData(isShow, units, omitsUnit);
        //重新设置高度
        ViewSetHinghUtil.resetGridViewHight(tenThousandGv,5);
        ViewSetHinghUtil.resetGridViewHight(thousandGv,5);
        ViewSetHinghUtil.resetGridViewHight(hundredGv,5);
        ViewSetHinghUtil.resetGridViewHight(tenGv,5);
        ViewSetHinghUtil.resetGridViewHight(unitGv,5);
    }


    private void setListener() {
        mtopRightMenu.setOnTopRightMenuItemClickListener(new OnTopRightMenuItemClickListener() {
            @Override
            public void onTopRightMenuItemClick(int position) {
                switch (position) {
                    case 0://走势图
                        Intent intent1=new Intent(Line5Activity.this, WebViewActivity.class);
                        intent1.putExtra("url","http://test.m.lottery.anwenqianbao.com/#/zst/p5");
                        intent1.putExtra("title","排列5走势图");
                        startActivity(intent1);
                        break;
                    case 1:
                        if (isShow == 2) {
                            list.set(1, new MenuItem(0, "隐藏遗漏"));
                            mtopRightMenu.addMenuItems(list);
                            isShow = 1;
                        } else {
                            list.set(1, new MenuItem(0, "显示遗漏"));
                            mtopRightMenu.addMenuItems(list);
                            isShow = 2;
                        }
                        showOmit();
                        break;
                    case 2://开奖信息
                        Intent intent2 = new Intent(Line5Activity.this, LotteryInformationActivity.class);
                        intent2.putExtra("type", "p5");
                        startActivity(intent2);
                        break;
                    case 3://玩法说明
                        Intent intent4=new Intent(Line5Activity.this, WebViewActivity.class);
                        intent4.putExtra("url","http://p96a3nm36.bkt.clouddn.com/p5.jpg");
                        intent4.putExtra("title","排列5玩法说明");
                        startActivity(intent4);
                        break;
                }

            }
        });

        // 万位
        tenThousandGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 在每次获取点击的item时将对应的checkbox状态改变，同时修改map的值。
                ArrangeAdapter.redGridViewHolder vHolder = (ArrangeAdapter.redGridViewHolder) view.getTag();
                vHolder.chkRed.toggle();
                tenThousandAdapter.getSelected().put(position, vHolder.chkRed.isChecked());
                String hq = "";
                tenThousands.clear();
                for (int i = 0; i < tenThousandGv.getCount(); i++) {
                    ArrangeAdapter.redGridViewHolder vHolder_red = (ArrangeAdapter.redGridViewHolder) tenThousandGv.getChildAt(i).getTag();
                    if (tenThousandAdapter.hisSelected.get(i)) {
                        vHolder_red.chkRed.setTextColor(ContextCompat.getColor(Line5Activity.this, android.R.color.white));
                    } else {
                        vHolder_red.chkRed.setTextColor(ContextCompat.getColor(Line5Activity.this, R.color.red_ball));
                    }
                    if (tenThousandAdapter.getSelected().get(i)) {
                        hq = hq + (i + 1) + "  ";
                        tenThousands.add(i + "");
                    }
                }
                selectTenThousandNumber = tenThousands.size();
                //计算
                calculatorResult();
                //是否选择了求
                isChooseBall();
            }
        });
        // 千位
        thousandGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 在每次获取点击的item时将对应的checkbox状态改变，同时修改map的值。
                ArrangeAdapter.redGridViewHolder vHolder = (ArrangeAdapter.redGridViewHolder) view.getTag();
                vHolder.chkRed.toggle();
                thousandAdapter.getSelected().put(position, vHolder.chkRed.isChecked());
                String hq = "";
                thousands.clear();
                for (int i = 0; i < thousandGv.getCount(); i++) {
                    ArrangeAdapter.redGridViewHolder vHolder_red = (ArrangeAdapter.redGridViewHolder) thousandGv.getChildAt(i).getTag();
                    if (thousandAdapter.hisSelected.get(i)) {
                        vHolder_red.chkRed.setTextColor(ContextCompat.getColor(Line5Activity.this, android.R.color.white));
                    } else {
                        vHolder_red.chkRed.setTextColor(ContextCompat.getColor(Line5Activity.this, R.color.red_ball));
                    }
                    if (thousandAdapter.getSelected().get(i)) {
                        hq = hq + (i + 1) + "  ";
                        thousands.add(i + "");
                    }
                }
                selectThousandNumber = thousands.size();
                //计算
                calculatorResult();
                //是否选择了求
                isChooseBall();
            }
        });
        // 百位
        hundredGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 在每次获取点击的item时将对应的checkbox状态改变，同时修改map的值。
                ArrangeAdapter.redGridViewHolder vHolder = (ArrangeAdapter.redGridViewHolder) view.getTag();
                vHolder.chkRed.toggle();
                hundredAdapter.getSelected().put(position, vHolder.chkRed.isChecked());
                String hq = "";
                hundreds.clear();
                for (int i = 0; i < hundredGv.getCount(); i++) {
                    ArrangeAdapter.redGridViewHolder vHolder_red = (ArrangeAdapter.redGridViewHolder) hundredGv.getChildAt(i).getTag();
                    if (hundredAdapter.hisSelected.get(i)) {
                        vHolder_red.chkRed.setTextColor(ContextCompat.getColor(Line5Activity.this, android.R.color.white));
                    } else {
                        vHolder_red.chkRed.setTextColor(ContextCompat.getColor(Line5Activity.this, R.color.red_ball));
                    }
                    if (hundredAdapter.getSelected().get(i)) {
                        hq = hq + (i + 1) + "  ";
                        hundreds.add(i + "");
                    }
                }
                selectHundredNumber = hundreds.size();
                //计算
                calculatorResult();
                //是否选择了求
                isChooseBall();
            }
        });
        // 十位
        tenGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 在每次获取点击的item时将对应的checkbox状态改变，同时修改map的值。
                ArrangeAdapter.redGridViewHolder vHolder = (ArrangeAdapter.redGridViewHolder) view.getTag();
                vHolder.chkRed.toggle();
                tenAdapter.getSelected().put(position, vHolder.chkRed.isChecked());
                String hq = "";
                tens.clear();
                for (int i = 0; i < tenGv.getCount(); i++) {
                    ArrangeAdapter.redGridViewHolder vHolder_red = (ArrangeAdapter.redGridViewHolder) tenGv.getChildAt(i).getTag();
                    if (tenAdapter.hisSelected.get(i)) {
                        vHolder_red.chkRed.setTextColor(ContextCompat.getColor(Line5Activity.this, android.R.color.white));
                    } else {
                        vHolder_red.chkRed.setTextColor(ContextCompat.getColor(Line5Activity.this, R.color.red_ball));
                    }
                    if (tenAdapter.getSelected().get(i)) {
                        hq = hq + (i + 1) + "  ";
                        tens.add(i + "");
                    }
                }
                selectTenNumber = tens.size();
                //计算
                calculatorResult();
                //是否选择了求
                isChooseBall();
            }
        });
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
                        vHolder_red.chkRed.setTextColor(ContextCompat.getColor(Line5Activity.this, android.R.color.white));
                    } else {
                        vHolder_red.chkRed.setTextColor(ContextCompat.getColor(Line5Activity.this, R.color.red_ball));
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
                if( state == State.EXPANDED ) {
                    IsXpand=true;
                    tvExpand.setText(R.string.expand);
                    RoteteUtils.rotateArrow(ivExpand, !IsXpand);
                    //展开状态
                }else if(state == AppBarStateChangeListener.State.COLLAPSED){
                    IsXpand=false;
                    tvExpand.setText(R.string.un_expand);
                    RoteteUtils.rotateArrow(ivExpand, !IsXpand);
                    //折叠状态
                }else {
                    pastLotteryAdapter.setNewData(jsonArray);
                    //中间状态
                }
            }
        });

    }


    //获取多个随机注
    private void getRandomCount(int count) {
        ArrayList<Arrange> balls = new ArrayList<>();
        for (int j=0;j<count;j++){
            Arrange arrange=new Arrange();
            arrange.setAbsolutely((int) (Math.random() * 10) + "");
            arrange.setThousand((int) (Math.random() * 10) + "");
            arrange.setHundred((int) (Math.random() * 10) + "");
            arrange.setTen((int) (Math.random() * 10) + "");
            arrange.setIndividual((int) (Math.random() * 10) + "");

            arrange.setNotes(1);
            arrange.setMoney(2);

            balls.add(arrange);
        }
        intent(balls);
    }

    //计算注数并显示
    private void calculatorResult() {
        zhushu = selectTenThousandNumber*selectThousandNumber*selectHundredNumber*selectTenNumber*selectUnitNumber;
        bottomResultCountTv.setText(String.valueOf(zhushu));
        bottomResultMoneyTv.setText(String.valueOf(zhushu * 2));
        if (zhushu==0){
            tipTv.setVisibility(View.GONE);
        }else {
            tipTv.setVisibility(View.VISIBLE);
            tipTv.setText("若中奖：奖金 100000元，盈利"+(100000-zhushu*2)+"元");
        }
    }

    /**
     * 检查是否选择了球，以切换清空与机选按钮
     */
    private void isChooseBall() {
        if (tenThousands.size() == 0 && thousands.size() == 0&& hundreds.size() == 0&& tens.size() == 0&& units.size() == 0) {
            bottomResultChooseTv.setVisibility(View.VISIBLE);
            bottomResultClearTv.setVisibility(View.GONE);
        } else {
            bottomResultChooseTv.setVisibility(View.GONE);
            bottomResultClearTv.setVisibility(View.VISIBLE);
        }
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
        tenThousandAdapter.updateData(isShow, tenThousands, omitsTenThousand);
        thousandAdapter.updateData(isShow, thousands, omitsThousand);
        hundredAdapter.updateData(isShow, hundreds, omitsHundred);
        tenAdapter.updateData(isShow, tens, omitsTen);
        unitAdapter.updateData(isShow, units, omitsUnit);
        selectTenThousandNumber = 1;
        selectThousandNumber = 1;
        selectHundredNumber = 1;
        selectTenNumber = 1;
        selectUnitNumber = 1;
        //计算
        calculatorResult();
        //是否选择了求
        isChooseBall();
    }

    //获取随机数据
    private void getBallNumber() {
        tenThousands.clear();
        tenThousands.add((int) (Math.random() * 10) + "");
        thousands.clear();
        thousands.add((int) (Math.random() * 10) + "");
        hundreds.clear();
        hundreds.add((int) (Math.random() * 10) + "");
        tens.clear();
        tens.add((int) (Math.random() * 10) + "");
        units.clear();
        units.add((int) (Math.random() * 10) + "");
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

}
