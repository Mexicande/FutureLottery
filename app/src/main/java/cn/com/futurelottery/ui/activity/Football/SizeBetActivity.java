package cn.com.futurelottery.ui.activity.Football;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.support.v7.widget.Toolbar;
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
import com.mancj.slideup.SlideUp;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.futurelottery.R;
import cn.com.futurelottery.base.BaseActivity;
import cn.com.futurelottery.model.FootBallList;
import cn.com.futurelottery.model.ScoreList;
import cn.com.futurelottery.ui.adapter.football.FootChooseScoreAdapter;
import cn.com.futurelottery.ui.adapter.football.FootChooseSizeAdapter;
import cn.com.futurelottery.ui.adapter.football.FootChooseWinAdapter;
import cn.com.futurelottery.utils.CommonUtil;
import cn.com.futurelottery.utils.LogUtils;
import cn.com.futurelottery.utils.MenuDecoration;
import cn.com.futurelottery.utils.ToastUtils;
import cn.com.futurelottery.view.topRightMenu.MenuItem;
import cn.com.futurelottery.view.topRightMenu.TRMenuAdapter;


public class SizeBetActivity extends BaseActivity {

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


    @Override
    public int getLayoutResource() {
        return R.layout.activity_size_bet;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = getIntent().getIntExtra("type", 0);
        initView();
        if(type==7){
            //过关
            initAllPass();
        }else {
            //单关
            initOnePass();
        }
        setListener();
    }

    private void initView() {
        mSizeBeanList = (List<FootBallList.DataBean.MatchBean>) getIntent().getSerializableExtra("bean");
        mSizeAdapter = new FootChooseSizeAdapter(mSizeBeanList);
        chooseRecycler.setLayoutManager(new LinearLayoutManager(this));
        chooseRecycler.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        ((SimpleItemAnimator) chooseRecycler.getItemAnimator()).setSupportsChangeAnimations(false);
        chooseRecycler.setAdapter(mSizeAdapter);
        View view = getLayoutInflater().inflate(R.layout.payment_rv_footor, null);
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
                if (len.startsWith("0")) {
                    String substring = len.substring(1, len.length() - 1);
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
                    return;
                } else if (Integer.valueOf(s.toString()) > 50) {
                    slideMultiple.setText(String.valueOf(50));
                    return;
                }

                if (Integer.valueOf(s.toString()) < 1) {
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
                MenuItem item = mSrceenList.get(position);
                if (item.getIcon() == 0) {
                    item.setIcon(1);
                } else {
                    item.setIcon(0);
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

    /**
     * 刷新数据
     */
    private void upDate() {
        int bet = Integer.parseInt(edMultiple.getText().toString());

        //单关预计奖金
        double oneMinMoney=0;
        double oneMaxMoney=0;

        //过关预计奖金
        double allMinMoney=0;
        double allMaxMoneyy=0;

        //注数
        int oneCount=0;
        int allCount=0;
        for(int i=0;i<mSrceenList.size();i++){
            if(i==0){
                if(mSrceenList.get(0).getIcon()==1){
                    oneCount = OnePass();
                    //单关最大预计奖金
                    oneMaxMoney = getOneMax()*2*bet;
                    //单关最小预计奖金
                    oneMinMoney = getOneMoney()*2*bet;

                }
            }else {
                if (mSrceenList.get(i).getIcon() == 1) {
                    String content = mSrceenList.get(i).getContent();
                    String substring = content.substring(0, 1);
                    Integer integer = Integer.valueOf(substring);
                    allCount += nuBet(integer);
                }
                //过关最小预计奖金
                allMinMoney = updateMinOddsAll()*2*bet;
                //过关最大预计奖金
                oneMaxMoney = updateMaxOddsAll()*2*bet;
            }

        }
        if(mSrceenList.size()==0){
            //单关最大预计奖金
            oneMaxMoney = getOneMax()*2*bet;
            //单关最小预计奖金
            oneMinMoney = getOneMoney()*2*bet;
        }
        double min=0;
        if(allMinMoney==0||oneMinMoney==0){
            if(oneMinMoney<=allMinMoney){
                min=allMinMoney;
            }
        }else {
            if(oneMinMoney<=allMinMoney){
                min=oneMinMoney;
            }
        }
        double max=oneMaxMoney+allMaxMoneyy;
        DecimalFormat decimalFormat =new DecimalFormat("#.00");
        String strMin = decimalFormat.format(min);
        String strMax = decimalFormat.format(max);
        if(min!=0&&max!=0&&min!=max){
            predictMoney.setText(strMin+"~"+strMax);
        }else if(min!=0&&max!=0&&min==max){
            predictMoney.setText(strMin+"");
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
        for (FootBallList.DataBean.MatchBean s : ScoreBeanList) {
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
        for(FootBallList.DataBean.MatchBean s:mSizeBeanList){

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
        ArrayList<String>mlist=new ArrayList<>();
        for (ScoreList.DataBean.MatchBean s : ScoreBeanList) {
            List<List<ScoreList.DataBean.MatchBean.OddsBean>> odds = s.getOdds();

            for(int i=0;i<odds.size();i++){
                List<ScoreList.DataBean.MatchBean.OddsBean> oddsBeans = odds.get(i);

                for(int k=0;k<oddsBeans.size();k++){
                    if(oddsBeans.get(k).getType()==1){
                        mlist.add(oddsBeans.get(k).getOdds());
                    }
                }

            }
        }
        if(mlist.size()!=0){
            int i = Integer.parseInt(edMultiple.getText().toString());
            bottomResultTvBet.setText(i+"倍");
            String minStr = Collections.min(mlist);
            min = Double.parseDouble(minStr);

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
            ArrayList<String>maxlist=new ArrayList<>();
            for(int i=0;i<odds.size();i++){
                List<ScoreList.DataBean.MatchBean.OddsBean> oddsBeans = odds.get(i);
                for(int k=0;k<oddsBeans.size();k++){
                    if(oddsBeans.get(k).getType()==1){
                        maxlist.add(oddsBeans.get(k).getOdds());
                    }
                }

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
        ToastUtils.showToast("mArrays=="+mArrays.toString()+",nu=="+nu);
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
        LogUtils.i("m="+m+"  mlist=="+mList.toString());
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
    private double updateMinOddsAll() {
        double min;
        ArrayList<String> list = new ArrayList<>();
        for (ScoreList.DataBean.MatchBean s : ScoreBeanList) {
            List<List<ScoreList.DataBean.MatchBean.OddsBean>> odds = s.getOdds();
            List<ScoreList.DataBean.MatchBean.OddsBean> oddsOne = odds.get(0);
            ArrayList<String>maxlist=new ArrayList<>();
            for(ScoreList.DataBean.MatchBean.OddsBean max:oddsOne){
                if(max.getType()==1){
                    maxlist.add(max.getOdds());
                }
            }
            List<ScoreList.DataBean.MatchBean.OddsBean> oddsTwo = odds.get(1);
            for(ScoreList.DataBean.MatchBean.OddsBean max:oddsTwo){
                if(max.getType()==1){
                    maxlist.add(max.getOdds());
                }
            }
            List<ScoreList.DataBean.MatchBean.OddsBean> oddsThree = odds.get(2);
            for(ScoreList.DataBean.MatchBean.OddsBean max:oddsThree){
                if(max.getType()==1){
                    maxlist.add(max.getOdds());
                }
            }


            if(maxlist.size()!=0){
                String minStr = Collections.min(maxlist);
                list.add(minStr);
            }

        }
        Collections.sort(list);
        min = Double.parseDouble(list.get(0)) * Double.parseDouble(list.get(1));
        return min;
    }
    /**
     * 多注最大奖金
     */
    private Double updateMaxOddsAll() {
        ArrayList<Double> mlist = new ArrayList<>();
        for (ScoreList.DataBean.MatchBean s : ScoreBeanList) {
            List<List<ScoreList.DataBean.MatchBean.OddsBean>> odds = s.getOdds();

            List<ScoreList.DataBean.MatchBean.OddsBean> oddsOne = odds.get(0);
            ArrayList<String>maxlist=new ArrayList<>();
            for(ScoreList.DataBean.MatchBean.OddsBean max:oddsOne){
                if(max.getType()==1){
                    maxlist.add(max.getOdds());
                }
            }
            List<ScoreList.DataBean.MatchBean.OddsBean> oddsTwo = odds.get(1);
            for(ScoreList.DataBean.MatchBean.OddsBean max:oddsTwo){
                if(max.getType()==1){
                    maxlist.add(max.getOdds());
                }
            }
            List<ScoreList.DataBean.MatchBean.OddsBean> oddsThree = odds.get(2);
            for(ScoreList.DataBean.MatchBean.OddsBean max:oddsThree){
                if(max.getType()==1){
                    maxlist.add(max.getOdds());
                }
            }

            if(maxlist.size()!=0){
                String maxStr = Collections.max(maxlist);
                double max = Double.parseDouble(maxStr);
                mlist.add(max);
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
                mSizeAdapter.getData().clear();
                mSizeAdapter.notifyDataSetChanged();
                ScoreBeanList.clear();
                upDate();
                emptyLayout.setVisibility(View.VISIBLE);
                break;
            case R.id.layout_bet:
                if(mSizeAdapter.getData().size()==1){

                }else if(mSizeAdapter.getData().size()==0){
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


}
