package cn.com.futurelottery.ui.activity.Football;

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
import cn.com.futurelottery.inter.OnRequestDataListener;
import cn.com.futurelottery.model.FootBallList;
import cn.com.futurelottery.model.FootPay;
import cn.com.futurelottery.ui.adapter.football.FootChooseWinAdapter;
import cn.com.futurelottery.utils.CommonUtil;
import cn.com.futurelottery.utils.LogUtils;
import cn.com.futurelottery.utils.MenuDecoration;
import cn.com.futurelottery.utils.ToastUtils;
import cn.com.futurelottery.view.topRightMenu.MenuItem;
import cn.com.futurelottery.view.topRightMenu.TRMenuAdapter;

/**
 * @author apple
 */
public class FootAllBetActivity extends BaseActivity {

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

    /**
     * 单关
     */
    private void initOnePass() {
        upDate();

    }

    private void initAllPass() {
        ivArrow.setVisibility(View.VISIBLE);
        tvSelectBet.setText("投注方式(必选)");
        initSlideLabel();
        trmRecyclerview.setLayoutManager(new GridLayoutManager(this, 4));
        trmRecyclerview.addItemDecoration(new MenuDecoration(CommonUtil.dip2px(10), 4));
        mBottomTRMenuAdapter = new TRMenuAdapter(R.layout.football_menu_item, mSrceenList);
        trmRecyclerview.setAdapter(mBottomTRMenuAdapter);
        View view = getLayoutInflater().inflate(R.layout.payment_rv_footor, null);
        mBottomTRMenuAdapter.addFooterView(view);



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
   }
    private void setListener() {
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                FootBallList.DataBean.MatchBean matchBean = mAdapter.getData().get(position);
                switch (view.getId()) {
                    case R.id.iv_delete:
                        mAdapter.remove(position);
                        if (bean.size() == 0) {
                            if (type%2!=0) {
                                footAll.setText("请至少选择2场比赛");
                            } else  {
                                footAll.setText("请至少选择1场比赛");
                            }
                        }
                        upDate();
                        break;
                    case R.id.layout_home:
                        if (matchBean.getHomeType() == 0) {
                            matchBean.setHomeType(1);
                        } else {
                            matchBean.setHomeType(0);
                        }
                        upDate();
                        break;
                    case R.id.layout_away:
                        if (matchBean.getAwayType() == 0) {
                            matchBean.setAwayType(1);
                        } else {
                            matchBean.setAwayType(0);
                        }
                        upDate();

                        break;
                    case R.id.layout_vs:
                        if (matchBean.getVsType() == 0) {
                            matchBean.setVsType(1);
                        } else {
                            matchBean.setVsType(0);
                        }
                        upDate();
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
                if(len.startsWith("0")){
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


    private void upDate() {
        String bet = edMultiple.getText().toString();
        if(type%2!=0){

            int set=0;
            for(int i=0;i<bean.size();i++){
                if(bean.get(i).getAwayType()==1||bean.get(i).getVsType()==1||bean.get(i).getHomeType()==1){
                    set++;
                }
            }
            if(mSrceenList.size()!=set-1){
                MenuItem item = new MenuItem();
                item.setContent(set+ "串" + 1);
                mSrceenList.add(item);
            }
            if (set - mSrceenList.size() <=1) {
                int i1 = mSrceenList.size() + 1 - set;
                for (int i = 0; i <i1-1; i++) {
                    mSrceenList.remove(mSrceenList.size()-1);
                }
            }
            if(mSrceenList.size()==0){
                tvSelectBet.setText("投注方式(必选)");
            }
            if(set==2){
                mSrceenList.get(0).setIcon(1);
                tvSelectBet.setText(mSrceenList.get(0).getContent());
            }
            mBottomTRMenuAdapter.notifyDataSetChanged();

            int number = 0;
            for (int i = 0; i < mSrceenList.size(); i++) {
                if (mSrceenList.get(i).getIcon() == 1) {
                    String content = mSrceenList.get(i).getContent();
                    String substring = content.substring(0, 1);
                    Integer integer = Integer.valueOf(substring);
                    number += nuBet(integer);
                }
            }
            updateMinOddsAll();
            bottomResultCountTv.setText(String.valueOf(number));

            bottomResultMoneyTv.setText(number * Integer.valueOf(bet) * 2 + "");


        }else {
            updateMinOddsOne();
            int count = 0;
            for (int i = 0; i < bean.size(); i++) {
                FootBallList.DataBean.MatchBean item = bean.get(i);
                count += item.getHomeType() + item.getVsType() + item.getAwayType();
            }
            bottomResultCountTv.setText(String.valueOf(count));
            bottomResultMoneyTv.setText(count * Integer.valueOf(bet) * 2 + "");

        }




    }

    /**
     * 单关奖金
     */
    private void updateMinOddsOne() {
        ArrayList<String>mlist=new ArrayList<>();
        for (FootBallList.DataBean.MatchBean s : bean) {
            List<FootBallList.DataBean.MatchBean.OddsBean> odds = s.getOdds();
            if(s.getHomeType()==1){
                mlist.add(odds.get(0).getOdds());
            }
            if(s.getVsType()==1){
                mlist.add(odds.get(1).getOdds());
            }
            if(s.getAwayType()==1){
                mlist.add(odds.get(2).getOdds());
            }

        }
        if(mlist.size()!=0){
            int i = Integer.parseInt(edMultiple.getText().toString());
            bottomResultTvBet.setText(i+"倍");
            String minStr = Collections.min(mlist);
            double min = Double.parseDouble(minStr)*2;
            double oneMax = getOneMax()*2;
            DecimalFormat decimalFormat =new DecimalFormat("#.00");
            String format = decimalFormat.format(min * i);
            String format1 = decimalFormat.format(oneMax * i);
            if(mlist.size()==1){
                predictMoney.setText(format);
            }else {
                predictMoney.setText(format+"~"+ format1);
            }

        }else {
            predictMoney.setText("0.00");

        }
    }

    /**
     * 单关最大奖金
     * @return
     */
    private double getOneMax() {
        int money=0;
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
        ArrayList<String> list = new ArrayList<>();
        for (FootBallList.DataBean.MatchBean s : bean) {
            List<FootBallList.DataBean.MatchBean.OddsBean> odds = s.getOdds();
            ArrayList<String>mlist=new ArrayList<>();
            if(s.getHomeType()==1){
                mlist.add(odds.get(0).getOdds());
            }
            if(s.getVsType()==1){
                mlist.add(odds.get(1).getOdds());
            }
            if(s.getAwayType()==1){
                mlist.add(odds.get(2).getOdds());
            }
            if(mlist.size()!=0){
                String min = Collections.min(mlist);
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
                    v = Double.parseDouble(list.get(0)) * Double.parseDouble(list.get(1))*2*i;
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


    private void initView() {
        bean = (List<FootBallList.DataBean.MatchBean>) getIntent().getSerializableExtra("bean");
        mAdapter = new FootChooseWinAdapter(bean);
        chooseRecycler.setLayoutManager(new LinearLayoutManager(this));
        chooseRecycler.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        ((SimpleItemAnimator) chooseRecycler.getItemAnimator()).setSupportsChangeAnimations(false);
        chooseRecycler.setAdapter(mAdapter);
        slideUp = new SlideUp.Builder(slideView)
                .withStartGravity(Gravity.BOTTOM)
                .withLoggingEnabled(true)
                .withAutoSlideDuration(1)
                .withStartState(SlideUp.State.HIDDEN)
                .build();
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
                mAdapter.getData().clear();
                mAdapter.notifyDataSetChanged();
                bean.clear();
                upDate();
                if (type%2!=0) {
                    footAll.setText("请至少选择2场比赛");
                } else  {
                    footAll.setText("请至少选择1场比赛");
                }
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
        for(int i=0;i<bean.size();i++){
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
        }
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
                switch (type){
                    case 1:
                        //队伍
                        messageBean.setStrand(substring);
                        messageBean.setPlay_rules(Api.FOOTBALL.FT001);
                        break;
                    case 2:
                        messageBean.setStrand("0");
                        messageBean.setPlay_rules(Api.FOOTBALL.FT001);
                        break;
                    case 3:
                        messageBean.setStrand(substring);
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
}
