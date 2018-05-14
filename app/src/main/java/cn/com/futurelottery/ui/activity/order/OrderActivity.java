package cn.com.futurelottery.ui.activity.order;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
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
import cn.com.futurelottery.listener.OnRequestDataListener;
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
                Intent intent=new Intent();

                if (order.getName().startsWith("竞彩足球")){
                    intent.setClass(OrderActivity.this,FootBallOrderDetailActivity.class);
                    intent.putExtra("id",order.getOrder_id());
                    intent.putExtra("lotid",order.getLotid());
                    intent.putExtra("type",TextUtils.isEmpty(order.getChasing_id())?"普通投注":"追号投注");
                    intent.putExtra("ballName","竞彩足球");
                }else {
                    if (TextUtils.isEmpty(order.getChasing_id())){
                        intent.putExtra("id",order.getOrder_id());
                        intent.putExtra("type","普通投注");
                        intent.setClass(OrderActivity.this,BallOrderDetailActivity.class);
                    }else {
                        intent.putExtra("id",order.getChasing_id());
                        intent.putExtra("type","追号投注");
                        intent.setClass(OrderActivity.this,BallOrderDetailOneActivity.class);
                    }
                    if ("双色球".equals(order.getName())){
                        intent.putExtra("ballName","双色球");
                    }else if ("大乐透".equals(order.getName())){
                        intent.putExtra("ballName","大乐透");
                    }else if ("排列3".equals(order.getName())){
                        intent.putExtra("ballName","排列3");
                    }else if ("排列5".equals(order.getName())){
                        intent.putExtra("ballName","排列5");
                    }else if ("3D".equals(order.getName())){
                        intent.putExtra("ballName","3D");
                    }
                }

                startActivityForResult(intent,ORDER_DETAIL);
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
