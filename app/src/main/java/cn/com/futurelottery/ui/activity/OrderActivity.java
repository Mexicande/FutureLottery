package cn.com.futurelottery.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.JSONObject;
import java.lang.reflect.Type;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.OnClick;
import cn.com.futurelottery.R;
import cn.com.futurelottery.base.Api;
import cn.com.futurelottery.base.ApiService;
import cn.com.futurelottery.base.BaseActivity;
import cn.com.futurelottery.inter.OnRequestDataListener;
import cn.com.futurelottery.model.Order;
import cn.com.futurelottery.ui.adapter.OrderAdapter;
import cn.com.futurelottery.utils.ToastUtils;

public class OrderActivity extends BaseActivity {

    @BindView(R.id.layout_top_back)
    ImageView layoutTopBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.null_rl)
    RelativeLayout nullRl;
    private ArrayList<Order> orders=new ArrayList<>();
    private OrderAdapter adapter;
    private String url=Api.Order.whole;
    private final int ORDER_DETAIL=1001;

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
                if ("ssq".equals(order.getLotid())){
                    Intent intent=new Intent(OrderActivity.this,BallOrderDetailActivity.class);
                    intent.putExtra("id",order.getId());
                    intent.putExtra("type","0".equals(order.getIs_chasing())?"普通投注":"追号投注");
                    intent.putExtra("ballName","双色球");
                    startActivityForResult(intent,ORDER_DETAIL);
                }else if ("dlt".equals(order.getLotid())){
                    Intent intent=new Intent(OrderActivity.this,BallOrderDetailActivity.class);
                    intent.putExtra("id",order.getId());
                    intent.putExtra("type","0".equals(order.getIs_chasing())?"普通投注":"追号投注");
                    intent.putExtra("ballName","大乐透");
                    startActivityForResult(intent,ORDER_DETAIL);
                }
            }
        });
    }

    private void initView() {
        Intent intent=getIntent();
        String intentType = intent.getStringExtra("intentType");
        if ("1".equals(intentType)){
            url=Api.Order.whole;
            tvTitle.setText("全部订单");
        }else if ("2".equals(intentType)){
            url=Api.Order.chase;
            tvTitle.setText("追号订单");
        }else if ("3".equals(intentType)){
            url=Api.Order.winning;
            tvTitle.setText("中奖订单");
        }else if ("4".equals(intentType)){
            url=Api.Order.wait;
            tvTitle.setText("待开奖订单");
        }

        adapter=new OrderAdapter(orders);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private void getData() {
        orders.clear();
        JSONObject jsonObject = new JSONObject();
        ApiService.GET_SERVICE(url, this, jsonObject, new OnRequestDataListener() {
            @Override
            public void requestSuccess(int code, JSONObject data) {
                try {
                    Gson gson = new Gson();
                    Type bannerType = new TypeToken<ArrayList<Order>>(){}.getType();
                    orders.addAll((ArrayList<Order>) gson.fromJson(data.getJSONArray("data").toString(),bannerType));
                    refreshView();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void requestFailure(int code, String msg) {
                ToastUtils.showToast(msg);
            }
        });
    }

    //刷新界面
    private void refreshView() {
        if (orders.size()==0){
            recyclerView.setVisibility(View.GONE);
        }else {
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
        if (-1==resultCode){
            switch (requestCode){
                case ORDER_DETAIL:
                    getData();
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
