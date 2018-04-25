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
import cn.com.futurelottery.inter.OnRequestDataListener;
import cn.com.futurelottery.model.FootBallList;
import cn.com.futurelottery.model.FootPay;
import cn.com.futurelottery.model.ScoreList;
import cn.com.futurelottery.ui.adapter.football.FootChooseScoreAdapter;
import cn.com.futurelottery.ui.adapter.football.FootChooseWinAdapter;
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
public class ScoreBetActivity extends BaseActivity {
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
    @BindView(R.id.bottom_result_tv_bet)
    TextView bottomResultTvBet;
    @BindView(R.id.predict_money)
    TextView predictMoney;
    private FootChooseScoreAdapter  mScoreAdapter;
    private List<ScoreList.DataBean.MatchBean> ScoreBeanList;
    private int type = 0;

    @Override
    public int getLayoutResource() {
        return R.layout.activity_score_bet;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        setListener();


    }
    /**
     * 公共
     */
    private void initView() {
        ScoreBeanList = (List<ScoreList.DataBean.MatchBean>) getIntent().getSerializableExtra("bean");
        mScoreAdapter = new FootChooseScoreAdapter(ScoreBeanList);
        chooseRecycler.setLayoutManager(new LinearLayoutManager(this));
        chooseRecycler.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        ((SimpleItemAnimator) chooseRecycler.getItemAnimator()).setSupportsChangeAnimations(false);
        chooseRecycler.setAdapter(mScoreAdapter);
        upDate();
    }

    private void setListener() {
        mScoreAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                ScoreList.DataBean.MatchBean matchBean = mScoreAdapter.getData().get(position);
                switch (view.getId()) {
                    case R.id.iv_delete:
                        mScoreAdapter.remove(position);
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
                mScoreAdapter.notifyItemChanged(position);

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
    }


    private void upDate() {
        String bet = edMultiple.getText().toString();

            updateMinOddsOne();
            int count = 0;
            for (int i = 0; i < bean.size(); i++) {
                FootBallList.DataBean.MatchBean item = bean.get(i);
                count += item.getHomeType() + item.getVsType() + item.getAwayType();
            }
            bottomResultCountTv.setText(String.valueOf(count));
            bottomResultMoneyTv.setText(count * Integer.valueOf(bet) * 2 + "");
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


    @OnClick({R.id.add_choose, R.id.choose_clear, R.id.layout_top_back, R.id.bottom_result_btn})
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
                    footAll.setText("请至少选择1场比赛");
                break;
            case R.id.bottom_result_btn:

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
                payMent(text);
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


    @Override
    protected void setTitle() {
        tvTitle.setText(R.string.football_title);
    }
}
