package cn.com.futurelottery.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
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
import cn.com.futurelottery.utils.RoteteUtils;
import cn.com.futurelottery.utils.ToastUtils;
import cn.com.futurelottery.utils.ViewSetHinghUtil;


/**
 * 双色球胆拖
 */
public class DoubleBallDuplexFragment extends BaseFragment {

    @BindView(R.id.danhao_red_gv)
    GridView danhaoRedGv;
    @BindView(R.id.tuohao_red_gv)
    GridView tuohaoRedGv;
    @BindView(R.id.bule_gv)
    GridView buleGv;
    @BindView(R.id.bottom_result_clear_tv)
    TextView bottomResultClearTv;
    @BindView(R.id.bottom_result_count_tv)
    TextView bottomResultCountTv;
    @BindView(R.id.bottom_result_money_tv)
    TextView bottomResultMoneyTv;
    @BindView(R.id.bottom_result_next_btn)
    Button bottomResultNextBtn;
    @BindView(R.id.bottom_result_choose_tv)
    TextView bottomResultChooseTv;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.appBar)
    AppBarLayout appBar;
    @BindView(R.id.tv_period)
    TextView tvPeriod;
    @BindView(R.id.tv_award)
    TextView tvAward;
    @BindView(R.id.limite_date)
    TextView limiteDate;
    @BindView(R.id.tv_expand)
    TextView tvExpand;
    @BindView(R.id.iv_expand)
    ImageView ivExpand;
    @BindView(R.id.lion)
    LinearLayout lion;
    private ArrayList<String> chooseDanBalls = new ArrayList<>();
    private ArrayList<String> chooseTuoBalls = new ArrayList<>();
    private ArrayList<String> chooseBlueBalls = new ArrayList<>();
    //是否遗漏
    private int isShow;
    private DoubleBallRedAdapter danBallAdapter;
    private DoubleBallRedAdapter tuoBallAdapter;
    private DoubleBallBlueAdapter blueBallAdapter;
    private int selectDanNumber, selectTuoNumber, selectBlueNumber;
    //总共的注数
    private long zhushu;
    private ArrayList<String> omitsRed = new ArrayList<>();
    private ArrayList<String> omitsBlue = new ArrayList<>();
    private boolean IsXpand=false;
    private ArrayList<AwardPeriod> jsonArray;
    private AwardPeriodAdapter mAwardPeriodAdapter;
    //第多少期
    private String phase;
    private int intentType;

    public DoubleBallDuplexFragment() {
        // Required empty public constructor
    }


    @Override
    public int getLayoutResource() {
        return R.layout.fragment_double_ball_duplex;
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
                    for (int i=0;i<jsonArray.size();i++){
                        AwardPeriod ja = jsonArray.get(i);
                        jsonArray.remove(i);
                        ja.setType(1);
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

    private void initView() {
        danBallAdapter = new DoubleBallRedAdapter(getContext(), chooseDanBalls, isShow, 33);
        tuoBallAdapter = new DoubleBallRedAdapter(getContext(), chooseTuoBalls, isShow, 33);
        blueBallAdapter = new DoubleBallBlueAdapter(getContext(), chooseBlueBalls, isShow, 16);

        danhaoRedGv.setAdapter(danBallAdapter);
        tuohaoRedGv.setAdapter(tuoBallAdapter);
        buleGv.setAdapter(blueBallAdapter);

        mAwardPeriodAdapter = new AwardPeriodAdapter(null);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(mAwardPeriodAdapter);

        //重新设置高度
        ViewSetHinghUtil.resetGridViewHight(danhaoRedGv,7);
        ViewSetHinghUtil.resetGridViewHight(tuohaoRedGv,7);
        ViewSetHinghUtil.resetGridViewHight(buleGv,7);

        //底部显示清空
        bottomResultChooseTv.setVisibility(View.GONE);
        bottomResultClearTv.setVisibility(View.VISIBLE);
    }

    private void setListener() {
        // 对胆号GridView进行监听，获得红球胆号选中的数。
        danhaoRedGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //限制球数
                if (limit(5, position, chooseDanBalls)) {
                    ToastUtils.showToast("最多选择5个胆码");
                    return;
                }
                //判断是否包含此位置的球
                for (int i = 0; i < chooseTuoBalls.size(); i++) {
                    if (chooseTuoBalls.get(i).equals(position + "")) {
                        chooseTuoBalls.remove(i);
                        selectTuoNumber--;
                        tuoBallAdapter.updateData(isShow, chooseTuoBalls, omitsRed);
                    }
                }
                // 在每次获取点击的item时将对应的checkbox状态改变，同时修改map的值。
                DoubleBallRedAdapter.redGridViewHolder vHolder = (DoubleBallRedAdapter.redGridViewHolder) view.getTag();
                vHolder.chkRed.toggle();
                danBallAdapter.getSelected().put(position, vHolder.chkRed.isChecked());
                int tempRed = 0;
                String hq = "";
                chooseDanBalls.clear();
                for (int i = 0; i < danhaoRedGv.getCount(); i++) {
                    DoubleBallRedAdapter.redGridViewHolder vHolder_red = (DoubleBallRedAdapter.redGridViewHolder) danhaoRedGv.getChildAt(i).getTag();
                    if (danBallAdapter.hisSelected.get(i)) {
                        ++tempRed;
                        selectDanNumber = tempRed;
                        vHolder_red.chkRed.setTextColor(ContextCompat.getColor(getContext(), android.R.color.white));
                    } else {
                        vHolder_red.chkRed.setTextColor(ContextCompat.getColor(getContext(), R.color.red_ball));
                    }
                    if (danBallAdapter.getSelected().get(i)) {
                        hq = hq + (i + 1) + "  ";
                        chooseDanBalls.add(i + "");
                    }
                }

                selectDanNumber = chooseDanBalls.size();
                //计算
                calculatorResult();
            }
        });
        // 对拖号GridView进行监听，获得红球拖号选中的数。
        tuohaoRedGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //限制球数
                if (limit(20, position, chooseTuoBalls)) {
                    ToastUtils.showToast("最多选择20个拖码");
                    return;
                }
                //判断是否此位置的球
                for (int i = 0; i < chooseDanBalls.size(); i++) {
                    if (chooseDanBalls.get(i).equals(position + "")) {
                        chooseDanBalls.remove(i);
                        selectDanNumber--;
                        danBallAdapter.updateData(isShow, chooseDanBalls, omitsRed);
                    }
                }
                // 在每次获取点击的item时将对应的checkbox状态改变，同时修改map的值。
                DoubleBallRedAdapter.redGridViewHolder vHolder = (DoubleBallRedAdapter.redGridViewHolder) view.getTag();
                vHolder.chkRed.toggle();
                tuoBallAdapter.getSelected().put(position, vHolder.chkRed.isChecked());
                int tempRed = 0;
                String hq = "";
                chooseTuoBalls.clear();
                for (int i = 0; i < tuohaoRedGv.getCount(); i++) {
                    DoubleBallRedAdapter.redGridViewHolder vHolder_red = (DoubleBallRedAdapter.redGridViewHolder) tuohaoRedGv.getChildAt(i).getTag();
                    if (tuoBallAdapter.hisSelected.get(i)) {
                        ++tempRed;
                        selectTuoNumber = tempRed;
                        vHolder_red.chkRed.setTextColor(ContextCompat.getColor(getContext(), android.R.color.white));
                    } else {
                        vHolder_red.chkRed.setTextColor(ContextCompat.getColor(getContext(), R.color.red_ball));
                    }
                    if (tuoBallAdapter.getSelected().get(i)) {
                        hq = hq + (i + 1) + "  ";
                        chooseTuoBalls.add(i + "");
                    }
                }
                selectTuoNumber = chooseTuoBalls.size();
                //计算
                calculatorResult();
            }
        });

        // 对篮球GridView进行监听，获取选球状态。
        buleGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DoubleBallBlueAdapter.LanGridViewHolder vHollder = (DoubleBallBlueAdapter.LanGridViewHolder) view.getTag();
                vHollder.chkBlue.toggle();
                blueBallAdapter.getSelected().put(position, vHollder.chkBlue.isChecked());
                int tempBlue = 0;
                String lq = "";
                chooseBlueBalls.clear();
                for (int i = 0; i < buleGv.getCount(); i++) {
                    DoubleBallBlueAdapter.LanGridViewHolder vHolder_Blue = (DoubleBallBlueAdapter.LanGridViewHolder) buleGv.getChildAt(i).getTag();
                    if (blueBallAdapter.lisSelected.get(i)) {
                        ++tempBlue;
                        selectBlueNumber = tempBlue;
                        vHolder_Blue.chkBlue.setTextColor(getResources().getColor(android.R.color.white));
                    } else {
                        vHolder_Blue.chkBlue.setTextColor(getResources().getColor(R.color.blue_ball));
                    }

                    if (blueBallAdapter.getSelected().get(i)) {
                        lq = lq + (i + 1) + "  ";
                        chooseBlueBalls.add(i + "");
                    }
                }
                selectBlueNumber = chooseBlueBalls.size();
                //计算
                calculatorResult();
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
                }else if(state == State.COLLAPSED){
                    IsXpand=false;
                    tvExpand.setText(R.string.un_expand);
                    RoteteUtils.rotateArrow(ivExpand, !IsXpand);
                    //折叠状态
                }else {
                    mAwardPeriodAdapter.setNewData(jsonArray);
                    //中间状态
                }
            }
        });
    }


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


    @OnClick({R.id.bottom_result_clear_tv, R.id.bottom_result_next_btn,R.id.layout_expand})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bottom_result_clear_tv:
                //数据清空
                chooseDanBalls.clear();
                chooseTuoBalls.clear();
                chooseBlueBalls.clear();
                selectDanNumber = 0;
                selectTuoNumber = 0;
                selectBlueNumber = 0;
                //计算
                calculatorResult();
                //清除选中的球
                danBallAdapter.clearData();
                tuoBallAdapter.clearData();
                blueBallAdapter.clearData();
                break;
            case R.id.bottom_result_next_btn:
                if (zhushu == 0) {
                    ToastUtils.showToast("至少选择一注");
                    return;
                }
                DoubleBall db = new DoubleBall();
                String dan = "";
                String tuo = "";
                String blue = "";
                for (int i = 0; i < chooseDanBalls.size(); i++) {
                    int number = Integer.parseInt(chooseDanBalls.get(i)) + 1;
                    if (i == 0) {
                        dan = dan + ((number < 10) ? ("0" + number) : number);
                    } else {
                        dan = dan + "," + ((number < 10) ? ("0" + number) : number);
                    }
                }
                for (int i = 0; i < chooseTuoBalls.size(); i++) {
                    int number = Integer.parseInt(chooseTuoBalls.get(i)) + 1;
                    if (i == 0) {
                        tuo = tuo + ((number < 10) ? ("0" + number) : number);
                    } else {
                        tuo = tuo + "," + ((number < 10) ? ("0" + number) : number);
                    }
                }
                for (int i = 0; i < chooseBlueBalls.size(); i++) {
                    int number = Integer.parseInt(chooseBlueBalls.get(i)) + 1;
                    if (i == 0) {
                        blue = blue + ((number < 10) ? ("0" + number) : number);
                    } else {
                        blue = blue + "," + ((number < 10) ? ("0" + number) : number);
                    }
                }
                //判断单复式胆拖
                db.setType(2);
                db.setDan(dan);
                db.setRed(tuo);
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

    //计算注数并显示
    private void calculatorResult() {
        zhushu = Calculator.calculateDanTuoNum(selectDanNumber, selectTuoNumber, selectBlueNumber);
        bottomResultCountTv.setText(String.valueOf(zhushu));
        bottomResultMoneyTv.setText(String.valueOf(zhushu * 2));
    }

    //显示遗漏
    public void showOmit(ArrayList<String> omitsRed, ArrayList<String> omitsBlue) {
        isShow = 1;
        this.omitsRed = omitsRed;
        this.omitsBlue = omitsBlue;
        danBallAdapter.updateData(isShow, chooseDanBalls, omitsRed);
        tuoBallAdapter.updateData(isShow, chooseTuoBalls, omitsRed);
        blueBallAdapter.updateData(isShow, chooseBlueBalls, omitsBlue);
        //重新设置高度
        ViewSetHinghUtil.resetGridViewHight(danhaoRedGv,7);
        ViewSetHinghUtil.resetGridViewHight(tuohaoRedGv,7);
        ViewSetHinghUtil.resetGridViewHight(buleGv,7);
    }

    //显示遗漏
    public void unShowOmit() {
        isShow = 2;
        omitsRed.clear();
        omitsBlue.clear();
        danBallAdapter.updateData(isShow, chooseDanBalls, omitsRed);
        tuoBallAdapter.updateData(isShow, chooseTuoBalls, omitsRed);
        blueBallAdapter.updateData(isShow, chooseBlueBalls, omitsBlue);
        //重新设置高度
        ViewSetHinghUtil.resetGridViewHight(danhaoRedGv,7);
        ViewSetHinghUtil.resetGridViewHight(tuohaoRedGv,7);
        ViewSetHinghUtil.resetGridViewHight(buleGv,7);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
            if (hidden) {
                appBar.setExpanded(false);
            } else {
            }

    }
}
