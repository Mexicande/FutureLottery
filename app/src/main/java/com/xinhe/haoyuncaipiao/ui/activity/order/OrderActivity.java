package com.xinhe.haoyuncaipiao.ui.activity.order;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xinhe.haoyuncaipiao.R;
import com.xinhe.haoyuncaipiao.base.Api;
import com.xinhe.haoyuncaipiao.base.ApiService;
import com.xinhe.haoyuncaipiao.base.BaseActivity;
import com.xinhe.haoyuncaipiao.base.BaseApplication;
import com.xinhe.haoyuncaipiao.listener.OnRequestDataListener;
import com.xinhe.haoyuncaipiao.model.Order;
import com.xinhe.haoyuncaipiao.ui.adapter.OrderAdapter;
import com.xinhe.haoyuncaipiao.utils.DeviceUtil;
import com.xinhe.haoyuncaipiao.utils.SPUtil;
import com.xinhe.haoyuncaipiao.utils.ToastUtils;
import com.xinhe.haoyuncaipiao.view.progressdialog.KProgressHUD;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class OrderActivity extends BaseActivity {

    @BindView(R.id.layout_top_back)
    ImageView layoutTopBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.null_rl)
    RelativeLayout nullRl;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    private ArrayList<Order> orders = new ArrayList<>();
    private OrderAdapter adapter;
    private String url = Api.Order.whole;
    private final int ORDER_DETAIL = 1001;
    private KProgressHUD hud;

    @Override
    public int getLayoutResource() {
        return R.layout.activity_order;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        getData();
        setListener();
    }


    private void setListener() {
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Order order = orders.get(position);
                Intent intent = new Intent();

                if (3 == order.getType()) {
                    intent.putExtra("id", order.getOrder_id());
                    intent.putExtra("type", "合买");
                    intent.setClass(OrderActivity.this, ChippedOrderDetailActivity.class);
                } else if (order.getName().startsWith("竞彩足球")) {
                    intent.setClass(OrderActivity.this, FootBallOrderDetailActivity.class);
                    intent.putExtra("id", order.getOrder_id());
                    intent.putExtra("type", "普通投注");
                    intent.putExtra("ballName", "竞彩足球");
                } else {
                    if (1 == order.getType()) {
                        intent.putExtra("id", order.getOrder_id());
                        intent.putExtra("type", "普通投注");
                        intent.setClass(OrderActivity.this, BallOrderDetailActivity.class);
                    } else if (2 == order.getType()) {
                        intent.putExtra("id", order.getOrder_id());
                        intent.putExtra("type", "追号投注");
                        intent.setClass(OrderActivity.this, BallOrderDetailOneActivity.class);
                    }
                    if ("双色球".equals(order.getName())) {
                        intent.putExtra("ballName", "双色球");
                    } else if ("大乐透".equals(order.getName())) {
                        intent.putExtra("ballName", "大乐透");
                    } else if ("排列3".equals(order.getName())) {
                        intent.putExtra("ballName", "排列3");
                    } else if ("排列5".equals(order.getName())) {
                        intent.putExtra("ballName", "排列5");
                    } else if ("3D".equals(order.getName())) {
                        intent.putExtra("ballName", "3D");
                    }
                }
                intent.putExtra("lotid", order.getLotid());
                startActivityForResult(intent, ORDER_DETAIL);
            }
        });

        //刷新
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                if (!DeviceUtil.IsNetWork(OrderActivity.this)) {
                    ToastUtils.showToast("网络异常");
                    refreshLayout.finishRefresh();
                    return;
                }
                getData();
            }
        });
    }

    private void initView() {
        Intent intent = getIntent();
        String intentType = intent.getStringExtra("intentType");
        if ("1".equals(intentType)) {
            url = Api.Order.whole;
            tvTitle.setText("全部订单");
        } else if ("2".equals(intentType)) {
            url = Api.Order.chase;
            tvTitle.setText("追号订单");
        } else if ("3".equals(intentType)) {
            url = Api.Order.winning;
            tvTitle.setText("中奖订单");
        } else if ("4".equals(intentType)) {
            url = Api.Order.wait;
            tvTitle.setText("待开奖订单");
        }

        adapter = new OrderAdapter(orders);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private void getData() {
        hud = KProgressHUD.create(this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel(getString(R.string.progress_str_jiazai))
                .setDimAmount(0.5f)
                .show();
        JSONObject json = new JSONObject();
        try {
            json.put("type","2");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ApiService.GET_SERVICE(Api.user.share, BaseApplication.getInstance(), json, new OnRequestDataListener() {
            @Override
            public void requestSuccess(int code, JSONObject data) {

                try {
                    if (0==code){
                        JSONObject data1 = data.getJSONObject("data");
                        String url = data1.getString("url");
                        SPUtil.putString(OrderActivity.this,"qr",url);
                    }else {
                        ToastUtils.showToast("分享失败");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    ToastUtils.showToast("分享失败");
                }
            }

            @Override
            public void requestFailure(int code, String msg) {
                ToastUtils.showToast(msg);
            }
        });



        orders.clear();
        JSONObject jsonObject = new JSONObject();
        ApiService.GET_SERVICE(url, this, jsonObject, new OnRequestDataListener() {
            @Override
            public void requestSuccess(int code, JSONObject data) {
                try {
                    Gson gson = new Gson();
                    Type bannerType = new TypeToken<ArrayList<Order>>() {
                    }.getType();
                    orders.addAll((ArrayList<Order>) gson.fromJson(data.getJSONArray("data").toString(), bannerType));
                    refreshView();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                hud.dismiss();
                //停止刷新
                if (refreshLayout.isRefreshing()){
                    refreshLayout.finishRefresh();
                }
            }

            @Override
            public void requestFailure(int code, String msg) {
                ToastUtils.showToast(msg);
                hud.dismiss();
                //停止刷新
                if (refreshLayout.isRefreshing()){
                    refreshLayout.finishRefresh();
                }
            }
        });
    }

    //刷新界面
    private void refreshView() {
        if (orders.size() == 0) {
            recyclerView.setVisibility(View.GONE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
        }
        adapter.notifyDataSetChanged();
    }

    @OnClick({R.id.layout_top_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layout_top_back:
                finish();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (-1 == resultCode) {
            switch (requestCode) {
                case ORDER_DETAIL:
                    getData();
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
