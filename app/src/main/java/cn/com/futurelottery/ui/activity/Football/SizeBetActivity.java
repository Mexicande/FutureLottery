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
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.futurelottery.R;
import cn.com.futurelottery.base.Api;
import cn.com.futurelottery.base.ApiService;
import cn.com.futurelottery.base.BaseActivity;
import cn.com.futurelottery.base.Contacts;
import cn.com.futurelottery.listener.ClearDialogListener;
import cn.com.futurelottery.listener.OnRequestDataListener;
import cn.com.futurelottery.listener.SizeDialogListener;
import cn.com.futurelottery.model.FootBallList;
import cn.com.futurelottery.model.FootPay;
import cn.com.futurelottery.ui.activity.LoginActivity;
import cn.com.futurelottery.ui.activity.PayActivity;
import cn.com.futurelottery.ui.adapter.football.FootChooseSizeAdapter;
import cn.com.futurelottery.ui.dialog.ClearDialogFragment;
import cn.com.futurelottery.ui.dialog.SizeBetDialogFragment;
import cn.com.futurelottery.utils.ActivityUtils;
import cn.com.futurelottery.utils.CommonUtil;
import cn.com.futurelottery.utils.MenuDecoration;
import cn.com.futurelottery.utils.SPUtils;
import cn.com.futurelottery.utils.ToastUtils;
import cn.com.futurelottery.view.topRightMenu.MenuItem;
import cn.com.futurelottery.view.topRightMenu.TRMenuAdapter;


public class SizeBetActivity extends BaseActivity implements SizeDialogListener,ClearDialogListener{

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
    private FootChooseSizeAdapter mSizeAdapter;
    private List<FootBallList.DataBean.MatchBean> mSizeBeanList;
    private List<MenuItem> mSrceenList;
    private int type = 0;
    private  View view;

    @Override
    public int getLayoutResource() {
        return R.layout.activity_size_bet;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = getIntent().getIntExtra("type", 0);
        initView();
        if(type%2!=0){
            //过关
            initAllPass();
        }else {
            //单关
            initOnePass();
        }
        setListener();
    }

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

    private void initView() {
        mSizeBeanList = (List<FootBallList.DataBean.MatchBean>) getIntent().getSerializableExtra("bean");
        mSizeAdapter = new FootChooseSizeAdapter(mSizeBeanList);
        chooseRecycler.setLayoutManager(new LinearLayoutManager(this));
        chooseRecycler.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        ((SimpleItemAnimator) chooseRecycler.getItemAnimator()).setSupportsChangeAnimations(false);
        chooseRecycler.setAdapter(mSizeAdapter);
         view = getLayoutInflater().inflate(R.layout.payment_rv_footor, null);
        mSizeAdapter.addFooterView(view);

        slideUp = new SlideUp.Builder(slideView)
                .withStartGravity(Gravity.BOTTOM)
                .withLoggingEnabled(true)
                .withAutoSlideDuration(1)
                .withStartState(SlideUp.State.HIDDEN)
                .build();

    }
    private void initAllPass() {
        initSlideLabel();
        trmRecyclerview.setLayoutManager(new GridLayoutManager(this, 4));
        trmRecyclerview.addItemDecoration(new MenuDecoration(CommonUtil.dip2px(10), 4));
        mBottomTRMenuAdapter = new TRMenuAdapter(R.layout.football_menu_item, mSrceenList);
        trmRecyclerview.setAdapter(mBottomTRMenuAdapter);
        slideMultiple.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String len = s.toString();
                if (len.startsWith("0")&&len.length()>1) {
                    String substring = len.substring(1, len.length() - 1);
                    slideMultiple.setText(substring);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s)) {
                    return;
                }

                int amount = Integer.valueOf(s.toString());

                if (amount > 50) {
                    slideMultiple.setText("50");
                    return;
                }
                if (amount<1){
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
                MenuItem item = mSrceenList.get(position);
                if (item.getIcon() == 0) {
                    item.setIcon(1);
                } else {
                    item.setIcon(0);
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
        int size = mSizeBeanList.size();
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
        mSizeAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()){
                    case R.id.iv_delete:
                        mSizeAdapter.remove(position);
                        delectDate();
                        break;
                    case R.id.tv_score:
                        FootBallList.DataBean.MatchBean item = mSizeAdapter.getItem(position);
                        SizeBetDialogFragment adialogFragment = SizeBetDialogFragment.newInstance(item,position);
                        adialogFragment.show(getSupportFragmentManager(), "timePicker");
                        break;
                    default:
                        break;
                }
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
                }

                int amount = Integer.valueOf(s.toString());

                if (amount > 50) {
                    edMultiple.setText("50");
                    return;
                }
                if (amount<1){
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
            if(mSizeAdapter.getData().size()>0){
                if(mSizeAdapter.getData().size()==mBottomTRMenuAdapter.getData().size()){
                    mBottomTRMenuAdapter.remove(mBottomTRMenuAdapter.getData().size()-1);
                    mBottomTRMenuAdapter.notifyDataSetChanged();
                }
                if(mSizeAdapter.getData().size()==0){
                    tvSelectBet.setText("投注方式(必选)");
                    chooseRecycler.setVisibility(View.GONE);
                    emptyLayout.setVisibility(View.VISIBLE);
                    mSizeAdapter.removeFooterView(view);
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
    private void  OnePass() {
        String bet = edMultiple.getText().toString();
        int count = 0;

        for(FootBallList.DataBean.MatchBean matchBean:mSizeBeanList){
            List<FootBallList.DataBean.MatchBean.OddsBean> odds = matchBean.getOdds();
                for(int i=0;i<odds.size();i++){
                    if(odds.get(i).getType()==1){
                        count++;
                    }
                }
        }

        bottomResultCountTv.setText(String.valueOf(count));
        bottomResultMoneyTv.setText(count * Integer.valueOf(bet) * 2 + "");
        //单关奖金
        getOneMoney();
    }

    /**
     *
     * 单关奖金
     *
     */
    private double getOneMoney() {
        double min=0;
        ArrayList<Double>mlist=new ArrayList<>();
        for(FootBallList.DataBean.MatchBean matchBean:mSizeBeanList){
            List<FootBallList.DataBean.MatchBean.OddsBean> odds = matchBean.getOdds();
            for(int i=0;i<odds.size();i++){
                if(odds.get(i).getType()==1){
                    mlist.add(Double.parseDouble(odds.get(i).getOdds()));
                }

            }

        }

        if(mlist.size()!=0){
            int i = Integer.parseInt(edMultiple.getText().toString());
            min= Collections.min(mlist);

        }
        return min;
    }

    /**
     * 单关最大奖金
     * @return
     */
    private double getOneMax() {
        double money=0;

        for(FootBallList.DataBean.MatchBean matchBean:mSizeBeanList){
            List<FootBallList.DataBean.MatchBean.OddsBean> odds = matchBean.getOdds();
            ArrayList<Double>mlist=new ArrayList<>();
            for(int i=0;i<odds.size();i++){
                if(odds.get(i).getType()==1){
                    mlist.add(Double.parseDouble(odds.get(i).getOdds()));
                }

            }
            if(mlist.size()!=0){
                double max  = Collections.max(mlist);
                money+=max;
            }
        }


        return money;
    }




    /**
     * 更新
     *
     * @param nu
     * @return
     */
    private Integer nuBet(int nu) {
        Integer integer = 0;
        int number=0;
        ArrayList<Integer> mArrays = new ArrayList<>();
        for(FootBallList.DataBean.MatchBean matchBean:mSizeBeanList){
            number=0;
            List<FootBallList.DataBean.MatchBean.OddsBean> odds = matchBean.getOdds();
            for(int i=0;i<odds.size();i++){
                if(odds.get(i).getType()==1){
                    number++;
                }

            }
            mArrays.add(number);
        }
        if (mArrays.size() != 0) {
            integer = countExpect(nu, mArrays);
        }

        return integer;
    }



    /**
     * 过关计算注数
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

    /**
     * 多注最小奖金
     */
    private void updateMinOddsAll() {
        double min;
        int bet = Integer.parseInt(edMultiple.getText().toString());
        ArrayList<Double> list = new ArrayList<>();
        list.clear();
        for (FootBallList.DataBean.MatchBean s : mSizeBeanList) {
            ArrayList<Double>maxlist=new ArrayList<>();
            List<FootBallList.DataBean.MatchBean.OddsBean> odds = s.getOdds();
            for(int i=0;i<odds.size();i++){
                if(odds.get(i).getType()==1){
                    maxlist.add(Double.parseDouble(odds.get(i).getOdds()));
                }
            }
            if(maxlist.size()!=0){
                Double minStr = Collections.min(maxlist);
                list.add(minStr);
            }

        }
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
            min = money*2*bet;
            double v = updateMaxOddsAll() * 2 * bet;

            DecimalFormat decimalFormat =new DecimalFormat("#.00");
            String strMin = decimalFormat.format(min);
            String strMax = decimalFormat.format(v);

            if(v!=min){
                predictMoney.setText(strMin+"~"+strMax);
            } else {
                predictMoney.setText(strMax+"");
            }
    }
    /**
     * 多注最大奖金
     */
    private Double updateMaxOddsAll() {

        ArrayList<Double> mlist = new ArrayList<>();
        for (FootBallList.DataBean.MatchBean s : mSizeBeanList) {
            List<FootBallList.DataBean.MatchBean.OddsBean> odds = s.getOdds();
            ArrayList<Double>maxlist=new ArrayList<>();
            for(int i=0;i<odds.size();i++){
                if(odds.get(i).getType()==1){
                    maxlist.add(Double.parseDouble(odds.get(i).getOdds()));
                }
            }
            if(maxlist.size()!=0){
                Double max = Collections.max(maxlist);
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
        return money;
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

    /**
     * 支付
     * @param
     */
    private void payMent(String str) {
        ArrayList<FootPay.TitleBean>list=new ArrayList<>();
        for(int i=0;i<mSizeBeanList.size();i++){
            FootBallList.DataBean.MatchBean matchBean = mSizeBeanList.get(i);
            List<FootBallList.DataBean.MatchBean.OddsBean> odds = matchBean.getOdds();

            FootPay.TitleBean titleBean=new FootPay.TitleBean();
            StringBuilder type=new StringBuilder();
            for(int j=0;j<odds.size();j++){
                if(odds.get(j).getType()==1){
                    if("7+".equals(odds.get(j).getName())){
                        type.append("7").append(",");
                    }else if("胜胜".equals(odds.get(j).getName())){
                        type.append("33").append(",");
                    }else if("胜负".equals(odds.get(j).getName())){
                        type.append("30").append(",");
                    }else if("负胜".equals(odds.get(j).getName())){
                        type.append("03").append(",");
                    }else if("负负".equals(odds.get(j).getName())){
                        type.append("00").append(",");
                    }else if("负平".equals(odds.get(j).getName())){
                        type.append("01").append(",");
                    }else if("平负".equals(odds.get(j).getName())){
                        type.append("10").append(",");
                    }else if("平胜".equals(odds.get(j).getName())){
                        type.append("13").append(",");
                    }else if("胜平".equals(odds.get(j).getName())){
                        type.append("31").append(",");
                    }else if("平平".equals(odds.get(j).getName())){
                        type.append("11").append(",");
                    }else {
                        type.append(odds.get(j).getName()).append(",");
                    }
                }
            }
            String s = type.toString();
            String substring = s.substring(0, s.length() - 1);
            titleBean.setType(substring);
            titleBean.setMatch(mSizeBeanList.get(i).getMatch_id());
            titleBean.setTeam(matchBean.getHomeTeam()+":"+matchBean.getAwayTeam());
            list.add(titleBean);
        }
        FootPay.MessageBean messageBean=new FootPay.MessageBean();

        if(mSrceenList!=null){
            if(mSrceenList.size()!=0){
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
        }

        //钱数
        String money = bottomResultMoneyTv.getText().toString();
        //倍数
        String bet = edMultiple.getText().toString();
        messageBean.setMoney(Integer.parseInt(money));
        messageBean.setMultiple(Integer.parseInt(bet));
        messageBean.setNumber(Integer.parseInt(str));
        switch (type){
            case 7:
                //队伍
                messageBean.setPlay_rules(Api.FOOTBALL.FT003);
                break;
            case 8:
                messageBean.setStrand("0");
                messageBean.setPlay_rules(Api.FOOTBALL.FT003);
                break;
            case 9:
                messageBean.setPlay_rules(Api.FOOTBALL.FT004);
                break;
            case 10:
                messageBean.setStrand("0");
                messageBean.setPlay_rules(Api.FOOTBALL.FT004);
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
                    ToastUtils.showToast(getString(R.string.checkout_success));
                    Intent intent = new Intent();
                    intent.setAction(Contacts.INTENT_EXTRA_LOGIN_SUCESS);
                    sendBroadcast(intent);
                    finish();
                }else {
                    Intent intent=new Intent(SizeBetActivity.this,PayActivity.class);
                    if(type<9){
                        intent.putExtra("information","精彩足球总进球");
                    }else {
                        intent.putExtra("information","精彩足球半全场");
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

    @Override
    public void onDefeateComplete(int index, FootBallList.DataBean.MatchBean matchBean) {
            int number=0;
        List<FootBallList.DataBean.MatchBean.OddsBean> odds = matchBean.getOdds();
            for(int i=0;i<odds.size();i++){
                for(int k=0;k<odds.size();k++){
                    if(odds.get(k).getType()==1){
                        number++;
                    }
                }
            }
            if(number==0){
                mSizeAdapter.remove(index);
                    delectDate();
            }else {
                mSizeAdapter.setData(index,matchBean);
                mSizeAdapter.notifyItemChanged(index);
                if(type%2==0){
                    //过关
                    upDate();
                }else {
                    //单关
                    initOnePass();
                }
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
        if(mSizeBeanList.size()!=0){
            ClearDialogFragment quitDialogFragment=ClearDialogFragment.newInstance(2);
            quitDialogFragment.show(getSupportFragmentManager(),"footAll");
        }else {
            finish();
        }
    }
    @Override
    public void sure(int type) {
        if(type==1){
            mSizeAdapter.getData().clear();
            mSizeAdapter.notifyDataSetChanged();
            mSizeBeanList.clear();
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
            mSizeAdapter.removeFooterView(view);
        }else {
            finish();
        }

    }
}
