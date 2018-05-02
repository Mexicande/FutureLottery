package cn.com.futurelottery.ui.activity.Football;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.mancj.slideup.SlideUp;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.futurelottery.R;
import cn.com.futurelottery.base.Api;
import cn.com.futurelottery.base.ApiService;
import cn.com.futurelottery.base.BaseActivity;
import cn.com.futurelottery.base.Contacts;
import cn.com.futurelottery.inter.ClearDialogListener;
import cn.com.futurelottery.inter.OnRequestDataListener;
import cn.com.futurelottery.inter.SaveDialogListener;
import cn.com.futurelottery.model.DoubleBall;
import cn.com.futurelottery.model.FootBallList;
import cn.com.futurelottery.model.FootPay;
import cn.com.futurelottery.model.ScoreList;
import cn.com.futurelottery.ui.activity.ChooseBallPaymentActivity;
import cn.com.futurelottery.ui.activity.LoginActivity;
import cn.com.futurelottery.ui.activity.PayActivity;
import cn.com.futurelottery.ui.adapter.football.FootChooseWinAdapter;
import cn.com.futurelottery.ui.dialog.ClearDialogFragment;
import cn.com.futurelottery.ui.dialog.QuitDialogFragment;
import cn.com.futurelottery.utils.ActivityUtils;
import cn.com.futurelottery.utils.CommonUtil;
import cn.com.futurelottery.utils.LogUtils;
import cn.com.futurelottery.utils.MenuDecoration;
import cn.com.futurelottery.utils.SPUtil;
import cn.com.futurelottery.utils.SPUtils;
import cn.com.futurelottery.utils.ToastUtils;
import cn.com.futurelottery.view.topRightMenu.MenuItem;
import cn.com.futurelottery.view.topRightMenu.TRMenuAdapter;

/**
 * @author apple
 */
public class FootAllBetActivity extends BaseActivity implements ClearDialogListener {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.choose_recycler)
    RecyclerView chooseRecycler;
    @BindView(R.id.ed_multiple)
    EditText edMultiple;
    @BindView(R.id.bottom_result_count_tv)
    TextView bottomResultCountTv;
    @BindView(R.id.bottom_result_money_tv)
    TextView bottomResultMoneyTv;
    @BindView(R.id.bottom_result_btn)
    Button bottomResultBtn;
    @BindView(R.id.empty_layout)
    LinearLayout emptyLayout;
    @BindView(R.id.foot_all)
    TextView footAll;
    @BindView(R.id.layout_slide_bet)
    LinearLayout layoutSlideBet;
    @BindView(R.id.slide_multiple)
    EditText slideMultiple;
    @BindView(R.id.trm_recyclerview)
    RecyclerView trmRecyclerview;
    @BindView(R.id.slideView)
    RelativeLayout slideView;
    @BindView(R.id.tv_select_bet)
    TextView tvSelectBet;
    @BindView(R.id.bottom_result_tv_bet)
    TextView bottomResultTvBet;
    @BindView(R.id.iv_arrow)
    ImageView ivArrow;
    @BindView(R.id.predict_money)
    TextView predictMoney;
    private SlideUp slideUp;
    private TRMenuAdapter mBottomTRMenuAdapter;
    private FootChooseWinAdapter mAdapter;
    private List<FootBallList.DataBean.MatchBean> bean;
    private List<MenuItem> mSrceenList;
    private int type = 0;
    private View view;

    @Override
    public int getLayoutResource() {
        return R.layout.activity_foot_ball_bet;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = getIntent().getIntExtra("type", 0);
        initView();
        if (type%2!=0) {
            initAllPass();
        } else {
            initOnePass();
        }
        setListener();

    }


    private void initView() {
        bean = (List<FootBallList.DataBean.MatchBean>) getIntent().getSerializableExtra("bean");
        mAdapter = new FootChooseWinAdapter(bean);
        chooseRecycler.setLayoutManager(new LinearLayoutManager(this));
        chooseRecycler.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        ((SimpleItemAnimator) chooseRecycler.getItemAnimator()).setSupportsChangeAnimations(false);
        chooseRecycler.setAdapter(mAdapter);
        view = getLayoutInflater().inflate(R.layout.payment_rv_footor, null);
        mAdapter.addFooterView(view);

        slideUp = new SlideUp.Builder(slideView)
                .withStartGravity(Gravity.BOTTOM)
                .withLoggingEnabled(true)
                .withAutoSlideDuration(1)
                .withStartState(SlideUp.State.HIDDEN)
                .build();
    }
    /**
     * 单关
     */
    private void initOnePass() {
        int bet = Integer.parseInt(edMultiple.getText().toString());
        //注数
        bottomResultTvBet.setText(bet+"倍");

        //单关预计奖金
        double oneMinMoney;
        double oneMaxMoney;

        OnePass();
        //单关最大预计奖金
        oneMaxMoney = getOneMax()*2*bet;
        //单关最小预计奖金
        oneMinMoney = getOneMoney()*2*bet;

        double max=oneMaxMoney;

        DecimalFormat decimalFormat =new DecimalFormat("#.00");
        String strMin = decimalFormat.format(oneMinMoney);
        String strMax = decimalFormat.format(max);
        if(oneMaxMoney==oneMinMoney){
            predictMoney.setText(strMin+"");
        }else {
            predictMoney.setText(strMin+"~"+strMax);
        }
    }


    private void initAllPass() {
        initSlideLabel();
        trmRecyclerview.setLayoutManager(new GridLayoutManager(this, 4));
        trmRecyclerview.addItemDecoration(new MenuDecoration(CommonUtil.dip2px(10), 4));
        mBottomTRMenuAdapter = new TRMenuAdapter(R.layout.football_menu_item, mSrceenList);
        trmRecyclerview.setAdapter(mBottomTRMenuAdapter);
        //mBottomTRMenuAdapter.getData().get(0).setIcon(1);
        //upDate();
        slideMultiple.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String len = s.toString();
                if(len.startsWith("0")&&len.length()>1){
                    String substring = len.substring(1, len.length()-1);
                    slideMultiple.setText(substring);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                String len = s.toString();
                if (len.equals("0")) {
                    s.clear();
                }
                if (TextUtils.isEmpty(s)) {
                    slideMultiple.setText("1");
                    return;
                } else if (Integer.valueOf(s.toString()) > 50) {
                    slideMultiple.setText(String.valueOf(50));
                    return;
                }

                if (Integer.valueOf(s.toString())<1){
                    slideMultiple.setText("1");
                    return;
                }
                edMultiple.setText(s);
                if(type%2!=0){
                    //过关
                    upDate();
                }else {
                    //单关
                    initOnePass();
                }
            }
        });


        mBottomTRMenuAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                MenuItem item = mBottomTRMenuAdapter.getItem(position);
                if (item != null) {
                    if (item.getIcon() == 0) {
                        item.setIcon(1);
                    } else {
                        item.setIcon(0);
                    }
                }
                mBottomTRMenuAdapter.notifyItemChanged(position);
                if(type%2!=0){
                    //过关
                    upDate();
                }else {
                    //单关
                    initOnePass();
                }
            }
        });
    }

    /**
     *
     * 串数标签
     */
    private void initSlideLabel(){
        int size = bean.size();
        mSrceenList = new ArrayList<>();
        for (int i = 2; i <= size; i++) {
            if (i <= 8) {
                MenuItem item = new MenuItem();
                item.setContent(i + "串" + 1);
                mSrceenList.add(item);
            } else {
                return;
            }
        }
        ivArrow.setVisibility(View.VISIBLE);
        if(mSrceenList.size()==1){
            tvSelectBet.setText(mSrceenList.get(0).getContent());
            mSrceenList.get(0).setIcon(1);
        }else {
            tvSelectBet.setText("投注方式(必选)");
            ivArrow.setVisibility(View.VISIBLE);
        }
    }
    private void setListener() {
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                FootBallList.DataBean.MatchBean matchBean = mAdapter.getData().get(position);
                switch (view.getId()) {
                    case R.id.iv_delete:
                        mAdapter.remove(position);
                        delectDate();
                        break;
                    case R.id.layout_home:
                        if (matchBean.getHomeType() == 0) {
                            matchBean.setHomeType(1);
                        } else {
                            matchBean.setHomeType(0);
                        }
                        if(type%2!=0){
                            //过关
                            upDate();
                        }else {
                            //单关
                            initOnePass();
                        }
                        break;
                    case R.id.layout_away:
                        if (matchBean.getAwayType() == 0) {
                            matchBean.setAwayType(1);
                        } else {
                            matchBean.setAwayType(0);
                        }
                        if(type%2!=0){
                            //过关
                            upDate();
                        }else {
                            //单关
                            initOnePass();
                        }

                        break;
                    case R.id.layout_vs:
                        if (matchBean.getVsType() == 0) {
                            matchBean.setVsType(1);
                        } else {
                            matchBean.setVsType(0);
                        }
                        if(type%2!=0){
                            //过关
                            upDate();
                        }else {
                            //单关
                            initOnePass();
                        }
                        break;
                    default:
                        break;

                }
                mAdapter.notifyItemChanged(position);

            }
        });

        edMultiple.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String len = s.toString();
                if(len.startsWith("0")&&len.length()>1){
                    String substring = len.substring(1, len.length()-1);
                    edMultiple.setText(substring);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s)) {
                    return;
                } else if (Integer.valueOf(s.toString()) > 50) {
                    edMultiple.setText(String.valueOf(50));
                    return;
                }
                if (Integer.valueOf(s.toString())<1){
                    edMultiple.setText("1");
                    return;
                }
                if(type%2!=0){
                    //过关
                    upDate();
                }else {
                    //单关
                    initOnePass();
                }
            }
        });
        slideUp.addSlideListener(new SlideUp.Listener.Visibility() {
            @Override
            public void onVisibilityChanged(int visibility) {
                if(visibility==View.VISIBLE){
                    slideMultiple.setText(edMultiple.getText().toString());

                }else {
                    edMultiple.setText(slideMultiple.getText().toString());
                }
            }
        });
    }

    /**
     * 删除数据
     */
    private void delectDate() {
        if(type%2!=0){
            //过关
            if(mAdapter.getData().size()>0){
                if(mAdapter.getData().size()==mBottomTRMenuAdapter.getData().size()){
                    mBottomTRMenuAdapter.remove(mBottomTRMenuAdapter.getData().size()-1);
                    mBottomTRMenuAdapter.notifyDataSetChanged();
                }
                if(mAdapter.getData().size()==0){
                    tvSelectBet.setText("投注方式(必选)");
                    chooseRecycler.setVisibility(View.GONE);
                    emptyLayout.setVisibility(View.VISIBLE);
                    mAdapter.removeFooterView(view);
                }
                getSelectBet();
                upDate();
            }
        }else {
            //单关
            initOnePass();
        }
    }


    /**
     * 刷新数据
     */

    private void upDate() {
        int select=0;
        //注数
        int bet = Integer.parseInt(edMultiple.getText().toString());
        int allCount=0;
        for(MenuItem s:mSrceenList){
            if(s.getIcon()==1){
                select++;
            }
        }

        if(select!=0){
            for(int i=0;i<mSrceenList.size();i++){
                if (mSrceenList.get(i).getIcon() == 1) {
                    String content = mSrceenList.get(i).getContent();
                    String substring = content.substring(0, 1);
                    Integer integer = Integer.valueOf(substring);

                    allCount += nuBet(integer);
                }
            }
            updateMinOddsAll();
        }else {
            predictMoney.setText("0.00");
        }

        bottomResultTvBet.setText(bet+"倍");
        bottomResultCountTv.setText(String.valueOf(allCount));
        bottomResultMoneyTv.setText(allCount * Integer.valueOf(bet) * 2 + "");

    }

    /**
     * 单关注数---钱数
     */
    private int  OnePass() {
        String bet = edMultiple.getText().toString();
        int count = 0;
        List<FootBallList.DataBean.MatchBean> data = mAdapter.getData();
        for (int i = 0; i < data.size(); i++) {
            FootBallList.DataBean.MatchBean item = data.get(i);
            count+=item.getHomeType() + item.getVsType() + item.getAwayType();
        }
        bottomResultCountTv.setText(String.valueOf(count));
        bottomResultMoneyTv.setText(count * Integer.valueOf(bet) * 2 + "");
        //单关奖金
        getOneMoney();
        return count;
    }
    /**
     *
     * 单关最小奖金
     *
     */
    private double getOneMoney() {
        double min=0;

        ArrayList<Double>mlist=new ArrayList<>();
        for (FootBallList.DataBean.MatchBean s : bean) {
            List<FootBallList.DataBean.MatchBean.OddsBean> odds = s.getOdds();
            if(s.getHomeType()==1){
                mlist.add(Double.parseDouble(odds.get(0).getOdds()));
            }
            if(s.getVsType()==1){
                mlist.add(Double.parseDouble(odds.get(1).getOdds()));
            }
            if(s.getAwayType()==1){
                mlist.add(Double.parseDouble(odds.get(2).getOdds()));
            }

        }

        if(mlist.size()!=0){
            min = Collections.min(mlist);
        }
        return min;
    }


    /**
     * 单关最大奖金
     * @return
     */
    private double getOneMax() {
        double money=0;
        for (FootBallList.DataBean.MatchBean s : bean) {
            List<FootBallList.DataBean.MatchBean.OddsBean> odds = s.getOdds();
            ArrayList<String>maxlist=new ArrayList<>();
            if(s.getHomeType()==1){
                maxlist.add(odds.get(0).getOdds());
            }
            if(s.getVsType()==1){
                maxlist.add(odds.get(1).getOdds());
            }
            if(s.getAwayType()==1){
                maxlist.add(odds.get(2).getOdds());
            }
            if(maxlist.size()!=0){
                String maxStr = Collections.max(maxlist);
                double max = Double.parseDouble(maxStr);
                money+=max;
            }
        }
        return money;
    }


    /**
     * 多注最小奖金
     */
    private void updateMinOddsAll() {
        ArrayList<Double> list = new ArrayList<>();
        for (FootBallList.DataBean.MatchBean s : bean) {
            List<FootBallList.DataBean.MatchBean.OddsBean> odds = s.getOdds();
            ArrayList<Double>mlist=new ArrayList<>();
            if(s.getHomeType()==1){
                mlist.add(Double.parseDouble(odds.get(0).getOdds()));
            }
            if(s.getVsType()==1){
                mlist.add(Double.parseDouble(odds.get(1).getOdds()));
            }
            if(s.getAwayType()==1){
                mlist.add(Double.parseDouble(odds.get(2).getOdds()));
            }
            if(mlist.size()!=0){
                Double min = Collections.min(mlist);
                list.add(min);
            }
        }
        boolean selected=false;
        for(MenuItem s:mSrceenList){
            if(s.getIcon()==1){
                selected=true;
            }
        }

        if(selected){
            double v;
            int i = Integer.parseInt(edMultiple.getText().toString());
                if(list.size()>=2){
                    Collections.sort(list);

                    ArrayList<Integer>mList=new ArrayList<>();
                    for (int j = 0; j < mSrceenList.size(); j++) {
                        if (mSrceenList.get(j).getIcon() == 1) {
                            String content = mSrceenList.get(j).getContent();
                            String substring = content.substring(0, 1);
                            Integer integer = Integer.valueOf(substring);
                            mList.add(integer);
                        }
                    }
                    double money=1;
                    Integer integer = mList.get(0);
                    for(int k=0;k<integer;k++){
                        money=money*list.get(k);
                    }
                    v = money*2*i;

                    DecimalFormat decimalFormat =new DecimalFormat("#.00");
                    String min = decimalFormat.format(v);
                    String max = decimalFormat.format(updateMaxOddsAll()*i);
                    if(min.equals(max)){
                        predictMoney.setText(min);
                    }else {
                        predictMoney.setText(min+"~"+ max );
                    }
                    bottomResultTvBet.setText(i+"倍");
                }else {
                    predictMoney.setText("0.00");
                }

        }else {
            predictMoney.setText("0.00");
        }

    }
    /**
     * 多注最大奖金
     */
    private Double updateMaxOddsAll() {
        ArrayList<Double> mlist = new ArrayList<>();
        for (FootBallList.DataBean.MatchBean s : bean) {
            List<FootBallList.DataBean.MatchBean.OddsBean> odds = s.getOdds();
            ArrayList<String>maxlist=new ArrayList<>();
            if(s.getHomeType()==1){
                maxlist.add(odds.get(0).getOdds());
            }
            if(s.getVsType()==1){
                maxlist.add(odds.get(1).getOdds());
            }
            if(s.getAwayType()==1){
                maxlist.add(odds.get(2).getOdds());
            }
            if(maxlist.size()!=0){
                String maxStr = Collections.max(maxlist);
                double max = Double.parseDouble(maxStr);
                mlist.add(max);
            }
        }
        double money=0;
        for (int i = 0; i < mSrceenList.size(); i++) {
            if (mSrceenList.get(i).getIcon() == 1) {
                String content = mSrceenList.get(i).getContent();
                String substring = content.substring(0, 1);
                Integer integer = Integer.valueOf(substring);
                money += setMaX(mlist,integer);
            }
        }
        return money*2;
    }

    private Double setMaX(ArrayList<Double>mList,int bunch){

        if (bunch == 1) {
            double count = 0;
            for(int i=0;i<mList.size();i++){

                count+=mList.get(i)*1;
            }
            return count;
        }else {
            double money=0;
            ArrayList<Double> mArrays = new ArrayList<>();

            mArrays.addAll(mList);

            for(int j=0;j<mList.size()-bunch+1;j++){
                mArrays.remove(0);
                money+=mList.get(j)*setMaX(mArrays,bunch-1);
            }
            return money;
        }

    }



    /**
     * 更新
     *
     * @param nu
     * @return
     */
    private Integer nuBet(int nu) {
        Integer integer = 0;
        ArrayList<Integer> mArrays = new ArrayList<>();
        for (int i = 0; i < bean.size(); i++) {
            FootBallList.DataBean.MatchBean item = bean.get(i);
            mArrays.add(item.getHomeType() + item.getVsType() + item.getAwayType());
        }
        if (mArrays.size() != 0) {
            integer = countExpect(nu, mArrays);
        }

        return integer;
    }

    /**
     * 计算注数
     *
     * @param m
     * @param mList
     * @return
     */
    private Integer countExpect(int m, ArrayList<Integer> mList) {
        int count = 0;
        if (m == 2) {
            return countDouble(mList);
        } else {
            ArrayList<Integer> mArrays = new ArrayList<>();
            mArrays.addAll(mList);
            for (int i = 0; i < mList.size() - m + 1; i++) {
                mArrays.remove(0);
                count += mList.get(i) * countExpect(m - 1, mArrays);
            }
            return count;
        }
    }



    /**
     * 2串1
     *
     * @param mList
     * @return
     */
    private Integer countDouble(ArrayList<Integer> mList) {
        int doubleCount = 0;
        for (int i = 0; i < mList.size() - 1; i++) {
            int size = 0;
            for (int j = i + 1; j < mList.size(); j++) {
                size += mList.get(j);
            }
            doubleCount += mList.get(i) * size;
        }
        return doubleCount;
    }


    @OnClick({R.id.add_choose, R.id.choose_clear, R.id.layout_top_back, R.id.layout_bet, R.id.layoutGo
            , R.id.layout_slide_bet, R.id.bottom_result_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layout_top_back:
                finish();
                break;
            case R.id.add_choose:
                finish();
                break;
            case R.id.choose_clear:
                ClearDialogFragment quitDialogFragment=ClearDialogFragment.newInstance(1);
                quitDialogFragment.show(getSupportFragmentManager(),"footAll");

                break;
            case R.id.layout_bet:
                if (type%2!=0) {
                    slideUp.show();
                    if (mSrceenList.size() == 0) {
                        ToastUtils.showToast("请选择投注方式");
                    }
                }
                break;
            case R.id.layout_slide_bet:
            case R.id.layoutGo:
                if (type%2!=0) {
                    slideUp.hide();
                    getSelectBet();
                }
                break;
            case R.id.bottom_result_btn:
                if(slideUp.isVisible()){
                    slideUp.hide();
                    getSelectBet();
                }else {
                    paySubmit();
                }
                break;
            default:
                break;
        }
    }

    /**
     * 提交订单
     */
    private void paySubmit() {
        String string = (String) SPUtils.get(this, Contacts.TOKEN,"");
        String text = bottomResultCountTv.getText().toString();
            if(!"0".equals(text)){
                if(TextUtils.isEmpty(string)){
                    ToastUtils.showToast(getString(R.string.login_please));
                    ActivityUtils.startActivity(LoginActivity.class);
                }else {
                    payMent(text);
                }
            }else {
                if (type%2!=0) {
                    slideUp.show();
                    ToastUtils.showToast("请至少选择2场比赛");
                }else {
                    ToastUtils.showToast("请至少选择1场比赛");
                }
            }
    }

    /**
     * 下注
     * @param number
     */
    private void payMent(String number) {
        ArrayList<FootPay.TitleBean>list=new ArrayList<>();
        for(int i=0;i<bean.size();i++){
            FootBallList.DataBean.MatchBean matchBean = bean.get(i);
            if(bean.get(i).getAwayType()==1||bean.get(i).getVsType()==1||bean.get(i).getHomeType()==1){
                FootPay.TitleBean titleBean=new FootPay.TitleBean();
                titleBean.setMatch(bean.get(i).getMatch_id());
                titleBean.setTeam(matchBean.getHomeTeam()+":"+matchBean.getAwayTeam());
                String str = (matchBean.getHomeType() == 1 ? "3," : "") + (matchBean.getVsType() == 1 ? "1," : "")
                        + (matchBean.getAwayType() == 1 ? "0," : "");
                if(str.endsWith(",")){
                    String substring = str.substring(0, str.length() - 1);
                    titleBean.setType(substring);
                }else {
                    titleBean.setType(str);
                }
                list.add(titleBean);
            }
        }
        FootPay.MessageBean messageBean=new FootPay.MessageBean();

        if (type%2!=0) {
            StringBuilder sb=new StringBuilder();
            for(MenuItem s:mSrceenList){
                if(s.getIcon()==1){
                    String content = s.getContent();
                    String substring = content.substring(0, 1);
                    sb.append(substring).append(",");
                }
            }
            String string = sb.toString();
            String substring = string.substring(0, string.length() - 1);
            messageBean.setStrand(substring);
        }

            //钱数
            String money = bottomResultMoneyTv.getText().toString();
            //倍数
            String bet = edMultiple.getText().toString();
                messageBean.setMoney(Integer.parseInt(money));
                messageBean.setMultiple(Integer.parseInt(bet));
                messageBean.setNumber(Integer.parseInt(number));
                switch (type){
                    case 1:
                        //队伍
                        messageBean.setPlay_rules(Api.FOOTBALL.FT001);
                        break;
                    case 2:
                        messageBean.setStrand("0");
                        messageBean.setPlay_rules(Api.FOOTBALL.FT001);
                        break;
                    case 3:
                        messageBean.setPlay_rules(Api.FOOTBALL.FT006);
                        break;
                    case 4:
                        messageBean.setStrand("0");
                        messageBean.setPlay_rules(Api.FOOTBALL.FT006);
                        break;
                    case 5:
                    case 6:
                        break;
                    default:
                        break;

                }


        FootPay footPay=new FootPay();
        footPay.setMessage(messageBean);
        footPay.setTitle(list);
        Gson gson=new Gson();

        String json = gson.toJson(footPay);

        JSONObject jsonObject = null;
        try {
             jsonObject=new JSONObject(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ApiService.GET_SERVICE(Api.FootBall_Api.Payment, this, jsonObject, new OnRequestDataListener() {
            @Override
            public void requestSuccess(int code, JSONObject data) {
                if(code==0){
                    // 发广播
                    Intent intent = new Intent();
                    intent.setAction(Contacts.INTENT_EXTRA_LOGIN_SUCESS);
                    sendBroadcast(intent);

                    ToastUtils.showToast("下单成功");
                    finish();
                }else {
                    Intent intent=new Intent(FootAllBetActivity.this,PayActivity.class);
                    if(type<=2){
                        intent.putExtra("information","精彩足球胜平负");
                    }else {
                        intent.putExtra("information","精彩足球让胜平负");
                    }
                    try {
                        intent.putExtra("money",data.getJSONObject("data").getString(Contacts.Order.MONEY));
                        intent.putExtra(Contacts.Order.ORDERID,data.getJSONObject("data").getString(Contacts.Order.ORDERID));
                        startActivityForResult(intent,Contacts.REQUEST_CODE_TO_PAY);
                        ToastUtils.showToast(data.getString("error_message"));
                    } catch (JSONException e) {

                    }

                }
            }

            @Override
            public void requestFailure(int code, String msg) {
                ToastUtils.showToast(msg);
            }
        });
    }

    private void getSelectBet() {
        StringBuilder sb = new StringBuilder();

        for (MenuItem s : mSrceenList) {
            if (s.getIcon() == 1) {
                sb.append(s.getContent()).append(",");
            }
        }
        if (sb.length() == 0) {
            tvSelectBet.setText("投注方式(必选)");
        } else {
            String s = sb.toString();
            String substring = s.substring(0, s.length() - 1);
            tvSelectBet.setText(substring);
        }

    }

    @Override
    protected void setTitle() {
        tvTitle.setText(R.string.football_title);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK&&slideUp.isVisible()) {
            slideUp.hide();
            return true;
        }else {
            return super.onKeyDown(keyCode, event);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode==-1){
            switch (requestCode){
                case Contacts.REQUEST_CODE_TO_PAY:
                    finish();
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }



    @Override
    public void onBackPressed() {
        if(bean.size()!=0){
            ClearDialogFragment quitDialogFragment=ClearDialogFragment.newInstance(2);
            quitDialogFragment.show(getSupportFragmentManager(),"footAll");
        }else {
            finish();
        }
    }

    @Override
    public void sure(int type) {
        if(type==1){
            mAdapter.getData().clear();
            mAdapter.notifyDataSetChanged();
            if(mSrceenList!=null){
                mSrceenList.clear();
                mBottomTRMenuAdapter.getData().clear();
                mBottomTRMenuAdapter.notifyDataSetChanged();
            }
            if(type%2==0){
                //过关
                upDate();
            }else {
                //单关
                initOnePass();
            }
            emptyLayout.setVisibility(View.VISIBLE);
            mAdapter.removeFooterView(view);
        }else {
            finish();
        }
    }
}
