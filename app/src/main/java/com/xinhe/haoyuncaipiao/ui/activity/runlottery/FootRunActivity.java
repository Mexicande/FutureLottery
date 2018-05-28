package com.xinhe.haoyuncaipiao.ui.activity.runlottery;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import com.xinhe.haoyuncaipiao.R;
import com.xinhe.haoyuncaipiao.base.Api;
import com.xinhe.haoyuncaipiao.base.ApiService;
import com.xinhe.haoyuncaipiao.base.BaseActivity;
import com.xinhe.haoyuncaipiao.listener.OnRequestDataListener;
import com.xinhe.haoyuncaipiao.model.FootRun;
import com.xinhe.haoyuncaipiao.ui.adapter.run.FootRunAdapter;
import com.xinhe.haoyuncaipiao.utils.ToastUtils;
import com.xinhe.haoyuncaipiao.view.progressdialog.KProgressHUD;

/**
 * @author apple
 *    足彩开奖详情
 */
public class FootRunActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_date)
    ImageView ivDate;
    @BindView(R.id.runRecycler)
    RecyclerView runRecycler;
    private FootRunAdapter mFootRunAdapter;
    private View notDataView;
    private ArrayList<FootRun.DataBean> res;
    private KProgressHUD hud;
    @Override
    public int getLayoutResource() {
        return R.layout.activity_foot_run;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        getDate(null);
    }

    private void initView() {
        res = new ArrayList<>();
        hud = KProgressHUD.create(this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setDimAmount(0.5f);
        mFootRunAdapter=new FootRunAdapter(null);
        runRecycler.setLayoutManager(new LinearLayoutManager(this));
        runRecycler.setAdapter(mFootRunAdapter);
        runRecycler.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        ((SimpleItemAnimator)runRecycler.getItemAnimator()).setSupportsChangeAnimations(false);
        notDataView = getLayoutInflater().inflate(R.layout.empty_layout, (ViewGroup) runRecycler.getParent(), false);
    }
    private void getDate(final String day) {
        hud.show();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("day",day);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ApiService.GET_SERVICE(Api.Open.FOOT_RUN, this, jsonObject, new OnRequestDataListener() {
            @Override
            public void requestSuccess(int code, JSONObject data) {
                Gson gson=new Gson();
                FootRun footRun = gson.fromJson(data.toString(), FootRun.class);
                List<FootRun.DataBean> footData = footRun.getData();
                if(footData.isEmpty()){
                    ToastUtils.showToast("该日期暂无比赛!");
                }
                if(res.size()>0){
                    res.clear();
                }
                for (int i = 0; i < footData.size(); i++) {

                    FootRun.DataBean dataBean = footData.get(i);
                    dataBean.setSubItems(footData.get(i).getMatchResult());
                    res.add(dataBean);
                }
                if(res.size()!=0){
                    mFootRunAdapter.getData().clear();
                    mFootRunAdapter.addData(res);
                    mFootRunAdapter.expandAll();
                }else {
                    mFootRunAdapter.setEmptyView(notDataView);
                }
                hud.dismiss();
            }

            @Override
            public void requestFailure(int code, String msg) {
                hud.dismiss();
                ToastUtils.showToast(msg);
            }
        });

    }


    @OnClick({R.id.layout_top_back, R.id.iv_date})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layout_top_back:
                finish();
                break;
            case R.id.iv_date:
                timePicker();
                break;
            default:
                break;
        }
    }
    private void timePicker() {

        TimePickerView pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                getDate(getTime(date));
            }
        })
                .setTitleBgColor(getResources().getColor(R.color.red_ball))
                //标题背景颜色 Night mode
                //.setTitleColor(getResources().getColor(R.color.white))
                .setSubmitColor(Color.WHITE)
                //确定按钮文字颜色
                .setCancelColor(Color.WHITE)
                //取消按钮文字颜色
                //标题文字颜色
                .setSubCalSize(17)
                //确定和取消文字大小
                //.isDialog(true)
                //是否显示为对话框样式
                .build();
        pvTime.show();
    }
    private String getTime(Date date) {//可根据需要自行截取数据显示
        Log.d("getTime()", "choice date millis: " + date.getTime());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }
}
