package cn.com.futurelottery.ui.activity.Football;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.futurelottery.R;
import cn.com.futurelottery.base.BaseActivity;
import cn.com.futurelottery.model.FootBallList;
import cn.com.futurelottery.ui.adapter.football.FootChooseWinAdapter;
import cn.com.futurelottery.utils.LogUtils;

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
    private FootChooseWinAdapter mAdapter;
    private List<FootBallList.DataBean.MatchBean> bean;
    private int type=0;
    @Override
    public int getLayoutResource() {
        return R.layout.activity_foot_ball_bet;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        setListener();
    }

    private void setListener() {
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.iv_delete:
                        mAdapter.remove(position);
                        if(bean.size()==0){
                            if(type==1){
                                footAll.setText("请至少选择2场比赛");
                            }else if(type==2){
                                footAll.setText("请至少选择1场比赛");
                            }
                        }
                    default:
                        break;
                }
            }
        });
    }

    private void initView() {
        type=getIntent().getIntExtra("type",0);
        bean = (List<FootBallList.DataBean.MatchBean>) getIntent().getSerializableExtra("bean");
        mAdapter = new FootChooseWinAdapter(bean);
        chooseRecycler.setLayoutManager(new LinearLayoutManager(this));
        chooseRecycler.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        ((SimpleItemAnimator)chooseRecycler.getItemAnimator()).setSupportsChangeAnimations(false);
        chooseRecycler.setAdapter(mAdapter);
    }

    @OnClick({R.id.add_choose, R.id.choose_clear, R.id.layout_top_back,R.id.layout_bet})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layout_top_back:
                break;
            case R.id.add_choose:
                break;
            case R.id.choose_clear:
                mAdapter.getData().clear();
                mAdapter.notifyDataSetChanged();
                bean.clear();
                normalLayout.setVisibility(View.GONE);
                emptyLayout.setVisibility(View.VISIBLE);
                if(type==1){
                    footAll.setText("请至少选择2场比赛");
                }else if(type==2){
                    footAll.setText("请至少选择1场比赛");
                }
                break;
            case R.id.layout_bet:

                break;
            default:
                break;
        }
    }

    @Override
    protected void setTitle() {
        tvTitle.setText(R.string.football_title);
    }
}
