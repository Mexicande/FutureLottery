package cn.com.futurelottery.ui.activity.Football;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.futurelottery.R;
import cn.com.futurelottery.base.Api;
import cn.com.futurelottery.base.ApiService;
import cn.com.futurelottery.base.BaseActivity;
import cn.com.futurelottery.inter.DialogListener;
import cn.com.futurelottery.inter.OnRequestDataListener;
import cn.com.futurelottery.model.FootBallList;
import cn.com.futurelottery.model.FootPay;
import cn.com.futurelottery.model.ScoreList;
import cn.com.futurelottery.ui.adapter.football.FootChooseScoreAdapter;
import cn.com.futurelottery.ui.adapter.football.FootChooseWinAdapter;
import cn.com.futurelottery.ui.dialog.PayMentFragment;
import cn.com.futurelottery.ui.dialog.ScoreDialogFragment;
import cn.com.futurelottery.utils.CommonUtil;
import cn.com.futurelottery.utils.LogUtils;
import cn.com.futurelottery.utils.MenuDecoration;
import cn.com.futurelottery.utils.ToastUtils;
import cn.com.futurelottery.view.topRightMenu.MenuItem;
import cn.com.futurelottery.view.topRightMenu.TRMenuAdapter;

/**
 * @author apple
 * 比分计算
 */
public class ScoreBetActivity extends BaseActivity implements DialogListener{

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
    private FootChooseScoreAdapter  mScoreAdapter;
    private List<ScoreList.DataBean.MatchBean> ScoreBeanList;
    private List<MenuItem> mSrceenList;
    private int type = 0;
    private double oneMinMoney=0;
    private double oneMaxMoney=0;
    private View view;
    @Override
    public int getLayoutResource() {
        return R.layout.activity_score_bet;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = getIntent().getIntExtra("type", 0);
        initView();

        initAllPass();

        setListener();

    }

    /**
     * 删除数据
     */
    private void delectDate() {

        mBottomTRMenuAdapter.notifyDataSetChanged();
        if(mScoreAdapter.getData().size()<mSrceenList.size()){
            mBottomTRMenuAdapter.remove(mSrceenList.size()-1);
        }
        if(mScoreAdapter.getData().size()>1){
            mSrceenList.get(mSrceenList.size()-1).setIcon(1);
            tvSelectBet.setText( mSrceenList.get(mSrceenList.size()-1).getContent());
        }else if(mScoreAdapter.getData().size()==1){
            mSrceenList.get(mSrceenList.size()-1).setIcon(1);
            tvSelectBet.setText(mSrceenList.get(0).getContent());
            ivArrow.setVisibility(View.GONE);

            //单关
        }else {
            tvSelectBet.setText("投注方式(必选)");
            chooseRecycler.setVisibility(View.GONE);
            emptyLayout.setVisibility(View.VISIBLE);
            mScoreAdapter.removeFooterView(view);

        }
        upDate();
    }

    /**
     * 刷新数据
     */
    private void upDate() {
        int bet = Integer.parseInt(edMultiple.getText().toString());

        //单关预计奖金
         oneMinMoney=0;
         oneMaxMoney=0;


        //注数
        int oneCount=0;
        int allCount=0;
        for(int i=0;i<mSrceenList.size();i++){
            if(i==0){
                if(mBottomTRMenuAdapter.getData().get(i).getIcon()==1){
                    oneCount = OnePass();
                    //单关最大预计奖金
                    oneMaxMoney = getOneMax()*2*bet;
                    //单关最小预计奖金
                    oneMinMoney = getOneMoney()*2*bet;
                }
            }else {
                if (mBottomTRMenuAdapter.getData().get(i).getIcon() == 1) {
                    String content = mSrceenList.get(i).getContent();
                    String substring = content.substring(0, 1);
                    Integer integer = Integer.valueOf(substring);
                    allCount += nuBet(integer);
                }
            }

        }

        updateMinOddsAll();

        if(mBottomTRMenuAdapter.getData().size()==0){
            //单关最大预计奖金
            oneMaxMoney = getOneMax()*2*bet;
            //单关最小预计奖金
            oneMinMoney = getOneMoney()*2*bet;
            if(oneMaxMoney==oneMinMoney){
                predictMoney.setText(oneMinMoney+"");
            }else {
                predictMoney.setText(oneMinMoney+"~"+oneMaxMoney);
            }
        }


        bottomResultCountTv.setText(String.valueOf((allCount+oneCount)));
        bottomResultMoneyTv.setText((allCount+oneCount) * Integer.valueOf(bet) * 2 + "");

    }



    /**
     * 单关注数---钱数
     */
    private int  OnePass() {
        String bet = edMultiple.getText().toString();
        int count = 0;
        for (ScoreList.DataBean.MatchBean s : ScoreBeanList) {
            List<List<ScoreList.DataBean.MatchBean.OddsBean>> odds = s.getOdds();
            for(int i=0;i<odds.size();i++){
                List<ScoreList.DataBean.MatchBean.OddsBean> oddsBeans = odds.get(i);
                for(int k=0;k<oddsBeans.size();k++){
                    if(oddsBeans.get(k).getType()==1){
                        count++;
                    }
                }

            }
        }
        bottomResultCountTv.setText(String.valueOf(count));
        bottomResultMoneyTv.setText(count * Integer.valueOf(bet) * 2 + "");
        //单关奖金
        getOneMoney();
        return count;
    }

    /**
     *
     * 单关奖金
     *
     */
    private double getOneMoney() {
        double min=0;
        ArrayList<Double>mlist=new ArrayList<>();
        for (ScoreList.DataBean.MatchBean s : ScoreBeanList) {
            List<List<ScoreList.DataBean.MatchBean.OddsBean>> odds = s.getOdds();
            for(int i=0;i<odds.size();i++){
                List<ScoreList.DataBean.MatchBean.OddsBean> oddsBeans = odds.get(i);
                for(int k=0;k<oddsBeans.size();k++){
                    if(oddsBeans.get(k).getType()==1){
                        mlist.add(Double.parseDouble(oddsBeans.get(k).getOdds()));
                    }
                }

            }
        }
        if(mlist.size()!=0){
            int i = Integer.parseInt(edMultiple.getText().toString());
            bottomResultTvBet.setText(i+"倍");
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
        for (ScoreList.DataBean.MatchBean s : ScoreBeanList) {
            List<List<ScoreList.DataBean.MatchBean.OddsBean>> odds = s.getOdds();
            ArrayList<Double>maxlist=new ArrayList<>();
            for(int i=0;i<odds.size();i++){
                List<ScoreList.DataBean.MatchBean.OddsBean> oddsBeans = odds.get(i);
                maxlist.clear();
                for(int k=0;k<oddsBeans.size();k++){
                    if(oddsBeans.get(k).getType()==1){
                        maxlist.add(Double.parseDouble(oddsBeans.get(k).getOdds()));
                    }
                }

            }
            if(maxlist.size()!=0){
                double  maxStr = Collections.max(maxlist);
                money+=maxStr;
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
        for (ScoreList.DataBean.MatchBean s : ScoreBeanList) {
            number=0;
            List<List<ScoreList.DataBean.MatchBean.OddsBean>> odds = s.getOdds();
            for(int i=0;i<odds.size();i++){
                List<ScoreList.DataBean.MatchBean.OddsBean> oddsBeans = odds.get(i);
                for(int k=0;k<oddsBeans.size();k++){
                    if(oddsBeans.get(k).getType()==1){
                        number++;
                    }
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

    private void initView() {
        ScoreBeanList = (List<ScoreList.DataBean.MatchBean>) getIntent().getSerializableExtra("bean");

        mScoreAdapter = new FootChooseScoreAdapter(ScoreBeanList);
        chooseRecycler.setLayoutManager(new LinearLayoutManager(this));
        chooseRecycler.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        ((SimpleItemAnimator) chooseRecycler.getItemAnimator()).setSupportsChangeAnimations(false);
        chooseRecycler.setAdapter(mScoreAdapter);
         view = getLayoutInflater().inflate(R.layout.payment_rv_footor, null);
        mScoreAdapter.addFooterView(view);

        slideUp = new SlideUp.Builder(slideView)
                .withStartGravity(Gravity.BOTTOM)
                .withLoggingEnabled(true)
                .withAutoSlideDuration(1)
                .withStartState(SlideUp.State.HIDDEN)
                .build();
    }

    private void setListener() {
        mScoreAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()){
                    case R.id.iv_delete:
                        mScoreAdapter.remove(position);
                        delectDate();
                        break;
                    case R.id.tv_score:
                        ScoreList.DataBean.MatchBean item = mScoreAdapter.getItem(position);
                        PayMentFragment adialogFragment = PayMentFragment.newInstance(item,position);
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
                if(len.startsWith("0")){
                    String substring = len.substring(1, len.length()-1);
                    edMultiple.setText(substring);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s)) {
                    edMultiple.setText("1");
                    return;
                } else if (Integer.valueOf(s.toString()) > 50) {
                    edMultiple.setText(String.valueOf(50));
                    return;
                }
                if (Integer.valueOf(s.toString())<1){
                    edMultiple.setText("1");
                    return;
                }
                upDate();
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


    private void initAllPass() {

        initSlideLabel();
        if(ScoreBeanList.size()==0){
            tvSelectBet.setText("单关");
        }else {
            //mSrceenList.get(0).setIcon(1);
            tvSelectBet.setText("投注方式(必选)");
            ivArrow.setVisibility(View.VISIBLE);
        }
        trmRecyclerview.setLayoutManager(new GridLayoutManager(this, 4));
        trmRecyclerview.addItemDecoration(new MenuDecoration(CommonUtil.dip2px(10), 4));
        mBottomTRMenuAdapter = new TRMenuAdapter(R.layout.football_menu_item, mSrceenList);
        trmRecyclerview.setAdapter(mBottomTRMenuAdapter);
        mBottomTRMenuAdapter.getData().get(0).setIcon(1);
        upDate();
        slideMultiple.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String len = s.toString();
                if(len.startsWith("0")){
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
                upDate();
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
                upDate();

            }
        });

    }

    /**
     *
     * 串数标签
     */
    private void initSlideLabel(){
        int size = ScoreBeanList.size();
        mSrceenList = new ArrayList<>();
        MenuItem itemOne = new MenuItem();
        itemOne.setContent("单关");
        mSrceenList.add(itemOne);
        for (int i = 2; i <= size; i++) {
            if (i <= 8) {
                MenuItem item = new MenuItem();
                item.setContent(i + "串" + 1);
                mSrceenList.add(item);
            } else {
                return;
            }
        }
    }



    /**
     * 多注最小奖金
     */
    private void updateMinOddsAll() {
        double min;
        int bet = Integer.parseInt(edMultiple.getText().toString());

        ArrayList<Double> list = new ArrayList<>();
        for (ScoreList.DataBean.MatchBean s : ScoreBeanList) {
            List<List<ScoreList.DataBean.MatchBean.OddsBean>> odds = s.getOdds();
            List<ScoreList.DataBean.MatchBean.OddsBean> oddsOne = odds.get(0);
            ArrayList<Double>maxlist=new ArrayList<>();
            for(ScoreList.DataBean.MatchBean.OddsBean max:oddsOne){
                if(max.getType()==1){
                    maxlist.add(Double.parseDouble(max.getOdds()));
                }
            }
            List<ScoreList.DataBean.MatchBean.OddsBean> oddsTwo = odds.get(1);
            for(ScoreList.DataBean.MatchBean.OddsBean max:oddsTwo){
                if(max.getType()==1){
                    maxlist.add(Double.parseDouble(max.getOdds()));
                }
            }
            List<ScoreList.DataBean.MatchBean.OddsBean> oddsThree = odds.get(2);
            for(ScoreList.DataBean.MatchBean.OddsBean max:oddsThree){
                if(max.getType()==1){
                    maxlist.add(Double.parseDouble(max.getOdds()));
                }
            }


            if(maxlist.size()!=0){
                double minStr = Collections.min(maxlist);
                list.add(minStr);
            }

        }
                Collections.sort(list);

        ArrayList<Integer>mList=new ArrayList<>();
        for (int j = 0; j < mSrceenList.size(); j++) {
            if (mSrceenList.get(j).getIcon() == 1) {
                if(j==0){
                    mList.add(1);
                }else {
                    String content = mSrceenList.get(j).getContent();
                    String substring = content.substring(0, 1);
                    Integer integer = Integer.valueOf(substring);
                    mList.add(integer);
                }
            }
        }
        double money=1;
        Integer integer = mList.get(0);
        ToastUtils.showToast(""+integer);
        for(int k=0;k<integer;k++){
            money=money*list.get(k);
        }
        min = money*2*bet;


        double v = updateMaxOddsAll() * 2 * bet;
        double minOne=0;

        DecimalFormat decimalFormat =new DecimalFormat("#.00");

        if(oneMinMoney!=0){
            minOne=oneMinMoney;
        }else {
            minOne=min;
        }
        String strMin = decimalFormat.format(minOne);
        String strMax = decimalFormat.format(v);
        if(minOne!=0&&v!=minOne){
            predictMoney.setText(strMin+"~"+strMax);
        }else {
            predictMoney.setText(strMax+"");
        }


    }
    /**
     * 多注最大奖金
     */
    private Double updateMaxOddsAll() {
        ArrayList<Double> mlist = new ArrayList<>();
        for (ScoreList.DataBean.MatchBean s : ScoreBeanList) {
            List<List<ScoreList.DataBean.MatchBean.OddsBean>> odds = s.getOdds();

            List<ScoreList.DataBean.MatchBean.OddsBean> oddsOne = odds.get(0);
            ArrayList<Double>maxlist=new ArrayList<>();
            for(ScoreList.DataBean.MatchBean.OddsBean max:oddsOne){
                if(max.getType()==1){
                    maxlist.add(Double.parseDouble(max.getOdds()));
                }
            }
            List<ScoreList.DataBean.MatchBean.OddsBean> oddsTwo = odds.get(1);
            for(ScoreList.DataBean.MatchBean.OddsBean max:oddsTwo){
                if(max.getType()==1){
                    maxlist.add(Double.parseDouble(max.getOdds()));
                }
            }
            List<ScoreList.DataBean.MatchBean.OddsBean> oddsThree = odds.get(2);
            for(ScoreList.DataBean.MatchBean.OddsBean max:oddsThree){
                if(max.getType()==1){
                    maxlist.add(Double.parseDouble(max.getOdds()));
                }
            }

            if(maxlist.size()!=0){
                double maxStr = Collections.max(maxlist);
                mlist.add(maxStr);
            }

        }


        double money=0;

        if(mScoreAdapter.getData().size()==1||mSrceenList.get(0).getIcon() == 1){
            money= setMaX(mlist,1);
        }
        for (int i = 1; i < mSrceenList.size(); i++) {
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
                mScoreAdapter.getData().clear();
                mScoreAdapter.notifyDataSetChanged();
                ScoreBeanList.clear();
                upDate();
                emptyLayout.setVisibility(View.VISIBLE);
                mScoreAdapter.removeFooterView(view);
                break;
            case R.id.layout_bet:
                if(mScoreAdapter.getData().size()==1){

                }else if(mScoreAdapter.getData().size()==0){
                    ToastUtils.showToast("请选择投注方式");
                }else {
                    slideUp.show();
                }
                break;
            case R.id.layout_slide_bet:
            case R.id.layoutGo:
                slideUp.hide();
                getSelectBet();
                break;
            case R.id.bottom_result_btn:
                if(slideUp.isVisible()){
                    slideUp.hide();
                }

                paySubmit();
                break;
            default:
                break;
        }
    }

    /**
     * 提交订单
     */
    private void paySubmit() {
        String text = bottomResultCountTv.getText().toString();
        if(!"0".equals(text)){
            if (type%2!=0) {
                payMent(text );

            } else {

            }
        }else {
            if (type%2!=0) {
                slideUp.show();
                ToastUtils.showToast("请选择投注方式");

            } else {


            }

        }
    }

    private void payMent(String number) {
        ArrayList<FootPay.TitleBean>list=new ArrayList<>();
        /*for(int i=0;i<bean.size();i++){
            FootBallList.DataBean.MatchBean matchBean = bean.get(i);
            LogUtils.i("matchBean="+matchBean.toString());
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
        }*/
        StringBuilder sb=new StringBuilder();
        for(MenuItem s:mSrceenList){
            if(s.getIcon()==1){
                sb.append(s.getContent()).append(",");
            }
        }
        FootPay.MessageBean messageBean=new FootPay.MessageBean();

        String string = sb.toString();
        String substring = string.substring(0, string.length() - 1);
        //钱数
        String money = bottomResultMoneyTv.getText().toString();
        //倍数
        String bet = edMultiple.getText().toString();
        messageBean.setMoney(Integer.parseInt(money));
        messageBean.setMultiple(Integer.parseInt(bet));
        messageBean.setNumber(Integer.parseInt(number));
        messageBean.setPlay_rules(Api.FOOTBALL.FT002);
        messageBean.setStrand(substring);



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

            }

            @Override
            public void requestFailure(int code, String msg) {

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
    public void onDefeateComplete(int index, ScoreList.DataBean.MatchBean matchBean) {
        int number=0;
        List<List<ScoreList.DataBean.MatchBean.OddsBean>> odds = matchBean.getOdds();
        for(int i=0;i<odds.size();i++){
            List<ScoreList.DataBean.MatchBean.OddsBean> oddsBeans = odds.get(i);
            for(int k=0;k<oddsBeans.size();k++){
                if(oddsBeans.get(k).getType()==1){
                    number++;
                }
            }
        }
        if(number==0){
            mScoreAdapter.remove(index);
            delectDate();
        }else {
            mScoreAdapter.setData(index,matchBean);
            mScoreAdapter.notifyItemChanged(index);
            upDate();
        }
    }
}
