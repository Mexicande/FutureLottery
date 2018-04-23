package cn.com.futurelottery.ui.activity.Football;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.text.Editable;
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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.futurelottery.R;
import cn.com.futurelottery.base.BaseActivity;
import cn.com.futurelottery.model.FootBallList;
import cn.com.futurelottery.ui.adapter.football.FootChooseWinAdapter;
import cn.com.futurelottery.utils.CommonUtil;
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
    @BindView(R.id.choose_ball_ll)
    LinearLayout chooseBallLl;
    @BindView(R.id.ed_multiple)
    EditText edMultiple;
    @BindView(R.id.bottom_result_count_tv)
    TextView bottomResultCountTv;
    @BindView(R.id.bottom_result_money_tv)
    TextView bottomResultMoneyTv;
    @BindView(R.id.bottom_result_btn)
    Button bottomResultBtn;
    @BindView(R.id.normal_layout)
    LinearLayout normalLayout;
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
    private boolean falg = false;
    private SlideUp slideUp;
    private TRMenuAdapter mBottomTRMenuAdapter;
    private FootChooseWinAdapter mAdapter;
    private List<FootBallList.DataBean.MatchBean> bean;
    private List<MenuItem> mSrceenList;
    private int type = 0;

    @Override
    public int getLayoutResource() {
        return R.layout.activity_foot_ball_bet;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = getIntent().getIntExtra("type", 0);
        initView();
        if(type==1){
            initAllPass();
        }else {
            initOnePass();
        }
        upDate();
        setListener();

    }

    private void initOnePass() {

    }

    private void initAllPass() {
        ivArrow.setVisibility(View.VISIBLE);
        tvSelectBet.setText("投注方式(必选)");

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

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().isEmpty()) {
                    return;
                }
                if (Integer.valueOf(s.toString()) < 1) {
                    ToastUtils.showToast("最小支持1倍");
                    slideMultiple.setText("1");
                } else if (Integer.valueOf(s.toString()) > 50) {
                    slideMultiple.setText(String.valueOf(50));
                    ToastUtils.showToast("最大支持50倍");
                }
                bottomResultTvBet.setText(s + "倍");
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

    private void setListener() {
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                FootBallList.DataBean.MatchBean matchBean = mAdapter.getData().get(position);
                switch (view.getId()) {
                    case R.id.iv_delete:
                        mAdapter.remove(position);
                        if (bean.size() == 0) {
                            if (type == 1) {
                                footAll.setText("请至少选择2场比赛");
                            } else if (type == 2) {
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

            }

            @Override
            public void afterTextChanged(Editable s) {
                String bet = s.toString();
                bottomResultTvBet.setText(bet + "倍");
            }
        });

    }

    private void upDate() {



        String bet = edMultiple.getText().toString();
        if(type==1){
            int number = 0;
            for (int i = 0; i < mSrceenList.size(); i++) {
                if (mSrceenList.get(i).getIcon() == 1) {
                    String content = mSrceenList.get(i).getContent();
                    String substring = content.substring(0, 1);
                    Integer integer = Integer.valueOf(substring);
                    number += nuBet(integer);
                }
            }
            bottomResultCountTv.setText(number + " 注 ");
            bottomResultMoneyTv.setText(number * Integer.valueOf(bet) * 2 + "");
            bottomResultTvBet.setText(edMultiple.getText() + "倍");

            mSrceenList.clear();
            int size = bean.size();
            for (int i = 2; i <= size; i++) {
                if (i <= 8) {
                    MenuItem item = new MenuItem();
                    item.setContent(i + "串" + 1);
                    mSrceenList.add(item);
                } else {
                    return;
                }
            }
            mBottomTRMenuAdapter.notifyDataSetChanged();
        }else {
            int count=0;
            for (int i = 0; i < bean.size(); i++) {
                FootBallList.DataBean.MatchBean item = bean.get(i);
                count+=item.getHomeType() + item.getVsType() + item.getAwayType();
            }
            bottomResultCountTv.setText(count + " 注 ");
            bottomResultMoneyTv.setText(count * Integer.valueOf(bet) * 2 + "");
            bottomResultTvBet.setText(edMultiple.getText() + "倍");

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
                normalLayout.setVisibility(View.GONE);
                emptyLayout.setVisibility(View.VISIBLE);
                upDate();
                if (type == 1) {
                    footAll.setText("请至少选择2场比赛");
                } else if (type == 2) {
                    footAll.setText("请至少选择1场比赛");
                }
                break;
            case R.id.layout_bet:
                if (type == 1) {
                    slideUp.show();
                }
                break;
            case R.id.layout_slide_bet:
            case R.id.layoutGo:
                slideUp.hide();
                String s = slideMultiple.getText().toString();
                edMultiple.setText(s);
                getSelectBet();
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
        if (!falg) {
            if(type==1){
                slideUp.show();
                ToastUtils.showToast("请选择投注方式");
            }else {

            }
        }else {


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
            falg = true;
            String s = sb.toString();
            String substring = s.substring(0, s.length() - 1);
            tvSelectBet.setText(substring);
        }

    }

    @Override
    protected void setTitle() {
        tvTitle.setText(R.string.football_title);
    }
}
