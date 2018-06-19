package com.xinhe.haoyuncaipiao.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xinhe.haoyuncaipiao.R;
import com.xinhe.haoyuncaipiao.base.Api;
import com.xinhe.haoyuncaipiao.base.ApiService;
import com.xinhe.haoyuncaipiao.base.Contacts;
import com.xinhe.haoyuncaipiao.listener.OnRequestDataListener;
import com.xinhe.haoyuncaipiao.model.MoneyDetail;
import com.xinhe.haoyuncaipiao.ui.adapter.MoneyDetailAdapter;
import com.xinhe.haoyuncaipiao.utils.SPUtil;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author apple
 *  余额明细
 */
public class MoneyDetailActivity extends AppCompatActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.monetRecycler)
    RecyclerView mRecyclerView;
    @BindView(R.id.smartRefresh)
    SmartRefreshLayout mSmartRefresh;
    TextView balance;
    TextView frostCash;
    private MoneyDetailAdapter mMoneyDetailAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money_detail);
        ButterKnife.bind(this);
        initView();
        getDate();
        setListener();
    }


    private void initView() {
        tvTitle.setText(R.string.money_detail);
        mMoneyDetailAdapter = new MoneyDetailAdapter(null);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mRecyclerView.setAdapter(mMoneyDetailAdapter);

        LinearLayout temp = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.money_detail_header, null);
        mMoneyDetailAdapter.addHeaderView(temp);
        balance = temp.findViewById(R.id.balance);
        frostCash = temp.findViewById(R.id.frost_cash);
    }

    private void setListener() {

        mSmartRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                getDate();
            }
        });
    }

    private void getDate() {
        JSONObject jsonObject = new JSONObject();
        String token = SPUtil.getString(this, Contacts.TOKEN);
        try {
            jsonObject.put(Contacts.TOKEN, token);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ApiService.GET_SERVICE(Api.user.LOG, this, jsonObject, new OnRequestDataListener() {
            @Override
            public void requestSuccess(int code, JSONObject data) {
                try {
                    String date = data.getString("data");
                    Gson gson = new Gson();
                    MoneyDetail moneyDetail = gson.fromJson(date, MoneyDetail.class);
                    balance.setText(moneyDetail.getMoney()+"元");
                    frostCash.setText(moneyDetail.getFrost_cash()+"元");
                    mMoneyDetailAdapter.setNewData(moneyDetail.getInfo());
                    if(mSmartRefresh.isRefreshing()){
                        mSmartRefresh.finishRefresh();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void requestFailure(int code, String msg) {
                if(mSmartRefresh.isRefreshing()){
                    mSmartRefresh.finishRefresh();
                }
            }
        });
    }
    @OnClick(R.id.layout_top_back)
    public void onViewClicked() {
        finish();
    }
}
